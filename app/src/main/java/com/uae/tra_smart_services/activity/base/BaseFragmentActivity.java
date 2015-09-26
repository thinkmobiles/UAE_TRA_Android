package com.uae.tra_smart_services.activity.base;

import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragmentActivity extends BaseActivity{

    private InputMethodManager mInputMethodManager;

    @IdRes
    protected abstract int getContainerId();

    protected final void addFragmentWithBackStackGlobally(final @NonNull BaseFragment _fragment) {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.rlGlobalContainer_AH, _fragment)
                .commit();
    }

    protected final void addFragmentWithOutBackStack(final @NonNull BaseFragment _fragment) {
        getFragmentManager()
                .beginTransaction()
                .add(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithBackStack(final @NonNull BaseFragment _fragment) {
        hideKeyboard();
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithOutBackStack(final @NonNull BaseFragment _fragment) {
        hideKeyboard();
        getFragmentManager()
                .beginTransaction()
                .replace(getContainerId(), _fragment)
                .commit();
    }

    public final void clearBackStack() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public final void popBackStack() {
        getFragmentManager().popBackStack();
        hideKeyboard();
    }

    protected final void hideKeyboard() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
//        if (getCurrentFocus() != null) {
            mInputMethodManager.hideSoftInputFromWindow(findView(getContainerId()).getWindowToken(), 0);
//        }
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            popBackStack();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            hideKeyboard();
        } else {
            TRAApplication.setIsLoggedIn(false);
            finish();
        }
    }
}
