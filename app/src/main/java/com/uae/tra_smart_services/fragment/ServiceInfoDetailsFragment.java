package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Vitaliy on 21/08/2015.
 */
public class ServiceInfoDetailsFragment extends BaseFragment implements View.OnTouchListener,
        View.OnClickListener {

    private LinearLayout llFragmentContainer;
    private ImageView ivCloseInfo;

    public static ServiceInfoDetailsFragment newInstance() {

        Bundle args = new Bundle();

        ServiceInfoDetailsFragment fragment = new ServiceInfoDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected final void initViews() {
        llFragmentContainer = findView(R.id.llFragmentContainer_FSID);
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
