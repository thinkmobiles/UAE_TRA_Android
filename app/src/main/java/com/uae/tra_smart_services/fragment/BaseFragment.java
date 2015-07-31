package com.uae.tra_smart_services.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.octo.android.robospice.SpiceManager;
import com.uae.tra_smart_services.rest.RestService;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;
    private SpiceManager spiceManager = new SpiceManager(RestService.class);

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        rootView = _inflater.inflate(getLayoutResource(), _container, false);
        initViews();
        setListeners();
        return rootView;
    }

    protected abstract int getLayoutResource();

    private void initViews() {

    }

    private void setListeners() {

    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
    }

    protected SpiceManager getSpiceManager(){
        return spiceManager;
    }

    protected final <T extends View> T findView(@IdRes int _id) {
        return (T) rootView.findViewById(_id);
    }

}
