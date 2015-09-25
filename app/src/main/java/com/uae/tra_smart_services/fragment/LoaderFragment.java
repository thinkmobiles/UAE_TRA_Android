package com.uae.tra_smart_services.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.dialog.ProgressDialog;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.Loader;

/**
 * Created by Andrey Korneychuk on 21.09.15.
 */
public class LoaderFragment extends BaseFragment implements View.OnClickListener, Loader {

    private static final String MSG = "message";

    private LoaderView lvLoader;
    private TextView tvBackOrCancelBtn, tvLoaderTitleText;
    private RelativeLayout rlFragmentContainer;
    private static Loader.Cancelled mOnLoadingListener;
    BackButton afterBackButton;

    public static LoaderFragment newInstance(String _msg, Loader.Cancelled _onLoadingListener) {
        Bundle args = new Bundle();
        args.putString(MSG, _msg);
        mOnLoadingListener = _onLoadingListener;
        LoaderFragment fragment = new LoaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitle() { return 0; }

    @Override
    protected int getLayoutResource() { return R.layout.fragment_loader; }

    @Override
    protected void setToolbarVisibility() { toolbarTitleManager.setToolbarVisibility(true); }
    private int backGroundColor;
    @Override
    protected void initViews() {
        super.initViews();
        rlFragmentContainer = findView(R.id.rlFragmentContainer_FL);
        lvLoader = findView(R.id.lvLoaderView);
        lvLoader.setTag(defineBGColor(rlFragmentContainer));
        tvLoaderTitleText = findView(R.id.tvLoaderTitleText);
        tvBackOrCancelBtn = findView(R.id.tvLoaderBackButton);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        rlFragmentContainer.setOnClickListener(this);
        tvBackOrCancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvLoaderBackButton:
                if(v.getTag() == LoaderView.State.PROCESSING){
                    mOnLoadingListener.onLoadingCanceled();
                } else {
                    if(afterBackButton != null){
                        afterBackButton.onBackButtonPressed();
                    } else {
                        backButtonPressed();
                    }
                }
                break;
        }
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // start processing after layout globlly created
                startLoading(getArguments().getString(MSG));
                ViewTreeObserver obs = view.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void startLoading(String _msg) {
        lvLoader.startProcessing();
        tvLoaderTitleText.setText(getArguments().getString(MSG));
        tvBackOrCancelBtn.setTag(LoaderView.State.PROCESSING);
    }

    @Override
    public void successLoading(String _msg) {
        lvLoader.startFilling(LoaderView.State.SUCCESS);
        tvLoaderTitleText.setText(_msg);
        tvBackOrCancelBtn.setTag(LoaderView.State.SUCCESS);
        tvBackOrCancelBtn.setText(R.string.str_back_to_dashboard);
    }

    @Override
    public void dissmissLoading(Dismiss afterDissmiss) {
        afterDissmiss.onLoadingDismissed();
    }

    @Override
    public void setBackButtonPressedBehaviour(BackButton _afterBackButton) {
        afterBackButton = _afterBackButton;
    }

    @Override
    public void dissmissLoading(String _msg) {
        lvLoader.startFilling(LoaderView.State.CANCELLED);
        tvLoaderTitleText.setText(_msg);
        tvBackOrCancelBtn.setText(R.string.str_back_to_dashboard);
        tvBackOrCancelBtn.setTag(LoaderView.State.CANCELLED);
    }

    @Override
    public void backButtonPressed() {
        toolbarTitleManager.setToolbarVisibility(true);
        getFragmentManager().popBackStackImmediate();
    }

    private int defineBGColor(View _view){
        Bitmap bitmap = ((BitmapDrawable)_view.getBackground()).getBitmap();
        int pixel = bitmap.getPixel(10, 10);
        return Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
    }
}
