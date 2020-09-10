package com.choicemmed.ichoice.healthcheck.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.R;


public class ZoomControlView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Bitmap bitmapShow, bitmapDefaut, bitmapLeft, bitmapRight;
    private Paint mPaint;
    private int state;
    private Rect rectOr, rectDst;
    private Context mContext;
    private final int withMatrice;
    private boolean enLeft = true;
    private boolean enRight = true;
    int width;
    int height;

    public void setState(int state) {
        this.state = state;
        switch (state) {
            case 0:
                bitmapShow = bitmapDefaut;
                break;
            case 1:
                bitmapShow = bitmapLeft;
                break;
            case 2:
                bitmapShow = bitmapRight;
                break;
            default:
                break;
        }
    }

    public void setLeftEnable(boolean enable) {
        enLeft = enable;
        if (enable) {
            if (enRight) {
                setState(0);
            } else {
                setState(2);
            }
        } else {
            setState(1);
        }
    }

    public void setRightEnable(boolean enable) {
        enRight = enable;
        if (enable) {
            if (enLeft) {
                setState(0);
            } else {
                setState(1);
            }
        } else {
            setState(2);
        }
    }

    public ZoomControlView(Context context) {
        this(context, null);
    }

    public ZoomControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        withMatrice = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mPaint.setStrokeWidth(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmapDefaut = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ecg_chart_add_sub);
        bitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ecg_chart_add);
        bitmapRight = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ecg_chart_sub);
        bitmapShow = bitmapDefaut;
//
//        int oH = bitmapShow.getHeight();
//        int oW = bitmapShow.getWidth();


//        int dW = withMatrice * 30 / 119;
//        int dH = dW * oH / oW;
//        LogUtils.d(TAG,"oH  "+oH+"  oW "+oW +" dW "+ dW +" dH "+dH);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectOr = new Rect(0, 0, width, height);
        rectDst = new Rect(0, 0, width, height);
        canvas.drawBitmap(bitmapShow, rectOr, rectDst, mPaint);
        LogUtils.d(TAG, "canvas.drawBitmap ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (y > rectDst.top && y < rectDst.bottom) {
            if (x > rectDst.left && x < (rectDst.left + rectDst.width() / 2)) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setState(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        setState(0);
                        if (onButtonClicked != null) {
                            onButtonClicked.onLeftclicked();
                        }
                        break;
                    default:
                        break;
                }
            } else if (x > rectDst.left + rectDst.width() / 2 && x < rectDst.right) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setState(2);
                        break;
                    case MotionEvent.ACTION_UP:
                        setState(0);
                        if (onButtonClicked != null) {
                            onButtonClicked.onRightclicked();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        if ((y < rectDst.top || y > rectDst.bottom || x < rectDst.left || x > rectDst.right)
                && event.getAction() == MotionEvent.ACTION_UP) {
            if (enLeft) {
                if (enRight) {
                    setState(0);
                } else {
                    setState(2);
                }
            } else {
                setState(1);
            }
        }
        invalidate();
        return true;
    }

    private OnButtonClicked onButtonClicked;

    public void setOnButtonClicked(OnButtonClicked onButtonClicked) {
        this.onButtonClicked = onButtonClicked;
    }

    public interface OnButtonClicked {
        void onLeftclicked();

        void onRightclicked();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + getPaddingRight() + rectDst.width();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + getPaddingBottom() + rectDst.height();
        }
        setMeasuredDimension(width, height);
        LogUtils.d(TAG, "width " + width + " height " + height);
    }
}
