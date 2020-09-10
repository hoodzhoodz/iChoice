package com.choicemmed.ichoice.healthcheck.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


public class SwipeDelete extends ViewGroup {

    private View mLeftView;
    private View mRightView;
    private int mLeftWidth;
    private int mLeftHeight;
    private int mRightWidth;
    private int mRightHeight;
    public ViewDragHelper mViewDragHelper;

    public SwipeDelete(Context context) {
        this(context, null);
    }

    public SwipeDelete(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public SwipeDelete(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    public SwipeDelete(Context context, AttributeSet attrs) {
        super(context, attrs);

        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return pointerId == 0;
            }


            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == mLeftView) {//【-mRightWidth，0】
                    if (left > 0) {
                        left = 0;
                    } else if (left < -mRightWidth) {
                        left = -mRightWidth;
                    }
                } else if (child == mRightView) {//[mLeftWidth-mRightWidth,mLeftWidth]
                    if (left < mLeftWidth - mRightWidth) {
                        left = mLeftWidth - mRightWidth;
                    } else if (left > mLeftWidth) {
                        left = mLeftWidth;
                    }
                }
                return left;
            }


            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (changedView == mLeftView) {
                    //让rightView跟着也移动（重新排版位置（在老的位置上+dx））
                    int newLeft = mRightView.getLeft() + dx;
                    mRightView.layout(newLeft, 0, newLeft + mRightWidth, mRightHeight);
                } else if (changedView == mRightView) {
                    //让LeftView跟着也移动
                    //让rightView跟着也移动（重新排版位置（在老的位置上+dx））
                    int newLeft = mLeftView.getLeft() + dx;
                    mLeftView.layout(newLeft, 0, newLeft + mLeftWidth, mLeftHeight);
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (mLeftView.getLeft() < -mRightWidth / 2) {
                    open();
                } else {
                    close();
                }

            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return mRightWidth;
            }

        });
    }

    private void open() {
        mViewDragHelper.smoothSlideViewTo(mLeftView, -mRightWidth, 0);
        postInvalidateOnAnimation();
        //当当前对象打开到时候记录到单例对象中
        SwipeDeleteManager.getInstance().setSwipeDelete(this);

    }

    public void close() {
        mViewDragHelper.smoothSlideViewTo(mLeftView, 0, 0);
        postInvalidateOnAnimation();
        //当当前对象关闭的时候记录到单例对象中
        SwipeDeleteManager.getInstance().setSwipeDelete(null);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//自己测量自己

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        String modeStr = "";
        switch (mode) {
            case MeasureSpec.EXACTLY:
                modeStr = "EXACTLY";
                break;
            case MeasureSpec.AT_MOST:
                modeStr = "AT_MOST";
                break;
            case MeasureSpec.UNSPECIFIED:
                modeStr = "UNSPECIFIED";
                break;
        }
        Log.d("TAG", "onMeasure: mode=" + modeStr + "/size=" + size);

        mLeftView = getChildAt(0);
        mRightView = getChildAt(1);
        //测量子控件
        measureChild(mLeftView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mRightView, widthMeasureSpec, heightMeasureSpec);
        //获取子控件的宽和高属性
        mLeftWidth = mLeftView.getMeasuredWidth();
        mLeftHeight = mLeftView.getMeasuredHeight();

        mRightWidth = mRightView.getMeasuredWidth();
        mRightHeight = mRightView.getMeasuredHeight();

    }

    /**
     * 排版两个子控件
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLeftView.layout(0, 0, mLeftWidth, mLeftHeight);
        mRightView.layout(mLeftWidth, 0, mLeftWidth + mRightWidth, mRightHeight);
    }

    int x_down = 0;    //mListView选中Item按下时的x坐标
    int x_up = 0;    //mListView选中Item松开时的x坐
    boolean flag = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mViewDragHelper.processTouchEvent(event);
//        Log.e("===", event.getX() + "");
//        Log.e("===", event.getAction() + "");
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                x_down = (int) event.getX();
//                flag = false;
//                break;
//            case MotionEvent.ACTION_UP:
//                x_up = (int) event.getX();
////                flag = true;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mViewDragHelper.processTouchEvent(event);
//                flag = true;
//                break;
//        }
//        if (flag&&x_up-x_down>0) {
//            mViewDragHelper.processTouchEvent(event);
//            return true;
//        } else {
//            return super.onTouchEvent(event);
//        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //判断当前是否有其他已经打开的控件，如果有则关闭
        SwipeDelete swipeDelete = SwipeDeleteManager.getInstance().getSwipeDelete();
        if (swipeDelete != null && swipeDelete != this) {
            swipeDelete.close();
        }


        boolean shouldInterceptTouchEvent = mViewDragHelper.shouldInterceptTouchEvent(ev);

        return shouldInterceptTouchEvent;
    }

    public static class SwipeDeleteManager {
        private SwipeDeleteManager() {
        }

        private static SwipeDeleteManager sSwipeDeleteManager;
        private SwipeDelete mSwipeDelete;

        public SwipeDelete getSwipeDelete() {
            return mSwipeDelete;
        }

        public void setSwipeDelete(SwipeDelete swipeDelete) {
            mSwipeDelete = swipeDelete;
        }

        public static SwipeDeleteManager getInstance() {
            if (sSwipeDeleteManager == null) {
                synchronized (SwipeDeleteManager.class) {
                    if (sSwipeDeleteManager == null) {
                        sSwipeDeleteManager = new SwipeDeleteManager();
                    }
                }
            }

            return sSwipeDeleteManager;
        }

    }

}
