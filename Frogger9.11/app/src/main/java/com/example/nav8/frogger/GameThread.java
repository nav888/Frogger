package com.example.nav8.frogger;

import android.graphics.Canvas;

/**
 * Created by nav8 on 25. 10. 2017.
 */
public class GameThread extends Thread {
    private GameView view;
    private boolean running = false;

    public GameThread(GameView view) {
        this.view = view;
    }
    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
