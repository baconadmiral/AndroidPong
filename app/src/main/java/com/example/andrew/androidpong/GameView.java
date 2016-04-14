package com.example.andrew.androidpong;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Andrew on 2/9/2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread thread;
    private Handler handler;
    private Context context;
    private boolean gameRunning = false;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //So we can listen for events...
        this.context = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        handler = new Handler(){
            @Override
        public void handleMessage(Message msg){
                sendVictoryMail();
            }
        };

        //and instantiate the thread
        thread = new GameThread(holder, context, handler);
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        //Mandatory, just swallowing it for this example

    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
            thread.start();
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
            thread.stopGame();
    }

    /*
* Implemented in GameView.java, needs an import of android.view.MotionEvent
*
* calls getGameState().motionDetected() if there’s a touch on the screen.
*/
    public boolean onTouchEvent(MotionEvent event) {
        return thread.getGameState().motionDetected(event);
    }

    private void sendVictoryMail()
    {
        //Implicit intent call
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}