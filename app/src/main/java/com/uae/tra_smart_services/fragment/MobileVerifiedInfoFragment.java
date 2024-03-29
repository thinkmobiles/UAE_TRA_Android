package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 29.09.15.
 */

public class MobileVerifiedInfoFragment extends BaseFragment {

    private static final String SELECTED_DEVICE_KEY = "SELECTED_DEVICE_KEY";

    private TextView tvApprovedDevice;

    private ArrayList<SearchDeviceResponseModel> mApprovedDevices;

    public static MobileVerifiedInfoFragment newInstance(final SearchDeviceResponseModel.List _device) {
        MobileVerifiedInfoFragment fragment = new MobileVerifiedInfoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(SELECTED_DEVICE_KEY, _device);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mApprovedDevices = getArguments().getParcelableArrayList(SELECTED_DEVICE_KEY);
    }

    @Override
    protected int getTitle() {
        return R.string.device_approval;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvApprovedDevice = findView(R.id.tvApprovedDevice_FADR);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StringBuilder builder = new StringBuilder();
        for (SearchDeviceResponseModel approvedDevice : mApprovedDevices) {
            builder.append(approvedDevice.marketingName).append(" ").append(approvedDevice.allocationDate).append('\n');
        }
        tvApprovedDevice.setText(builder);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mobile_verification_result;
    }
}