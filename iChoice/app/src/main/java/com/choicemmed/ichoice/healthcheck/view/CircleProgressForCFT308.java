package com.choicemmed.ichoice.healthcheck.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CircleProgressForCFT308 extends View {
    private Context context;
    private Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setColor(Color.parseColor("#ee86a1"));
        canvas.drawCircle(30, 30, 30, paint);

    }

    public CircleProgressForCFT308(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {


    }

    public CircleProgressForCFT308(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressForCFT308(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleProgressForCFT308(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
