

package com.example.nav8.frogger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by nav8 on 7. 11. 2017.
 */

public class HighScore extends AppCompatActivity {
    TextView textViewScore;
    TextView textViewLastScore;
    Button btnReset;

    private int[] hscoreSounds={
            R.raw.highscore,
            R.raw.highscore2
    };
    private MediaPlayer sounds;



    int[] best=new int[11];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);
        textViewScore=(TextView) findViewById(R.id.textViewScore);
        textViewLastScore=(TextView) findViewById(R.id.textViewLastScore);
        final SharedPreferences preferences=getSharedPreferences("PREFS",0);
        btnReset=(Button) findViewById(R.id.buttonReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                preferences.edit().clear().commit();
                GameView.scoreOfGame=0;

                textViewScore.setText(
                        "1st: "+0+"\n"+
                        "2nd: "+0+"\n"+
                        "3rd: "+0+"\n"+
                        "4th: "+0+"\n"+
                        "5th: "+0+"\n"+
                        "6th: "+0+"\n"+
                        "7th: "+0+"\n"+
                        "8th: "+0+"\n"+
                        "9th: "+0+"\n"+
                        "10th: "+0);

                textViewLastScore.setText("Last score: "+0);
            }
        });



        for(int i =0;i<10;i++)
        {
            best[i]=preferences.getInt("best"+i,0);
        }

        best[10]=GameView.scoreOfGame;

        if(best[10]>best[9])
            best[9] = best[10];

        int temp, min;
        for (int i = 0; i < 10; i++)
        {
            min = 9;
            for (int j = i; j < 9 ;j++)
                if (best[min] < best[j])
                    min = j;
            temp = best[min];
            best[min] = best[i];
            best[i] = temp;

            SharedPreferences.Editor editor=preferences.edit();
            editor.putInt("best"+i,best[i]);
            editor.apply();
        }

        textViewScore.setText(
                "1st: "+best[0]+"\n"+
                "2nd: "+best[1]+"\n"+
                "3rd: "+best[2]+"\n"+
                "4th: "+best[3]+"\n"+
                "5th: "+best[4]+"\n"+
                "6th: "+best[5]+"\n"+
                "7th: "+best[6]+"\n"+
                "8th: "+best[7]+"\n"+
                "9th: "+best[8]+"\n"+
                "10th: "+best[9]);


        textViewLastScore.setText("Last score: "+GameView.scoreOfGame);

        if(GameView.scoreOfGame>best[9])
            sounds=MediaPlayer.create(getApplicationContext(),hscoreSounds[0]);
        else
            sounds=MediaPlayer.create(getApplicationContext(),hscoreSounds[1]);

        sounds.start();
        sounds.setLooping(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        sounds.stop();
        finish();
    }
}
