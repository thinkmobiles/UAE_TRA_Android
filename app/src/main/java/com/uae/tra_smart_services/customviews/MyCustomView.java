package com.uae.tra_smart_services.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.uae.tra_smart_services.R;

/**
 * Created by ak-buffalo on 23.07.15.
 */
public class MyCustomView extends View {
    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, AttributeSet _attrs) {
        super(context, _attrs);
        TypedArray attrs = context.obtainStyledAttributes(_attrs, null);

        attrs.getBoolean(R.styleable.MyCustomView_custom_attr, false);
        attrs.recycle();
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
