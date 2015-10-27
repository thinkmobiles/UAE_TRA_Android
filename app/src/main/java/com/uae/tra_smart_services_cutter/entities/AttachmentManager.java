package com.uae.tra_smart_services_cutter.entities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.uae.tra_smart_services_cutter.R;
import com.uae.tra_smart_services_cutter.dialog.AttachmentPickerDialog;
import com.uae.tra_smart_services_cutter.global.AttachmentOption;
import com.uae.tra_smart_services_cutter.util.ImageUtils;
import com.uae.tra_smart_services_cutter.util.IntentUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mobimaks on 02.10.2015.
 */
public final class AttachmentManager {

    private static final int REQUEST_GALLERY_IMAGE_CODE = 130;
    private static final int REQUEST_CAMERA_PHOTO_CODE = 131;
    public static final int REQUEST_CUTTER_VIEW = 132;

    private static final String CAMERA_PHOTO_FILE_PATH_KEY = "CAMERA_PHOTO_FILE_PATH_KEY";
    public static final String SELECTED_IMAGE_URI_KEY = "SELECTED_IMAGE_URI_KEY";
    public static final String PHOTO_FILE_EXTENSION = ".jpg";
    public static final String CUTTED_IMAGE_PATH = "FITTED_IMAGE_PATH";
    public static final String CUTTED_IMAGE_NAME = "user_avatar";

    private final Context mContext;
    private final PackageManager mPackageManager;
    private final OnImageGetCallback mImageGetCallback;
    private final OnCutterRequest mCutterRequest;

    private String mPhotoFilePath;
    private Uri mImageUri;
    private Uri cuttedImageUri;

    public AttachmentManager(@NonNull final Context _context, @NonNull final OnImageGetCallback _imageGetCallback) {
        mContext = _context;
        mPackageManager = _context.getPackageManager();
        mImageGetCallback = _imageGetCallback;
        mCutterRequest = (OnCutterRequest) _imageGetCallback;
        if ((cuttedImageUri = getCuttedImageUri()) != null && !ImageUtils.deleteViaContentProvider(cuttedImageUri)) {
            cuttedImageUri = createTempFileRaw();
        } else {
            cuttedImageUri = createTempFile(CUTTED_IMAGE_NAME);
        }
        saveCuttedImagePath(cuttedImageUri.toString());
    }

    private void saveCuttedImagePath(String _imagepath){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CUTTED_IMAGE_PATH, _imagepath);
        editor.commit();
    }

    private Uri getCuttedImageUri(){
        String imagePAth = PreferenceManager.getDefaultSharedPreferences(mContext).getString(CUTTED_IMAGE_PATH, null);
        if(imagePAth != null){
            return Uri.parse(imagePAth);
        }
        return null;
    }

    public void onRestoreInstanceState(@NonNull final Bundle _savedInstanceState) {
        mPhotoFilePath = _savedInstanceState.getString(CAMERA_PHOTO_FILE_PATH_KEY);
        mImageUri = _savedInstanceState.getParcelable(SELECTED_IMAGE_URI_KEY);
        if (mImageUri != null) {
            mImageGetCallback.onAttachmentGet(mImageUri, cuttedImageUri);
        }
    }

    public void onSaveInstanceState(@NonNull final Bundle _outState) {
        _outState.putString(CAMERA_PHOTO_FILE_PATH_KEY, mPhotoFilePath);
        _outState.putParcelable(SELECTED_IMAGE_URI_KEY, mImageUri);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_PHOTO_CODE) {
            if (!TextUtils.isEmpty(mPhotoFilePath)) {
                final File photoFile = new File(mPhotoFilePath);
                if (resultCode == Activity.RESULT_OK) {
                    mImageUri = Uri.fromFile(photoFile);
                    mCutterRequest.moveToCutterActivity(mImageUri, cuttedImageUri);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    photoFile.delete();
                }
            }
        } else if (requestCode == REQUEST_GALLERY_IMAGE_CODE) {
            mImageUri = data.getData();
            mCutterRequest.moveToCutterActivity(mImageUri, cuttedImageUri);
        } else if (requestCode == REQUEST_CUTTER_VIEW && resultCode == Activity.RESULT_OK) {
            mImageGetCallback.onAttachmentGet(mImageUri, cuttedImageUri);
        }
    }

    public void openGallery(final Fragment _fragment) {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        if (galleryIntent.resolveActivity(mPackageManager) != null) {
            _fragment.startActivityForResult(galleryIntent, REQUEST_GALLERY_IMAGE_CODE);
        }
    }

    public void openCamera(final Fragment _fragment) {
        final Uri imageUri;
        if((imageUri = createTempFileRaw()) != null){
            Intent takePictureIntent = IntentUtils.getCameraStartIntent(imageUri);
            _fragment.startActivityForResult(takePictureIntent, REQUEST_CAMERA_PHOTO_CODE);
        } else {
            Toast.makeText(mContext, "Can't create photo", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri createTempFileRaw(){
       return createTempFile(null);
    }

    private Uri createTempFile(String _fileName){
        final File imageFolder = mContext.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_WORLD_WRITEABLE);
        final boolean isFolderExist = imageFolder.exists() || imageFolder.mkdir();
        if (isFolderExist) {
            String imageFileName = (_fileName == null) ? new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) : _fileName;
            File imageFile;
            try {
                imageFile = File.createTempFile(imageFileName, PHOTO_FILE_EXTENSION, imageFolder);
            } catch (IOException e) {
                imageFile = null;
            }
            if (imageFile != null) {
                mPhotoFilePath = imageFile.getPath();
                return Uri.fromFile(imageFile);
            }
        }
        return null;
    }

    @Nullable
    public final Uri getImageUri() {
        return mImageUri;
    }

    public final boolean isGalleryPickAvailable() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        return galleryIntent.resolveActivity(mPackageManager) != null;
    }

    public final boolean canGetCameraPicture() {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return mPackageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                && takePictureIntent.resolveActivity(mPackageManager) != null;
    }

    public void clearAttachment() {
        mPhotoFilePath = null;
        mImageUri = null;
    }

    public final void openDefaultPicker(@NonNull Context _context, @NonNull Fragment _fragment) {
        final boolean isGalleryAvailable = isGalleryPickAvailable();
        final boolean canGetPhoto = canGetCameraPicture();
        final boolean needDeleteOption = getImageUri() != null;
        final List<AttachmentOption> options = new ArrayList<>();

        if (isGalleryAvailable) {
            options.add(AttachmentOption.GALLERY);
        }
        if (canGetPhoto) {
            options.add(AttachmentOption.CAMERA);
        }
        if (needDeleteOption) {
            options.add(AttachmentOption.DELETE_ATTACHMENT);
        }

        if (options.size() > 1) {
            AttachmentOption[] optionsArray = new AttachmentOption[options.size()];
            options.toArray(optionsArray);
            AttachmentPickerDialog.newInstance(_fragment, optionsArray).show(_fragment.getFragmentManager());
        } else if (options.size() == 1) {
            if (isGalleryAvailable) {
                openGallery(_fragment);
            } else if (canGetPhoto) {
                openCamera(_fragment);
            }
        } else {
            Toast.makeText(_context, R.string.fragment_complain_about_service_no_camera_and_app, Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnImageGetCallback {
        void onAttachmentGet(final @NonNull Uri _fromImageUri, final @NonNull Uri _cutterImageUri);
    }

    public interface OnCutterRequest {
        void moveToCutterActivity(final @NonNull Uri _resImageUri, final @NonNull Uri _cuttedImageUri);
    }
}
