package com.tms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemConfiguration {

	private final static Logger LOGGER = LoggerFactory.getLogger(SystemConfiguration.class);

	private static SystemConfiguration loadSystemCongiguration = null;

	private Properties props = null;

	private SystemConfiguration() {

		// load key value in props
		loadProperties();

	}

	public static SystemConfiguration getInstance() {
		if (loadSystemCongiguration == null) {
			synchronized (SystemConfiguration.class) {
				if (loadSystemCongiguration == null) {
					loadSystemCongiguration = new SystemConfiguration();
				}
			}
		}
		return loadSystemCongiguration;
	}

	private void loadProperties() {
		LOGGER.info("Reading started from input.properties");

		InputStream is = null;
		try {
			props = new Properties();
			is = this.getClass().getResourceAsStream("/input.properties");
			props.load(is);
		} catch (IOException e) {
			LOGGER.error("Error occured while reading properties file", e);
		}
		
		LOGGER.info("Properties has been loaded !!");

	}

	public String getPropsValue(final String key) {
		String value = "";
		if (key != null && !"".equalsIgnoreCase(key)) {
			value = props.getProperty(key);
		}
		return value;
	}

}
