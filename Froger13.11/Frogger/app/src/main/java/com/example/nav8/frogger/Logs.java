package com.example.nav8.frogger;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by nav8 on 25. 10. 2017.
 */

public class Logs {

    private int x = 10; // sprite coordinate
    private int xSpeed;// = 20 ; // sprite x speed
    private int y=10;
    private boolean wrongway;

    private GameView gameView;  // reference to GameView
    private Bitmap bmp;         // sprite Bitmap


    public void setBmp(Bitmap b)
    {
        this.bmp=b;
    }
    public Bitmap getBmp()
    {
        return this.bmp;
    }
    public int getSpeed()
    {
        return xSpeed;
    }
    public void setSpeed(int speed)
    {
        this.xSpeed=speed;
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean getWrongway()
    {
        return wrongway;
    }

    public Logs(GameView gameView, Bitmap bmp, int xx, int yy,int speed,boolean w)
    {
        this.gameView=gameView;
        this.bmp=bmp;
        this.x=xx;
        this.y=yy;
        this.wrongway=w;
        if(this.wrongway)
            speed*=-1;
        this.setSpeed(speed);
    }


    // now we separated screen update from drawing a bitmap
    private void update()
    {
        if (!wrongway)
        {
            if (x > gameView.getWidth()) // - bmp.getWidth() - xSpeed) {
            {
                x = -58;
            }
            else
                x = x + xSpeed;
        }
        else
        {
            if (x < 0)
            {
                x = gameView.getWidth() + 58;
            }
            else
                x = x + xSpeed;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x , y, null);
    }
}
