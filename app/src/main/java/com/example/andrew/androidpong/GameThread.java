package com.example.andrew.androidpong;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.WindowManager;

/**
 * Created by Andrew on 2/9/2016.
 */
public class GameThread extends Thread implements Runnable{

    /** Handle to the surface manager object we interact with */
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private GameState state;
    private boolean finished;
    private Handler handler;
    private Context context;

    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler)
    {
        this.surfaceHolder = surfaceHolder;
        paint = new Paint();

        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        state = new GameState(metrics.heightPixels, metrics.widthPixels);
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        finished = false;
        while(!finished)
        {
            Canvas canvas = surfaceHolder.lockCanvas();
            state.update();
            state.draw(canvas, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
            if(state.isGameWon())
            {
                stopGame();
                ((Activity)context).finish();

                Message message = Message.obtain();
                handler.sendMessage(message);
            }
        }
    }

    public GameState getGameState()
    {
        return state;
    }

    public void stopGame()
    {
        finished = true;
    }
}
