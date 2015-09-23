package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Andrey Korneychuk on 21.09.15.
 */

public class LoaderFragment extends BaseFragment implements View.OnClickListener, LoaderView.OnPressListener {

    private LoaderView lvLoader;
    private TextView tvLoaderBackButton, tvLoaderTitleText;
    private RelativeLayout rlFragmentContainer;

    public static LoaderFragment newInstance() {
        Bundle args = new Bundle();
        
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
        rlFragmentContainer = findView(R.id.rlFragmentContainer_FL);
        lvLoader = findView(R.id.lvLoaderView);
        tvLoaderTitleText = findView(R.id.tvLoaderTitleText);
        tvLoaderBackButton = findView(R.id.tvLoaderBackButton);
        lvLoader.setOnPressListener(this);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        rlFragmentContainer.setOnClickListener(this);
        tvLoaderBackButton.setOnClickListener(this);
    }

    boolean state = true;
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlFragmentContainer_FL){
            if(state == true){
                lvLoader.startProcessing();
            } else {
                lvLoader.startFilling();
                tvLoaderBackButton.setVisibility(View.VISIBLE);
                tvLoaderTitleText.setText(R.string.str_reuqest_has_been_sent);
            }
            state = !state;
        } else if (v.getId() == R.id.tvLoaderBackButton){
            getFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public boolean onPressed(LoaderView.State _state) {
        getFragmentManager().popBackStackImmediate();
        return true;
    }

    @Override
    protected void setToolbarVisibility() {
        toolbarTitleManager.setToolbarVisibility(false);
    }
}
