package com.uae.tra_smart_services.rest.robo_requests;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.uae.tra_smart_services.entities.dynamic_service.Attachment;
import com.uae.tra_smart_services.rest.DynamicServicesApi;
import com.uae.tra_smart_services.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.client.Response;

/**
 * Created by mobimaks on 19.10.2015.
 */
public abstract class BaseDynamicServiceRequest extends BaseRequest<Response, DynamicServicesApi> {

    protected final ContentResolver mContentResolver;
    protected final String mUrl;
    protected final List<Attachment> mAttachments;

    protected final Map<String, String> mQueryParams;

    public BaseDynamicServiceRequest(@NonNull final Context _context,
                                     @NonNull final String _url,
                                     @NonNull final Map<String, String> _queryParams) {

        super(Response.class, DynamicServicesApi.class);
        mContentResolver = _context.getContentResolver();
        mUrl = _url;
        mAttachments = new ArrayList<>();
        mQueryParams = _queryParams;
    }

    public void addAttachments(@NonNull List<Attachment> _attachments) {
        mAttachments.addAll(_attachments);
    }

    protected final List<Attachment> getAttachments() {
        return mAttachments;
    }

    protected final ContentResolver getContentResolver() {
        return mContentResolver;
    }

    protected final String getUrl() {
        return mUrl;
    }

    protected final Map<String, String> getQueryParams() {
        return mQueryParams;
    }

    protected final void convertQueryAttachmentsToBase64() throws Exception {
        final List<Attachment> attachments = getAttachments();
        for (int i = 0; i < attachments.size(); i++) {
            final Attachment attachment = attachments.get(i);
            if (attachment.isQueryArgument()) {
                final String attachmentBase64 = ImageUtils.imageToBase64(mContentResolver, attachment.getUri());
                mQueryParams.put(attachment.getArgumentName(), attachmentBase64);
            }
        }
    }
}
