package business.base;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class BaseTest<TBusiness> {

	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		Class<TBusiness> type = (Class<TBusiness>) superClass.getActualTypeArguments()[0];

		Constructor<TBusiness> constructor = type.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		try {
			constructor.newInstance();
		} catch (InvocationTargetException ex) {
			assertEquals(UnsupportedOperationException.class, ex.getTargetException().getClass());
			return;
		}
		Assert.fail();
	}

}
