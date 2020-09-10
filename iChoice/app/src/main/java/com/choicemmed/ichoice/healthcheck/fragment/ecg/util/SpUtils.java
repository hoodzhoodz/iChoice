package com.choicemmed.ichoice.healthcheck.fragment.ecg.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.choicemmed.ichoice.healthcheck.fragment.ecg.bean.AnResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static android.content.Context.MODE_PRIVATE;

public class SpUtils {
    private static SharedPreferences sp;

    public static void putBean(Context ctx, String name, String key, AnResult anresult) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(name, MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(anresult);
        editor.putString(key, json);
        editor.commit();
    }

    public static AnResult getBean(Context ctx, String name, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(name, MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = sp.getString(key, null);
        Type type = new TypeToken<AnResult>() {
        }.getType();
        AnResult anresult = gson.fromJson(json, type);
        return anresult;
    }
}
