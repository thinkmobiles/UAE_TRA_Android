package com.uae.tra_smart_services.rest.gson_deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.DynamicService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import static com.uae.tra_smart_services.global.C.HttpMethod.GET;
import static com.uae.tra_smart_services.global.C.HttpMethod.POST;

/**
 * Created by mobimaks on 20.10.2015.
 */
public class DynamicServiceDeserializer extends BaseDeserializer<DynamicService> {

    private static final String ID = "_id";
    private static final String BASE_URL = "baseUrl";
    private static final String URL = "url";
    private static final String BUTTON_TEXT = "buttonTitle";
    private static final String INPUT_ITEMS = "inputItems";
    private static final String METHOD = "method";
    private static final String SERVICE_NAME = "serviceName";

    @Override
    public DynamicService deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final DynamicService model = new DynamicService();
        final JsonObject jsonObject = (JsonObject) json;
        model.id = jsonObject.get(ID).getAsString();
        model.url = jsonObject.get(BASE_URL).getAsString() + jsonObject.get(URL).getAsString();
        model.method = jsonObject.get(METHOD).getAsString().equalsIgnoreCase(GET) ? GET : POST;

        final Type listType = new TypeToken<ArrayList<BaseInputItem>>(){}.getType();
        model.inputItems = context.deserialize(jsonObject.get(INPUT_ITEMS), listType);
        Collections.sort(model.inputItems);

        model.buttonText = getLocalisedText((JsonObject)jsonObject.get(BUTTON_TEXT));
        model.serviceName = getLocalisedText((JsonObject)jsonObject.get(SERVICE_NAME));
        return model;
    }

}
