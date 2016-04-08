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
 * Created by Iryna on 4/5/16.
 */
public class CanvasView extends View {
    public Paint mPaint;
    public static Canvas mCanvas;
    private int startX=50;

    static String [] times;

    AudioRecordTest  audio =new AudioRecordTest();

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    public CanvasView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public void drawLine() {

        // 5 minutes = 5*60*1000 = 300 000
        // 300 000 ---> 1000
        // 1020 --->x
        // x=time*1000/300 000
        // x= time/300

//        Log.i("TIME!!!", audio.getInfo2());

        times= audio.getFiletime();

        for(int i=0; i<times.length; i++){
         Log.i("TIME ALl", audio.getFiletime()[i]);
        }

        Log.i("Number of times", String.valueOf(times.length));
//        Random random = new Random();
//        startX = random.nextInt(1000);
        for(int i=0; i<times.length; i++) {
            Log.i("startX1", startX + " ");
            startX = Integer.parseInt(audio.getFiletime()[i])/10;
            Log.i("startX2", startX +"");
        }

        Log.i("startX3", startX+" ");
      //  Log.i("nextInt", String.valueOf(startY));

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

        if(times!=null) {
            Log.i("Number of times 2", String.valueOf(times.length));
            for(int i=0; i<times.length; i++) {
                canvas.drawLine(startX, 200, startX, 400, mPaint);
            }
        }

      //  canvas.drawLine(startX, 200, startX, 400, mPaint);
     //   Log.i("startX2", String.valueOf(startX));
      //  Log.i("startY2", String.valueOf(startY));


    }
}
