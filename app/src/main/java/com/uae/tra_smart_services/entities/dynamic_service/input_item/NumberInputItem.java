package com.uae.tra_smart_services.entities.dynamic_service.input_item;

import android.support.annotation.Nullable;

import com.google.gson.JsonPrimitive;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule;

/**
 * Created by mobimaks on 26.10.2015.
 */
public final class NumberInputItem extends StringInputItem {

    protected NumberInputItem() {
    }

    @Override
    protected void initViews() {
        setValidationRule(ValidationRule.NUMBER);
        super.initViews();
    }

    @Override
    public boolean isDataValid() {
        return !isRequired();
    }

    @Nullable
    @Override
    public JsonPrimitive getJsonValue() {
        return super.getJsonValue();
    }

    public static final class Builder extends BaseBuilder {

        @Override
        protected NumberInputItem getInstance() {
            return new NumberInputItem();
        }

    }

}
