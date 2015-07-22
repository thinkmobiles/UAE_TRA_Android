package com.uae.tra_smart_services;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.uae.tra_smart_services.interfaces.ProgressDialogManager;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements ProgressDialogManager {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final void showProgressDialog() {
        showProgressDialog("");
    }

    @Override
    public final void showProgressDialog(final String _text) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        } else if (mProgressDialog.isShowing())
            return;
        mProgressDialog.setMessage(_text == null ? "" : _text);
        mProgressDialog.show();
    }

    @Override
    public final void hideProgressDialog() {
        if (mProgressDialog != null
                && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public final <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }
}
