package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by ak-buffalo on 21.08.15.
 */
public class CustomTestLayout extends RelativeLayout {
    public CustomTestLayout(Context context) {
        super(context);
    }

    public CustomTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchDragEvent(DragEvent ev){
        boolean r = super.dispatchDragEvent(ev);
        if (r && (ev.getAction() == DragEvent.ACTION_DRAG_STARTED
                || ev.getAction() == DragEvent.ACTION_DRAG_ENDED)){
            // If we got a start or end and the return value is true, our
            // onDragEvent wasn't called by ViewGroup.dispatchDragEvent
            // So we do it here.
            onDragEvent(ev);
        }
        return r;
    }
}
