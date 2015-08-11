package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.SmsService;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class SmsSpamFragment extends BaseFragment implements TextView.OnClickListener{

    private TextView tvReportNum_FSS, tvBlockNum_FSS;
    private OnSmsServiceSelectListener mServiceSelectListener;

    public static SmsSpamFragment newInstance() {
        return new SmsSpamFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mServiceSelectListener = (OnSmsServiceSelectListener) _activity;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sms_spam;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvReportNum_FSS = findView(R.id.itmReportNum_FSS);
        tvBlockNum_FSS = findView(R.id.itmBlockNum_FSS);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvReportNum_FSS.setOnClickListener(this);
    }

    @Override
    public final void onClick(View _view) {
        switch (_view.getId()){
            case R.id.itmReportNum_FSS:
                mServiceSelectListener.onServiceSelect(SmsService.REPORT);
                break;
            case R.id.itmBlockNum_FSS:
                mServiceSelectListener.onServiceSelect(SmsService.BLOCK);
                break;
        }
    }

    public interface OnSmsServiceSelectListener{
        void onServiceSelect(SmsService _service);
    }
}
