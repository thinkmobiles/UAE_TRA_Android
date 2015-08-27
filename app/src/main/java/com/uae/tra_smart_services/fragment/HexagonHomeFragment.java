package com.uae.tra_smart_services.fragment;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.ServicesRecyclerViewAdapter;
import com.uae.tra_smart_services.customviews.HexagonalButtonsLayout;
import com.uae.tra_smart_services.customviews.HexagonalHeader;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.uae.tra_smart_services.customviews.HexagonalButtonsLayout.StaticService.INTERNET_SPEED_TEST;
import static com.uae.tra_smart_services.customviews.HexagonalButtonsLayout.StaticService.POOR_COVERAGE_SERVICE;
import static com.uae.tra_smart_services.customviews.HexagonalButtonsLayout.StaticService.SMS_SPAM_SERVICE;
import static com.uae.tra_smart_services.customviews.HexagonalButtonsLayout.StaticService.VERIFICATION_SERVICE;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class HexagonHomeFragment extends BaseFragment implements HexagonalButtonsLayout.OnServiceSelected {


    public final String RECYCLER_TAG = "RecyclerView_test";

    private HexagonalButtonsLayout mHexagonalButtonsLayout;
    private RecyclerView mRecyclerView;
    private HexagonalHeader mHexagonalHeader;

    private ServicesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Service> mDataSet;

    private OnServiceSelectListener mServiceSelectListener;
    private OnStaticServiceSelectListener mStaticServiceSelectListener;

    private ValueAnimator mHexagonalHeaderAnimator, mHexagonButtonsAnimator, mHexagonHeaderReverseAnimator,
                            mHexagonButtonsReverseAnimator;
    private AnimatorSet mAnimatorSet, mReverseAnimator;

    private boolean isCollapsed = false;
    private boolean isAnimating = false;
    private boolean isScrollUp = false;

    private float mAnimationProgress = 0f;

    public static HexagonHomeFragment newInstance() {
        return new HexagonHomeFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mServiceSelectListener = (OnServiceSelectListener) _activity;
        mStaticServiceSelectListener = (OnStaticServiceSelectListener) _activity;
    }

    @Override
    protected void initViews() {
        mRecyclerView = findView(R.id.rvServices_FHH);
        mHexagonalButtonsLayout = findView(R.id.hblHexagonalButtons_FHH);
        mHexagonalHeader = findView(R.id.hhHeader_FHH);
    }

    @Override
    protected void initListeners() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (RecyclerView.SCROLL_STATE_SETTLING == newState ||
                            RecyclerView.SCROLL_STATE_IDLE == newState
                            && mAnimationProgress != 0f && mAnimationProgress != 1f) {

                    endAnimation();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                isScrollUp = dy > 0;

                if (!mHexagonalHeaderAnimator.isRunning() && !mHexagonHeaderReverseAnimator.isRunning()
                        && ((mAnimationProgress < 1f && dy > 0) || (mAnimationProgress > 0f && dy < 0))) {
                    mAnimationProgress += dy * 0.001f;

                    if (mAnimationProgress >= 1f) {
                        mAnimationProgress = 1f;
                        isCollapsed = true;
                    } else if (mAnimationProgress <= 0f) {
                        mAnimationProgress = 0f;
                        isCollapsed = false;
                    }

                    mHexagonalHeader.setAnimationProgress(mAnimationProgress);
                    mHexagonalButtonsLayout.setAnimationProgress(mAnimationProgress);
                }


                Log.d(RECYCLER_TAG, "Scrolled - " + String.valueOf(dy));
            }
        });
        mHexagonalButtonsLayout.setServiceSelectedListener(this);
        mHexagonalButtonsLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isAdded()) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mRecyclerView.getLayoutParams();
                    lp.setMargins(lp.leftMargin, (int) -mHexagonalButtonsLayout.getHalfOuterRadius(), lp.rightMargin, lp.bottomMargin);
                    mRecyclerView.setLayoutParams(lp);

                    mAdapter = new ServicesRecyclerViewAdapter(getActivity().getApplicationContext(), mDataSet,
                            mHexagonalButtonsLayout.getHalfOuterRadius());
                    mAdapter.setServiceSelectListener(mServiceSelectListener);
                    mRecyclerView.setAdapter(mAdapter);

                    mHexagonalButtonsLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    private void endAnimation() {
        if ((isCollapsed && !isScrollUp) || (!isCollapsed && !isScrollUp && mAnimationProgress != 1f)) {
            mHexagonHeaderReverseAnimator.setFloatValues(mAnimationProgress, 0f);
            mHexagonHeaderReverseAnimator.setDuration((int) (600 / 2 * mAnimationProgress));
            mHexagonHeaderReverseAnimator.start();
            isCollapsed = false;
        } else if ((!isCollapsed && isScrollUp) || (isCollapsed && isScrollUp && mAnimationProgress != 0f)) {
            mHexagonalHeaderAnimator.setFloatValues(mAnimationProgress, 1f);
            mHexagonalHeaderAnimator.setDuration((int) (600 / 2 * (1 - mAnimationProgress)));
            mHexagonalHeaderAnimator.start();
            isCollapsed = true;
        }
    }

    @Override
    protected void initData() {
        mHexagonalHeaderAnimator = ValueAnimator.ofFloat(0f, 1f);
        mHexagonalHeaderAnimator.setDuration(600);
        mHexagonalHeaderAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimationProgress = (float) animation.getAnimatedValue();
                mHexagonalHeader.setAnimationProgress( (float) animation.getAnimatedValue());
                mHexagonalButtonsLayout.setAnimationProgress((float) animation.getAnimatedValue());
            }
        });

        mHexagonHeaderReverseAnimator = ValueAnimator.ofFloat(1f, 0f);
        mHexagonHeaderReverseAnimator.setDuration(600);
        mHexagonHeaderReverseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimationProgress = (float) animation.getAnimatedValue();
                mHexagonalHeader.setAnimationProgress((float) animation.getAnimatedValue());
                mHexagonalButtonsLayout.setAnimationProgress((float) animation.getAnimatedValue());
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initServiceList();

        mLayoutManager = new StaggeredGridLayoutManager(4, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initServiceList() {
        mDataSet = new ArrayList<>(Arrays.asList(Service.values()));
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_service_list_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_hexagon_home;
    }

    @Override
    public final void serviceSelected(final int _id) {
        if(VERIFICATION_SERVICE.isEquals(_id)) {
            mStaticServiceSelectListener.onStaticServiceSelect(VERIFICATION_SERVICE);
        } else if (SMS_SPAM_SERVICE.isEquals(_id)) {
            mStaticServiceSelectListener.onStaticServiceSelect(SMS_SPAM_SERVICE);
        } else if (POOR_COVERAGE_SERVICE.isEquals(_id)) {
            mStaticServiceSelectListener.onStaticServiceSelect(POOR_COVERAGE_SERVICE);
        } else if (INTERNET_SPEED_TEST.isEquals(_id)) {
        }
    }

    public interface OnServiceSelectListener {
        void onServiceSelect(final Service _service);
    }

    public interface OnStaticServiceSelectListener {
        void onStaticServiceSelect(final HexagonalButtonsLayout.StaticService _service);
    }

    @Override
    protected void setToolbarVisibility() {
        toolbarTitleManager.setToolbarVisibility(false);
    }
}
