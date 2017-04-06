package com.study.whutwf.doubanmovie.support;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by whutwf on 17-4-6.
 */

public class SerializableHashMap implements Serializable {

    public HashMap<String, String> mMap;

    public HashMap<String, String> getMap() {
        return mMap;
    }

    public void setMap(HashMap<String, String> map) {
        mMap = map;
    }
}
