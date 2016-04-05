package com.example.attracti.audiorecorder2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by attracti on 4/5/16.
 */
public class CanvasView extends View {
    public Paint mPaint;
    public static Canvas mCanvas;
    private int startX=50;
    private int startY=50;
    private int stopX = 500;
    private int stopY = 500;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    //constructor
    public CanvasView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public void drawLine() {
        Random random = new Random();
        startX = random.nextInt(1000);
        Log.i("nextInt", String.valueOf(startX));
        Log.i("nextInt", String.valueOf(startY));
        startX=startY;

        //important. Refreshes the view by calling onDraw function
        invalidate();
    }


    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        super.onDraw(mCanvas);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        canvas.drawLine(0, 200, 1000, 200, mPaint);
        canvas.drawLine(0, 300, 1000, 300, mPaint);
        canvas.drawLine(0, 400, 1000, 400, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(startX, 200, startY, 400, mPaint);
    }
}
