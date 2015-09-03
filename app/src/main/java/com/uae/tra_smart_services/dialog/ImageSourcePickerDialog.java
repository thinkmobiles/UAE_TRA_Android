package com.uae.tra_smart_services.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.global.ImageSource;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * Created by mobimaks on 11.08.2015.
 */
public class ImageSourcePickerDialog extends DialogFragment implements OnClickListener {

    private OnImageSourceSelectListener mSelectListener;

    public static ImageSourcePickerDialog newInstance(Fragment targetFragment) {
        ImageSourcePickerDialog pickerDialog = new ImageSourcePickerDialog();
        pickerDialog.setTargetFragment(targetFragment, 0);
        return pickerDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof OnImageSourceSelectListener) {
            mSelectListener = (OnImageSourceSelectListener) targetFragment;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity(), THEME_HOLO_LIGHT)
                .setTitle(R.string.fragment_complain_about_service_take_image_from)
                .setItems(ImageSource.toStringArray(getActivity()), this)
                .create();
    }

    public final void show(FragmentManager manager) {
        super.show(manager, getClass().getSimpleName());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mSelectListener != null) {
            mSelectListener.onImageSourceSelect(ImageSource.values()[which]);
        }
    }

    @Override
    public void onDestroy() {
        mSelectListener = null;
        super.onDestroy();
    }

    public interface OnImageSourceSelectListener {
        void onImageSourceSelect(final ImageSource _source);
    }
}
