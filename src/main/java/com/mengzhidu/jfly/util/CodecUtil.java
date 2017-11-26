package com.mengzhidu.jfly.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by xinguimeng on 17/11/26.
 */
public class CodecUtil {

    public static String encodeUrl(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return target;
    }

    public static String decodeUrl(String source) {
        String target = null;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }

        return target;
    }
}
