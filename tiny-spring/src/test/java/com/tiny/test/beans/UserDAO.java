package com.tiny.test.beans;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("001", "one");
        hashMap.put("002", "two");
        hashMap.put("003", "three");
    }

    public String queryUserName(String uid) {
        return hashMap.get(uid);
    }
}
