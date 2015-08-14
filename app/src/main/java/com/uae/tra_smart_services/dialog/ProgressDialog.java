package com.uae.tra_smart_services.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by mobimaks on 14.08.2015.
 */
public final class ProgressDialog extends DialogFragment {

    public static String TAG = ProgressDialog.class.getSimpleName();

    public static ProgressDialog newInstance() {
        ProgressDialog dialog = new ProgressDialog();
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.ProgressDialog dialog = new android.app.ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        dialog.setIndeterminate(true);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void show(FragmentManager _manager) {
        show(_manager, TAG);
    }

}
