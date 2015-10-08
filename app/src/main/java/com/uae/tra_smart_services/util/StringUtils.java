package com.uae.tra_smart_services.util;

/**
 * Created by Vitaliy on 08/10/2015.
 */
public final class StringUtils {

    private StringUtils() {}

    public static boolean isAllLetters(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}
