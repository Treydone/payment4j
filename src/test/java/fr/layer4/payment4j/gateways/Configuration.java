package fr.layer4.payment4j.gateways;

import java.io.IOException;
import java.util.Properties;

import com.google.common.base.Throwables;

public abstract class Configuration {

	private static Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(Configuration.class.getClassLoader()
					.getResourceAsStream("accounts.properties"));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
