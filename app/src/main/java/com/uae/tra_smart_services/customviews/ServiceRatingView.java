package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.global.Service;

/**
 * Created by ak-buffalo on 14.09.15.
 */
public class ServiceRatingView extends LinearLayout implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup ratingGroup;
    private CallBacks mCallBacks;
    public ServiceRatingView(Context context) {
        this(context, null);
    }

    public ServiceRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setGravity(Gravity.CENTER);
        inflate(getContext(), R.layout.layout_service_rating, this);
        initViews();
    }

    public void init(CallBacks _callBacks){
        mCallBacks = _callBacks;
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
