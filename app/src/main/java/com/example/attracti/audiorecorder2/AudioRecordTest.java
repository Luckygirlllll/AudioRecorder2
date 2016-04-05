package com.example.attracti.audiorecorder2;

import android.app.Activity;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AudioRecordTest extends Activity {

    CanvasView mCanvasView;

    private int labeltime;
    ArrayList <Integer> time=new ArrayList();


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;

    private LabelButton mLabelButton = null;
    private ImportantButton mLabelPlayButton= null;
    private MediaPlayer   lPlayer = null;


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
            mPlayer.seekTo(labeltime);
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
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

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
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

    class  LabelButton extends Button  {
        public LabelButton(Context ctx) {
            super(ctx);
            setText("Label");
        }
    }

    class  ImportantButton extends Button  {
        public ImportantButton(Context ctx) {
            super(ctx);
            setText("Play Label");
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
        mLabelButton= new LabelButton(this);

        mLabelPlayButton= new ImportantButton(this);
        ll.addView(mLabelPlayButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));

        mLabelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String info2 = String.valueOf(mPlayer.getCurrentPosition());
                labeltime = mPlayer.getCurrentPosition();

                Log.i("BUttonClicked", "done");

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                Date now = new Date();
                String fileName = formatter.format(now) + ".txt";//like 2016_01_12.txt
                String sBody = info2;
                Log.i("Current", String.valueOf(labeltime));

                time.add(labeltime);
                for (int i=0; i<time.size(); i++ ){
                    Log.i("LabelInfo", String.valueOf(time.get(i)));
                }

                try {
                    // File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Music_Folder", "Report Files");
                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, fileName);

                    FileWriter writer = new FileWriter(gpxfile, true);
                    writer.append(sBody + "\n\n");
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        mLabelPlayButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean mStartPlaying = true;
                Log.i("Button", "bingo");
                Log.i("Current2", String.valueOf(labeltime));
                mPlayer.seekTo(labeltime);
                onPlayLabel(mStartPlaying);

                time.add(labeltime);
                for (int i=0; i<time.size(); i++ ){
          Log.i("LabelInfo", String.valueOf(time.get(i)));
        }
            }
        });

                 ll.addView(mLabelButton,
                         new LinearLayout.LayoutParams(
                                 ViewGroup.LayoutParams.WRAP_CONTENT,
                                 ViewGroup.LayoutParams.WRAP_CONTENT,
                                 0));
                 mCanvasView = new CanvasView(getApplicationContext());
                 ll.addView(mCanvasView,
                         new LinearLayout.LayoutParams(
                                 ViewGroup.LayoutParams.WRAP_CONTENT,
                                 ViewGroup.LayoutParams.WRAP_CONTENT,
                                 0));
                 mCanvasView.drawLine();
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
