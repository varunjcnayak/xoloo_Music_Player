package com.example.mahe.xoloo;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class songplay2 extends AppCompatActivity {
    MediaPlayer mp;
    SeekBar position;
    TextView textView8;
    TextView textView9;
    int totalTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songplay2);

        mp=MediaPlayer.create(this,R.raw.nazmnazm);
        textView8=(TextView)findViewById(R.id.textView8);
        textView9=(TextView)findViewById(R.id.textView9);
        mp.setLooping(true);
        mp.seekTo(0);
        totalTime=mp.getDuration();
        position=(SeekBar)findViewById(R.id.seekBar);
        position.setMax(totalTime);
        position.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if(b)
                {
                    mp.seekTo(i);
                    position.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mp!=null)
                {
                    try
                    {
                        Message msg=new Message();
                        msg.what=mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    }catch(InterruptedException e)
                    {

                    }
                }
            }
        }).start();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            int currentposition=msg.what;
            position.setProgress(currentposition);
            String elaspsed= createTimeLabel(currentposition);
            textView8.setText(elaspsed);
            String remain=createTimeLabel(totalTime-currentposition);
            textView9.setText(remain);
        }
    };
    public String createTimeLabel(int time)
    {
        String timelabel ="";
        int min=time/1000/60;
        int sec=time/1000%60;
        timelabel=min+":";
        if(sec<10)
            timelabel+="0";
        timelabel+=sec;
        return timelabel;
    }
    public void play(View view)
    {
        mp.start();
    }
    public void pause(View view)
    {
        mp.pause();
    }
    public void stop(View view)
    {
        mp.stop();
    }
}

