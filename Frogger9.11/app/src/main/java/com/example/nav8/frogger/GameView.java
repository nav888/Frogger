package com.example.nav8.frogger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by nav8 on 25. 10. 2017.
 */

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private GameThread gameThread;
    private int speed1 = 5;
    private int speed2 = 10;
    private int level = 1;
    boolean toStart = false;
    public static float currentX; // x coordinate for touch
    public static float currentY; //y coordinate for touch
    private int score = 0;
    private int lifesLeft = 5;
    private boolean boolNewGame=false;
    private int randNum;
    Paint paint = new Paint();
    private int lastBestPosition=1300;
    private int pomLog=0;
    private int pomCar=0;

    public static int scoreOfGame;

    // declare ArrayList of spriteLogs
    private List<Logs> spriteLogs = new ArrayList<Logs>();
    private List<Car> spriteCars = new ArrayList<Car>();
    private List<Car> spriteCarsBonus = new ArrayList<Car>();
    private int carColors[] =
            {
                    R.drawable.car1,
                    R.drawable.car3,
                    R.drawable.car5,
                    R.drawable.car7,
                    R.drawable.car9,
                    R.drawable.car0,
                    R.drawable.car2,
                    R.drawable.car4,
                    R.drawable.car6,
                    R.drawable.car8
            };

    private int soundType[]=
            {
                    R.raw.crazyfrog1,
                    R.raw.crazyfrog2,
                    R.raw.crazyfrog3,
                    R.raw.crazyfrog4,
                    R.raw.crazyfrog5,
            };



    MediaPlayer frogSound=MediaPlayer.create(getContext(),R.raw.frogsound);
    MediaPlayer carSound=MediaPlayer.create(getContext(),R.raw.carsound);
    MediaPlayer waterSound=MediaPlayer.create(getContext(),R.raw.watersound);
    MediaPlayer backSound=MediaPlayer.create(getContext(),soundType[getRandomNumber(0,4)]);
    MediaPlayer levelupSound=MediaPlayer.create(getContext(),R.raw.levelup);
    MediaPlayer booSound =MediaPlayer.create(getContext(),R.raw.applause);
    MediaPlayer applauseSound =MediaPlayer.create(getContext(),R.raw.applause1);


    private Frog spriteFrog;
    public GameView(Context context) {
        super(context);
        gameThread = new GameThread(this);
        holder = getHolder();

            holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameThread.setRunning(false);
                while (retry) {
                    try {
                        gameThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });

        // add sprite elements to Arraylist

        spriteLogs.add(createSpriteLog(R.drawable.wood, 100, 148, speed1, true));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 100, 148 + 48, speed2, false));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 100, 148 + 48 * 2, speed1, false));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 100, 148 + 48 * 3, speed2, true));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 500, 148, speed1, true));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 500, 148 + 48 * 2, speed1, false));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 500, 148 + 48, speed2, false));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 500, 148 + 48 * 3, speed2, true));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 900, 148 + 48, speed2, false));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 900, 148 + 48 * 2, speed1, false));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 900, 148 + 48 * 3, speed2, true));
        spriteLogs.add(createSpriteLog(R.drawable.wood, 900, 148, speed1, true));

        spriteCars.add(createSpriteCar(carColors[getRandomNumber(0, 4)], 800, 960, speed1, false));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(0, 4)], 400, 960, speed1, false));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(0, 4)], 0, 960, speed1, false));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(0, 4)], 0, 960 + 48 * 3, speed2, false));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(0, 4)], 800, 960 + 48 * 3, speed2, false));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(0, 4)], 400, 960 + 48 * 3, speed2, false));

        spriteCars.add(createSpriteCar(carColors[getRandomNumber(5, 9)], 0, 960 - 48 * 3, speed1, true));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(5, 9)], 800, 960 - 48 * 3, speed1, true));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(5, 9)], 400, 960 - 48 * 3, speed1, true));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(5, 9)], 0, 960 - 48 * 6, speed2, true));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(5, 9)], 400, 960 - 48 * 6, speed2, true));
        spriteCars.add(createSpriteCar(carColors[getRandomNumber(5, 9)], 800, 960 - 48 * 6, speed2, true));

        spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(5, 6)], 0, 960 - 216, speed1, true));
        //spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(6, 7)], 100, 960 - 72, speed1, true));
        spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(8, 9)], 200, 960 + 72, speed1, false));
        spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(5, 6)], 400, 960 - 216, speed1, true));
        //spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(6, 7)], 500, 960 - 72, speed1, true));
        spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(8, 9)], 600, 960 + 72, speed1, false));
        spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(5, 6)], 800, 960 - 216, speed1, true));
        //spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(6, 7)], 900, 960 - 72, speed1, true));
        spriteCarsBonus.add(createSpriteCar(carColors[getRandomNumber(8, 9)], 1000, 960 + 72, speed1, false));


        spriteFrog = createSpriteFrog(R.drawable.frog);
    }

    private int getRandomNumber(int min, int max) {
        return (new Random(System.currentTimeMillis())).nextInt((max - min) + 1) + min;
    }

    private Frog createSpriteFrog(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Frog(this, bmp);
    }

    private Logs createSpriteLog(int resource, int x, int y, int s, boolean w) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Logs(this, bmp, x, y, s, w);
    }

    private Car createSpriteCar(int resource, int x, int y, int s, boolean w) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Car(this, bmp, x, y, s, w);
    }


    boolean ted=false;
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null)
        {
            if(boolNewGame)
            {
                canvas.drawColor(Color.WHITE);
                printYourScore(canvas);
                scoreOfGame=getScore();
            }

            else
            {
                paint.setColor(Color.GREEN);
                canvas.drawRect(0,0,1080,184 , paint);
                paint.setColor(Color.BLUE);
                canvas.drawRect(0, 185, 1080, 365, paint);
                paint.setColor(Color.GREEN);
                canvas.drawRect(0, 366, 1080, 675, paint);
                paint.setColor(Color.GRAY);
                canvas.drawRect(0, 676, 1080, 1200, paint);
                paint.setColor(Color.WHITE);

                canvas.drawRect(0, 676, 1080, 680, paint);
                canvas.drawRect(0, 938, 1080, 942, paint);
                canvas.drawRect(0, 1196, 1080, 1200, paint);

                canvas.drawRect(50, 806, 200, 810, paint);
                canvas.drawRect(450, 806, 600, 810, paint);
                canvas.drawRect(850, 806, 1000, 810, paint);

                canvas.drawRect(100, 1070, 250, 1074, paint);
                canvas.drawRect(500, 1070, 650, 1074, paint);
                canvas.drawRect(900, 1070, 1050, 1074, paint);

                paint.setColor(Color.GREEN);
                canvas.drawRect(0, 1201, 1080, 1920, paint);

                if(spriteFrog.getY()>0 && spriteFrog.getY()<183  ||
                   spriteFrog.getY()>365 && spriteFrog.getY()<674||
                   spriteFrog.getY()>1200)
                {
                    backSound.start();
                    backSound.setLooping(true);
                }
                if(spriteFrog.getY()>184 && spriteFrog.getY()<365)
                {
                    backSound.pause();
                    waterSound.setVolume(0.1f,0.1f);
                    waterSound.start();
                }

                if(spriteFrog.getY()>675 && spriteFrog.getY()<1200)
                {
                    backSound.pause();
                    carSound.setVolume(0.1f,0.1f);
                    carSound.start();
                }
                if (getRandomNumber(0, 5) == 1)
                    frogSound.start();

                setLifes(canvas, getLifesLeft());
                scoreAndLevel(canvas);
                drawAll(canvas);
                toStart = false;
                checkAllCollision();

                if (toStart)
                {
                    setLifesLeft(getLifesLeft() - 1);
                    booSound.start();
                    if (getLifesLeft() < 0)
                    {
                        boolNewGame = true;
                    }
                    spriteFrog.setStartPosition();
                }

                if (spriteFrog.getY() < 10) {
                    speedUp();
                    setScore(getScore() + 50 * getLevel());
                    setLevel(getLevel() + 1);

                    if(getLevel()%2==0 && pomLog<6) {
                        spriteLogs.remove(pomLog);
                        pomLog++;
                    }
                    if(getLevel()%3==0 && pomCar<6) {
                        spriteCars.add(spriteCarsBonus.get(pomCar));
                        pomCar++;
                    }

                    applauseSound.start();
                    levelupSound.start();
                    if(getLifesLeft()<5) {
                        setLifesLeft(getLifesLeft() + 1);
                    }
                    spriteFrog.setStartPosition();
                    setlastBestPosition(1300);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event
    ) {
        currentX = event.getX();
        currentY = event.getY();
        spriteFrog.update();
        if(spriteFrog.getY()<getlastBestPosition()) {
            setlastBestPosition(spriteFrog.getY());
            setScore(getScore() + 2);
        }
        return false;
    }

    private boolean isCollisionCar(Car sprite)
    {
        if (abs(spriteFrog.getY() - sprite.getY()) <= sprite.getBmp().getHeight() &&
                abs(spriteFrog.getX() - sprite.getX()) <= sprite.getBmp().getWidth()) {
            return true;
        } else
            return false;
    }

    private boolean isCollisionLog(Logs sprite)
    {
        boolean result = false;
        if (abs(spriteFrog.getY() - sprite.getY()) <= sprite.getBmp().getHeight() &&
                abs(spriteFrog.getX() - sprite.getX()) <= sprite.getBmp().getWidth()) {
            return true;
        }
        else
            result = false;

        return result;
    }

    private void drawAll(Canvas canvas)
    {
        for (Car sprite : spriteCars)
        {
            sprite.onDraw(canvas);
        }
        for (Logs sprite : spriteLogs)
        {
            sprite.onDraw(canvas);
        }
        spriteFrog.onDraw(canvas);

    }

    private void checkAllCollision()
    {
        for (Car sprite : spriteCars)
        {
            if (spriteFrog.getY() < sprite.getY() + 48 && spriteFrog.getY() > sprite.getY() - 48)
            {
                if (isCollisionCar(sprite))
                {
                    toStart = true;
                    break;
                }
                else
                {
                    toStart = false;
                }
            }
        }
        for (Logs sprite : spriteLogs)
        {
            if (spriteFrog.getY() == sprite.getY())
            {
                if (isCollisionLog(sprite))
                {
                    spriteFrog.updateWithLog(sprite);
                    toStart = false;
                    break;
                }
                else
                {
                    toStart = true;
                }
            }
        }
    }

    private void scoreAndLevel(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("Score: " + getScore(), 0, 1500, paint);
        canvas.drawText("Level: " + getLevel(), 0, 1570, paint);
    }

    private void printYourScore(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setTextSize(150);
        canvas.drawText("GAME OVER", 100, 750, paint);
        paint.setTextSize(75);
        canvas.drawText("Your score: " + getScore(), 300, 1200, paint);
        backSound.stop();
    }

    private void speedUp()
    {
        for (Car sprite : spriteCars)
        {
            sprite.setSpeed((int) (sprite.getSpeed() * 1.20));
        }
        for (Logs sprite : spriteLogs)
        {
            sprite.setSpeed((int) (sprite.getSpeed() * 1.20));
        }
    }

    private void setScore(int s)
    {
        this.score = s;
    }

    private int getScore()
    {
        return this.score;
    }

    private void setLevel(int s)
    {
        this.level = s;
    }

    private int getLevel()
    {
        return this.level;
    }

    private int getLifesLeft()
    {
        return lifesLeft;
    }

    private void setLifesLeft(int l)
    {
        this.lifesLeft = l;
    }

    private void setNewGame(){
        setLifesLeft(5);
        setScore(0);
        setLevel(1);
    }
    private void setLifes(Canvas canvas, int left)
    {
        Drawable d[] = {getResources().getDrawable(R.drawable.life), getResources().getDrawable(R.drawable.life2)};
        int x1 = 560;
        int y1 = 1490;
        int x2 = 660;
        int y2 = 1590;
        for (int i = 0; i < 5; i++)
        {
            if (i < left)
            {
                d[0].setBounds(x1, y1, x2, y2);
                d[0].draw(canvas);
            }
            else
            {
                d[1].setBounds(x1, y1, x2, y2);
                d[1].draw(canvas);
            }
            x1 += 100;
            x2 += 100;
        }
    }
    private int getlastBestPosition()
    {
        return lastBestPosition;
    }
    private void setlastBestPosition(int i)
    {
        lastBestPosition=i;
    }
}


