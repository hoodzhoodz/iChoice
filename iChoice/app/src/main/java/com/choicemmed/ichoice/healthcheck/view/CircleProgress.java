package com.choicemmed.ichoice.healthcheck.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.choicemmed.ichoice.R;


public class CircleProgress extends View {
    public static final boolean ANTI_ALIAS = true;
    public static final int DEFAULT_SIZE = 300;
    public static final int DEFAULT_START_ANGLE = 90;
    public static final int DEFAULT_SWEEP_ANGLE = 360;
    public static final int DEFAULT_ANIM_TIME = 1000;
    public static final int DEFAULT_ARC_WIDTH = 30;

    private Context mContext;
    private int mDefaultSize;
    private boolean antiAlias;
    private Paint mArcPaint;
    private float mArcWidth;
    private float mStartAngle, mSweepAngle;
    private RectF mRectF;
    private RectF mRectF1;
    private float mPercent;
    private long mAnimTime;
    private ValueAnimator mAnimator;
    private Paint mBgArcPaint;
    private int mBgArcColor;
    private float mBgArcWidth;
    private Paint paint1;
    private Point mCenterPoint;
    private float mRadius;

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mDefaultSize = MiscUtil.dipToPx(mContext, DEFAULT_SIZE);
        mAnimator = new ValueAnimator();
        mRectF = new RectF();
        mRectF1 = new RectF();
        mCenterPoint = new Point();
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);

        antiAlias = typedArray.getBoolean(R.styleable.CircleProgressBar_antiAlias, ANTI_ALIAS);

        mArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_arcWidth, DEFAULT_ARC_WIDTH);
        mStartAngle = typedArray.getFloat(R.styleable.CircleProgressBar_startAngle, DEFAULT_START_ANGLE);
        mSweepAngle = typedArray.getFloat(R.styleable.CircleProgressBar_sweepAngle, DEFAULT_SWEEP_ANGLE);

        mBgArcColor = typedArray.getColor(R.styleable.CircleProgressBar_bgArcColor, getResources().getColor(R.color.blue_608c9eff));
        mBgArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_bgArcWidth, DEFAULT_ARC_WIDTH);
        mAnimTime = typedArray.getInt(R.styleable.CircleProgressBar_animTime, DEFAULT_ANIM_TIME);
        typedArray.recycle();
    }

    private void initPaint() {
        mArcPaint = new Paint();
        mArcPaint.setColor(getResources().getColor(R.color.blue_8c9eff));
        mArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.blue_608c9eff));
        mArcPaint.setAntiAlias(antiAlias);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcWidth);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mBgArcPaint = new Paint();
        mBgArcPaint.setAntiAlias(antiAlias);
        mBgArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.blue_608c9eff));
        mBgArcPaint.setColor(mBgArcColor);
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(mBgArcWidth);
        mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);

        paint1 = new Paint();
        paint1.setAntiAlias(antiAlias);
        paint1.setColor(Color.WHITE);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(30);
        paint1.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MiscUtil.measure(widthMeasureSpec, mDefaultSize),
                MiscUtil.measure(heightMeasureSpec, mDefaultSize));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float maxArcWidth = Math.max(mArcWidth, mBgArcWidth);
        int minSize = Math.min(w - getPaddingLeft() - getPaddingRight() - 2 * (int) maxArcWidth,
                h - getPaddingTop() - getPaddingBottom() - 2 * (int) maxArcWidth);
        mRadius = minSize / 2;
        mCenterPoint.x = w / 2;
        mCenterPoint.y = h / 2;
        mRectF.left = mCenterPoint.x - mRadius - maxArcWidth / 2;
        mRectF.top = mCenterPoint.y - mRadius - maxArcWidth / 2;
        mRectF.right = mCenterPoint.x + mRadius + maxArcWidth / 2;
        mRectF.bottom = mCenterPoint.y + mRadius + maxArcWidth / 2;

        mRectF1.left = mCenterPoint.x - mRadius + maxArcWidth / 2;
        mRectF1.top = mCenterPoint.y - mRadius + maxArcWidth / 2;
        mRectF1.right = mCenterPoint.x + mRadius - maxArcWidth / 2;
        mRectF1.bottom = mCenterPoint.y + mRadius - maxArcWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        drawArc(canvas);
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        float currentAngle = mSweepAngle * mPercent;
        canvas.rotate(mStartAngle, mCenterPoint.x, mCenterPoint.y);
        canvas.drawArc(mRectF, 2, currentAngle, false, mArcPaint);
        canvas.drawArc(mRectF, currentAngle, mSweepAngle - currentAngle + 2, false, mBgArcPaint);
        canvas.drawArc(mRectF1, 0, 360, false, paint1);
        canvas.restore();
    }

    public void setValue(float value) {
        if (value > 100) {
            value = 100;
        }
        float start = mPercent;
        float end = value / 100;
        startAnimator(start, end, mAnimTime);
    }

    private void startAnimator(float start, float end, long animTime) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.setDuration(animTime);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.start();
    }

    public void resetPintColor(int state) {
        if (state == 2) {
            mBgArcPaint.setColor(getResources().getColor(R.color.red2));
            mArcPaint.setColor(getResources().getColor(R.color.red1));
            mBgArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.red2));
            mArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.red2));
        } else if (state == 3) {
            mBgArcPaint.setColor(getResources().getColor(R.color.red_60e86060));
            mArcPaint.setColor(getResources().getColor(R.color.red_e86060));
            mBgArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.red_60e86060));
            mArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.red_60e86060));
        } else if (state == 1) {
            mBgArcPaint.setColor(getResources().getColor(R.color.blue_608c9eff));
            mBgArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.blue_608c9eff));
            mArcPaint.setShadowLayer(DEFAULT_ARC_WIDTH, 0, 0, getResources().getColor(R.color.blue_608c9eff));
            mArcPaint.setColor(getResources().getColor(R.color.blue_8c9eff));

        }
    }

    public void reset() {
        startAnimator(mPercent, 0.0f, 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
