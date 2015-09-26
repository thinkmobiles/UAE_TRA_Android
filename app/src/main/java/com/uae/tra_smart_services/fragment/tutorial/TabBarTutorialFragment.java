package com.uae.tra_smart_services.fragment.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uae.tra_smart_services.R;

/**
 * Created by Vitaliy on 24/09/2015.
 */
public class TabBarTutorialFragment extends Fragment {

    public static TabBarTutorialFragment newInstance() {
        TabBarTutorialFragment fragment = new TabBarTutorialFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _viewGroup, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_tab_bar_tutorial, _viewGroup, false);

        initViews(view);
        initListeners();

        return view;
    }

    private void initViews(final View _view) {

    }

    private void initListeners() {

    }
}
