package com.choicemmed.ichoice.healthcheck.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;


public class EcgViewSplit extends View {
    // P10b默认值
    private int mP10bEcgDataBaseline = 2048;
    private float mP10bEcgValuePerMv = 264f;
    // A12b默认值
    private int mEcgDataBaseline = 512;
    private float mEcgValuePerMv = 149f;

    private int mDefaultHeight = 250;
    private int mDefaultWidth = 3750;

    private int mP10bDefaultHeight = 200;
    private int mP10bDefaultWidth = 7500;

    private float mScaleW = 0.5f;
    private float mP10bScaleW = 0.4f;
    private float mScaleH = 1f;

    private boolean mDrawSmallCell = true;

    private float mScreenScaleFactor = 1f;

    private int[] mEcgData;

    Paint paint = new Paint();

    public EcgViewSplit(Context context) {
        this(context, null);
    }

    public EcgViewSplit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EcgViewSplit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EcgSettings, defStyleAttr, 0);
        for (int i = 0; i < attributes.getIndexCount(); i++) {
            int attr = attributes.getIndex(i);
            switch (attr) {
                case R.styleable.EcgSettings_dataBaseline:
                    if ("A12".equals(IchoiceApplication.type)) {
                        mEcgDataBaseline = attributes.getInt(attr, mEcgDataBaseline);
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        mP10bEcgDataBaseline = attributes.getInt(attr, mP10bEcgDataBaseline);
                    }
                    break;
                case R.styleable.EcgSettings_valuePerMv:
                    if ("A12".equals(IchoiceApplication.type)) {
                        mEcgValuePerMv = attributes.getFloat(attr, mEcgValuePerMv);
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        mP10bEcgValuePerMv = attributes.getFloat(attr, mP10bEcgValuePerMv);
                    }
                    break;
                case R.styleable.EcgSettings_bigCellCount:
                    int ecgBigCellCount = 0;
                    if ("A12".equals(IchoiceApplication.type)) {
                        ecgBigCellCount = attributes.getInt(attr, mDefaultHeight / 50);
                        mDefaultHeight = ecgBigCellCount * 50;
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        ecgBigCellCount = attributes.getInt(attr, mP10bDefaultHeight / 50);
                        mP10bDefaultHeight = ecgBigCellCount * 50;
                    }
                    break;
                case R.styleable.EcgSettings_dataCount:
                    if ("A12".equals(IchoiceApplication.type)) {
                        mDefaultWidth = attributes.getInt(attr, mDefaultWidth);
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        mP10bDefaultWidth = attributes.getInt(attr, mP10bDefaultWidth);
                    }
                    break;
                case R.styleable.EcgSettings_scaleW:
                    if ("A12".equals(IchoiceApplication.type)) {
                        mScaleW = attributes.getFloat(attr, mScaleW);
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        mP10bScaleW = attributes.getFloat(attr, mP10bScaleW);
                    }
                    break;
                case R.styleable.EcgSettings_scaleH:
                    mScaleH = attributes.getFloat(attr, mScaleH);
                    break;
                case R.styleable.EcgSettings_drawSmallCell:
                    mDrawSmallCell = attributes.getBoolean(attr, true);
                    break;
            }
        }
        attributes.recycle();
    }

    public void redrawEcg(int[] ecgData) {
        mEcgData = ecgData;
        invalidate();
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
            width = height * mP10bDefaultWidth / mP10bDefaultHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setScaleFactor();

        drawEcgLine(canvas);

        drawEcgData(canvas, mEcgData);
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
            float endX = 0;
            if ("A12".equals(IchoiceApplication.type)) {
                endX = scalePoint(dip2px(mDefaultWidth));
            } else if ("P10".equals(IchoiceApplication.type)) {
                endX = scalePoint(dip2px(mP10bDefaultWidth));
            }
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
        int verticalLineCount = 0;
        if ("A12".equals(IchoiceApplication.type)) {
            verticalLineCount = mDefaultWidth / 10 + 1;

        } else if ("P10".equals(IchoiceApplication.type)) {
            verticalLineCount = mP10bDefaultWidth / 10 + 1;

        }
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

    private void drawEcgData(Canvas canvas, int[] ecgData) {
        try {
            if (ecgData == null || ecgData.length < 2) {
                return;
            }
            float[] pts = new float[(ecgData.length - 1) * 4];
            for (int i = 0; i < ecgData.length; i++) {
                int ecgPoint = ecgData[i];
                float y = ecgPoint2ScreenPointY(ecgPoint - 74);
                if (i == 0) {
                    if ("A12".equals(IchoiceApplication.type)) {
                        pts[i] = scalePoint(dip2px(i * mScaleW));
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        pts[i] = scalePoint(dip2px(i * mP10bScaleW));
                    }
                    pts[i + 1] = scalePoint(y);
                } else if (i == ecgData.length - 1) {
                    if ("A12".equals(IchoiceApplication.type)) {
                        pts[i * 4 - 2] = scalePoint(dip2px(i * mScaleW));
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        pts[i * 4 - 2] = scalePoint(dip2px(i * mP10bScaleW));
                    }
                    pts[i * 4 - 1] = scalePoint(y);
                } else {
                    if ("A12".equals(IchoiceApplication.type)) {
                        pts[i * 4 - 2] = scalePoint(dip2px(i * mScaleW));
                    } else if ("P10".equals(IchoiceApplication.type)) {
                        pts[i * 4 - 2] = scalePoint(dip2px(i * mP10bScaleW));
                    }
                    pts[i * 4 - 1] = scalePoint(y);
                    pts[i * 4] = pts[i * 4 - 2];
                    pts[i * 4 + 1] = pts[i * 4 - 1];
                }
            }
            paint.setColor(getResources().getColor(R.color.ecg_data_line));
            paint.setStrokeWidth(2);
            paint.setAntiAlias(true);
            canvas.drawLines(pts, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float ecgPoint2ScreenPointY(int ecgPoint) {
        float ecgValue = 0;
        if ("A12".equals(IchoiceApplication.type)) {
            ecgValue = (ecgPoint - mEcgDataBaseline) / mEcgValuePerMv;
        } else if ("P10".equals(IchoiceApplication.type)) {
            ecgValue = (ecgPoint - mP10bEcgDataBaseline) / mP10bEcgValuePerMv;
        }
        if ("A12".equals(IchoiceApplication.type)) {
            return dip2px(mDefaultHeight / 2) - dip2px(ecgValue * 100 * mScaleH);
        } else {
            return dip2px(mP10bDefaultHeight / 2) - dip2px(ecgValue * 100 * mScaleH);
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
