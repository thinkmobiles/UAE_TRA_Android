package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by mobimaks on 11.08.2015.
 */
public class DeviceApprovalFragment extends BaseFragment {

    private static final String SELECTED_DEVICE_KEY = "SELECTED_DEVICE_KEY";

    private TextView tvApprovedDevice;

    private String mSelectedDevice;

    public static DeviceApprovalFragment newInstance(final String _device) {
        DeviceApprovalFragment fragment = new DeviceApprovalFragment();
        Bundle args = new Bundle();
        args.putString(SELECTED_DEVICE_KEY, _device);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectedDevice = getArguments().getString(SELECTED_DEVICE_KEY);
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvApprovedDevice = findView(R.id.tvApprovedDevice_FADR);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvApprovedDevice.setText(mSelectedDevice);
    }

    @Override
    public void onStart() {
        super.onStart();
        getToolbarTitleManager().setTitle(R.string.device_approval);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_device_approval;
    }
}
