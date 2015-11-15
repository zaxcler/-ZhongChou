package com.lianhai.zhongchou.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lianhai.zhongchou.R;

/**
 * Created by asdfgh on 15/10/20.
 */
public class SectorView extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private String mText = "";
    private int mTextColor;
    private float mTextSize;
    private int mSectorColor;
    private int mSectorAlpha;
    private int mSectorStartAngle;
    private int mSectorSweepAngle;
    private RectF rectF;


    public SectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mTextPaint = new Paint();
        rectF = new RectF(0, 0, getWidth() * 2, getHeight() * 2);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SectorView, defStyleAttr,0);
        mSectorAlpha = a.getInt(R.styleable.SectorView_SectorAlpha, 70);
        mSectorColor = a.getColor(R.styleable.SectorView_SectorColor, Color.BLACK);
        mSectorStartAngle = a.getInt(R.styleable.SectorView_SectorStartAngle, 0);
        mSectorSweepAngle = a.getInt(R.styleable.SectorView_SectorSweepAngle, 90);

        mTextColor = a.getColor(R.styleable.SectorView_TextColor, Color.WHITE);
        mTextSize = a.getDimension(R.styleable.SectorView_TextSize, 14);
        mText = a.getString(R.styleable.SectorView_Text);




        mPaint.setColor(mSectorColor);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(mSectorAlpha);

        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);




        a.recycle();


    }

    public SectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectorView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawArc(new RectF(0, 0, getWidth() * 2, getHeight() * 2), 180, 90, true, mPaint);
        float textWidth=mTextPaint.measureText(mText);
        canvas.drawText(mText, getWidth() / 2-textWidth/2, getHeight() / 2+textWidth/2, mTextPaint);
        super.onDraw(canvas);
    }

    public void setText(String mText) {
        this.mText = mText;
    }
}
