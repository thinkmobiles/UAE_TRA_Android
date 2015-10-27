package com.uae.tra_smart_services_cutter.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services_cutter.R;
import com.uae.tra_smart_services_cutter.customviews.LoaderView;
import com.uae.tra_smart_services_cutter.customviews.ServiceRatingView;
import com.uae.tra_smart_services_cutter.fragment.base.BaseFragment;
import com.uae.tra_smart_services_cutter.interfaces.Loader;
import com.uae.tra_smart_services_cutter.interfaces.LoaderMarker;
import com.uae.tra_smart_services_cutter.util.ImageUtils;

/**
 * Created by ak-buffalo on 21.09.15.
 */
public class LoaderFragment extends BaseFragment implements View.OnClickListener, Loader, ServiceRatingView.CallBacks {
    /** Constants */
    public static final String TAG = LoaderFragment.class.getName();
    private static final String MSG = "message";
    private static final String SHOW_RATING = "show_rating";
    /** Views */
    private LoaderView lvLoader;
    private ServiceRatingView srvRating;
    private TextView tvBackOrCancelBtn, tvLoaderTitleText;
    private RelativeLayout rlFragmentContainer;
    private BackButton afterBackButton;
    /** Listeners */
    private static Loader.Cancelled mOnLoadingListener;
    private static LoaderFragment.CallBacks mRatingCallbacks;

    public static LoaderFragment newInstance(String _msg, LoaderMarker _listener, boolean _showRating) {
        Bundle args = new Bundle();
        args.putString(MSG, _msg);
        args.putBoolean(SHOW_RATING, _showRating);
        if(_listener instanceof Loader.Cancelled) {
            mOnLoadingListener = (Loader.Cancelled) _listener;
        }
        if(_listener instanceof LoaderFragment.CallBacks) {
            mRatingCallbacks = (LoaderFragment.CallBacks) _listener;
        }
        LoaderFragment fragment = new LoaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setToolbarVisibility() { toolbarTitleManager.setToolbarVisibility(true); }

    @Override
    protected void initViews() {
        super.initViews();
        rlFragmentContainer = findView(R.id.rlFragmentContainer_FL);
        int bgColor = defineBGColor(rlFragmentContainer);
        if(ImageUtils.isBlackAndWhiteMode(getActivity())){
            ImageUtils.getFilteredDrawable(getActivity(), rlFragmentContainer.getBackground());
            bgColor = Color.parseColor("#505050");
        }
        lvLoader = findView(R.id.lvLoaderView);
        lvLoader.setTag(bgColor);
        tvLoaderTitleText = findView(R.id.tvLoaderTitleText);
        srvRating = findView(R.id.srvRating_FL);
        srvRating.init(this);
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
                if(v.getTag() == LoaderView.State.PROCESSING && mOnLoadingListener != null){
                    mOnLoadingListener.onLoadingCanceled();
                } else if(afterBackButton != null){
                    afterBackButton.onBackButtonPressed(lvLoader.getCurrentState());
                } else {
                    toolbarTitleManager.setToolbarVisibility(true);
                    getFragmentManager().popBackStackImmediate();
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
        tvBackOrCancelBtn.setText(R.string.str_back_to_dashboard);
        tvBackOrCancelBtn.setTag(LoaderView.State.SUCCESS);
        if(getArguments().getBoolean(SHOW_RATING)){
            srvRating.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dissmissLoadingWithAction(Dismiss afterDissmiss) {
        afterDissmiss.onLoadingDismissed();
    }

    @Override
    public void setButtonPressedBehavior(BackButton _afterBackButton) {
        afterBackButton = _afterBackButton;
    }

    @Override
    public void cancelLoading(String _msg) {
        lvLoader.startFilling(LoaderView.State.CANCELLED);
        tvLoaderTitleText.setText(_msg);
        tvBackOrCancelBtn.setText(R.string.str_back_to_dashboard);
        tvBackOrCancelBtn.setTag(LoaderView.State.CANCELLED);
        if(getArguments().getBoolean(SHOW_RATING)){
            srvRating.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void failedLoading(String _msg, boolean _hasToShowRating) {
        lvLoader.startFilling(LoaderView.State.FAILURE);
        tvLoaderTitleText.setText(_msg);
        tvBackOrCancelBtn.setText(R.string.str_back_to_dashboard);
        tvBackOrCancelBtn.setTag(LoaderView.State.FAILURE);
        if(getArguments().getBoolean(SHOW_RATING) && _hasToShowRating)
            srvRating.setVisibility(View.VISIBLE);
    }

    private int defineBGColor(View _view){
        Bitmap bitmap = ((BitmapDrawable)_view.getBackground()).getBitmap();
        int pixel = bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);

        return Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
    }

    @Override
    public void onRate(int _rate) {
        mRatingCallbacks.onRate(_rate, lvLoader.getCurrentState());
    }

    public interface CallBacks extends LoaderMarker {
        void onRate(int _rate, LoaderView.State _state);
    }

    @Override
    protected int getTitle() { return 0; }

    @Override
    protected int getLayoutResource() { return R.layout.fragment_loader; }
}
