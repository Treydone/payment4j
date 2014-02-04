package com.app55.message;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.error.ApiException;
import com.app55.error.InvalidSignatureException;
import com.app55.error.RequestException;
import com.app55.transport.HttpListener;
import com.app55.transport.HttpResponse;
import com.app55.util.EncodeUtil;
import com.app55.util.JsonUtil;

public abstract class Request<T extends Response> extends Message
{
	private Class<T>	responseClass;

	Request(Class<T> responseClass)
	{
		this.responseClass = responseClass;
	}

	@JsonIgnore
	public abstract String getHttpEndpoint();

	@JsonIgnore
	public String getHttpMethod()
	{
		return "GET";
	}

	@Override
	@JsonIgnore
	@JsonProperty(value = "sig")
	public String getSignature()
	{
		return toSignature(true);
	}

	@Override
	@JsonProperty(value = "ts")
	public String getTimestamp()
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public T send() throws ApiException
	{
		try
		{
			String qs = fetchQueryString();
			byte[] data = fetchData(qs);
			String url = fetchUrl(qs);
			String authString = fetchAuthString();
			
			HttpResponse response = getGateway().getHttpAdapter().onSendRequest(data, url, getHttpMethod(), authString);
			return processRequest(response);
		}
		catch (ApiException a)
		{
			// This just gets rethrown
			throw a;
		}
		catch (Exception e)
		{
			throw ApiException.createException(e.getMessage(), -1L);
		}
	}
	
	public T send(Map<String, String> headers) throws ApiException
	{
		
		try
		{
			String qs = fetchQueryString();
			byte[] data = fetchData(qs);
			String url = fetchUrl("");
			String authString = fetchAuthString();
			
			HttpResponse response = getGateway().getHttpAdapter().onSendRequest(data, url, getHttpMethod(), authString, headers);
			return processRequest(response);
		}
		catch (ApiException a)
		{
			// This just gets rethrown
			throw a;
		}
		catch (Exception e)
		{
			throw ApiException.createException(e.getMessage(), -1L);
		}
	}

	public void send(final ResponseListener<T> responseListener)
	{
		try
		{
			String qs = fetchQueryString();
			byte[] data = fetchData(qs);
			String url = fetchUrl(qs);
			String authString = fetchAuthString();
			
			getGateway().getHttpAdapter().onSendRequest(data, url, getHttpMethod(), authString, new HttpListener() {

				public void onResponse(HttpResponse response)
				{
					T r = processRequest(response);
					responseListener.onResponse(r);
				}

				public void onError(Exception e)
				{
					responseListener.onError(processException(e));
				}
			});
		}
		catch (Exception e)
		{
			responseListener.onError(processException(e));
		}
	}

	@SuppressWarnings("unchecked")
	private T processRequest(HttpResponse response)
	{
		// TODO Removing this bullshit since http status is not 200-OK when you have an error...
//		if (response.getStatusCode() != 200)
//			throw new RequestException("Http Error " + response.getStatusCode(), (long) response.getStatusCode(), null);

		Map<String, Object> ht = JsonUtil.map(response.getContent());
		if (ht.containsKey("error"))
			throw ApiException.createException((Map<String, Object>) ht.get("error"));

		T r = JsonUtil.object(response.getContent(), responseClass);
		r.populate(ht);
		r.setGateway(getGateway());

		if (!r.isValidSignature())
			throw new InvalidSignatureException();
		
		return r;
	}

	private ApiException processException(Exception e)
	{
		ApiException apiEx = null;
		if (e instanceof ApiException)
			apiEx = (ApiException) e;
		else
			apiEx = ApiException.createException(e.getMessage(), -1L);
		return apiEx;
	}

	private String fetchAuthString()
	{
		String authString = EncodeUtil.createBasicAuthString(getGateway().getApiKey(), getGateway().getApiSecret());
		return authString;
	}

	private String fetchUrl(String qs)
	{
		return isBodySent() ? getHttpEndpoint() : getHttpEndpoint() + "?" + qs;
	}

	private byte[] fetchData(String qs) throws UnsupportedEncodingException
	{
		return isBodySent() ? qs.getBytes("UTF-8") : null;
	}

	private String fetchQueryString()
	{
		Map<String, Boolean> exclude = new HashMap<String, Boolean>();
		exclude.put("sig", true);
		exclude.put("ts", true);
		exclude.put("httpEndpoint", true);
		exclude.put("redirectUrl", true);
		String qs = toFormData(false, exclude);
		return qs;
	}

	private boolean isBodySent()
	{
		return !("GET".equals(getHttpMethod()) || "DELETE".equals(getHttpMethod()));
	}
}