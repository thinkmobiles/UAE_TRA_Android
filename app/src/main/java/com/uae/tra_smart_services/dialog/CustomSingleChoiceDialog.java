package com.uae.tra_smart_services.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class CustomSingleChoiceDialog extends DialogFragment implements Dialog.OnClickListener {

    private OnItemPickListener mSelectListener;

    public static CustomSingleChoiceDialog newInstance(Fragment targetFragment) {
        CustomSingleChoiceDialog pickerDialog = new CustomSingleChoiceDialog();
        pickerDialog.setTargetFragment(targetFragment, 0);
        return pickerDialog;
    }

    private String mTitle;
    public CustomSingleChoiceDialog setTitle(String _title){
        mTitle = _title;
        return this;
    }

    private String[] mBodyItems;
    public CustomSingleChoiceDialog setBodyItems(String[] _bodyItems){
        mBodyItems = _bodyItems;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof OnItemPickListener) {
            mSelectListener = (OnItemPickListener) targetFragment;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity(), THEME_HOLO_LIGHT)
                .setTitle(mTitle)
                .setItems(mBodyItems, this)
                .create();
    }

    public final void show(FragmentManager manager) {
        super.show(manager, getClass().getSimpleName());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mSelectListener != null) {
            mSelectListener.onItemPicked(which);
        }
    }

    @Override
    public void onDestroy() {
        mSelectListener = null;
        super.onDestroy();
    }

    public interface OnItemPickListener {
        void onItemPicked(final int _dialogItem);
    }
}