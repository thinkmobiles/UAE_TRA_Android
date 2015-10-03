package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.activity.AuthorizationActivity;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.SmsService;

/**
 * Created by ak-buffalo on 11.08.15.
 */
@Deprecated
public class SmsServiceListFragment extends BaseFragment implements TextView.OnClickListener {

    private TextView tvReportNum, tvBlockNum;

    private OnSmsServiceSelectListener mServiceSelectListener;

    public static SmsServiceListFragment newInstance() {
        return new SmsServiceListFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnSmsServiceSelectListener) {
            mServiceSelectListener = (OnSmsServiceSelectListener) _activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvReportNum = findView(R.id.itmReportNum_FSS);
        tvReportNum.setText(SmsService.REPORT.toString());

        tvBlockNum = findView(R.id.itmBlockNum_FSS);
        tvBlockNum.setText(SmsService.BLOCK.toString());
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvReportNum.setOnClickListener(this);
        tvBlockNum.setOnClickListener(this);
    }

    @Override
    public final void onClick(View _view) {
        if (mServiceSelectListener != null) {
            switch (_view.getId()) {
                case R.id.itmReportNum_FSS:
                    openReportSmsScreenOrLogin();
                    break;
                case R.id.itmBlockNum_FSS:
                    mServiceSelectListener.onSmsServiceChildSelect(SmsService.BLOCK);
                    break;
            }
        }
    }

    private void openReportSmsScreenOrLogin() {
        if (TRAApplication.isLoggedIn() && mServiceSelectListener != null) {
            mServiceSelectListener.onSmsServiceChildSelect(SmsService.REPORT);
        } else {
            Intent intent = AuthorizationActivity.getStartForResultIntent(getActivity(), SmsService.REPORT);
            startActivityForResult(intent, C.REQUEST_CODE_LOGIN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == C.REQUEST_CODE_LOGIN && resultCode == C.LOGIN_SUCCESS) {
            openReportSmsScreenOrLogin();
        }
    }

    @Override
    protected int getTitle() {
        return R.string.str_sms_service_list;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sms_spam;
    }

    @Override
    public void onDetach() {
        mServiceSelectListener = null;
        super.onDetach();
    }

    @Deprecated
    public interface OnSmsServiceSelectListener {
        void onSmsServiceChildSelect(SmsService _service);
    }
}
