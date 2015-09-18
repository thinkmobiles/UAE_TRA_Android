package com.uae.tra_smart_services.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Vitaliy on 21/08/2015.
 */
public class ServiceInfoDetailsFragment extends BaseFragment implements View.OnTouchListener,
        View.OnClickListener {

    private static final String HEX_ICON_RES = "hexagon_icon_res";
    private static final String DESCRIPTION = "description";

    private LinearLayout llFragmentContainer;
    private ImageView ivCloseInfo;
    private TextView tvServiceInfoContent;
    private HexagonView hvServiceInfoIcon;
    private Drawable mServiceInfoIconDrawable;
    private String mServiceInfoContent;

    public static ServiceInfoDetailsFragment newInstance(@DrawableRes int _hexagonSrc, @StringRes int _text) {
        Bundle args = new Bundle();
        args.putInt(HEX_ICON_RES, _hexagonSrc);
        args.putInt(DESCRIPTION, _text);
        ServiceInfoDetailsFragment fragment = new ServiceInfoDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mServiceInfoIconDrawable = ContextCompat.getDrawable(getActivity(), getArguments().getInt(HEX_ICON_RES));
        mServiceInfoContent = getResources().getString(getArguments().getInt(DESCRIPTION));
    }

    @Override
    protected final void initViews() {
        llFragmentContainer = findView(R.id.llFragmentContainer_FSID);
        tvServiceInfoContent = findView(R.id.tvServiceInfoContent_FSID);
        tvServiceInfoContent.setText(mServiceInfoContent);
        hvServiceInfoIcon = findView(R.id.hvServiceInfoIcon_FSID);
        hvServiceInfoIcon.setHexagonSrcDrawable(mServiceInfoIconDrawable);
        ivCloseInfo = findView(R.id.ivCloseInfo_FSID);
    }

    @Override
    protected final void initListeners() {
        llFragmentContainer.setOnTouchListener(this);
        ivCloseInfo.setOnClickListener(this);
    }

    @Override
    protected final int getTitle() {
        return 0;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_service_info_details;
    }

    @Override
    public final boolean onTouch(final View _view, final MotionEvent _event) {
        return true;
    }

    @Override
    public final void onClick(final View _view) {
        switch (_view.getId()) {
            case R.id.ivCloseInfo_FSID:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
