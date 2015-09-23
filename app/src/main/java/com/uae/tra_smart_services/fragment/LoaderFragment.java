package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.Loader;

/**
 * Created by Andrey Korneychuk on 21.09.15.
 */
public class LoaderFragment extends BaseFragment implements View.OnClickListener, Loader {

    private LoaderView lvLoader;
    private TextView tvBackOrCancelButton, tvLoaderTitleText;
    private RelativeLayout rlFragmentContainer;
    private Button btnStart, btnSuccess, btnFailure, btnCancel;
    private static OnLoadingListener mOnLoadingListener;

    public static LoaderFragment newInstance(LoaderFragment.OnLoadingListener _onLoadingListener) {
        Bundle args = new Bundle();
        mOnLoadingListener = _onLoadingListener;
        LoaderFragment fragment = new LoaderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_loader;
    }

    @Override
    protected void initViews() {
        super.initViews();
        btnStart = findView(R.id.start);
        btnStart.setOnClickListener(this);
        btnSuccess = findView(R.id.success);
        btnSuccess.setOnClickListener(this);
        btnFailure = findView(R.id.failure);
        btnFailure.setOnClickListener(this);


        rlFragmentContainer = findView(R.id.rlFragmentContainer_FL);
        lvLoader = findView(R.id.lvLoaderView);
        tvLoaderTitleText = findView(R.id.tvLoaderTitleText);
        tvBackOrCancelButton = findView(R.id.tvLoaderBackButton);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        rlFragmentContainer.setOnClickListener(this);
        tvBackOrCancelButton.setOnClickListener(this);
    }

    @Override
    protected void setToolbarVisibility() {
        toolbarTitleManager.setToolbarVisibility(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                startLoading();
                break;
            case R.id.success:
                showSuccessLoading();
                break;
            case R.id.failure:
                showFailureLoading();
                break;
            case R.id.tvLoaderBackButton:
                if(v.getTag() == LoaderView.State.PROCESSING){
                    showCancelLoading();
                } else {
                    backButtonPressed();
                }
                break;
        }
    }

    @Override
    public void startLoading() {
        tvLoaderTitleText.setText(R.string.str_give_us_moment);
        tvBackOrCancelButton.setVisibility(View.VISIBLE);
        tvBackOrCancelButton.setText(R.string.str_cancel_request);
        tvBackOrCancelButton.setTag(LoaderView.State.PROCESSING);
        lvLoader.startProcessing();
    }

    @Override
    public void successLoading() {
        lvLoader.startFilling(LoaderView.State.SUCCESS);
        tvLoaderTitleText.setText(R.string.str_reuqest_has_been_sent);
        tvBackOrCancelButton.setText(R.string.str_back_to_dashboard);
        tvBackOrCancelButton.setTag(LoaderView.State.SUCCESS);
    }

    @Override
    public void cancelLoading() {
        lvLoader.startFilling(LoaderView.State.CANCELLED);
        tvLoaderTitleText.setText(R.string.str_reuqest_has_been_cancelled);
        tvBackOrCancelButton.setText(R.string.str_back_to_dashboard);
        tvBackOrCancelButton.setTag(LoaderView.State.CANCELLED);
        mOnLoadingListener.onRequestCancel(LoaderView.State.CANCELLED);
    }

    @Override
    public void failureLoading() {
        lvLoader.startFilling(LoaderView.State.FAILURE);
        tvLoaderTitleText.setText(R.string.str_reuqest_has_been_failed);
        tvBackOrCancelButton.setText(R.string.str_back_to_dashboard);
        tvBackOrCancelButton.setTag(LoaderView.State.FAILURE);
    }

    @Override
    public void backButtonPressed() {
        toolbarTitleManager.setToolbarVisibility(true);
        getFragmentManager().popBackStackImmediate();
    }

    public interface OnLoadingListener{
        void onRequestCancel(LoaderView.State _state);
    }
}
