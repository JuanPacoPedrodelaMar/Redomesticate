package com.evandev.redomesticate.util;

import com.evandev.redomesticate.Constants;

public class LangUtil {
    public static String text(String name) {
        return "text." + Constants.MOD_ID + "." + name;
    }

    public static String conf(String name) {
        return Constants.MOD_ID + ".configuration." + name;
    }

    public static String gui(String number) {
        return "gui." + Constants.MOD_ID + "." + number;
    }

    public static String event(String name) {
        return "event." + Constants.MOD_ID + "." + name;
    }

    public static String effect(String name) {
        return "effect." + Constants.MOD_ID + "." + name;
    }
}
