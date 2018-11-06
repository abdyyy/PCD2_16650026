package com.example.hopefoundation.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

    private static final String TAG = JSONUtil.class.getSimpleName();

    public static JSONObject getJSONObject(String key) {
        try {
            JSONObject j = new JSONObject(key);
            return j;
        } catch (JSONException e) {
            return null;
        }
    }
}
