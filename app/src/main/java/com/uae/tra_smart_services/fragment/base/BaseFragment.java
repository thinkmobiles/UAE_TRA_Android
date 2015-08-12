package com.uae.tra_smart_services.fragment.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.octo.android.robospice.SpiceManager;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.interfaces.OnReloadData;
import com.uae.tra_smart_services.interfaces.ProgressDialogManager;
import com.uae.tra_smart_services.interfaces.RetrofitFailureHandler;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;
import com.uae.tra_smart_services.rest.RestService;

import retrofit.RetrofitError;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragment extends Fragment implements RetrofitFailureHandler, OnReloadData {

    private View rootView;
    private SpiceManager spiceManager = new SpiceManager(RestService.class);

    protected ProgressDialogManager progressDialogManager;
    protected ErrorHandler errorHandler;
    protected ToolbarTitleManager toolbarTitleManager;
    protected ThemaDefiner mThemaDefiner;

    @Override
    public void onAttach(final Activity _activity) {
        super.onAttach(_activity);
        try {
            toolbarTitleManager = (ToolbarTitleManager) _activity;
            progressDialogManager = (ProgressDialogManager) _activity;
            errorHandler = (ErrorHandler) _activity;
            mThemaDefiner = (ThemaDefiner) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement ProgressDialogManager and ErrorHandler and ThemaDefiner");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        rootView = _inflater.inflate(getLayoutResource(), _container, false);
        initViews();
        initListeners();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getTitle() != 0)
            toolbarTitleManager.setTitle(getTitle());
    }

    protected abstract @StringRes int getTitle();

    @Override
    public void failure(final RetrofitError _error) {
        errorHandler.handleError(_error, this);
    }

    public void failure(final RetrofitError _error, final OnReloadData _listener) {
        errorHandler.handleError(_error, _listener);
    }

    protected void initViews() {
    }

    protected void initListeners() {
    }

    @Override
    public void reloadData() {
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

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected final <T extends View> T findView(@IdRes int _id) {
        return (T) rootView.findViewById(_id);
    }

    public interface ErrorHandler {

        void handleError(final RetrofitError _error);

        void handleError(final RetrofitError _error, final OnReloadData _listener);
    }

    public interface ThemaDefiner {
        String getThemeStringValue();
    }
}
