package com.master.utils;

import java.util.UUID;

public class DdUtil {

    public final static String getDdId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
