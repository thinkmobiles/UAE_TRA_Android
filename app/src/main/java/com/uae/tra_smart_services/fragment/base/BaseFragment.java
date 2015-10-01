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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.dialog.ProgressDialog;
import com.uae.tra_smart_services.fragment.LoaderFragment;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.interfaces.SpiceLoader;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;
import com.uae.tra_smart_services.rest.model.response.ErrorResponseModel;

import java.net.HttpURLConnection;

import retrofit.RetrofitError;

/**
 * Created by Mikazme on 22/07/2015.
 */
public abstract class BaseFragment extends Fragment implements Loader.Dismiss, Loader.BackButton, View.OnFocusChangeListener {

    private View rootView;
    private InputMethodManager mInputMethodManager;

    protected ToolbarTitleManager toolbarTitleManager;
    protected ThemaDefiner mThemaDefiner;
    protected SpiceLoader mSpiceLoader;
    private Loader loader;

    @Override
    public void onAttach(final Activity _activity) {
        super.onAttach(_activity);
        try {
            toolbarTitleManager = (ToolbarTitleManager) _activity;
            mThemaDefiner = (ThemaDefiner) _activity;
            mSpiceLoader = (SpiceLoader) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement ProgressDialogManager, ErrorHandler, ThemaDefiner, Loader and SpiceLoader");
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

    protected View getRootView() {
        return rootView;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getTitle() != 0)
            toolbarTitleManager.setTitle(getTitle());
    }

    @StringRes
    protected abstract int getTitle();

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

    protected final ToolbarTitleManager getToolbarTitleManager() {
        return toolbarTitleManager;
    }

    protected final void loaderDialogShow() {
        loaderDialogShow("Loading...", null);
    }

    protected final void loaderDialogShow(String _title, Loader.Cancelled _callBack){
        ProgressDialog.newInstance(_title, _callBack).show(getFragmentManager());
    }

    protected final boolean loaderDialogDismiss(){
        Log.d("DeviceBrand", "loaderDialogDismiss");
        ProgressDialog dialog = findFragmentByTag(ProgressDialog.TAG);
        if (dialog != null) {
            dialog.dismiss();
            return true;
        } else {
            return false;
        }
    }

    protected final boolean loaderDialogDismiss(String _msg){
        boolean isLoaded = loaderDialogDismiss();
        if (isLoaded)
            Toast.makeText(getActivity(), _msg, Toast.LENGTH_SHORT).show();
        return isLoaded;
    }

    protected final void loaderOverlayShow(String _title, Loader.Cancelled _callBack) {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.rlGlobalContainer_AH, (Fragment) (loader = LoaderFragment.newInstance(_title, _callBack)), LoaderFragment.TAG)
                .commit();
    }

    public void loaderOverlaySuccess(String _msg){
        if(loader != null){
            loader.successLoading(_msg);
        }
    }

    public void loaderOverlayCancelled(String _msg){
        if(loader != null){
            loader.cancelLoading(_msg);
        }
    }

    public void loaderOverlayFailed(String _msg){
        if(loader != null){
            loader.failedLoading(_msg);
        }
    }

    protected final void loaderOverlayDismissWithAction(Loader.Dismiss _afterDissmiss) {
        if (loader != null) {
            loader.dissmissLoadingWithAction(_afterDissmiss);
        }
    }

    protected final void loaderOverlayButtonBehaviour(Loader.BackButton _backButtonPressed){
        if(loader != null){
            loader.setBackButtonPressedBehaviour(_backButtonPressed);
        }
    }

    @Override
    public void onLoadingDismissed() {
        if (getFragmentManager().findFragmentByTag(LoaderFragment.TAG) != null) {
            getFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onBackButtonPressed(LoaderView.State _currentState) {
        getFragmentManager().popBackStackImmediate();
    }

    protected final void processError(final SpiceException _exception) {
        if (isAdded()) {
            String errorMessage;
            Throwable cause = _exception.getCause();
            if (cause != null && cause instanceof RetrofitError) {
                errorMessage = processRetrofitError(((RetrofitError) cause));
                loaderOverlayFailed(errorMessage);
            } else if (_exception instanceof NoNetworkException) {
                errorMessage = getString(R.string.error_no_network);
                loaderOverlayFailed(errorMessage);
            } else {
                errorMessage = _exception.getMessage();
                loaderOverlayCancelled(errorMessage);
            }
            loaderDialogDismiss(errorMessage);
        }
    }

    private String processRetrofitError(final RetrofitError _error) {
        switch (_error.getKind()) {
            case NETWORK:
                return getString(R.string.error_no_network);
            case CONVERSION:
                //TODO: change this on production, added just to see when developing
                return getString(R.string.error_conversion_error);
            case HTTP:
                if (_error.getResponse().getStatus() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    return getString(R.string.error_server);
                }
                try {
                    ErrorResponseModel errorResponse = (ErrorResponseModel) _error.getBodyAs(ErrorResponseModel.class);
                    return errorResponse.error;
                } catch (RuntimeException _exc) {
                    _exc.printStackTrace();
                    return getString(R.string.error_server);
                }
            default:
            case UNEXPECTED:
                return getString(R.string.str_something_went_wrong);
        }
    }

    protected final SpiceManager getSpiceManager() {
        return mSpiceLoader.getSpiceManager();
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

    public interface ThemaDefiner {
        String getThemeStringValue();
    }

    protected final void showMessage(int messageId, @StringRes int _titleRes, @StringRes int _bodyRes){
        AlertDialogFragment.newInstance(this)
                .setMessageId(messageId)
                .setDialogTitle(getString(_titleRes))
                .setDialogBody(
                        getString(_bodyRes)
                )
                .show(getFragmentManager());
    }

    protected final void showMessage(@StringRes int _titleRes, @StringRes int _bodyRes) {
        showMessage(-1, _titleRes, _bodyRes);
    }

    protected final void showFormattedMessage(int messageId, @StringRes int _titleRes, @StringRes int _bodyRes, String _replace){
        AlertDialogFragment.newInstance(this)
                .setMessageId(messageId)
                .setDialogTitle(getString(_titleRes))
                .setDialogBody(
                        String.format(getString(_bodyRes), _replace)
                )
                .show(getFragmentManager());
    }

    protected final void showFormattedMessage(@StringRes int _titleRes, @StringRes int _bodyRes, String _replace){
        showFormattedMessage(-1, _titleRes, _bodyRes, _replace);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            TextView view  = ((TextView)v);
            view.setText(view.getText().toString().replaceAll(" ", ""));
        }
    }
}