package com.uae.tra_smart_services.entities.dynamic_service;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem.BaseBuilder;
import com.uae.tra_smart_services.global.C.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mobimaks on 22.10.2015.
 */
public class DynamicService {

    //region Save keys
    private static final String KEY_PREFIX = DynamicService.class.getSimpleName();
    private static final String KEY_ID = KEY_PREFIX + "_ID";
    private static final String KEY_URL = KEY_PREFIX + "_URL";
    private static final String KEY_BUTTON_TEXT = KEY_PREFIX + "_BUTTON_TEXT";
    private static final String KEY_SERVICE_NAME = KEY_PREFIX + "_SERVICE_NAME";
    private static final String KEY_METHOD = KEY_PREFIX + "_METHOD";
    private static final String KEY_BODY_ARGS = KEY_PREFIX + "_BODY_ARGS";
    private static final String KEY_QUERY_ARGS = KEY_PREFIX + "_QUERY_ARGS";
    private static final String KEY_INPUT_ITEM = KEY_PREFIX + "_INPUT_ITEM";
    private static final String KEY_INPUT_ITEMS_COUNT = KEY_PREFIX + "_INPUT_ITEMS_COUNT";
    //endregion

    @Expose
    public String id;

    @Expose
    public String url;

    @Expose
    public List<BaseInputItem> inputItems;

    @Expose
    public String buttonText;

    @Expose
    public String serviceName;

    @Expose
    @HttpMethod
    public String method;

    @Expose
    public Set<String> bodyArgs;

    @Expose
    public Set<String> queryArgs;

    public final void onRestoreInstanceState(@NonNull final Bundle _savedInstanceState) {
        id = _savedInstanceState.getString(KEY_ID);
        url = _savedInstanceState.getString(KEY_URL);
        buttonText = _savedInstanceState.getString(KEY_BUTTON_TEXT);
        serviceName = _savedInstanceState.getString(KEY_SERVICE_NAME);
        method = _savedInstanceState.getString(KEY_METHOD);

        final ArrayList<String> bodyArgsList = _savedInstanceState.getStringArrayList(KEY_BODY_ARGS);
        bodyArgs = (bodyArgsList == null) ? new HashSet<String>() : new HashSet<>(bodyArgsList);

        final ArrayList<String> queryArgsList = _savedInstanceState.getStringArrayList(KEY_QUERY_ARGS);
        queryArgs = (queryArgsList == null) ? new HashSet<String>() : new HashSet<>(queryArgsList);

        inputItems = new ArrayList<>();
        final int itemsCount = _savedInstanceState.getInt(KEY_INPUT_ITEMS_COUNT);
        final InputItemBuilderFabric fabric = new InputItemBuilderFabric();
        for (int i = 0; i < itemsCount; i++) {
            final Bundle savedItemState = _savedInstanceState.getBundle(KEY_INPUT_ITEM + i);
            if (savedItemState == null) {
                continue;
            }
            final String itemType = BaseInputItem.getRestoredInputItemType(savedItemState);
            final BaseBuilder builder = fabric.createBuilder(itemType);
            final BaseInputItem inputItem = builder.build();
            inputItem.onRestoreInstanceState(savedItemState);
            inputItems.add(inputItem);
        }
    }

    public final void onSaveInstanceState(@NonNull final Bundle _outState) {
        _outState.putString(KEY_ID, id);
        _outState.putString(KEY_URL, url);
        _outState.putString(KEY_BUTTON_TEXT, buttonText);
        _outState.putString(KEY_SERVICE_NAME, serviceName);
        _outState.putString(KEY_METHOD, method);
        _outState.putStringArrayList(KEY_BODY_ARGS, new ArrayList<>(bodyArgs));
        _outState.putStringArrayList(KEY_QUERY_ARGS, new ArrayList<>(queryArgs));

        final int itemsCount = inputItems.size();
        _outState.putInt(KEY_INPUT_ITEMS_COUNT, itemsCount);
        for (int i = 0; i < itemsCount; i++) {
            final BaseInputItem inputItem = inputItems.get(i);
            final Bundle args = new Bundle();
            inputItem.onSaveInstanceState(args);
            _outState.putBundle(KEY_INPUT_ITEM + i, args);
        }
    }

    public boolean isDataValid() {
        for (final BaseInputItem inputItem : inputItems) {
            if (!inputItem.isDataValid()) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    public Map<String, String> getQueryMap() {
        final Map<String, String> map = new HashMap<>();
        for (final BaseInputItem inputItem : inputItems) {
            if (queryArgs.contains(inputItem.getQueryName())) {
                map.put(inputItem.getQueryName(), inputItem.getArgsData());
            }
        }
        return map;
    }

    @Nullable
    public JsonObject getJsonData() {
        JsonObject object = null;
        for (final BaseInputItem inputItem : inputItems) {
            if (bodyArgs.contains(inputItem.getQueryName())) {
                if (object == null) {
                    object = new JsonObject();
                }
                object.add(inputItem.getQueryName(), inputItem.getJsonValue());
            }
        }
        return object;
    }

}
