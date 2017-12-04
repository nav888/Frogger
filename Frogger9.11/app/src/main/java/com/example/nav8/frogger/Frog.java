package com.example.nav8.frogger;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;

/**
 * Created by nav8 on 1. 11. 2017.
 */


public class Frog
{
    private GameView gameView;  // reference to GameView
    private int xSpeed;

    public int startX=500;//gameView.getWidth()/2;
    public int startY=1300;
    private int y=startY;
    private int x=startX ; // sprite coordinate
    private int distanceOfJump=48;
    public static Bitmap bmp;         // sprite Bitmap



    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public void setX(int xx)
    {
         this.x=xx;
    }
    public void setY(int yy)
    {
        this.y=yy;
    }


    public void setStartPosition()
    {
        setX(startX);
        setY(startY);
    }


    public Frog(GameView gameView, Bitmap bmp)
    {
        this.gameView=gameView;
        this.bmp=bmp;
        this.x=startX;
        this.y=startY;//startY;
    }

    // now we separated screen update from drawing a bitmap
    public void update()
    {
        System.out.println(getX());
        if(GameView.currentX>900)
            if(getX()+distanceOfJump>gameView.getWidth()-bmp.getWidth())
                return;
        else
            setX(getX()+distanceOfJump);

        else if(GameView.currentX<180)
            if(getX()-distanceOfJump<0)
                return;
            else
                setX(getX()-distanceOfJump);

        if(GameView.currentY>y && GameView.currentX<900 && GameView.currentX>180)
            if(getY()==startY)
                return;
            else
                setY(getY()+distanceOfJump);

        else if(GameView.currentY<y && GameView.currentX<900 && GameView.currentX>180)
                setY(getY()-distanceOfJump);
    }

    public void updateWithLog(Logs log) {

        if (!log.getWrongway())
        {
            if (getX() > gameView.getWidth()) // - bmp.getWidth() - xSpeed) {
            {
                setX(-58);
            }
            else
                setX(log.getX()+(int)log.getSpeed());
        }
        else
        {
            if (getX() < 0)
            {
                setX(gameView.getWidth() + 58);
            }
            else
                setX(log.getX()+(int)log.getSpeed());
        }
    }

    public void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(bmp, x , y, null);
    }
}
