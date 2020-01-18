package com.springboot.issues1.Utility;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	private static ReflectionUtil refObj = null;

	private ReflectionUtil() {

	}

	private static HashMap<String, String> objectBeanMap = new HashMap<String, String>() {
		{
			put("Planetary", "com.springboot.issues1.Beans.Planetary");
		}
	};

	private static HashMap<String, HashMap> objGetterPropMap = new HashMap<String, HashMap>();
	private static HashMap<String, HashMap> objSetterPropMap = new HashMap<String, HashMap>();

	public static ReflectionUtil getInstance() {
		logger.info("****************Inside Get Instance Method*************************");
		if (refObj == null) {
			refObj = new ReflectionUtil();
			setUpMethod();
		}
		return refObj;
	}

	private static void setUpMethod() {
		logger.info("**************Inside SetUpMethod() ***************************");
		for (Iterator iterator = objectBeanMap.keySet().iterator(); iterator.hasNext();) {
			String propName = (String) iterator.next();
			HashMap<String, Method> propGetMethodsMap = new HashMap<String, Method>();
			HashMap<String, Method> propSetmethodsMap = new HashMap<String, Method>();
			try {

				Class<?> cls = Class.forName(objectBeanMap.get(propName));
				for (PropertyDescriptor pd : Introspector.getBeanInfo(cls).getPropertyDescriptors()) {
					if (!"clsss".equals(pd.getName())) {
						if (pd.getReadMethod() != null) {
							propGetMethodsMap.put(pd.getName(), pd.getReadMethod());
						}
						if (pd.getWriteMethod() != null) {
							propSetmethodsMap.put(pd.getName(), pd.getWriteMethod());
						}
					}

				}
			} catch (ClassNotFoundException | IntrospectionException ex) {
				logger.info(ex.getMessage());
			}
			objGetterPropMap.put(propName, propGetMethodsMap);
			objSetterPropMap.put(propName, propSetmethodsMap);
		}
	}

	public HashMap<String, HashMap> getObjGetterPropMap() {
		return objGetterPropMap;
	}

	public HashMap<String, HashMap> getObjSetterPropMap() {
		return objSetterPropMap;
	}

	public Method getSetterMethod(String objectName, String propName) {
		HashMap propMethods = getObjSetterPropMap().get(objectName);
		return (Method) propMethods.get(propName);

	}

	public Method getGetterMethod(String objectName, String propName) {
		HashMap propMethods = getObjGetterPropMap().get(objectName);
		return (Method) propMethods.get(propName);
	}

}
