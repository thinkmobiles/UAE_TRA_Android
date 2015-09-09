package com.uae.tra_smart_services.fragment;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Vitaliy on 21/08/2015.
 */
public class ServiceInfoFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener {

    private ImageView ivCloseInfo;
    private HexagonView hvServiceInfo, hvRequiredDocuments, hvTermsAndConditions,
            hvServicePackage, hvProcessingTime, hvServiceFees, hvContactInCharge;
    private RelativeLayout rlFragmentContainer;

    public static ServiceInfoFragment newInstance() {
        ServiceInfoFragment fragment = new ServiceInfoFragment();
        return fragment;
    }

    @Override
    protected final void initViews() {
        ivCloseInfo = findView(R.id.ivCloseInfo_FSI);
        rlFragmentContainer = findView(R.id.rlFragmentContainer_FSI);
        hvServiceInfo = findView(R.id.hvSearchButton_FS);
        hvRequiredDocuments = findView(R.id.hvRequiredDocuments_FSI);
        hvTermsAndConditions = findView(R.id.hvTermsAndConditions_FSI);
        hvServicePackage = findView(R.id.hvServicePackage_FSI);
        hvProcessingTime = findView(R.id.hvProcessingTime_FSI);
        hvServiceFees = findView(R.id.hvServiceFees_FSI);
        hvContactInCharge = findView(R.id.hvContactInCharge_FSI);
    }

    @Override
    protected final void initListeners() {
        ivCloseInfo.setOnClickListener(this);
        rlFragmentContainer.setOnTouchListener(this);
        hvServiceInfo.setOnClickListener(this);
        hvRequiredDocuments.setOnClickListener(this);
        hvTermsAndConditions.setOnClickListener(this);
        hvServicePackage.setOnClickListener(this);
        hvProcessingTime.setOnClickListener(this);
        hvServiceFees.setOnClickListener(this);
        hvContactInCharge.setOnClickListener(this);
    }

    @Override
    public final void onClick(final View _view) {
        switch (_view.getId()) {
            case R.id.ivCloseInfo_FSI:
                getFragmentManager().popBackStack();
                break;
            case R.id.hvSearchButton_FS:
            case R.id.hvRequiredDocuments_FSI:
            case R.id.hvTermsAndConditions_FSI:
            case R.id.hvServicePackage_FSI:
            case R.id.hvProcessingTime_FSI:
            case R.id.hvServiceFees_FSI:
            case R.id.hvContactInCharge_FSI:
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.rlGlobalContainer_AH, ServiceInfoDetailsFragment.newInstance())
                        .commit();
                break;
        }
    }

    @Override
    protected final int getTitle() {
        return 0;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_service_info;
    }

    @Override
    public final boolean onTouch(final View _view, final MotionEvent _event) {
        return true;
    }
}
