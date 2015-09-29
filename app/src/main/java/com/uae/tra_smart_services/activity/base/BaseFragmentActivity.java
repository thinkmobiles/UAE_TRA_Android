package com.uae.tra_smart_services.activity.base;

import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

import com.octo.android.robospice.SpiceManager;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.SpiceLoader;
import com.uae.tra_smart_services.rest.TRARestService;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragmentActivity extends BaseActivity implements SpiceLoader {

    private InputMethodManager mInputMethodManager;
    private SpiceManager spiceManager = new SpiceManager(TRARestService.class);


    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @CallSuper
    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    @Override
    public final SpiceManager getSpiceManager() {
        return spiceManager;
    }

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
