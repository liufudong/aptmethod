package com.liu.test;

import android.app.Activity;
import android.util.ArrayMap;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.util.HashMap;

public class BindsActivity {

    private static final String CLASS_NAME = "$BindsActivity";
    private static final HashMap<String, Object> injectMap = new HashMap<String, Object>();

    private static final String METHOD_NAME = "getTitles";

    public static String[] inject(Activity activity) {
        String className = activity.getClass().getName();
        try {
            Object inject = injectMap.get(className);
            if (inject == null) {
                //加载build生成的类
                Class<?> aClass = Class.forName(className + CLASS_NAME);
                inject = aClass.newInstance();
                injectMap.put(className, inject);
            }
            return (String[]) inject.getClass().getMethod(METHOD_NAME).invoke(inject);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
