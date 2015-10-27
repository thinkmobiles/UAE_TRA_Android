package com.uae.tra_smart_services.entities.dynamic_service;

import android.support.annotation.StringDef;

import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem.BaseBuilder;
import com.uae.tra_smart_services.entities.dynamic_service.input_item.BooleanInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.input_item.PickerInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.input_item.StringInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.input_item.TextInputItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.BOOLEAN_ITEM;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.FILE_ITEM;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.NUMBER_ITEM;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.PICKER_ITEM;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.STRING_ITEM;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.TEXT_ITEM;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.EMAIL;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.NONE;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.NUMBER;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.STRING;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule.URL;

/**
 * Created by mobimaks on 22.10.2015.
 */
public final class InputItemBuilderFabric {

    @StringDef({STRING_ITEM, TEXT_ITEM, NUMBER_ITEM, BOOLEAN_ITEM, FILE_ITEM, PICKER_ITEM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InputItemType {
        String STRING_ITEM = "string";
        String TEXT_ITEM = "text";
        String NUMBER_ITEM = "number";
        String BOOLEAN_ITEM = "boolean";
        String FILE_ITEM = "file";
        String PICKER_ITEM = "picker";
    }

    @StringDef({NONE, STRING, NUMBER, EMAIL, URL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ValidationRule {
        String NONE = "none";
        String STRING = "string";
        String NUMBER = "number";
        String EMAIL = "email";
        String URL = "url";
    }

    public BaseBuilder createBuilder(final @InputItemType String _itemType) {
        switch (_itemType) {
            case STRING_ITEM:
                return new StringInputItem.Builder();
            case TEXT_ITEM:
                return new TextInputItem.Builder();
            case PICKER_ITEM:
                return new PickerInputItem.Builder();
            case NUMBER_ITEM:
            case FILE_ITEM:
            case BOOLEAN_ITEM:
            default:
                return new BooleanInputItem.Builder();
        }
    }

}
