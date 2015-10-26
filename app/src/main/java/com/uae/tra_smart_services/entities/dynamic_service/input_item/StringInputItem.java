package com.uae.tra_smart_services.entities.dynamic_service.input_item;

import android.support.annotation.NonNull;
import android.text.InputType;
import android.widget.EditText;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;

import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.EMAIL;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.NUMBER;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.STRING;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.URL;

/**
 * Created by mobimaks on 22.10.2015.
 */
public class StringInputItem extends BaseInputItem {

    private EditText etEditText;

    protected StringInputItem() {
        super();
    }

    @Override
    protected final void initViews() {
        super.initViews();
        etEditText = findView(R.id.etEdit_IIT);
        etEditText.setHint(getPlaceholder());
        if (mValidationRule != null) {
            processValidationRule();
        }
    }

    private void processValidationRule() {
        final int inputType;
        switch (mValidationRule) {
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
        return super.isDataValid() || !etEditText.getText().toString().isEmpty();
    }

    @NonNull
    @Override
    public JsonElement getJsonData() {
        final JsonObject object = new JsonObject();
        object.addProperty(getQueryName(), getArgsData());
        return object;
    }

    @NonNull
    @Override
    public final String getArgsData() {
        return etEditText.getText().toString();
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
