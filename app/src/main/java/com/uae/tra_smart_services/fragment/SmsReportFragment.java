package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class SmsReportFragment extends BaseFragment {
    public static SmsReportFragment newInstance() {
        return new SmsReportFragment();
    }

    @Override
    protected int getLayoutResource() { return R.layout.fragment_sms_report; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private EditText etNumberOfSpammer;
    @Override
    protected void initViews() {
        super.initViews();
        etNumberOfSpammer = findView(R.id.etNumberOfSpammer_FSR);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    protected int getTitle() {
        return R.string.str_report_number;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.menu.menu_send:
                // TODO implement sending logic
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
