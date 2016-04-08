package com.example.attracti.audiorecorder2;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class AudioRecordTest extends Activity {

    long start;
    long after;

    int current = 1;
    int currlabel = 1;


    static String[] filetime = new String[100];

    public String[] getFiletime() {
        return filetime;
    }

    public void setFiletime(String[] filetime) {
        this.filetime = filetime;
    }

    CanvasView mCanvasView;

    private static int labeltime;

    BufferedReader br = null;
    File gpxfile;

    static String info2;

    public String getInfo2() {
        return info2;
    }

    public static void setInfo2(String info2) {
        AudioRecordTest.info2 = info2;
    }

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;

    private LabelButton mLabelButton = null;
    private ImportantButton mLabelPlayButton = null;

    // go to the next label
    private NextButton mNextButton = null;
    private PreviousButton mPreviousButton = null;

    public static int timefile = 1;

    public int getTimefile() {
        return timefile;
    }


    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void onPlayLabel(boolean start) {
        if (start) {
            startPlayingmodified();
        } else {
            stopPlaying();
        }
    }

    private void onPlayNext(boolean start) {
        if (start) {
            startNextPlaying();
        } else {
            stopPlaying();
        }
    }

    private void onPlayPrevious(boolean start) {
        if (start) {
            startPreviousPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void startPlayingmodified() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.seekTo(Integer.parseInt(filetime[0]));
            mPlayer.start();

            Log.i("Stop", String.valueOf(filetime[1]));

            //playing the next label
            new CountDownTimer(Integer.parseInt(filetime[1]) - Integer.parseInt(filetime[0]), 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    stopPlaying();
                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startNextPlaying() {
        mPlayer = new MediaPlayer();
        try {
            if (currlabel < filetime.length - 1) {
                currlabel++;
            }
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.seekTo(Integer.parseInt(filetime[currlabel - 1]));
            mPlayer.start();

            Log.i("CurrentLabel", String.valueOf(currlabel));

            new CountDownTimer(Integer.parseInt(filetime[currlabel]) - Integer.parseInt(filetime[currlabel - 1]), 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    stopPlaying();
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startPreviousPlaying() {
        mPlayer = new MediaPlayer();
        try {

            if (currlabel > 1) {
                currlabel--;
            }
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.seekTo(Integer.parseInt(filetime[currlabel - 1]));
            mPlayer.start();
            Log.i("CurrentLabelPrevious", String.valueOf(currlabel));
            new CountDownTimer(Integer.parseInt(filetime[currlabel]) - Integer.parseInt(filetime[currlabel - 1]), 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    stopPlaying();
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    public void readFromFile() {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(gpxfile));
            String line;

            while ((line = br.readLine()) != null) {

                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TextInfo", String.valueOf(text));

        filetime = text.toString().split("\n");
        Arrays.sort(filetime);
        for (int i = 0; i < filetime.length - 1; i++) {
            Log.i("Sorted array 2", filetime[i]);
        }
    };

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);

                start = System.currentTimeMillis();
                android.util.Log.i("Time Current ", " Time value in millisecinds " + start);

                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }

    }

    class LabelButton extends Button {
        public LabelButton(Context ctx) {
            super(ctx);
            setText("Label");
        }
    }

    class ImportantButton extends Button {
        public ImportantButton(Context ctx) {
            super(ctx);
            setText("Play Label");
        }
    }

    class NextButton extends Button {
        public NextButton(Context ctx) {
            super(ctx);
            setText("Next Label");
        }
    }

    class PreviousButton extends Button {
        public PreviousButton(Context ctx) {
            super(ctx);
            setText("Previous Label");
        }
    }

    public AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        mNextButton = new NextButton(this);
        ll.addView(mNextButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));

        mPreviousButton = new PreviousButton(this);
        ll.addView(mPreviousButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));


        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        mLabelButton = new LabelButton(this);

        mLabelPlayButton = new ImportantButton(this);
        ll.addView(mLabelPlayButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));

        mLabelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPlayer == null) {
                    after = System.currentTimeMillis();
                    android.util.Log.i("Time after click", " Time value in millisecinds " + after);
                    int difference = (int) (after - start);
                    Log.i("difference", String.valueOf(difference));

                    // here should be a method write to file
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                    Date now = new Date();
                    String fileName = formatter.format(now) + ".txt";//like 2016_01_12.txt
                    int sBody = difference;

                    Log.i("Current", String.valueOf(labeltime));

                    try {
                        // File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Music_Folder", "Report Files");
                        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        gpxfile = new File(root, fileName);

                        FileWriter writer = new FileWriter(gpxfile, true);
                        writer.append(sBody + "\n");
                        writer.flush();
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    readFromFile();
                    mCanvasView.drawLine();

                } else {

                    info2 = String.valueOf(mPlayer.getCurrentPosition());
                    labeltime = mPlayer.getCurrentPosition();

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                    Date now = new Date();
                    String fileName = formatter.format(now) + ".txt";//like 2016_01_12.txt
                    String sBody = info2;

                    Log.i("Current", String.valueOf(labeltime));

                    try {
                        // File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Music_Folder", "Report Files");
                        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        gpxfile = new File(root, fileName);

                        FileWriter writer = new FileWriter(gpxfile, true);
                        writer.append(sBody + "\n");
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    readFromFile();
                    mCanvasView.drawLine();
                }
            }
        });

        //play first label
        mLabelPlayButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean mStartPlaying = true;
                readFromFile();
                mCanvasView = new CanvasView(AudioRecordTest.this);
             //   mCanvasView.drawLine();
                onPlayLabel(mStartPlaying);
            }
        });

        ll.addView(mLabelButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));

        mNextButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean mStartPlaying = true;
                StringBuilder text = new StringBuilder();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(gpxfile));
                    String line;

                    while ((line = br.readLine()) != null) {

                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("TextInfo", String.valueOf(text));

                String[] filetime = text.toString().split("\n");

                if (current - 1 < filetime.length - 1) {
                    current++;
                }


                Log.i("Iinfo!!!!", String.valueOf(current));

                mCanvasView = new CanvasView(AudioRecordTest.this);
                mCanvasView.invalidate();
                onPlayNext(mStartPlaying);
            }
        });


        mPreviousButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean mStartPlaying = true;
                onPlayPrevious(mStartPlaying);
            }
        });


        mCanvasView = new CanvasView(AudioRecordTest.this);

        ll.addView(mCanvasView,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));

        setContentView(ll);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
