package com.uae.tra_smart_services.entities.dynamic_service.input_item;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonPrimitive;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.interfaces.OnOpenAttachmentPickerListener;

/**
 * Created by mobimaks on 27.10.2015.
 */
public class AttachmentInputItem extends BaseInputItem implements OnClickListener {

    private static final String KEY_PREFIX = AttachmentInputItem.class.getSimpleName();
    private static final String KEY_ATTACHMENT_URI = KEY_PREFIX + "_ATTACHMENT_URI";

    private RelativeLayout rlContainer;
    private TextView tvText;
    private ImageView ivAttachment;

    private Uri mAttachmentUri;
    private OnOpenAttachmentPickerListener mListener;

    protected AttachmentInputItem() {
    }

    @Override
    protected void initViews() {
        super.initViews();
        rlContainer = findView(R.id.rlContainer_IIA);
        ivAttachment = findView(R.id.ivAttachment_IIA);
        tvText = findView(R.id.tvText_IIA);

        invalidateAttachmentIndicator();
    }

    private void invalidateAttachmentIndicator() {
        if (mAttachmentUri == null) {
            ivAttachment.setImageResource(R.drawable.ic_action_attachment);
            tvText.setText(getPlaceholder());
        } else {
            ivAttachment.setImageResource(R.drawable.ic_check);
            tvText.setText(mAttachmentUri.toString());
        }
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        rlContainer.setOnClickListener(this);
    }

    @Override
    public final void onClick(final View _view) {
        switch (_view.getId()) {
            case R.id.rlContainer_IIA:
                mListener.onOpenAttachmentPicker(this);
                break;
        }
    }

    public Uri getAttachmentUri() {
        return mAttachmentUri;
    }

    public void setAttachmentCallback(OnOpenAttachmentPickerListener _listener) {
        mListener = _listener;
    }

    public void onAttachmentStateChanged(@Nullable Uri _uri) {
        mAttachmentUri = _uri;
        invalidateAttachmentIndicator();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle _outState) {
        _outState.putParcelable(KEY_ATTACHMENT_URI, mAttachmentUri);
        super.onSaveInstanceState(_outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle _savedInstanceState) {
        super.onRestoreInstanceState(_savedInstanceState);
        mAttachmentUri = _savedInstanceState.getParcelable(KEY_ATTACHMENT_URI);
    }

    @Override
    public boolean isDataValid() {
        return !isRequired() || mAttachmentUri != null;
    }

    @Nullable
    @Override
    public JsonPrimitive getJsonValue() {
        return null;
    }

    @Nullable
    @Override
    public String getArgsData() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.input_item_attachment;
    }

    public static final class Builder extends BaseBuilder {

        @Override
        protected BaseInputItem getInstance() {
            return new AttachmentInputItem();
        }

    }
}
