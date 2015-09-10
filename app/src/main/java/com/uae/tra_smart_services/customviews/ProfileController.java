package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.uae.tra_smart_services.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mobimaks on 09.09.2015.
 */
public final class ProfileController extends LinearLayout implements OnClickListener {

    @IntDef({BUTTON_CANCEL, BUTTON_RESET, BUTTON_CONFIRM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ControllerButton {
    }

    private static final int BUTTON_CANCEL = 0;
    private static final int BUTTON_RESET = 1;
    private static final int BUTTON_CONFIRM = 2;

    private HexagonView hvCancel, hvReset, hvConfirm;

    private OnControllerButtonClickListener mButtonClickListener;

    public ProfileController(Context context) {
        this(context, null);
    }

    public ProfileController(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setBaselineAligned(false);
        inflate(getContext(), R.layout.layout_profile_controller, this);
        initViews();
        setListeners();
    }

    private void initViews() {
        hvCancel = (HexagonView) findViewById(R.id.hvCancel_LPC);
        hvReset = (HexagonView) findViewById(R.id.hvReset_LPC);
        hvConfirm = (HexagonView) findViewById(R.id.hvConfirm_LPC);
    }

    private void setListeners() {
        hvCancel.setOnClickListener(this);
        hvReset.setOnClickListener(this);
        hvConfirm.setOnClickListener(this);
    }

    public void setOnButtonClickListener(OnControllerButtonClickListener _buttonClickListener) {
        mButtonClickListener = _buttonClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mButtonClickListener != null) {
            switch (v.getId()) {
                case R.id.hvCancel_LPC:
                    mButtonClickListener.onControllerButtonClick(BUTTON_CANCEL);
                    break;
                case R.id.hvReset_LPC:
                    mButtonClickListener.onControllerButtonClick(BUTTON_RESET);
                    break;
                case R.id.hvConfirm_LPC:
                    mButtonClickListener.onControllerButtonClick(BUTTON_CONFIRM);
                    break;
            }
        }
    }

    public interface OnControllerButtonClickListener {
        void onControllerButtonClick(@ControllerButton final int _buttonId);
    }
}
