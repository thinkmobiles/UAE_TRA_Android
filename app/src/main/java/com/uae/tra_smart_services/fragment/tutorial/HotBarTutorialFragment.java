package com.uae.tra_smart_services.fragment.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uae.tra_smart_services.R;

/**
 * Created by Vitaliy on 23/09/2015.
 */
public class HotBarTutorialFragment extends Fragment {

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

    }

    private void initListeners() {

    }
}
