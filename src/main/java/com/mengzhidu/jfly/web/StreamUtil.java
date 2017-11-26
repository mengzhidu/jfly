package com.mengzhidu.jfly.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by xinguimeng on 17/11/26.
 */
public class StreamUtil {
    public static String getString(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {

        }

        return builder.toString();
    }
}
