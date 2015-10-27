package com.uae.tra_smart_services_cutter.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

import com.uae.tra_smart_services_cutter.R;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * Created by ak-buffalo on 13.08.15.
 */
public class AlertDialogFragment extends DialogFragment implements OnClickListener {
    private OnOkListener mSelectListener;
    private String mTitle, mBody;
    private int mMessageId;

    public static AlertDialogFragment newInstance(Fragment targetFragment) {
        AlertDialogFragment pickerDialog = new AlertDialogFragment();
        pickerDialog.setTargetFragment(targetFragment, 0);
        return pickerDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof OnOkListener) {
            mSelectListener = (OnOkListener) targetFragment;
        }
    }

    public final AlertDialogFragment setMessageId(int _messageId){
        mMessageId =_messageId;
        return this;
    }

    public final AlertDialogFragment setDialogTitle(String _title){
        mTitle = _title;
        return this;
    }

    public final AlertDialogFragment setDialogBody(String _body){
        mBody = _body;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder =
                new AlertDialog.Builder(getActivity(), THEME_HOLO_LIGHT)
                .setTitle(mTitle!=null?mTitle:"")
                .setMessage(mBody!=null?mBody:"");
        if (mSelectListener instanceof OnOkListener){
            alertBuilder.setPositiveButton(getString(R.string.str_ok), this);
        }
        if (mSelectListener instanceof OnOkCancelListener){
            alertBuilder.setNegativeButton(getString(R.string.str_cancel), this);
        }

        return alertBuilder.create();
    }

    public final void show(FragmentManager manager) {
        super.show(manager, getClass().getSimpleName());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mSelectListener != null) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    mSelectListener.onOkPressed(mMessageId);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    ((OnOkCancelListener)mSelectListener).onCancelPressed(mMessageId);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        mSelectListener = null;
        super.onDestroy();
    }

    public interface OnOkListener {
        void onOkPressed(final int _messageId);
    }

    public interface OnOkCancelListener extends OnOkListener{
        void onCancelPressed(final int _messageId);
    }
}
