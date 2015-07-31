package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.XmlRes;
import android.util.Log;

import com.uae.tra_smart_services.R;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Andrey Korneychuk on 28.07.15.
 *
 * Helper class collect all necessary methods
 */
public class H {
    /**
     * Returns the last non NULL variable
     * */
    public static <T> T coalesce(T ...items) {
        for(T i : items) if(i != null) return i;
        return null;
    }
    /**
     * Returns parsed resource xml
     * */
    public static Map<String, String> parseXmlToMap(Context context, @XmlRes int xmlRes){
        Map<String,String> map = null;
        String key = null, value = null;
        XmlResourceParser parser = context.getResources().getXml(xmlRes);

        try {
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("utils", "Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("map")) {
                        boolean isLinked = parser.getAttributeBooleanValue(null, "linked", false);

                        map = isLinked ? new LinkedHashMap<String, String>() : new HashMap<String, String>();
                    } else if (parser.getName().equals("entry")) {
                        key = parser.getAttributeValue(null, "key");

                        if (null == key) {
                            parser.close();
                            return null;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("entry")) {
                        map.put(key, value);
                        key = null;
                        value = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.getText();
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return map;
    }
    /**
     * Get resource id from string by type
     *
     * @param resName resource id in string representation
     * @param className resource type
     * @return resource id integer representation
     * */
    public static int getResIdFromString(String resName, Class<?> className) {
        try {
            Field idField = className.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
