package com.yzx.xiaomusic.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.utils.DensityUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;

/**
 * Created by yzx on 2018/2/1.
 * Description
 */

public class CircleProgress extends View {

    int max;
    int progress;
    int state =STATE_PAUSE;
    private Paint mPaint;
    public static final int GRAY= ResourceUtils.parseColor(R.color.colorIconGray);
    public static final int LIGHT_GRAY = ResourceUtils.parseColor(R.color.colorGray);
    public static final int COLOR_PLAY = ResourceUtils.parseColor(R.color.colorAccent);

    public static final int STATE_PAUSE =1;
    public static final int STATE_PLAY =2;

    public CircleProgress(Context context) {
        this(context,null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(DensityUtils.spToPx(1.5f));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(GRAY);

//        max =100;
//        progress =30;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int measuredHeight = getMeasuredHeight();
        float radius = 0.8f * measuredHeight / 2;

        Path path;
        if (STATE_PAUSE==state){
            mPaint.setColor(GRAY);
            path = getPausePath();
        }else {
            mPaint.setColor(LIGHT_GRAY);
            path =getPlayPath();
        }
        canvas.drawPath(path,mPaint);

        canvas.drawCircle(measuredHeight/2,measuredHeight/2,radius,mPaint);

        if (max<=0||progress<=0){
            return;
        }
        mPaint.setColor(COLOR_PLAY);

        @SuppressLint("DrawAllocation")
        RectF oval=new RectF(measuredHeight*0.1f,measuredHeight*0.1f,measuredHeight * 0.9f,measuredHeight * 0.9f );
        canvas.drawArc(oval,0,getSweepAngle(),false,mPaint);


    }


    private Path getPlayPath() {

        int measuredHeight = getMeasuredHeight();
        int centerX =measuredHeight/2;
        int centerY =measuredHeight/2;
        Path path = new Path();
        path.moveTo(centerX-10,centerY-15);
        path.lineTo(centerX-10,centerY+15);
        path.moveTo(centerX+10,centerY-15);
        path.lineTo(centerX+10,centerY+15);
        return path;
    }

    private Path getPausePath() {
        int measuredHeight = getMeasuredHeight();
        int centerX =measuredHeight/2;
        int centerY =measuredHeight/2;
        Path path = new Path();
        path.moveTo(centerX+17,centerY);
        path.lineTo(centerX-8,centerY-15);
        path.lineTo(centerX-8,centerY+15);
        path.close();
        return path;
    }

    private float getSweepAngle() {
        return 360 * progress / max;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
        postInvalidate();
    }
}
