package com.choicemmed.ichoice.healthcheck.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.choicemmed.ichoice.R;

public class EcgScaleView extends View {
    private int mDefaultHeight = 200;
    private int mDefaultWidth = 50;

    private float mScaleH = 1f;

    private boolean mDrawSmallCell = true;

    private float mScreenScaleFactor = 1f;

    Paint paint = new Paint();

    public EcgScaleView(Context context) {
        this(context, null);
    }

    public EcgScaleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EcgScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EcgSettings, defStyleAttr, 0);
        for (int i = 0; i < attributes.getIndexCount(); i++) {
            int attr = attributes.getIndex(i);
            switch (attr) {
                case R.styleable.EcgSettings_bigCellCount:
                    int ecgBigCellCount = attributes.getInt(attr, 4);
                    mDefaultHeight = ecgBigCellCount * 50;
                    break;
                case R.styleable.EcgSettings_dataCount:
                    mDefaultWidth = attributes.getInt(attr, 50);
                    break;
                case R.styleable.EcgSettings_scaleH:
                    mScaleH = attributes.getFloat(attr, 1f);
                    break;
                case R.styleable.EcgSettings_drawSmallCell:
                    mDrawSmallCell = attributes.getBoolean(attr, true);
                    break;
            }
        }
        attributes.recycle();

    }

    public void scaleH(float scaleH) {
        mScaleH = scaleH;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = heightSize;
        int width = height * mDefaultWidth / mDefaultHeight;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setScaleFactor();

        drawEcgLine(canvas);

        drawScale(canvas);
    }

    private void setScaleFactor() {
        float height = getMeasuredHeight();
        float scaleFactorH = height / dip2px(mDefaultHeight);
        mScreenScaleFactor = scaleFactorH;
    }

    private void drawEcgLine(Canvas canvas) {

        paint.setColor(getResources().getColor(R.color.ecg_bg_line_red));
        // 画横线
        int horizontalLineCount = mDefaultHeight / 10 + 1;
        for (int i = 0; i < horizontalLineCount; i++) {
            float startX = 0;
            float startY = scalePoint(dip2px(10 * i));
            float endX = scalePoint(dip2px(mDefaultWidth));
            float endY = startY;
            if (i % 5 == 0) {
                paint.setStrokeWidth(2);
                canvas.drawLine(startX, startY, endX, endY, paint);
            } else {
                paint.setStrokeWidth(1);
                if (mDrawSmallCell) {
                    canvas.drawLine(startX, startY, endX, endY, paint);
                }
            }
        }
        // 画竖线
        int verticalLineCount = mDefaultWidth / 10 + 1;
        for (int i = 0; i < verticalLineCount; i++) {
            float startX = scalePoint(dip2px(10 * i));
            float startY = 0;
            float endX = startX;
            float endY = scalePoint(dip2px(mDefaultHeight));
            if (i % 5 == 0) {
                paint.setStrokeWidth(2);
                canvas.drawLine(startX, startY, endX, endY, paint);
            } else {
                paint.setStrokeWidth(1);
                if (mDrawSmallCell) {
                    canvas.drawLine(startX, startY, endX, endY, paint);
                }
            }
        }
    }

    private void drawScale(Canvas canvas) {
        float[] pts = new float[20];
        int step = 10;
        if (mDefaultWidth >= 200) {
            step = 50;
        }
        pts[0] = scalePoint(dip2px(step));
        pts[1] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH));
        pts[2] = scalePoint(dip2px(step * 2));
        pts[3] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH));

        pts[4] = scalePoint(dip2px(step * 2));
        ;
        pts[5] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH));
        pts[6] = scalePoint(dip2px(step * 2));
        pts[7] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH));

        pts[8] = scalePoint(dip2px(step * 2));
        pts[9] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH));
        pts[10] = scalePoint(dip2px(step * 3));
        pts[11] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH));

        pts[12] = scalePoint(dip2px(step * 3));
        pts[13] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH));
        pts[14] = scalePoint(dip2px(step * 3));
        pts[15] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH));

        pts[16] = scalePoint(dip2px(step * 3));
        pts[17] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH));
        pts[18] = scalePoint(dip2px(step * 4));
        pts[19] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH));

        paint.setColor(getResources().getColor(R.color.ecg_scale));
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        canvas.drawLines(pts, paint);
    }

    private float dip2px(float dp) {

        float scale = getContext().getResources().getDisplayMetrics().density;


        return dp * scale + 0.5f;
    }

    private float scalePoint(float value) {
        return value * mScreenScaleFactor;
    }
}
