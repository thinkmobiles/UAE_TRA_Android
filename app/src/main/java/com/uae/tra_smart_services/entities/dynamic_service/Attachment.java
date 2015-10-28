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
    private final boolean mIsQueryArgument;

    public Attachment(@NonNull AttachmentInputItem _attachmentItem, boolean _isQueryArgument) {
        this(_attachmentItem.getAttachmentUri(), _attachmentItem.getQueryName(), _isQueryArgument);
    }

    public Attachment(@NonNull Uri _uri, @NonNull String _argumentName, boolean _isQueryArgument) {
        mUri = _uri;
        mArgumentName = _argumentName;
        mIsQueryArgument = _isQueryArgument;
    }

    @NonNull
    public Uri getUri() {
        return mUri;
    }

    @NonNull
    public String getArgumentName() {
        return mArgumentName;
    }

    public boolean isQueryArgument() {
        return mIsQueryArgument;
    }
}
