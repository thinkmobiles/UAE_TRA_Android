package com.uae.tra_smart_services.fragment.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
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
import android.view.inputmethod.InputMethodManager;

import com.octo.android.robospice.SpiceManager;
import com.uae.tra_smart_services.dialog.ProgressDialog;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.interfaces.OnReloadData;
import com.uae.tra_smart_services.interfaces.ProgressDialogManager;
import com.uae.tra_smart_services.interfaces.RetrofitFailureHandler;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;
import com.uae.tra_smart_services.rest.TRARestService;

import retrofit.RetrofitError;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseFragment extends Fragment implements RetrofitFailureHandler, OnReloadData {

    private View rootView;
    private SpiceManager spiceManager = new SpiceManager(TRARestService.class);
    private InputMethodManager mInputMethodManager;

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
        initData();
        initViews();
        initListeners();
        setToolbarVisibility();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getTitle() != 0)
            toolbarTitleManager.setTitle(getTitle());
    }

    @StringRes
    protected abstract int getTitle();

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

    protected void initData() {
    }

    protected void setToolbarVisibility() {
        toolbarTitleManager.setToolbarVisibility(true);
    }

    @CallSuper
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void reloadData() {
    }

    protected final ToolbarTitleManager getToolbarTitleManager() {
        return toolbarTitleManager;
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

    protected final void showProgressDialog(){
        ProgressDialog.newInstance().show(getFragmentManager());
    }

    protected final void hideProgressDialog(){
        ProgressDialog dialog = findFragmentByTag(ProgressDialog.TAG);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    protected final SpiceManager getSpiceManager() {
        return spiceManager;
    }

    protected final void hideKeyboard(View view) {
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected final <F extends Fragment> F findFragmentById(final @IdRes int _id){
        return (F) getFragmentManager().findFragmentById(_id);
    }

    protected final <F extends Fragment> F findFragmentByTag(final String _tag){
        return (F) getFragmentManager().findFragmentByTag(_tag);
    }

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

    protected final void showMessage(@StringRes int _titleRes, @StringRes int _bodyRes){
        AlertDialogFragment.newInstance(this)
                .setDialogTitle(getString(_titleRes))
                .setDialogBody(
                        getString(_bodyRes)
                )
                .show(getFragmentManager());
    }

    protected final void showFormattedMessage(@StringRes int _titleRes, @StringRes int _bodyRes, String _replace){
        AlertDialogFragment.newInstance(this)
                .setDialogTitle(getString(_titleRes))
                .setDialogBody(
                        String.format(getString(_bodyRes), _replace)
                )
                .show(getFragmentManager());
    }
}
