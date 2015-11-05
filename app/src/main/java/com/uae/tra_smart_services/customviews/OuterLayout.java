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
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activity.TestAcivity;
import com.uae.tra_smart_services.interfaces.OuterLayoutState;

/**
 * Created by and on 03.11.15.
 */

public class OuterLayout extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener, OuterLayoutState {

    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private int mDraggingState = 0;
    private Button mQueenButton;
    private ViewDragHelper mDragHelper;
    private int mDraggingBorder;
    private int mVerticalRange;
    private boolean mIsOpen;
    private LoaderView loaderView;
    private ListView listview;
    private TextView noPendingTransactions;

    public OuterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        mIsOpen = false;
    }

    @Override
    public void onGlobalLayout() {
        mVerticalRange = loaderView.getHeight() + 40;
    }

    @Override
    public void onLoadingStart() {
        if(listview.getCount() == 0){
            loaderView.setTop((getHeight() - loaderView.getHeight()) / 2);
            listview.setVisibility(GONE);
        }
        loaderView.setAlpha(1);
        loaderView.startProcessing();
    }

    @Override
    public void onLoadingFinished(boolean _isSucceed) {
        loaderView.stopProcessing();
        mDragHelper.smoothSlideViewTo(listview, 0, 0);
        if(_isSucceed){
            listview.setVisibility(VISIBLE);
            loaderView.setTop(0);
        } else {
            listview.setVisibility(INVISIBLE);
            loaderView.setTop((getHeight() - loaderView.getHeight()) / 2);
        }
        loaderView.setAlpha(0);
    }

    @Override
    public void onRefreshStart() {

    }

    @Override
    public void onRefreshSucceed() {

    }

    private OuterLayout.Listener listener;
    public void registerListener(Listener _listener){
        listener = _listener;
    }

    public class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public void onViewDragStateChanged(int state) {
            if (state == mDraggingState) { // no change
                return;
            }
            if ((mDraggingState == ViewDragHelper.STATE_DRAGGING || mDraggingState == ViewDragHelper.STATE_SETTLING) && state == ViewDragHelper.STATE_IDLE) {
                // the view stopped from moving.

                if (mDraggingBorder == 0) {
//                    onStopDraggingToClosed();
                } else if (mDraggingBorder == mVerticalRange) {
                    mIsOpen = true;
                }
            }
            if (state == ViewDragHelper.STATE_DRAGGING) {
//                onStartDragging();
            }
            mDraggingState = state;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            float loaderPhase = (top / (float) mVerticalRange);
            loaderView.setProgress(loaderPhase);
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
                mIsOpen = true;
                onLoadingStart();
                listener.onRefresh();

                return;
            }
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
        loaderView = (LoaderView) findViewById(R.id.loaderView);
        loaderView.init(Color.WHITE);
        listview = (ListView) findViewById(R.id.listview);
        listview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (isMoving()) {
                    v.setTop(oldTop);
                    v.setBottom(oldBottom);
                    v.setLeft(oldLeft);
                    v.setRight(oldRight);
                }
            }
        });
        noPendingTransactions = (TextView) findViewById(R.id.tvNoPendingTransactions_FIH);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mIsOpen = false;
        super.onFinishInflate();
    }

    /*private void onStartDragging() {
        Log.e("TAG", "onStartDragging()");
//        loaderView.startProcessing();
    }

    private void onStopDraggingToClosed() {
        Log.e("TAG", "onStopDraggingToClosed()");
//        loaderView.startFilling(LoaderView.State.SUCCESS);
    }
    */

    private boolean canMoveList(MotionEvent event) {
        return listview.getFirstVisiblePosition() == 0 || listview.getCount() == 0;
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
    public void computeScroll() {
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


    public interface Listener {
        void onRefresh();
    }
}
