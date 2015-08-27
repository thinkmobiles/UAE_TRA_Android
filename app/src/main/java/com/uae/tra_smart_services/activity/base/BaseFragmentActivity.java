package com.uae.tra_smart_services.activity.base;

import android.app.FragmentManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragmentActivity extends BaseActivity implements BaseFragment.ErrorHandler {

    @IdRes
    protected abstract int getContainerId();

    protected final void addFragment(final @NonNull BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
                .add(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithBackStack(final @NonNull BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithOutBackStack(final @NonNull BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
                .replace(getContainerId(), _fragment)
                .commit();
    }

    public final void clearBackStack() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public final void popBackStack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
