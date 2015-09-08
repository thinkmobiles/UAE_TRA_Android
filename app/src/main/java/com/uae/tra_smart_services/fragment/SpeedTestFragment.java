package com.uae.tra_smart_services.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.ProgressDialog.MyDialogInterface;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.robo_requests.SpeedTestRequest;

import java.math.BigDecimal;

/**
 * Created by mobimaks on 07.09.2015.
 */
public final class SpeedTestFragment extends BaseFragment implements OnClickListener, MyDialogInterface, RequestListener<Long> {

    private static final String KEY_SPEED_TEST_REQUEST = "SPEED_TEST_REQUEST";

    private TextView tvResult;
    private Button btnRunSpeedtest;

    private SpeedTestRequest mSpeedTestRequest;

    public static SpeedTestFragment newInstance() {
        return new SpeedTestFragment();
    }

    @Override
    protected final void initViews() {
        super.initViews();
        tvResult = findView(R.id.tvResult_FS);
        btnRunSpeedtest = findView(R.id.btnRunSpeedtest_FS);
    }

    @Override
    protected final void initListeners() {
        super.initListeners();
        btnRunSpeedtest.setOnClickListener(this);
    }

    @Override
    public final void onClick(final View _view) {
        showProgressDialog(getString(R.string.fragment_speed_test_progress), this);
        mSpeedTestRequest = new SpeedTestRequest();
        getSpiceManager().execute(mSpeedTestRequest, KEY_SPEED_TEST_REQUEST, DurationInMillis.ALWAYS_EXPIRED, this);
    }

    @Override
    public final void onRequestSuccess(final Long _kbPerSecond) {
        if (isAdded() && _kbPerSecond != null) {
            double mBitPerSecond = _kbPerSecond / 1024f;
            BigDecimal bd = new BigDecimal(mBitPerSecond);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            tvResult.setText(getString(R.string.fragment_speed_test_result, bd.toString()));
            hideProgressDialog();
        }
    }

    @Override
    public final void onDialogCancel() {
        if (mSpeedTestRequest != null) {
            mSpeedTestRequest.cancel();
        }
    }

    @Override
    public final void onRequestFailure(final SpiceException _spiceException) {
        processError(_spiceException);
    }

    @Override
    protected final int getTitle() {
        return R.string.fragment_speed_test_title;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_speedtest;
    }
}
