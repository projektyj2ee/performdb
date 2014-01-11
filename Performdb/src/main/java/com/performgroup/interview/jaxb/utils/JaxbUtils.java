package com.performgroup.interview.jaxb.utils;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Utils class which create Marshaller or Unmarshaller object
 * @author Marcin
 *
 */
public class JaxbUtils {
	private static final Map<String, Marshaller> marshallersMap = new HashMap<String, Marshaller>();
	private static final Map<String, Unmarshaller> unmarshallersMap = new HashMap<String, Unmarshaller>();

	public static Marshaller getMarshaller(String packageName) throws JAXBException {
		synchronized (JaxbUtils.class) {
			Marshaller marshaller = marshallersMap.get(packageName);
			if (marshaller == null) {
				JAXBContext jc = JAXBContext.newInstance(packageName);
				marshaller = jc.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshallersMap.put(packageName, marshaller);
				if (!unmarshallersMap.containsKey(packageName)) {
					Unmarshaller unmarshaller = jc.createUnmarshaller();
					unmarshallersMap.put(packageName, unmarshaller);
				}
			}
			return marshaller;
		}
	}

	public static Unmarshaller getUnmarshaller(String packageName) throws JAXBException {
		synchronized (JaxbUtils.class) {
			Unmarshaller unmarshaller = unmarshallersMap.get(packageName);
			if (unmarshaller == null) {
				JAXBContext jc = JAXBContext.newInstance(packageName);
				unmarshaller = jc.createUnmarshaller();
				unmarshallersMap.put(packageName, unmarshaller);
				if (!marshallersMap.containsKey(packageName)) {
					Marshaller marshaller = jc.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
					marshallersMap.put(packageName, marshaller);
				}
			}
			return unmarshaller;
		}
	}
}
