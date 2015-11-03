package com.uae.tra_smart_services.rest.gson_deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemsPage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mobimaks on 02.11.2015.
 */
public final class InputItemsPageDeserializer extends BaseDeserializer<InputItemsPage> {

    private static final String NUMBER = "number";
    private static final String ID = "_id";
    private static final String INPUT_ITEMS = "inputItems";

    @Override
    public InputItemsPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject mainObject = json.getAsJsonObject();
        final InputItemsPage inputItemsPage = new InputItemsPage();
        inputItemsPage.number = mainObject.get(NUMBER).getAsInt();
        inputItemsPage.id = mainObject.get(ID).getAsString();

        final Type listType = new TypeToken<ArrayList<BaseInputItem>>() {}.getType();
        inputItemsPage.inputItems = context.deserialize(mainObject.get(INPUT_ITEMS), listType);
        Collections.sort(inputItemsPage.inputItems);
        return inputItemsPage;
    }

}
