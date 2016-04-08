package com.example.attracti.audiorecorder2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
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

    int x ;
    int y;
    AudioRecordTest  audio;
//    public CanvasView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mPaint = new Paint();
//        audio = (AudioRecordTest)context;
//
//    }

    //constructor
//    public CanvasView(AudioRecordTest context,int x, int y) {
//        super(context);
//        mPaint = new Paint();
//        audio = (AudioRecordTest)context;
//
//        this.startX = x;
//        this.startY = y;
//    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    public CanvasView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public void drawLine() {
        Random random = new Random();
        startX = random.nextInt(1000);

        Log.i("nextInt", String.valueOf(startX));
        Log.i("nextInt", String.valueOf(startY));

        //important. Refreshes the view by calling onDraw function
        invalidate();
    }



//    public void drawLine(int i1, int i) {
//
//        Random random = new Random();
//          startX = random.nextInt(1000);
//        Log.i("nextInt", String.valueOf(startX));
//        Log.i("nextInt", String.valueOf(startY));
//        startX=startY;

        // 5 minutes = 5*60*1000 = 300 000
        // 300 000 ---> 1000
        // 1020 --->x
        // x=time*1000/300 000
        // x= time/300

//        if (audio!=null) {
//            String[] timeImp = audio.getFiletime();
//            if (timeImp != null) {
//                Log.i("Time in the canvas view", String.valueOf(audio.getFiletime()));
//            }
//        }

      // int labeltime= audio.getLabeltime();
      // Log.i("labeltime", String.valueOf(labeltime));
      // int timeImp3=Integer.parseInt(timeImp2);
      // Log.i("timeImp3", String.valueOf(timeImp3));

//        startX=x;
//        startY=y;


//        Log.i("startX", String.valueOf(startX));
//        Log.i("startY", String.valueOf(startY));
//        //important. Refreshes the view by calling onDraw function
//        invalidate();
//
//    }


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
        canvas.drawLine(startX, 200, startX, 400, mPaint);
        Log.i("startX2", String.valueOf(startX));
        Log.i("startY2", String.valueOf(startY));
    }
}
