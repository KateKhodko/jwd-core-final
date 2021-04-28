package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.PropertyKey;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();

    private PropertyReaderUtil() {

    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        final String propertiesFileName = "application.properties";

        try (InputStream iStream = PropertyReaderUtil.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (iStream == null) {
                throw new IOException("File not found");
            }
            properties.load(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(PropertyKey propertyKey) {
        return properties.getProperty(propertyKey.getKey());
    }

    public static Integer getIntegerProperty(PropertyKey propertyKey) {
        Integer intProperty;
        try {
            intProperty = Integer.valueOf(properties.getProperty(propertyKey.getKey()));
        } catch (NumberFormatException e) {
            intProperty = null;
        }
        return intProperty;
    }
}
