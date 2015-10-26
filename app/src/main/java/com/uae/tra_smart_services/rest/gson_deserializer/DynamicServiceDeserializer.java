package com.uae.tra_smart_services.rest.gson_deserializer;

import android.net.Uri;

import com.google.gson.JsonArray;
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
import java.util.HashSet;
import java.util.Set;

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
    private static final String PARAMS = "params";
    private static final String BODY_ARGS = "body";
    private static final String QUERY_ARGS = "query";

    @Override
    public DynamicService deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final DynamicService model = new DynamicService();
        final JsonObject jsonObject = (JsonObject) json;
        model.id = jsonObject.get(ID).getAsString();
        model.method = jsonObject.get(METHOD).getAsString().equalsIgnoreCase(GET) ? GET : POST;
        model.url = parseUrl(jsonObject);
        final Type listType = new TypeToken<ArrayList<BaseInputItem>>() {}.getType();
        model.inputItems = context.deserialize(jsonObject.get(INPUT_ITEMS), listType);
        Collections.sort(model.inputItems);

        model.buttonText = getLocalisedText((JsonObject) jsonObject.get(BUTTON_TEXT));
        model.serviceName = getLocalisedText((JsonObject) jsonObject.get(SERVICE_NAME));

        final JsonObject params = (JsonObject) jsonObject.get(PARAMS);
        model.bodyArgs = getArgsSet(params, BODY_ARGS);
        model.queryArgs = getArgsSet(params, QUERY_ARGS);
        return model;
    }

    private Set<String> getArgsSet(JsonObject _params, String _argsKey) {
        final Set<String> args = new HashSet<>();
        if (_params.get(_argsKey) != null) {
            final JsonArray bodyArgs = _params.get(_argsKey).getAsJsonArray();
            for (JsonElement bodyArg : bodyArgs) {
                args.add(bodyArg.getAsString());
            }
        }
        return args;
    }

    private String parseUrl(final JsonObject _jsonObject) {
        final Uri uri = Uri.parse(_jsonObject.get(BASE_URL).getAsString() + _jsonObject.get(URL).getAsString());
        return uri.getSchemeSpecificPart().substring(uri.getSchemeSpecificPart().indexOf(uri.getHost()));
    }

}
