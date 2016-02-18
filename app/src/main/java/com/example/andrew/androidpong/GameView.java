package com.example.andrew.androidpong;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Andrew on 2/9/2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread _thread;
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
        _thread = new GameThread(holder, context, handler);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return _thread.getGameState().keyPressed(keyCode, msg);
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
        if(!gameRunning) {
            gameRunning = true;
            _thread.start();
        }
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(gameRunning) {
            _thread.stopGame();
            gameRunning = false;
        }
    }

    /*
* Implemented in GameView.java, needs an import of android.view.MotionEvent
*
* calls getGameState().motionDetected() if there’s a touch on the screen.
*/
    public boolean onTouchEvent(MotionEvent event) {
        return _thread.getGameState().motionDetected(event);
    }

    private void sendVictoryMail()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_INTENT, "I am the Winnar ot Teh PongZ!");
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);

        /*Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_SUBJECT, "I am the Winner of Teh Pongs!");

        try
        {
            context.startActivity(Intent.createChooser(i, "Send Victory Mail!"));
        }
        catch(android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(context, "No Installed Email Clients", Toast.LENGTH_SHORT).show();
        }*/
    }
}