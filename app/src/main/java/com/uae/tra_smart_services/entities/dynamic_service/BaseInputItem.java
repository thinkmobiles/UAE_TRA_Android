package com.uae.tra_smart_services.entities.dynamic_service;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonElement;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule;

import java.util.ArrayList;

/**
 * Created by mobimaks on 22.10.2015.
 */
@SuppressWarnings("unchecked")
public abstract class BaseInputItem implements Comparable<BaseInputItem> {

    protected View rootView;

    protected String mId;
    protected String mQueryName;
    protected String mDisplayName;
    protected String mPlaceholder;
    protected boolean isValidationRequired;
    protected int order;

    @ValidationRule
    protected String mValidationRule;

    public View getView(final LayoutInflater _inflater, final ViewGroup _container) {
        if (rootView == null) {
            rootView = _inflater.inflate(getLayoutRes(), _container, false);
            initViews();
            initListeners();
        }
        return rootView;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected void initViews() {
    }

    protected void initListeners() {
    }

    public boolean isDataValid() {
        return !isValidationRequired;
    }

    public final String getId() {
        return mId;
    }

    public final String getQueryName() {
        return mQueryName;
    }

    public final String getDisplayName() {
        return mDisplayName;
    }

    public final String getPlaceholder() {
        return mPlaceholder;
    }

    public final int getOrder() {
        return order;
    }

    @NonNull
    public abstract JsonElement getJsonData();

    @NonNull
    public abstract String getArgsData();

    @Override
    public int compareTo(@NonNull final BaseInputItem _another) {
        return Integer.valueOf(order).compareTo(_another.order);
    }

    protected <V extends View> V findView(@IdRes int _id) {
        return (V) rootView.findViewById(_id);
    }

    public static abstract class BaseBuilder<T extends BaseInputItem> {

        protected final T mInstance;

        public BaseBuilder() {
            mInstance = getInstance();
        }

        protected abstract T getInstance();

        public <E extends BaseBuilder<T>> E setId(String _id) {
            mInstance.mId = _id;
            return (E) this;
        }

        public <E extends BaseBuilder<T>> E setQueryName(String _queryName) {
            mInstance.mQueryName = _queryName;
            return (E) this;
        }

        public <E extends BaseBuilder<T>> E setIsValidationRequired(boolean _isValidationRequired) {
            mInstance.isValidationRequired = _isValidationRequired;
            return (E) this;
        }

        public <E extends BaseBuilder<T>> E setDisplayName(String _displayName) {
            mInstance.mDisplayName = _displayName;
            return (E) this;
        }

        public <E extends BaseBuilder<T>> E setPlaceholder(String _placeholder) {
            mInstance.mPlaceholder = _placeholder;
            return (E) this;
        }

        public <E extends BaseBuilder<T>> E setOrder(int _order) {
            mInstance.order = _order;
            return (E) this;
        }

        public <E extends BaseBuilder<T>> E setValidationRule(@ValidationRule String _validationRule) {
            mInstance.mValidationRule = _validationRule;
            return (E) this;
        }

        public T build() {
            return mInstance;
        }

    }

    public static abstract class List<T extends BaseInputItem> extends ArrayList<T> {
    }

}
