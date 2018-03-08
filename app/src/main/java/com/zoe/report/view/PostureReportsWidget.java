package com.zoe.report.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zoe.report.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostureReportsWidget extends View {
    public PostureReportsWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public PostureReportsWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public PostureReportsWidget(Context context) {
        super(context);
        init();
    }

    private int mWidth, mHeight = 200;
    private int unitX,unitLen/*一个小刻度的长度*/;
    private float unitY;
    private int blankLeave;
    private int textSize, space;
    private Paint mPaint = new Paint();
    private void init() {
        unitLen = dip2px(6);
        mPaint.setColor(getResources().getColor(R.color.progress_widget_bg));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
        textSize = dip2px(12);
        space = dip2px(10);
        blankLeave = space / 2;
        mPaint.setTextSize(textSize);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private int dip2px(int dp){
        return (int) (getResources().getDisplayMetrics().density * dp);
    }
    private List<Integer> mList = new ArrayList<>();
    private Map<Integer, String> mMap = new HashMap<>();
    public void setData(List<Integer> list, Map<Integer, String> map){
        mList.clear();
        mList.addAll(list);
        mMap = map;
        unitX = (mWidth - space - blankLeave * 2)/(mList.size() - 1);
        int max = 0;
        for(int i : mList){
            if(i > max){
                max = i;
            }
        }
        if(max == 0) unitY = 0;
        else unitY = (mHeight - space)/ (float)max;//顶部剩余最少10dp
        invalidate();
    }

    /**
     * 设置留白
     * @param blankLeave 单位:dp
     */
    public void setBlankLeave(int blankLeave){
        this.blankLeave = dip2px(blankLeave);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHeight = getHeight() - textSize * 2 - space;
        mWidth = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(2);
        canvas.drawLine(space / 2, mHeight, mWidth - space / 2, mHeight, mPaint);

        for(int i = 0; i < mList.size(); i++){
            if(mMap.containsKey(i)){
                mPaint.setStrokeWidth(4);
                canvas.drawLine(space/2 + blankLeave + i * unitX, mHeight, space/2 + blankLeave + i * unitX, mHeight + unitLen * 2, mPaint);
                String text = mMap.get(i);
                float len = mPaint.measureText(text);
                if(len / 2 > space/2 + blankLeave + i * unitX){
                    mPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText(text, 5, mHeight + unitLen * 2 + textSize, mPaint);
                    mPaint.setTextAlign(Paint.Align.CENTER);
                }else if(len / 2 > mWidth - space/2 - blankLeave - i * unitX){
                    mPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(text, mWidth - 5, mHeight + unitLen * 2 + textSize, mPaint);
                    mPaint.setTextAlign(Paint.Align.CENTER);
                }else {
                    canvas.drawText(text, space/2 + blankLeave + i * unitX, mHeight + unitLen * 2 + textSize, mPaint);
                }
                mPaint.setStrokeWidth(2);
            }else {
                canvas.drawLine(space/2 + blankLeave + i * unitX, mHeight, space/2 + blankLeave + i * unitX, mHeight + unitLen, mPaint);
            }
        }
        mPaint.setStrokeWidth(25);
        for(int i = 0; i < mList.size(); i++){
            if(mList.get(i) > 0){
                canvas.drawLine(space/2 + blankLeave + i * unitX, mHeight - dip2px(4), space/2 + blankLeave + i * unitX, mHeight - mList.get(i) * unitY, mPaint);
            }
        }
    }
}
