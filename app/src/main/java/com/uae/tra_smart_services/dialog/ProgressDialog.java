package com.uae.tra_smart_services.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import com.uae.tra_smart_services.global.C;

/**
 * Created by mobimaks on 14.08.2015.
 */
public final class ProgressDialog extends DialogFragment {

    static DialogInterface dialogInterface;

    public static String TAG = ProgressDialog.class.getSimpleName();
    public static ProgressDialog newInstance(final String _title, DialogInterface _onCancel) {
        ProgressDialog dialog = new ProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putString(C.TITLE, _title);
        boolean isCancelabel = _onCancel != null ? true : false;
        bundle.putBoolean(C.IS_CANCELABLE, isCancelabel);
        dialog.setArguments(bundle);
        dialogInterface = (DialogInterface) _onCancel;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        android.app.ProgressDialog dialog = new android.app.ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        dialog.setIndeterminate(true);
        dialog.setMessage(bundle.getString(C.TITLE));
        boolean isCancelabel = bundle.getBoolean(C.IS_CANCELABLE);
        dialog.setCancelable(isCancelabel);
        dialog.setCanceledOnTouchOutside(isCancelabel);
        return dialog;
    }

    public void show(FragmentManager _manager) {
        show(_manager, TAG);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialogInterface.cancel();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dialogInterface.cancel();
    }

    public interface MyDialogInterface extends DialogInterface{}
}
