package com.mengzhidu.jfly.web;

import java.util.Map;

/**
 * Created by xinguimeng on 17/11/26.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
