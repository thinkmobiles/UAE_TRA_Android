package com.uae.tra_smart_services.fragment.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog.OnImageSourceSelectListener;
import com.uae.tra_smart_services.global.ImageSource;
import com.uae.tra_smart_services.util.IntentUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mobimaks on 11.08.2015.
 */
public abstract class BaseComplainFragment extends BaseServiceFragment implements OnImageSourceSelectListener {

    private static final int REQUEST_GALLERY_IMAGE_CODE = 1;
    private static final int REQUEST_CAMERA_PHOTO_CODE = 2;

    private static final String CAMERA_PHOTO_FILE_PATH_KEY = "CAMERA_PHOTO_FILE_PATH_KEY";
    private static final String SELECTED_IMAGE_URI_KEY = "SELECTED_IMAGE_URI_KEY";
    private static final String PHOTO_FILE_EXTENSION = ".jpg";

    private String mPhotoFilePath;
    private Uri mImageUri;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @CallSuper
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoFilePath = savedInstanceState.getString(CAMERA_PHOTO_FILE_PATH_KEY);
            mImageUri = savedInstanceState.getParcelable(SELECTED_IMAGE_URI_KEY);
            if (mImageUri != null) {
                onImageGet(mImageUri);
            }
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
            sendComplain();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void sendComplain();

    protected void openImagePicker() {
        final boolean isGalleryAvailable = isGalleryPickAvailable();
        final boolean canGetPhoto = canGetCameraPicture();
        if (isGalleryAvailable && canGetPhoto) {
            ImageSourcePickerDialog.newInstance(this).show(getFragmentManager());
        } else if (isGalleryAvailable) {
            openGallery();
        } else if (canGetPhoto) {
            openCamera();
        } else {
            Toast.makeText(getActivity(), "There is no camera or gallery app", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onImageSourceSelect(ImageSource _source) {
        switch (_source) {
            case GALLERY:
                openGallery();
                break;
            case CAMERA:
                openCamera();
                break;
        }
    }

    protected void openGallery() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        if (galleryIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(galleryIntent, REQUEST_GALLERY_IMAGE_CODE);
        }
    }

    protected void openCamera() {
        final File imageFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        boolean isFolderExist = imageFolder.exists() || imageFolder.mkdir();
        if (isFolderExist) {
            String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File imageFile;
            try {
                imageFile = File.createTempFile(imageFileName, PHOTO_FILE_EXTENSION, imageFolder);
            } catch (IOException e) {
                imageFile = null;
            }
            if (imageFile != null) {
                mPhotoFilePath = imageFile.getPath();
                Intent takePictureIntent = IntentUtils.getCameraStartIntent(Uri.fromFile(imageFile));
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_PHOTO_CODE);
            }
        } else {
            Toast.makeText(getActivity(), "Can't create photo", Toast.LENGTH_SHORT).show();
        }
    }

    @CallSuper
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_PHOTO_CODE) {
            if (!TextUtils.isEmpty(mPhotoFilePath)) {
                final File photoFile = new File(mPhotoFilePath);
                if (resultCode == Activity.RESULT_OK) {
                    mImageUri = Uri.fromFile(photoFile);
                    onImageGet(mImageUri);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    photoFile.delete();
                }
            }
        } else if (requestCode == REQUEST_GALLERY_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            mImageUri = data.getData();
            onImageGet(mImageUri);
        }
    }

    protected abstract void onImageGet(Uri _uri);

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECTED_IMAGE_URI_KEY, mImageUri);
        outState.putString(CAMERA_PHOTO_FILE_PATH_KEY, mPhotoFilePath);
        super.onSaveInstanceState(outState);
    }

    protected final Uri getImageUri() {
        return mImageUri;
    }

    protected final boolean isGalleryPickAvailable() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        return galleryIntent.resolveActivity(getActivity().getPackageManager()) != null;
    }

    protected final boolean canGetCameraPicture() {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager pm = getActivity().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && takePictureIntent.resolveActivity(pm) != null;
    }
}
