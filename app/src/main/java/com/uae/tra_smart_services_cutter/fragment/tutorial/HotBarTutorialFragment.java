package com.uae.tra_smart_services_cutter.fragment.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.uae.tra_smart_services_cutter.R;
import com.uae.tra_smart_services_cutter.customviews.HexagonalButtonsLayout;
import com.uae.tra_smart_services_cutter.customviews.tutorial.HotBarTipView;

/**
 * Created by Mikazme on 23/09/2015.
 */
public class HotBarTutorialFragment extends Fragment {

    private HexagonalButtonsLayout hblHexagonButtons;
    private HotBarTipView hbtvHotBarTipView;

    public static HotBarTutorialFragment newInstance() {
        HotBarTutorialFragment fragment = new HotBarTutorialFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _viewGroup, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_hot_bar_tutorial, _viewGroup, false);

        initViews(view);
        initListeners();

        return view;
    }

    private void initViews(final View _view) {
        hblHexagonButtons = (HexagonalButtonsLayout) _view.findViewById(R.id.hblHexagonalButtons_FHBT);
        hblHexagonButtons.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                hbtvHotBarTipView.setCenterPoints(hblHexagonButtons.getCenters());
                return true;
            }
        });

        hbtvHotBarTipView = (HotBarTipView) _view.findViewById(R.id.hbtvHotBarTipView_FHBT);
    }

    private void initListeners() {

    }
}
