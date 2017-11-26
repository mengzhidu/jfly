package com.mengzhidu.jfly.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 这里一定要返回封装类型或者基础类型吗
 * 或许，这是一个值得考虑的问题
 * 而且，由于有了null的存在，有必要指定默认值吗
 * Created by xinguimeng on 17/11/21.
 */
public class PropsUtil {
    //private static final Logger

    public static Properties loadProps(String filename) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (null == is) {
                throw  new FileNotFoundException(filename + " file is not found");
            }

            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    public static  String getString(Properties properties, String key, String defaultValue) {
        String result = defaultValue;
        if (properties.containsKey(key)) {
            result = properties.getProperty(key);
        }
        return result;
    }

    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    public static int getInt(Properties properties, String key, int defaultValue) {
        int result = defaultValue;
        if (properties.containsKey(key)) {
            result = Integer.parseInt(properties.getProperty(key));
        }
        return result;
    }

    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    public static  boolean getBoolean(Properties properties, String key, boolean defaultValue) {
        boolean result = defaultValue;
        if (properties.containsKey(key)) {
            if ("true".equals(properties.getProperty(key))) {
                return true;
            }

            if ("false".equals(properties.getProperty(key))) {
                return false;
            }
        }

        return result;
    }
}
