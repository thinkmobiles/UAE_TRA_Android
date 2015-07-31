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

import static com.uae.tra_smart_services.entities.H.parseXmlToMap;

/**
 * Created by ak-buffalo on 27.07.15.
 */
public class ThemeSwitcherView extends BaseCustomSwitcher implements View.OnTouchListener {

    public ThemeSwitcherView(Context context) {
        super(context);
    }

    public ThemeSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private String currentTheme;
    @Override
    public <T> void initPreferences(T prefs) {
        currentTheme = prefs.toString();
    }

    private Map<String, String> colosrMap;
    @Override
    protected void initData(Context context, AttributeSet attrs){
         colosrMap = parseXmlToMap(getContext(), R.xml.themes);
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
        float first = Float.valueOf(position) / Float.valueOf(count);
        float second = Float.valueOf((position + 1)) / Float.valueOf(count);
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

    private int containerWidth;
    private ArrayList<CirclePoint> points;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        containerWidth = w;
        points = new ArrayList<>();
        int iter = 0;
        for (Map.Entry<String,String> entry : colosrMap.entrySet()){
            Paint.Style style;
            if (entry.getValue().equals(currentTheme)){
                style = Paint.Style.STROKE;
            } else {
                style = Paint.Style.FILL_AND_STROKE;
            }
            points.add(getCirclePoint(getCircleDX(containerWidth, colosrMap.size(), iter), style, Color.parseColor(entry.getKey()), entry.getValue()));
            iter++;
        }
    }

    protected void onDraw(Canvas canvas) {
        for (CirclePoint p : points) {
            canvas.drawCircle(p.dX, getHeight() / 2, getHeight() / 4, p.paint);
        }
    }

    long touchDownTime;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean handled = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchDownTime = SystemClock.elapsedRealtime();
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                handled = false;
                break;

            case MotionEvent.ACTION_UP:
                if (SystemClock.elapsedRealtime() - touchDownTime <= 250){
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