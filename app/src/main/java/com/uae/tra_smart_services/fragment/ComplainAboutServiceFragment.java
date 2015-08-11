package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog.OnImageSourceSelectListener;
import com.uae.tra_smart_services.dialog.ServicePickerDialog;
import com.uae.tra_smart_services.dialog.ServicePickerDialog.OnServiceProviderSelectListener;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.ImageSource;
import com.uae.tra_smart_services.global.ServiceProvider;
import com.uae.tra_smart_services.util.IntentUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mobimaks on 10.08.2015.
 */
public final class ComplainAboutServiceFragment extends BaseFragment
        implements OnClickListener, OnServiceProviderSelectListener, OnImageSourceSelectListener {

    private static final int REQUEST_GALLERY_IMAGE_CODE = 1;
    private static final int REQUEST_CAMERA_PHOTO_CODE = 2;
    private static final String CAMERA_PHOTO_FILE_PATH_KEY = "CAMERA_PHOTO_FILE_PATH_KEY";
    private static final String PHOTO_FILE_EXTENSION = ".jpg";

    private InputMethodManager mInputMethodManager;

    private ImageView ivAddAttachment, ivNextItem;
    private TextView tvServiceProvider;
    private EditText etComplainTitle, etReferenceNumber, etDescription;

    private String mPhotoFilePath;

    public static ComplainAboutServiceFragment newInstance() {
        return new ComplainAboutServiceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivAddAttachment = findView(R.id.ivAddAttachment_FCAS);
        ivNextItem = findView(R.id.ivNextItem_FCAS);

        tvServiceProvider = findView(R.id.tvServiceProvider_FCAS);

        etComplainTitle = findView(R.id.etComplainTitle_FCAS);
        etReferenceNumber = findView(R.id.etReferenceNumber_FCAS);
        etDescription = findView(R.id.etDescription_FCAS);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivAddAttachment.setOnClickListener(this);
        ivNextItem.setOnClickListener(this);
        tvServiceProvider.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (savedInstanceState != null) {
            mPhotoFilePath = savedInstanceState.getString(CAMERA_PHOTO_FILE_PATH_KEY);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            hideKeyboard(getView());
            sendComplain();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendComplain() {
        Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.ivAddAttachment_FCAS:
                openImagePicker();
                break;
            case R.id.tvServiceProvider_FCAS:
            case R.id.ivNextItem_FCAS:
                openServiceProviderPicker();
                break;
        }
    }

    private void hideKeyboard(View view) {
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openImagePicker() {
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

    private void openGallery() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        if (galleryIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(galleryIntent, REQUEST_GALLERY_IMAGE_CODE);
        }
    }

    private void openCamera() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_PHOTO_CODE) {
            if (!TextUtils.isEmpty(mPhotoFilePath)) {
                final File photoFile = new File(mPhotoFilePath);
                if (resultCode == Activity.RESULT_OK) {
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    photoFile.delete();
                }
            }
        } else if (requestCode == REQUEST_GALLERY_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
        }
    }

    private void openServiceProviderPicker() {
        ServicePickerDialog.newInstance(this).show(getFragmentManager());
    }

    @Override
    public void onServiceProviderSelect(final ServiceProvider _provider) {
        tvServiceProvider.setText(_provider.toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CAMERA_PHOTO_FILE_PATH_KEY, mPhotoFilePath);
        super.onSaveInstanceState(outState);
    }

    private boolean isGalleryPickAvailable() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        return galleryIntent.resolveActivity(getActivity().getPackageManager()) != null;
    }

    private boolean canGetCameraPicture() {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager pm = getActivity().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && takePictureIntent.resolveActivity(pm) != null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_complain_about_service;
    }
}
