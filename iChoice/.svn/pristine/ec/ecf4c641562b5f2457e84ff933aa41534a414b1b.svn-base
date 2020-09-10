package com.choicemmed.ichoice.healthcheck.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;


public class NoPaddingTextView extends android.support.v7.widget.AppCompatTextView {
    //根据当前文本、字号计算出的高度
    private int retHeight = 0;
    //原始高度
    private int originHeight = 0;

    private String lastText = null;

    public NoPaddingTextView(Context context) {
        super(context);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(getGravity() | Gravity.CENTER_VERTICAL);
        setIncludeFontPadding(false);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint.FontMetrics fm = getPaint().getFontMetrics();
        if (fm != null) {
            if (getScrollY() != (originHeight - retHeight) / 2) {
                setScrollY((originHeight - retHeight) / 2);
            }
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int padding;
        Paint.FontMetrics fm = getPaint().getFontMetrics();

        //如果文案已变化则重新测量
        if (lastText != null && !lastText.equals(getText().toString())) {
            originHeight = 0;
            retHeight = 0;
        }

        if (fm != null) {
            padding = (int) Math.abs(fm.top - fm.ascent) + (int) Math.abs(fm.bottom - fm.descent);
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            if (originHeight == 0) {
                //只记录第一次，即原始高度
                originHeight = height;
            }

            //TextView计算高度并缓存
            if (retHeight == 0) {
                if (getText().toString().matches("^[0-9|.]+$")) {
                    //全是数字
                    retHeight = height - padding - (int) descToBaselineDistance(getPaint());
                } else if (getText().toString().contains("g")
                        || getText().toString().contains("y")
                        || getText().toString().contains("p")
                        || getText().toString().contains("j")) {
                    retHeight = height - padding;
                } else {
                    //修复误差
                    retHeight = height - padding - (int) (getTextSize() * 0.1);
                }
            }

            setMeasuredDimension(width, retHeight);
        }
    }

    /**
     * 计算绘制文字时的基线到中轴线的距离
     *
     * @param p
     * @return 基线和centerY的距离
     */
    private float getBaseline(Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return Math.abs((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
    }

    /**
     * 计算descent到中线centerY的距离
     *
     * @param paint
     * @return
     */
    private float destToMidDistance(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.abs((fontMetrics.descent - fontMetrics.ascent) / 2);
    }

    /**
     * 返回baseline到descent的距离
     *
     * @param paint
     * @return
     */
    private float descToBaselineDistance(Paint paint) {
        return Math.abs(destToMidDistance(paint) - getBaseline(paint));
    }
}
