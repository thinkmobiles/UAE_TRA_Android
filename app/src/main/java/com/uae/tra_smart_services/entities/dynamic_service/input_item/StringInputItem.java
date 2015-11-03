package com.uae.tra_smart_services.entities.dynamic_service.input_item;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonPrimitive;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;

import org.apache.commons.lang3.math.NumberUtils;

import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.EMAIL;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.NUMBER;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.STRING;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.URL;

/**
 * Created by mobimaks on 22.10.2015.
 */
public class StringInputItem extends BaseInputItem {

    private static final String KEY_PREFIX = StringInputItem.class.getSimpleName();
    private static final String KEY_TEXT = KEY_PREFIX + "TEXT";

    protected EditText etEditText;
    protected TextView tvDisplayName;

    private String mText;

    protected StringInputItem() {
    }

    @CallSuper
    @Override
    protected void initViews() {
        super.initViews();
        tvDisplayName = findView(R.id.tvDisplayName_IIS);
        tvDisplayName.setText(getDisplayName());

        etEditText = findView(R.id.etEdit_IIT);
        etEditText.setHint(getPlaceholder());
        if (!TextUtils.isEmpty(mText)) {
            etEditText.setText(mText);
        }

        if (getValidationRule() != null) {
            processValidationRule();
        }
    }

    protected void processValidationRule() {
        final int inputType;
        switch (getValidationRule()) {
            case STRING:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
                break;
            case EMAIL:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                break;
            case NUMBER:
                inputType = InputType.TYPE_CLASS_NUMBER;
                break;
            case URL:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI;
                break;
            default:
                inputType = InputType.TYPE_CLASS_TEXT;
                break;
        }
        etEditText.setRawInputType(inputType);
    }

    @Override
    public boolean isDataValid() {
        final String text = etEditText.getText().toString();
        if (isRequired) {
            return validateData(text);
        } else {
            return text.isEmpty() || validateData(text);
        }
    }

    private boolean validateData(final String text) {
        switch (getValidationRule()) {
            case EMAIL:
                return Patterns.EMAIL_ADDRESS.matcher(text).matches();
            case NUMBER:
                return NumberUtils.isNumber(text);
            case URL:
                return Patterns.WEB_URL.matcher(text).matches();
            case STRING:
            default:
                return !text.isEmpty();
        }
    }

    @Nullable
    @Override
    public JsonPrimitive getJsonValue() {
        return new JsonPrimitive(etEditText.getText().toString());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle _outState) {
        _outState.putString(KEY_TEXT, etEditText.getText().toString());
        super.onSaveInstanceState(_outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle _savedInstanceState) {
        super.onRestoreInstanceState(_savedInstanceState);
        mText = _savedInstanceState.getString(KEY_TEXT);
    }

    protected final EditText getEditText() {
        return etEditText;
    }

    public static final class Builder extends BaseBuilder {

        @Override
        protected BaseInputItem getInstance() {
            return new StringInputItem();
        }

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.input_item_string;
    }

}
