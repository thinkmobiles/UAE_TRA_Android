package com.uae.tra_smart_services.fragment;

import android.webkit.WebView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by mobimaks on 16.09.2015.
 */
public class AboutTraFragment extends BaseFragment {

    private WebView wvWebView;

    public static AboutTraFragment newInstance() {
        return new AboutTraFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        wvWebView = findView(R.id.wvText_FAT);
        wvWebView.getSettings().setJavaScriptEnabled(true);
        wvWebView.loadDataWithBaseURL(null, getString(R.string.fragment_about_tra_html), "text/html", "utf-8", null);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_about_tra;
    }

    @Override
    protected int getTitle() {
        return R.string.str_about_tra;
    }
}
