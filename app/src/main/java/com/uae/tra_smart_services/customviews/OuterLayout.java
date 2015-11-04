package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.uae.tra_smart_services.R;

/**
 * Created by and on 03.11.15.
 */

public class OuterLayout extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener, LoaderView.Callbacks {
    /*private ViewDragHelper mDragHelper;
    private ListView listView;
    private LoaderView loaderView;

    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private int mDraggingState = 0;
    private int mDraggingBorder;
    private int mVerticalRange;
    private boolean mIsOpen;

    public OuterLayout(Context context) { this(context, null); }

    public OuterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mVerticalRange = (int) (h * 0.66);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent _event) {
        if (canMoveList(_event) && mDragHelper.shouldInterceptTouchEvent(_event)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event) {
        if (canMoveList(_event) || isMoving()) {
            mDragHelper.processTouchEvent(_event);
            return true;
        } else {
            return super.onTouchEvent(_event);
        }
    }

    @Override
    protected void onFinishInflate() {
        listView = (ListView) findViewById(R.id.listview);
        loaderView = (LoaderView) findViewById(R.id.loaderView);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mIsOpen = false;
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    private boolean canMoveList(MotionEvent event) {
        int[] queenLocation = new int[2];
        listView.getLocationOnScreen(queenLocation);
        int upperLimit = queenLocation[1] + listView.getMeasuredHeight();
        int lowerLimit = queenLocation[1];
        int y = (int) event.getRawY();
        return (y > lowerLimit && y < upperLimit);
    }

    @Override
    public void onGlobalLayout() {
//        listView = (ListView) findViewById(R.id.listview);
//        loaderView = (LoaderView) findViewById(R.id.loaderView);
    }

    @Override
    public void computeScroll() { // needed for automatic settling.
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragHelperCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return (child.getId() == R.id.outerLayout);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mVerticalRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = mVerticalRange;
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == mDraggingState) { // no change
                return;
            }
            if ((mDraggingState == ViewDragHelper.STATE_DRAGGING || mDraggingState == ViewDragHelper.STATE_SETTLING) &&
                    state == ViewDragHelper.STATE_IDLE) {
                // the view stopped from moving.

                if (mDraggingBorder == 0) {
                    onStopDraggingToClosed();
                } else if (mDraggingBorder == mVerticalRange) {
                    mIsOpen = true;
                }
            }
            if (state == ViewDragHelper.STATE_DRAGGING) {
                onStartDragging();
            }
            mDraggingState = state;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final float rangeToCheck = mVerticalRange;
            if (mDraggingBorder == 0) {
                mIsOpen = false;
                return;
            }
            if (mDraggingBorder == rangeToCheck) {
                mIsOpen = true;
                return;
            }
            boolean settleToOpen = false;
            if (yvel > AUTO_OPEN_SPEED_LIMIT) { // speed has priority over position
                settleToOpen = true;
            } else if (yvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false;
            } else if (mDraggingBorder > rangeToCheck / 2) {
                settleToOpen = true;
            } else if (mDraggingBorder < rangeToCheck / 2) {
                settleToOpen = false;
            }

            final int settleDestY = settleToOpen ? mVerticalRange : 0;

            if(mDragHelper.settleCapturedViewAt(0, settleDestY)) {
                ViewCompat.postInvalidateOnAnimation(OuterLayout.this);
            }
        }
    }

    private void onStopDraggingToClosed() {
        // To be implemented
    }

    private void onStartDragging() {
        // To be implemented
    }

    public boolean isMoving() {
        return (mDraggingState == ViewDragHelper.STATE_DRAGGING ||
                mDraggingState == ViewDragHelper.STATE_SETTLING);
    }

    public boolean isOpen() {
        return mIsOpen;
    }*/

    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private int mDraggingState = 0;
    private Button mQueenButton;
    private ViewDragHelper mDragHelper;
    private int mDraggingBorder;
    private int mVerticalRange;
    private boolean mIsOpen;
    private LoaderView loaderView;
    private ListView listview;

    public OuterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        mIsOpen = false;
    }

    @Override
    public void onGlobalLayout() {
        loaderView.init(Color.WHITE);
        mVerticalRange = loaderView.getHeight() + 40;
    }

    @Override
    public void onLoadingStarted() {
        Log.e("TAG", "onLoadingStarted()");
    }

    @Override
    public void onLoadingFinished(LoaderView.State _state) {
        mDragHelper.smoothSlideViewTo(listview, 0, 0);
    }

    public class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public void onViewDragStateChanged(int state) {
            if (state == mDraggingState) { // no change
                return;
            }
            if ((mDraggingState == ViewDragHelper.STATE_DRAGGING || mDraggingState == ViewDragHelper.STATE_SETTLING) &&
                    state == ViewDragHelper.STATE_IDLE) {
                // the view stopped from moving.

                if (mDraggingBorder == 0) {
                    onStopDraggingToClosed();
                } else if (mDraggingBorder == mVerticalRange) {
                    mIsOpen = true;
                }
            }
            if (state == ViewDragHelper.STATE_DRAGGING) {
                onStartDragging();
            }
            mDraggingState = state;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            float loaderPhase = (top / (float) mVerticalRange);
            loaderView.setPhaseStart(1 - loaderPhase);
            loaderView.setAlpha(loaderPhase);
            mDraggingBorder = top;
        }

        public int getViewVerticalDragRange(View child) {
            return mVerticalRange;
        }

        @Override
        public boolean tryCaptureView(View view, int i) {
            return (view.getId() == R.id.listview);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = mVerticalRange;
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final float rangeToCheck = mVerticalRange;
            if (mDraggingBorder == 0) {
                mIsOpen = false;
                return;
            }
            if (mDraggingBorder == rangeToCheck) {
//                loaderView.startProcessing();
                mIsOpen = true;
                final Handler handler = new Handler();
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loaderView.startProcessing();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(2000);
                            handler.postAtFrontOfQueue(new Runnable() {
                                @Override
                                public void run() {
                                    loaderView.startFilling(LoaderView.State.SUCCESS);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        handler.postAtFrontOfQueue(new Runnable() {
                            @Override
                            public void run() {
                                loaderView.init(Color.WHITE);
                            }
                        });
                    }
                }.execute();
                return;
            }
            /*boolean settleToOpen = false;
            if (yvel > AUTO_OPEN_SPEED_LIMIT) { // speed has priority over position
                settleToOpen = true;
            } else if (yvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false;
            } else if (mDraggingBorder > rangeToCheck / 2) {
                settleToOpen = true;
            } else if (mDraggingBorder < rangeToCheck / 2) {
                settleToOpen = false;
            }*/

            scrollToTop();
        }

        private void scrollToTop() {
            if(mDragHelper.settleCapturedViewAt(0, 0)) {
                ViewCompat.postInvalidateOnAnimation(OuterLayout.this);
            }
        }
    }


    @Override
    protected void onFinishInflate() {
//        mQueenButton  = (Button) findViewById(R.id.queen_button);
        loaderView = (LoaderView) findViewById(R.id.loaderView);
        loaderView.registerCallbacks(this);
        listview = (ListView) findViewById(R.id.listview);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mIsOpen = false;
        super.onFinishInflate();
    }

    private void onStartDragging() {
        Log.e("TAG", "onStartDragging()");
//        loaderView.startProcessing();
    }

    private void onStopDraggingToClosed() {
        Log.e("TAG", "onStopDraggingToClosed()");
//        loaderView.startFilling(LoaderView.State.SUCCESS);
    }

    private boolean canMoveList(MotionEvent event) {
        /*int[] queenLocation = new int[2];
        mQueenButton.getLocationOnScreen(queenLocation);
        int upperLimit = queenLocation[1] + mQueenButton.getMeasuredHeight();
        int lowerLimit = queenLocation[1];
        int y = (int) event.getRawY();
        return (y > lowerLimit && y < upperLimit);*/
        return listview.getFirstVisiblePosition() == 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (canMoveList(event) && mDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canMoveList(event) || isMoving()) {
            mDragHelper.processTouchEvent(event);
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public void computeScroll() { // needed for automatic settling.
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public boolean isMoving() {
        return (mDraggingState == ViewDragHelper.STATE_DRAGGING ||
                mDraggingState == ViewDragHelper.STATE_SETTLING);
    }

    public boolean isOpen() {
        return mIsOpen;
    }
}
