package com.uae.tra_smart_services.fragment.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog.OnImageSourceSelectListener;
import com.uae.tra_smart_services.entities.AttachmentManager;
import com.uae.tra_smart_services.entities.AttachmentManager.OnImageGetCallback;
import com.uae.tra_smart_services.global.ImageSource;

/**
 * Created by mobimaks on 11.08.2015.
 */
public abstract class BaseComplainFragment extends BaseServiceFragment implements OnImageSourceSelectListener, OnImageGetCallback {

    private AttachmentManager mAttachmentManager;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttachmentManager = new AttachmentManager(getActivity(), this);
        setHasOptionsMenu(true);
    }

    @CallSuper
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mAttachmentManager.onRestoreInstanceState(savedInstanceState);
        }
    }

    @CallSuper
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            hideKeyboard(getView());
            sendComplainIfDataValid();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendComplainIfDataValid() {
        if (validateData()) {
            sendComplain();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAttachmentManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mAttachmentManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    protected abstract boolean validateData();

    protected abstract void sendComplain();

    protected Uri getImageUri() {
        return mAttachmentManager.getImageUri();
    }

    protected void openImagePicker() {
        final boolean isGalleryAvailable = mAttachmentManager.isGalleryPickAvailable();
        final boolean canGetPhoto = mAttachmentManager.canGetCameraPicture();
        if (isGalleryAvailable && canGetPhoto) {
            ImageSourcePickerDialog.newInstance(this).show(getFragmentManager());
        } else if (isGalleryAvailable) {
            mAttachmentManager.openGallery(this);
        } else if (canGetPhoto) {
            mAttachmentManager.openCamera(this);
        } else {
            Toast.makeText(getActivity(), R.string.fragment_complain_about_service_no_camera_and_app, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onImageSourceSelect(ImageSource _source) {
        switch (_source) {
            case GALLERY:
                mAttachmentManager.openGallery(this);
                break;
            case CAMERA:
                mAttachmentManager.openCamera(this);
                break;
        }
    }

    @Override
    public void onDestroy() {
        mAttachmentManager = null;
        super.onDestroy();
    }

    @Override
    public void onLoadingCanceled() {

    }
}
