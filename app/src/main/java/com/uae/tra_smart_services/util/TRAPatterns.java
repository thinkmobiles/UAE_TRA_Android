package com.uae.tra_smart_services.util;

import java.util.regex.Pattern;

/**
 * Created by Mikazme on 23/09/2015.
 */
public class TRAPatterns {
    public static final Pattern EMIRATES_ID = Pattern.compile("^[0-9]{3}[-][0-9]{4}[-][0-9]{7}[-][0-9]$");

    public static final int MIN_PHONE_NUMBER_LENGTH = 4;
}
