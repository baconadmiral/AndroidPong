package com.example.andrew.androidpong;

import android.content.Context;
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

    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /*
* Implemented in GameView.java, needs an import of android.view.MotionEvent
*
* calls getGameState().motionDetected() if there’s a touch on the screen.
*/
    public boolean onTouchEvent(MotionEvent event) {
    }

    private void sendVictoryMail()
    {

    }
}