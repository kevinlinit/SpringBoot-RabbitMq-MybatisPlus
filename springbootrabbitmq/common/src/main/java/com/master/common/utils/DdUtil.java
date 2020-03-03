package com.master.common.utils;

import java.util.UUID;

public class DdUtil {

    public final static String getDdId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
