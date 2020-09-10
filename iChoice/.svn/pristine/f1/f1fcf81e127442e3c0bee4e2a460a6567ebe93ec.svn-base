package com.choicemmed.ichoice.healthcheck.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;

public class EcgScaleViewSplit extends View {
    // A12b默认值
    private int mDefaultHeight = 250;
    // P10b默认值
    private int mP10bDefaultHeight = 200;

    private int mDefaultWidth = 50;

    private float mScaleH = 1f;

    private boolean mDrawSmallCell = true;

    private float mScreenScaleFactor = 1f;
    private String text;

    Paint paint = new Paint();

    public EcgScaleViewSplit(Context context) {
        this(context, null);
    }

    public EcgScaleViewSplit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EcgScaleViewSplit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EcgSettings, defStyleAttr, 0);
        for (int i = 0; i < attributes.getIndexCount(); i++) {
            int attr = attributes.getIndex(i);
            switch (attr) {
                case R.styleable.EcgSettings_bigCellCount:
                    int ecgBigCellCount = attributes.getInt(attr, 5);
                    if ("A12".equals(IchoiceApplication.type)) {
                        mDefaultHeight = ecgBigCellCount * 50;
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        mP10bDefaultHeight = ecgBigCellCount * 50;
                    }
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
        int width = 0;
        if ("A12".equals(IchoiceApplication.type)) {
            width = height * mDefaultWidth / mDefaultHeight;
        } else if ("P10".equals(IchoiceApplication.type)) {
            width = height * mDefaultWidth / mP10bDefaultHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setScaleFactor();

        drawEcgLine(canvas);

        drawScale(canvas);
        drawtext(canvas);
    }

    private void setScaleFactor() {
        float height = getMeasuredHeight();
        float scaleFactorH = 0;
        if ("A12".equals(IchoiceApplication.type)) {
            scaleFactorH = height / dip2px(mDefaultHeight);
        } else if ("P10".equals(IchoiceApplication.type)) {
            scaleFactorH = height / dip2px(mP10bDefaultHeight);
        }
        mScreenScaleFactor = scaleFactorH;
    }

    private void drawEcgLine(Canvas canvas) {

        paint.setColor(getResources().getColor(R.color.ecg_bg_line_red));
        // 画横线
        int horizontalLineCount = 0;
        if ("A12".equals(IchoiceApplication.type)) {
            horizontalLineCount = mDefaultHeight / 10 + 1;
        } else if ("P10".equals(IchoiceApplication.type)) {
            horizontalLineCount = mP10bDefaultHeight / 10 + 1;
        }
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
            float endY = 0;
            if ("A12".equals(IchoiceApplication.type)) {
                endY = scalePoint(dip2px(mDefaultHeight));
            } else if ("P10".equals(IchoiceApplication.type)) {
                endY = scalePoint(dip2px(mP10bDefaultHeight));
            }
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

        if ("A12".equals(IchoiceApplication.type)) {
            pts[1] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[3] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[5] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[7] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[9] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[11] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[13] = scalePoint(dip2px(mDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[15] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[17] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[19] = scalePoint(dip2px(mDefaultHeight / 2 + 50 * mScaleH)) + 30;
        } else if ("P10".equals(IchoiceApplication.type)) {
            pts[1] = scalePoint(dip2px(mP10bDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[3] = scalePoint(dip2px(mP10bDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[5] = scalePoint(dip2px(mP10bDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[7] = scalePoint(dip2px(mP10bDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[9] = scalePoint(dip2px(mP10bDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[11] = scalePoint(dip2px(mP10bDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[13] = scalePoint(dip2px(mP10bDefaultHeight / 2 - 50 * mScaleH)) + 30;
            pts[15] = scalePoint(dip2px(mP10bDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[17] = scalePoint(dip2px(mP10bDefaultHeight / 2 + 50 * mScaleH)) + 30;
            pts[19] = scalePoint(dip2px(mP10bDefaultHeight / 2 + 50 * mScaleH)) + 30;
        }
        pts[2] = scalePoint(dip2px(step * 2));

        pts[4] = scalePoint(dip2px(step * 2));
        ;

        pts[6] = scalePoint(dip2px(step * 2));


        pts[8] = scalePoint(dip2px(step * 2));

        pts[10] = scalePoint(dip2px(step * 3));


        pts[12] = scalePoint(dip2px(step * 3));

        pts[14] = scalePoint(dip2px(step * 3));


        pts[16] = scalePoint(dip2px(step * 3));

        pts[18] = scalePoint(dip2px(step * 4));


        paint.setColor(getResources().getColor(R.color.ecg_scale));
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        canvas.drawLines(pts, paint);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    private void drawtext(Canvas canvas) {
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.CENTER);
        float height = getMeasuredHeight();
        float width = getMeasuredWidth();
        if (!TextUtils.isEmpty(text)) {
            canvas.drawText(text, width / 2, height * 22 / 25, paint);
        }
    }

    private float dip2px(float dp) {

        float scale = getContext().getResources().getDisplayMetrics().density;


        return dp * scale + 0.5f;
    }

    private float scalePoint(float value) {
        return value * mScreenScaleFactor;
    }
}
