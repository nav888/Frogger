package com.example.nav8.frogger;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button btnStart;
    public Button btnHighScore;
    public ImageButton greenFrog;
    public ImageButton purpleFrog;
    public TextView textView1;
    public TextView textView2;
    public static int width;
    public static int height;
    public static int color=0;
    MediaPlayer menuSound;
    public void Init()
    {
        btnStart = (Button) findViewById(R.id.btnStartGame);
        btnHighScore = (Button) findViewById(R.id.btnHighScore);
        greenFrog=(ImageButton) findViewById(R.id.imageButton1);
        purpleFrog=(ImageButton) findViewById(R.id.imageButton2);
        textView1=(TextView) findViewById(R.id.textView1);
        textView2=(TextView) findViewById(R.id.textView2);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startGame();
            }
        });
        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showHighScore();
            }
        });

        greenFrog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                color=0;
            }
        });

        purpleFrog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                textView2.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.INVISIBLE);
                color=1;
            }
        });

        menuSound=MediaPlayer.create(getApplicationContext(),R.raw.menusound);
        menuSound.start();
        menuSound.setLooping(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();

        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView1.setTextColor(Color.WHITE);
        textView2.setTextColor(Color.WHITE);

        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight()-display.getHeight()/4;
    }

    private void startGame()
    {
        menuSound.pause();
        setContentView(new GameView(this));
    }

    private void showHighScore()
    {
        SharedPreferences preferences=getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("lastScore",GameView.scoreOfGame);
        editor.apply();

        Toast.makeText(getApplicationContext(),"High Score",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HighScore.class);
        startActivity(intent);
        menuSound.pause();
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.Menu){
            Toast.makeText(getApplicationContext(),"Main Menu",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.MenuHighScore)
        {
            showHighScore();
            finish();
        }
        return true;
    }



}
