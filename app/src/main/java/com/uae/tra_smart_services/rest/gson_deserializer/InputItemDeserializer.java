package com.uae.tra_smart_services.rest.gson_deserializer;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem.BaseBuilder;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.ValidationRule;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.BOOLEAN_ITEM;
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
public final class InputItemDeserializer extends BaseDeserializer<BaseInputItem> {

    private static final String ID = "_id";
    private static final String TYPE = "inputType";
    private static final String NAME = "name";
    private static final String ORDER = "order";
    private static final String IS_REQUIRED = "required";
    private static final String VALIDATE_RULE = "validateAs";
    private static final String DISPLAY_NAME = "displayName";
    private static final String PLACEHOLDER = "placeHolder";
    private static final String DATA_SOURCE = "dataSource";

    @Override
    public BaseInputItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject main = (JsonObject) json;
        final String inputType = parseInputType(main.get(TYPE).getAsString());
        final InputItemBuilderFabric fabric = new InputItemBuilderFabric();
        final BaseBuilder builder = fabric.createBuilder(inputType);
        return builder
                .setId(main.get(ID).getAsString())
                .setQueryName(main.get(NAME).getAsString())
                .setOrder(main.get(ORDER).getAsInt())
                .setIsValidationRequired(main.get(IS_REQUIRED).getAsBoolean())
                .setDisplayName(getLocalisedText((JsonObject) main.get(DISPLAY_NAME)))
                .setPlaceholder(getLocalisedText((JsonObject) main.get(PLACEHOLDER)))
                .setValidationRule(parseValidationRule(main.get(VALIDATE_RULE).getAsString()))
                .setDataSource(parseStringList(main, DATA_SOURCE))
                .setInputItemType(inputType)
                .build();
    }

    @InputItemType
    private String parseInputType(final String _inputType) {
        if (BOOLEAN_ITEM.equalsIgnoreCase(_inputType)) {
            return BOOLEAN_ITEM;
        } else if (STRING_ITEM.equalsIgnoreCase(_inputType)) {
            return STRING_ITEM;
        } else if (TEXT_ITEM.equalsIgnoreCase(_inputType)) {
            return TEXT_ITEM;
        } else if (PICKER_ITEM.equalsIgnoreCase(_inputType)) {
            return PICKER_ITEM;
        }
        return BOOLEAN_ITEM;//default item (may be placeholder or sth like that)
    }

    @ValidationRule
    private String parseValidationRule(final String _rule) {
        if (STRING.equalsIgnoreCase(_rule)) {
            return STRING;
        } else if (NUMBER.equalsIgnoreCase(_rule)) {
            return NUMBER;
        } else if (EMAIL.equalsIgnoreCase(_rule)) {
            return EMAIL;
        } else if (URL.equalsIgnoreCase(_rule)) {
            return URL;
        } else {
            return NONE;
        }
    }

    @NonNull
    private ArrayList<String> parseStringList(final JsonObject _sourceObject, final String _listKey) {
        final ArrayList<String> data = new ArrayList<>();
        final JsonElement listElement = _sourceObject.get(_listKey);
        if (listElement != null && listElement.isJsonArray()) {
            final JsonArray elements = listElement.getAsJsonArray();
            for (final JsonElement element : elements) {
                data.add(element.getAsString());
            }
        }
        return data;
    }

}
