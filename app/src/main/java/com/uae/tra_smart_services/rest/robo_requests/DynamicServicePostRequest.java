package com.uae.tra_smart_services.rest.robo_requests;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uae.tra_smart_services.entities.dynamic_service.Attachment;
import com.uae.tra_smart_services.util.ImageUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit.client.Response;

/**
 * Created by mobimaks on 19.10.2015.
 */
public final class DynamicServicePostRequest extends BaseDynamicServiceRequest {

    private JsonElement mBody;

    public DynamicServicePostRequest(@NonNull Context _context,
                                     @NonNull final String _url,
                                     @NonNull final Map<String, String> _queryParams) {
        super(_context, _url, _queryParams);
    }

    public void setJsonBody(JsonElement _body) {
        mBody = _body;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        try {
            convertQueryAttachmentsToBase64();
            if (mBody == null) {
                return getService().performPostRequest(mUrl, mQueryParams);
            } else {
                convertBodyAttachmentsToBase64();
                return getService().performPostRequest(mUrl, mQueryParams, mBody);
            }
        } catch (IOException e) {
            throw new Exception("Can't load image from device");
        }
    }

    private void convertBodyAttachmentsToBase64() throws Exception {
        final JsonObject bodyObject = (JsonObject) mBody;
        final List<Attachment> attachments = getAttachments();
        for (int i = 0; i < attachments.size(); i++) {
            final Attachment attachment = attachments.get(i);
            if (!attachment.isQueryArgument()) {
                final String attachmentBase64 = ImageUtils.imageToBase64(mContentResolver, attachment.getUri());
                bodyObject.addProperty(attachment.getArgumentName(), attachmentBase64);
            }
        }
    }
}
