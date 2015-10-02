package com.uae.tra_smart_services.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.ServiceRatingView;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * Created by ak-buffalo on 02.10.15.
 */

public class ServiceRatingDialog  extends DialogFragment implements DialogInterface.OnClickListener, ServiceRatingView.CallBacks {
    private CallBacks mCallBacks;
    private String mTitle;
    private ServiceRatingView ratingView;


    public static ServiceRatingDialog newInstance(Fragment targetFragment) {
        ServiceRatingDialog pickerDialog = new ServiceRatingDialog();
        pickerDialog.setTargetFragment(targetFragment, 0);
        return pickerDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof CallBacks) {
            mCallBacks = (CallBacks) targetFragment;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public final ServiceRatingDialog setDialogBody(ServiceRatingView _ratingView){
        ratingView = _ratingView;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ratingView.init(this);
        AlertDialog.Builder alertBuilder =
                new AlertDialog.Builder(getActivity(), THEME_HOLO_LIGHT)
                        .setView(ratingView, 20, 20, 20, 20);
        if (mCallBacks instanceof CallBacks){
            alertBuilder.setNegativeButton(getString(R.string.str_cancel), this);
        }

        return alertBuilder.create();
    }

    public final void show(FragmentManager manager) {
        super.show(manager, getClass().getSimpleName());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mCallBacks != null) {
            switch (which){
                case DialogInterface.BUTTON_NEGATIVE:
                        mCallBacks.onCancelPressed();
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        mCallBacks = null;
        super.onDestroy();
    }

    @Override
    public void onRate(int _rate) {
        dismiss();
        mCallBacks.onRate(_rate);
    }


    public interface CallBacks{
        void onCancelPressed();
        void onRate(int _rate);
    }
}