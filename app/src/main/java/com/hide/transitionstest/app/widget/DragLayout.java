package com.hide.transitionstest.app.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.*;
import android.widget.FrameLayout;
import com.hide.transitionstest.app.R;

/**
 * * * * * * * * * * * * * * * * * * * * * * *
 * Created by zhaoyiding
 * Date: 15/10/12
 * * * * * * * * * * * * * * * * * * * * * * *
 **/
public class DragLayout extends FrameLayout {

    public static final int STATE_COLLAPSE = 1;
    public static final int STATE_EXPAND = 2;

    private ViewGroup mDragContent;
    private View mTouchView;


    private IDragListener iDragListener;
    private ViewDragHelper mDragHelper;
    private int mSlideMaxY;
    private int mTopPadding;
    private int mCurState = STATE_EXPAND;


    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragContent;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return clampHeight(top);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (iDragListener != null) {
                iDragListener.onDragChange(dragPercent(top));
            }
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mSlideMaxY;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            int settleTop = mTopPadding;
            int slideRange = releasedChild.getTop() - mTopPadding;
            switch (mCurState) {
                case STATE_EXPAND:
                    if (yvel >= 0
                            && slideRange > mSlideMaxY / 5) {
                        settleTop = getHeight() - mTouchView.getMeasuredHeight();
                    }
                    break;
                case STATE_COLLAPSE:
                    slideRange = getHeight() - releasedChild.getTop() - mTouchView.getMeasuredHeight();
                    if (yvel > 0
                            || slideRange < mSlideMaxY / 5) {
                        settleTop = getHeight() - mTouchView.getMeasuredHeight();
                    }
                    break;
            }

            mDragHelper.settleCapturedViewAt(getLeft(), settleTop);
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            switch (state) {
                case ViewDragHelper.STATE_IDLE:
                    mCurState = mTopPadding == mDragContent.getTop() ? STATE_EXPAND : STATE_COLLAPSE;
                    break;
            }
        }
    };


    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        removeAllViews();
        mDragContent = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.drag_layout, null);
        mTouchView = mDragContent.findViewById(R.id.drag);
        addView(mDragContent);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);

                int height = getContext().getResources().getDisplayMetrics().heightPixels * 2 / 3;
                ViewGroup.LayoutParams params = mDragContent.getLayoutParams();
                params.height = height;
                mDragContent.setLayoutParams(params);

                mSlideMaxY = height - mTouchView.getMeasuredHeight();

                return false;
            }
        });

        mDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
//        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean intercept;
        try {
            intercept = mDragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            intercept = false;
        }
        return intercept;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int height = getContext().getResources().getDisplayMetrics().heightPixels;
        int childHeight = height * 2 / 3;
        Rect statusBarRect = new Rect();
        getWindowVisibleDisplayFrame(statusBarRect);
        int statusBar = statusBarRect.top;
        height -= statusBar;
        mTopPadding = height - childHeight;
        mDragContent.layout(left, top + mTopPadding, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean onTouch = true;
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
            onTouch = false;
        }
        return onTouch;
    }

    private int clampHeight(int top) {
        return Math.max(mTopPadding, Math.min(top, mSlideMaxY + mTopPadding));
    }

    private float dragPercent(int top) {
        return 1 - (top - mTopPadding) * 1.0f / mSlideMaxY;

    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setOnDragListener(IDragListener l) {
        iDragListener = l;
    }

    public interface IDragListener {
        void onDragChange(float fraction);
    }
}
