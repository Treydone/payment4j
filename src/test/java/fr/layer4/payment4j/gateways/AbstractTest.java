package fr.layer4.payment4j.gateways;

import static org.junit.Assert.fail;

import org.apache.commons.lang.NotImplementedException;

import com.google.common.base.Throwables;

public abstract class AbstractTest {

	public void catchException(
			Class<? extends Exception> expectedExceptionClass, Exception e) {
		if (e instanceof NotImplementedException) {
			throw (NotImplementedException) e;
		}
		if (expectedExceptionClass != null) {
			if (!e.getClass().getCanonicalName()
					.equals(expectedExceptionClass.getCanonicalName())) {
				fail("expected a " + expectedExceptionClass.getCanonicalName()
						+ " get a " + e.getClass().getCanonicalName() + ": "
						+ Throwables.getStackTraceAsString(e));
			} else {
				// Nice!
			}
		} else {
			fail("get a " + e.getClass().getCanonicalName() + ": "
					+ Throwables.getStackTraceAsString(e));
		}
	}

}
