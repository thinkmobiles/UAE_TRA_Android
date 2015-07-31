package com.uae.tra_smart_services.activity;

import android.app.FragmentManager;
import android.support.annotation.IdRes;

import com.uae.tra_smart_services.fragment.BaseFragment;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected abstract @IdRes
    int getContainerId();

    protected final void addFragment(final BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
                .add(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithBackStack(final BaseFragment _fragment) {
        getFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
//                        R.anim.slide_in_right, R.anim.slide_out_right)
                .addToBackStack(null)
                .replace(getContainerId(), _fragment)
                .commit();
    }

    public final void replaceFragmentWithoutBackStack(final BaseFragment _fragment) {
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
}
