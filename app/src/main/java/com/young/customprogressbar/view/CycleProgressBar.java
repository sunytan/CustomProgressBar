package com.young.customprogressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.young.customprogressbar.R;

public class CycleProgressBar extends HoritationProgressBar {


    private static final int DEFAULT_RADIUS = 40;//dp

    private float radius = dp2px(DEFAULT_RADIUS);

    private float mMaxPaintWidth;

    private Rect rect = new Rect();

    public CycleProgressBar(Context context) {
        this(context,null);
    }

    public CycleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CycleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.CycleProgressBar);

        radius = typedArray.getDimension(R.styleable.CycleProgressBar_radius,radius);

        typedArray.recycle();
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {

        String text = getProgress()+"%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent());

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2,getPaddingTop() + mMaxPaintWidth /2);
        mPaint.setStyle(Paint.Style.STROKE);
        //draw unreach
        mPaint.setColor(color_unreach);
        mPaint.setStrokeWidth(unreach_height);
        canvas.drawCircle(radius,radius,radius,mPaint);

        // draw reach bar
        float angle = (getProgress() * 1.0f/ getMax())*360;
        mPaint.setColor(color_reach);
        mPaint.setStrokeWidth(reach_height);
        canvas.drawArc(new RectF(0,0,radius*2,radius *2),
                0,angle,false,mPaint);

        mPaint.setColor(color_text);
        mPaint.setTextSize(text_size);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(text,radius - textWidth/ 2,radius - textHeight/2,mPaint);

        canvas.restore();


    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth = Math.max(reach_height,unreach_height);
        int exp = (int) (radius*2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight());

        int width = resolveSize(exp,widthMeasureSpec);
        int height = resolveSize(exp,heightMeasureSpec);

        int realWidth = Math.min(width,height);
        radius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth )/2;

        setMeasuredDimension(realWidth,realWidth);

    }
}
