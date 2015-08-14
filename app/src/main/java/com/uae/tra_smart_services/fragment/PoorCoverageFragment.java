package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class PoorCoverageFragment extends BaseFragment implements AlertDialogFragment.OnOkListener{

    public static PoorCoverageFragment newInstance() {
        return new PoorCoverageFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_poor_coverage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private EditText etLocation;
    private SeekBar sbPoorCoverage;
    @Override
    protected void initViews() {
        super.initViews();
        etLocation = findView(R.id.etLocation_FPC);
        sbPoorCoverage = findView(R.id.sbPoorCoverage_FPC);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    protected int getTitle() {
        return R.string.str_signal_coverage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        progressDialogManager.showProgressDialog(getString(R.string.str_checking));
        switch (item.getItemId()){
            case R.id.action_send:
                // TODO implement sending logic
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
    }
}
