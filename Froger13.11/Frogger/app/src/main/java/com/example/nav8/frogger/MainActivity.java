package com.example.nav8.frogger;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button btnStart;
    public Button btnHighScore;
    public static int width;
    public static int height;
    MediaPlayer menuSound;
    public void Init()
    {
        btnStart = (Button) findViewById(R.id.btnStartGame);
        btnHighScore = (Button) findViewById(R.id.btnHighScore);

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
