package com.uae.tra_smart_services.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;

import com.uae.tra_smart_services.global.ServiceProvider;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * Created by mobimaks on 11.08.2015.
 */
public final class ServicePickerDialog extends DialogFragment implements OnClickListener {

    private OnServiceProviderSelectListener mSelectListener;

    public static ServicePickerDialog newInstance(Fragment targetFragment) {
        ServicePickerDialog pickerDialog = new ServicePickerDialog();
        pickerDialog.setTargetFragment(targetFragment, 0);
        return pickerDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof OnServiceProviderSelectListener) {
            mSelectListener = (OnServiceProviderSelectListener) targetFragment;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Builder(getActivity(), THEME_HOLO_LIGHT)
                .setTitle("Select service provider")
                .setItems(ServiceProvider.toStringArray(), this)
                .create();
    }

    public final void show(FragmentManager manager) {
        super.show(manager, getClass().getSimpleName());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mSelectListener != null) {
            mSelectListener.onServiceProviderSelect(ServiceProvider.values()[which]);
        }
    }

    @Override
    public void onDestroy() {
        mSelectListener = null;
        super.onDestroy();
    }

    public interface OnServiceProviderSelectListener {
        void onServiceProviderSelect(final ServiceProvider _provider);
    }

}
