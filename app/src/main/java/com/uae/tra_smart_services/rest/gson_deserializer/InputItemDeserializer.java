package com.uae.tra_smart_services.rest.gson_deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem.BaseBuilder;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType;

import java.lang.reflect.Type;

import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.BOOLEAN_ITEM;
import static com.uae.tra_smart_services.entities.dynamic_service.InputItemBuilderFabric.InputItemType.STRING_ITEM;

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
                .build();
    }

    @InputItemType
    private String parseInputType(final String _inputType) {
        if (BOOLEAN_ITEM.equalsIgnoreCase(_inputType)) {
            return BOOLEAN_ITEM;
        } else if (STRING_ITEM.equalsIgnoreCase(_inputType)) {
            return STRING_ITEM;
        }
        return BOOLEAN_ITEM;//default item (may be placeholder or sth like that)
    }

}
