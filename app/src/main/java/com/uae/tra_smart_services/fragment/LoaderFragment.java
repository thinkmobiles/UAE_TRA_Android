package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Andrey Korneychuk on 21.09.15.
 */

public class LoaderFragment extends BaseFragment implements View.OnTouchListener{

    private LoaderView loaderView;

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
        loaderView = findView(R.id.lvLoaderView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case  MotionEvent.ACTION_MOVE:
                loaderView.doAnimate();
                break;
        }
        return true;
    }
}
