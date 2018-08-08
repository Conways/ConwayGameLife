package com.conways.conwaygamelife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */

public class ConwayView extends View {

    private Paint bgPaint;
    private Paint gridPaint;
    private Paint liveLifePaint;

    private int gridCounts;

    private int mWith;
    private int mHeight;

    private int[][] holder;


    public ConwayView(Context context) {
        super(context);
        init();
    }

    public ConwayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConwayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        bgPaint = new Paint();
        bgPaint.setColor(0xffffffff);
        bgPaint.setAntiAlias(true);
        gridPaint = new Paint();
        gridPaint.setColor(0xff0000ff);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(0.5f);
        liveLifePaint = new Paint();
        liveLifePaint.setColor(0xff000000);
        liveLifePaint.setAntiAlias(true);
        gridCounts = 50;
        holder = new int[gridCounts][gridCounts];
        for (int i = 0; i < holder.length; i++) {
            for (int j = 0; j < holder[0].length; j++) {
                holder[i][j] = i == holder.length / 2 ? 1 : 0;
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMeasureMode == MeasureSpec.EXACTLY) {
            mWith = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            mWith = 400;
        }
        if (heightMeasureMode == MeasureSpec.EXACTLY) {
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            mHeight = 400;
        }
        setMeasuredDimension(mWith, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawLiveLife(canvas);
        drawGrid(canvas);
    }

    private void drawBg(Canvas canvas) {

        canvas.drawRect(0, 0, mWith, mHeight, bgPaint);

    }

    private void drawLiveLife(Canvas canvas) {
        for (int i = 0; i < holder.length; i++) {
            for (int j = 0; j < holder[0].length; j++) {
                if (holder[i][j]==0){
                    continue;
                }
                canvas.drawRect(j*mWith/gridCounts,i*mHeight/gridCounts,(j+1)*mWith/gridCounts,(i+1)*mHeight/gridCounts,liveLifePaint);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < gridCounts + 1; i++) {
            canvas.drawLine(0, i * mWith / gridCounts, mWith, i * mWith / gridCounts, gridPaint);
            canvas.drawLine(i * mWith / gridCounts, 0, i * mWith / gridCounts, mHeight, gridPaint);
        }
    }



    private int[][] next(int[][] previous) {

        int[][] now = copyArray(previous);
        for (int i = 0; i < previous.length; i++) {
            for (int j = 0; j < previous[i].length; j++) {
                int lifeNum = findLifeNum(j, i, previous);
                if (previous[i][j] == 0) {
                    if (lifeNum == 3) {
                        now[i][j] = 1;
                    }
                } else {
                    if (lifeNum == 2 || lifeNum == 3) {
                        continue;
                    }
                    now[i][j] = 0;

                }
            }
        }
        return now;
    }

    private int[][] copyArray(int[][] model) {
        int[][] child = new int[model.length][model[0].length];
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[0].length; j++) {
                child[i][j] = model[i][j];
            }
        }
        return child;
    }

    private int findLifeNum(int x, int y, int[][] previous) {

        int num = 0;
        //左边
        if (x != 0) {
            num += previous[y][x - 1];
        }
        //左上角
        if (x != 0 && y != 0) {
            num += previous[y - 1][x - 1];
        }
        //上边
        if (y != 0) {
            num += previous[y - 1][x];
        }
        //右上
        if (x != previous[y].length - 1 && y != 0) {
            num += previous[y - 1][x + 1];
        }
        //右边
        if (x != previous[y].length - 1) {
            num += previous[y][x + 1];
        }
        //右下
        if (x != previous[y].length - 1 && y != previous.length - 1) {
            num += previous[y + 1][x + 1];
        }
        //下边
        if (y != previous.length - 1) {
            num += previous[y + 1][x];
        }
        //左下
        if (x != 0 && y != previous.length - 1) {
            num += previous[y + 1][x - 1];
        }
        return num;
    }

    public void getnext(){
        holder=copyArray(next(holder));
        invalidate();
    }

    public void reset(){
        for (int i = 0; i < holder.length; i++) {
            for (int j = 0; j < holder[0].length; j++) {
                holder[i][j] = i == holder.length / 2 ? 1 : 0;
            }
        }
        invalidate();
    }

}
