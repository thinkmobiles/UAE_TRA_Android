package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.entities.CirclePoint;

import java.util.ArrayList;
import java.util.Map;

import static com.uae.tra_smart_services.global.H.parseXmlToMap;

/**
 * Created by Andrey Korneychuk on 27.07.15.
 */
public class ThemeSwitcherView extends BaseCustomSwitcher implements View.OnTouchListener {

    public ThemeSwitcherView(Context context) {
        super(context);
    }

    public ThemeSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private String mCurrentTheme;
    @Override
    public <T> void initPreferences(T prefs) {
        mCurrentTheme = prefs.toString();
    }

    private Map<String, String> mColorsMap;
    @Override
    protected void initData(Context context, AttributeSet attrs){
         mColorsMap = parseXmlToMap(getContext(), R.xml.themes);
    }

    private CirclePoint getCirclePoint(float circleDX, Paint.Style style, int color, String colorThema){
        CirclePoint p = new CirclePoint();
        p.dX = Math.round(circleDX);
        p.colorThema = colorThema;
        p.paint = new Paint();
        p.paint.setStyle(style);
        p.paint.setStrokeWidth(5f);
        p.paint.setColor(color);
        return p;
    }

    @Override
    protected void initListeners() {
        setOnTouchListener(this);
    }

    private float getCircleDX(float width, int count, int position){
        float first =  position / (float) count;
        float second = (position + 1) / (float) count;
        float dX = width * (first + second) / 2f;
        return dX;
    }

    @Override
    public Type getType() {
        return Type.THEME;
    }

    @Override
    protected void bindView(View view) {/*Unimplemented method*/}

    @Override
    protected void unBindView(View view) {/*Unimplemented method*/}

    private int mContainerWidth;
    private ArrayList<CirclePoint> points;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContainerWidth = w;
        points = new ArrayList<>();
        int iter = 0;
        for (Map.Entry<String,String> entry : mColorsMap.entrySet()){
            Paint.Style style;
            if (entry.getValue().equals(mCurrentTheme)){
                style = Paint.Style.STROKE;
            } else {
                style = Paint.Style.FILL_AND_STROKE;
            }
            points.add(getCirclePoint(getCircleDX(mContainerWidth, mColorsMap.size(), iter), style, Color.parseColor(entry.getKey()), entry.getValue()));
            iter++;
        }
    }

    protected void onDraw(Canvas canvas) {
        for (CirclePoint p : points) {
            canvas.drawCircle(p.dX, getHeight() / 2, getHeight() / 4, p.paint);
        }
    }

    private long mTouchDownTime;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean handled = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouchDownTime = SystemClock.elapsedRealtime();
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                handled = false;
                break;

            case MotionEvent.ACTION_UP:
                if (SystemClock.elapsedRealtime() - mTouchDownTime <= 250){
                    return handleClick(event.getX(), event.getY());
                }
                break;
        }
        return handled;
    }

    private boolean handleClick(float dX, float dY ){
        for(CirclePoint point : points) {
            if (isInArea(point, dX, dY)) {
                point.paint.setStyle(Paint.Style.STROKE);
                mSettingsChangeListener.onSettingsChanged(this, point.colorThema);
            } else {
                point.paint.setStyle(Paint.Style.FILL_AND_STROKE);
            }
        }
        invalidate();
        return true;
    }

    private boolean isInArea(CirclePoint point, float x, float y){
        float halfSector = Float.valueOf(getWidth()) / points.size() / 2;
        return x >= point.dX - halfSector && x <= point.dX + halfSector;
    }

    @Override
    protected int getLayoutId() {
        return -1;
    }
}