package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ak-buffalo on 27.07.15.
 */
public class ThemeSwitcherView extends BaseCustomSwitcher implements View.OnTouchListener {

    private List<Integer> themes;

    private Integer currentTheme;

    private ShapeDrawable mDrawable;

    public ThemeSwitcherView(Context context) {
        super(context);
    }

    public ThemeSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.hex_black_color));
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
    }

    @Override
    public <T> void initPreferences(T prefs) {
        currentTheme = Color.parseColor(prefs.toString());
    }

    @Override
    protected void initData(Context context, AttributeSet attrs){
        TypedArray typedArrayData =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.ThemeSwitcherView, 0, 0);
        try {
            themes = parseThemes(typedArrayData.getTextArray(R.styleable.ThemeSwitcherView_android_entries));
        } finally {
            typedArrayData.recycle();
        }
    }

    @Override
    protected void initViews() {
//        for(Integer color : themes){
//            // Instantiate an ImageView and define its properties
//            ImageView i = new ImageView(getContext());
//            i.setImageResource(R.drawable.ic_temp_left_field_icon);
//            i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
//            i.setLayoutParams(
//                    new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//            );
//            // Add the ImageView to the layout and set the layout as the content view
//            rootView.addView(i);
//        }
//
//        bindView(rootView);
    }

    @Override
    public Type getType() {
        return Type.THEME;
    }

    @Override
    protected void bindView(View view) {
        view.setOnTouchListener(this);
    }

    @Override
    protected void unBindView(View view) {
        // TODO Implements logic
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.v("onTouch", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("onTouch", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.v("onTouch", "ACTION_UP");
                break;
        }
        invalidate();
        mSettingsChangeListener.onSettingsChanged(this, "#ffff0a00");
        return false;
    }

    @Override
    protected int getLayoutId() {
        return -1;
    }

    private static List<Integer> parseThemes(final CharSequence[] strShemas){
        return new ArrayList<Integer>(){
            {
                for (CharSequence strShema : strShemas){
                    add(Color.parseColor(strShema.toString()));
                }
            }
        };
    }
}