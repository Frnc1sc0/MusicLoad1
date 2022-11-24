package com.example.musicload1;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button b1,b2,b3,b4;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;
    int canciones[] = {R.raw.bones, R.raw.sharks};
    String can[] = {"bones.mp3", "sharks.mp3"};
    int index =0;
    public static int oneTimeOnly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.play);
        b2 = (Button) findViewById(R.id.pause);
        b3 = (Button) findViewById(R.id.next);
        b4 = (Button) findViewById(R.id.ant);
        tx1 = (TextView) findViewById(R.id.textView2);
        tx2 = (TextView) findViewById(R.id.textView3);
        tx3 = (TextView) findViewById(R.id.textView4);
        tx3.setText(can[index]);
        mediaPlayer = MediaPlayer.create(this,canciones[index]);
        seekbar = (SeekBar)findViewById(R.id.seekBar);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();
                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }
                tx2.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                );
                tx1.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                );
                seekbar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime,100);
            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        b3.setOnClickListener(view -> {
            index++;
            if(index>1)index=0;
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplication(), canciones [index]);
            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();
            tx3.setText(can[index]);
        });

        b4.setOnClickListener(view -> {
            index--;
            if(index>0)index=1;
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplication(), canciones [index]);
            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();
            tx3.setText(can[index]);
        });

    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
}