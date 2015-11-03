package com.uae.tra_smart_services.entities.dynamic_service;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.uae.tra_smart_services.entities.dynamic_service.input_item.AttachmentInputItem;

/**
 * Created by mobimaks on 28.10.2015.
 */
public final class Attachment {

    private final Uri mUri;
    private final String mArgumentName;

    public Attachment(@NonNull AttachmentInputItem _attachmentItem) {
        this(_attachmentItem.getAttachmentUri(), _attachmentItem.getQueryName());
    }

    public Attachment(@NonNull Uri _uri, @NonNull String _argumentName) {
        mUri = _uri;
        mArgumentName = _argumentName;
    }

    @NonNull
    public Uri getUri() {
        return mUri;
    }

    @NonNull
    public String getArgumentName() {
        return mArgumentName;
    }

}
