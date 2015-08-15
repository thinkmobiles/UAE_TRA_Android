package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.ServicesRecyclerViewAdapter;
import com.uae.tra_smart_services.customviews.HexagonalButtonsLayout;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.uae.tra_smart_services.customviews.HexagonalButtonsLayout.StaticService.*;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class HexagonHomeFragment extends BaseFragment implements HexagonalButtonsLayout.OnServiceSelected {

    protected RecyclerView mRecyclerView;
    protected ServicesRecyclerViewAdapter mAdapter;
    private HexagonalButtonsLayout mHexagonalButtonsLayout;
    protected RecyclerView.LayoutManager mLayoutManager;
    private List<Service> mDataSet;

    private OnServiceSelectListener mServiceSelectListener;
    private OnStaticServiceSelectListener mStaticServiceSelectListener;

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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initServiceList();

        mLayoutManager = new StaggeredGridLayoutManager(4, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHexagonalButtonsLayout.setServiceSelectedListener(this);
        mHexagonalButtonsLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mRecyclerView.getLayoutParams();
                lp.setMargins(lp.leftMargin, (int) -mHexagonalButtonsLayout.getHalfOuterRadius(), lp.rightMargin, lp.bottomMargin);
                mRecyclerView.setLayoutParams(lp);

                mAdapter = new ServicesRecyclerViewAdapter(getActivity().getApplicationContext(), mDataSet,
                        mHexagonalButtonsLayout.getHalfOuterRadius());
                mAdapter.setServiceSelectListener(mServiceSelectListener);
                mRecyclerView.setAdapter(mAdapter);

                mHexagonalButtonsLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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
            mStaticServiceSelectListener.onStaticServiceSelect(INTERNET_SPEED_TEST);
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
