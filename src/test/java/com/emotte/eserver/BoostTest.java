package com.emotte.eserver;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.junit.Test;

import com.emotte.eserver.boost.Bootstrap;
import com.emotte.eserver.core.context.Context;
import com.emotte.eserver.core.helper.LogHelper;
import com.emotte.eserver.thread.StartThread;
import com.emotte.eserver.thread.StopThread;

public class BoostTest {
	@Test
	public void test() {
		InputStream is = System.in;
		byte[] bytes = new byte[1024];
		StringBuffer sb = new StringBuffer();
		StartThread start = new StartThread();
		try {
			while(is.read(bytes) > 0) {
				String s = new String(bytes);
				System.out.println(s);
				switch(COMMAND.valueOf(s.trim().toUpperCase())) {
				case START : start.start(); break;
				case STOP : {
					sb.append("BYE!");
					new StopThread(start).start();
					return;
				}
				case CALLA : callA(); break;
				case CALLB : callB(); break;
				case INVOKE : invoke(); break;
				default : return;
				}
				sb.append(s);
			}
		} catch (Exception e) {
			LogHelper.error(Bootstrap.class, e.getMessage(), e);
		} finally {
			LogHelper.info(Bootstrap.class, sb.toString());
		}
	}

	public enum COMMAND {
		START, STOP, CALLA, CALLB, INVOKE;
	}
	
	private void callA () {
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName("com.powergrass.TestA");
			//clazz.newInstance().
			@SuppressWarnings("unchecked")
			Method method = clazz.getDeclaredMethod("display", String.class);
			Object result = method.invoke(clazz.newInstance(), "cj");
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void callB () {
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName("com.powergrass.test.TestB");
			//clazz.newInstance().
			@SuppressWarnings("unchecked")
			Method method = clazz.getDeclaredMethod("display", String.class);
			Object result = method.invoke(clazz.newInstance(), "cj");
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void invoke () {
		try {
			System.out.println(Context.invoke("serviceA", "test", "cj", null, 13));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
