package com.uae.tra_smart_services.activity.base;

import android.app.FragmentManager;
import android.support.annotation.IdRes;

import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    @IdRes
    protected abstract  int getContainerId();

    protected final void addFragment(final BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
                .add(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithBackStack(final BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithOutBackStack(final BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
                .replace(getContainerId(), _fragment)
                .commit();
    }

    public final void clearBackStack(){
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
