package com.young.customprogressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.young.customprogressbar.R;

public class HoritationProgressBar extends ProgressBar {


    private static final int DEFAULT_REACH_COLOR = 0xFFFF0000;
    private static final int DEFAULT_UNREACH_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_TEXT_SIZE = 15;//sp
    private static final int DEFAULT_REACH_HEIGHT = 3;//dp
    private static final int DEFAULT_UNREACH_HEIGHT = 1;//dp
    Paint mPaint = new Paint();
    protected int color_reach = DEFAULT_REACH_COLOR;
    protected int color_unreach = DEFAULT_UNREACH_COLOR;
    protected int color_text = DEFAULT_REACH_COLOR;
    protected float text_size = sp2px(DEFAULT_TEXT_SIZE);
    protected float reach_height = dp2px(DEFAULT_REACH_HEIGHT);
    protected float unreach_height = dp2px(DEFAULT_UNREACH_HEIGHT);
    private int mRealWidth;
    private int mTextOffect = 2;

    public HoritationProgressBar(Context context) {
        this(context, null);
    }

    public HoritationProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoritationProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attributeSet, R.styleable.HoritationProgressBar);

        color_reach = typedArray.getColor(R.styleable.HoritationProgressBar_color_reach, color_reach);

        color_unreach = typedArray.getColor(R.styleable.HoritationProgressBar_color_unreach, color_unreach);

        text_size = typedArray.getDimension(R.styleable.HoritationProgressBar_text_size, text_size);

        reach_height = typedArray.getDimension(R.styleable.HoritationProgressBar_reach_height, reach_height);

        unreach_height = typedArray.getDimension(R.styleable.HoritationProgressBar_unreach_height
                , unreach_height);

        color_text = (int) typedArray.getDimension(R.styleable.HoritationProgressBar_color_text,color_text);

        typedArray.recycle();

        mPaint.setTextSize(text_size);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // draw reach_bar
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);
        boolean noNeedUnreach = false;

        String text = getProgress() + "%";

        int textWidth = (int) mPaint.measureText(text);

        float radio = getProgress() * 1.0f / getMax();

        float progressX = radio * mRealWidth;

        if (progressX + textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnreach = true;
        }
        float endx = progressX - mTextOffect / 2;

        if (endx>0) {
            mPaint.setColor(color_reach);
            mPaint.setStrokeWidth(reach_height);
            canvas.drawLine(0,0,endx,0,mPaint);
        }

        // draw text
        mPaint.setColor(color_text);
        int y = (int) (-(mPaint.descent() + mPaint.ascent()) /2);
        canvas.drawText(text,progressX,y,mPaint);


        if (!noNeedUnreach) {
            float start = progressX + mTextOffect /2 + textWidth;
            mPaint.setStrokeWidth(unreach_height);
            mPaint.setColor(color_unreach);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }

        canvas.restore();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);


        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        if (mode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            height = (int) (getPaddingTop() + getPaddingBottom() + Math.max(Math.max(reach_height, unreach_height)
                    , Math.abs(textHeight)));

            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
            }
        }

        setMeasuredDimension(width, height);
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    protected float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    protected float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp
                , getResources().getDisplayMetrics());
    }
}
