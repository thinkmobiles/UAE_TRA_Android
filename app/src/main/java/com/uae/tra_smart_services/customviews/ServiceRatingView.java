package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.global.Service;

/**
 * Created by ak-buffalo on 14.09.15.
 */
public class ServiceRatingView extends RelativeLayout implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup ratingGroup;
    private CallBacks mCallBacks;
    public ServiceRatingView(Context context) {
        this(context, null);
    }

    public ServiceRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.layout_service_rating, this);
    }

    public void init(CallBacks _callBacks){
        mCallBacks = _callBacks;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        inflate(getContext(), R.layout.layout_service_rating, this);
        initViews();
    }

    private void initViews(){
        ratingGroup = (RadioGroup) findViewById(R.id.rgDomainCheckServiceRating_FDCH);
        ratingGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        if(radioButton.isChecked()){
            int rating = Integer.valueOf(radioButton.getTag().toString());
            mCallBacks.onRate(rating);
        }
    }

    public interface CallBacks{
        void onRate(int _rate);
    }
}
