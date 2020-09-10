package com.choicemmed.ichoice.framework.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Created by Jiang nan on 2019/11/18 16:50.
 * @description
 **/
public class CircleLoading extends View {
    private ObjectAnimator objectAnimator;
    public CircleLoading(Context context) {
        super(context);

    }

    public CircleLoading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor( Color.parseColor("#04b9d4"));
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        SweepGradient sweepGradient = new SweepGradient((getMeasuredWidth()-40)/2 ,
                (getMeasuredWidth()-40)/2, new int[]{ Color.parseColor("#04b9d4"), Color.WHITE},null);
        Matrix matrix = new Matrix();
        matrix.setRotate(180,getMeasuredWidth()/2, getMeasuredHeight()/2);
        sweepGradient.setLocalMatrix(matrix);
        paint.setShader(sweepGradient);
        RectF rect = new RectF(20, 20, getMeasuredWidth() - 20, getMeasuredHeight() - 20);
        canvas.drawArc(rect,0,360,false,paint);
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 359f);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(4000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                240, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        240, getResources().getDisplayMetrics()));

    }

}
