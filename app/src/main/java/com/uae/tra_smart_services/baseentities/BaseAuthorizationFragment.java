package com.uae.tra_smart_services.baseentities;

import android.app.Activity;
import android.widget.ImageButton;

/**
 * Created by ak-buffalo on 22.07.15.
 */
public abstract class BaseAuthorizationFragment extends BaseFragment{
    protected AuthorizationActionsListener actionsListener;
    protected ImageButton btnBack;

    @Override
    public void onAttach(final Activity _activity) {
        super.onAttach(_activity);

        try {
            actionsListener = (AuthorizationActionsListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public interface AuthorizationActionsListener {

        /** Handlers methods for log in screen*/
        void onOpenLoginScreen();

        void onLogInSuccess();

        /** Handlers methods for register screen*/
        void onOpenRegisterScreen();

        void onRegisterSuccess();

        /** Handlers methods for restore password screen*/
        void onOpenRestorePassScreen();

        void onRestorePassSuccess();

        void onBackPressed();
    }
}
