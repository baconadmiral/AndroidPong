package com.example.andrew.androidpong;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

/**
 * Created by Andrew on 2/9/2016.
 */
public class GameThread extends Thread implements Runnable{

    /** Handle to the surface manager object we interact with */
    private SurfaceHolder _surfaceHolder;
    private Paint _paint;
    private GameState _state;
    private boolean finished;
    private Handler handler;
    private Context context;

    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler)
    {
        _surfaceHolder = surfaceHolder;
        _paint = new Paint();
        _state = new GameState();
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        finished = false;
        while(!finished)
        {
            Canvas canvas = _surfaceHolder.lockCanvas();
            _state.update();
            _state.draw(canvas,_paint);
            _surfaceHolder.unlockCanvasAndPost(canvas);
            if(_state.isGameWon())
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
        return _state;
    }

    public void stopGame()
    {
        finished = true;
    }
}
