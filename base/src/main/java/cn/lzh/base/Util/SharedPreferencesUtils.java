package cn.lzh.base.Util;

import android.content.Context;
import android.content.SharedPreferences;

import cn.lzh.base.app.ApplicationContext;

/**
 * Created by LZH On 2019/3/21
 */
public class SharedPreferencesUtils {

    public static String getString(String name, String key) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static Long getLong(String name, String key) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    public static int getInt(String name, String key) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static float getFloat(String name, String key) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    public static boolean getBoolean(String name, String key) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void putString(String name, String key, String value) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public static void putInt(String name, String key, int value) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
        editor.commit();
    }

    public static void putBoolean(String name, String key, boolean value) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        editor.commit();
    }

    public static void putFloat(String name, String key, float value) {
        SharedPreferences preferences = ApplicationContext.application.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
        editor.commit();
    }

}
