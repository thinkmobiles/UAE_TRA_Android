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
public class SmsServiceListFragment extends BaseFragment implements TextView.OnClickListener{

    public static SmsServiceListFragment newInstance() {
        return new SmsServiceListFragment();
    }

    private OnSmsServiceSelectListener mServiceSelectListener;
    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mServiceSelectListener = (OnSmsServiceSelectListener) _activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getTitle() {
        return R.string.str_sms_service_list;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sms_spam;
    }

    private TextView tvReportNum_FSS, tvBlockNum_FSS;
    @Override
    protected void initViews() {
        super.initViews();
        tvReportNum_FSS = findView(R.id.itmReportNum_FSS);
        tvReportNum_FSS.setText(SmsService.REPORT.toString());
        tvBlockNum_FSS = findView(R.id.itmBlockNum_FSS);
        tvBlockNum_FSS.setText(SmsService.BLOCK.toString());
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvReportNum_FSS.setOnClickListener(this);
        tvBlockNum_FSS.setOnClickListener(this);
    }

    @Override
    public final void onClick(View _view) {
        switch (_view.getId()){
            case R.id.itmReportNum_FSS:
                mServiceSelectListener.onSmsServiceChildSelect(SmsService.REPORT);
                break;
            case R.id.itmBlockNum_FSS:
                mServiceSelectListener.onSmsServiceChildSelect(SmsService.BLOCK);
                break;
        }
    }

    public interface OnSmsServiceSelectListener{
        void onSmsServiceChildSelect(SmsService _service);
    }
}
