package com.uae.tra_smart_services.fragment.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.TutorialPagerAdapter;

/**
 * Created by Vitaliy on 24/09/2015.
 */
public class TutorialContainerFragment extends Fragment {

    private ViewPager vpTutorial;
    private TutorialPagerAdapter mAdapter;

    public static TutorialContainerFragment newInstance() {
        TutorialContainerFragment fragment = new TutorialContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _viewGroup, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_tutorial_container, _viewGroup, false);

        initViews(view);
        initListeners();

        return view;
    }

    private void initViews(final View _view) {
        vpTutorial = (ViewPager) _view.findViewById(R.id.vpTutorial_FTC);
        mAdapter = new TutorialPagerAdapter(getChildFragmentManager());
        vpTutorial.setAdapter(mAdapter);
    }

    private void initListeners() {

    }

}
