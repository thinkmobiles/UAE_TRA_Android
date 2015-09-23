package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Andrey Korneychuk on 21.09.15.
 */

public class LoaderFragment extends BaseFragment implements View.OnClickListener {

    private LoaderView loaderView;
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
        loaderView = findView(R.id.lvLoaderView);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        rlFragmentContainer.setOnClickListener(this);
    }

    boolean state = true;
    @Override
    public void onClick(View v) {
        if(state == true){
            loaderView.startProcessing();
        } else {
            loaderView.startFilling();
        }
        state = !state;
    }
}
