package com.company;

import java.util.Map;

/**
 * @author Андрей
 * @since 8 Apr 2016
 */
public class Util {

    public static long getLong(Map<String,Object> keyVal,String name) {
        return ((Number) keyVal.get(name)).longValue();
    }
}
