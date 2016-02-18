package com.example.andrew.androidpong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by Andrew on 2/9/2016.
 */
public class GameState {

    //screen width and height
    final int _screenWidth = 1000;
    final int _screenHeight = 1600;

    //The ball
    final int _ballSize = 30;
    float _ballX = 100; 	float _ballY = 100;
    float _ballVelocityX = 6; 	float _ballVelocityY = 6;

    //The bats
    final int _batLength = 300;	final int _batHeight = 10;
    int _topBatX = (_screenWidth/2) - (_batLength / 2);
    final int _topBatY = 20;
    int _bottomBatX = (_screenWidth/2) - (_batLength / 2);
    final int _bottomBatY = _screenHeight - 20;
    final int _batSpeed = 3;

    boolean gameBeenOne = false;

    public GameState()
    {
    }

    //The update method
    public void update() {

        _ballX += _ballVelocityX;
        _ballY += _ballVelocityY;

//DEATH!

        if(_ballY < 0)
        {
            _ballX = _screenWidth/2;
            _ballY = _screenHeight/2;
            _ballVelocityX = 4;
            _ballVelocityY = 7;

            //Bottom Wins!
            gameBeenOne = true;
        }
        else if(_ballY > _screenHeight)
        {
            _ballX = _screenWidth/2;
            _ballY = _screenHeight/2;
            _ballVelocityX = -4;
            _ballVelocityY = -7;

            //Top Wins!
            gameBeenOne = true;
        }

        //Collisions with the sides
        if(_ballX > _screenWidth || _ballX < 0)
            _ballVelocityX *= -1.01;

        //Collisions with the bats
        if(_ballX  > _topBatX && _ballX < _topBatX+_batLength && _ballY - _ballSize < _topBatY  + _batHeight)
        {
            xDirectionBounce(_ballX - findCenter(_topBatX, _batLength));
            _ballVelocityY *= -1.01;
        }


        //Collisions with the bats
        if(_ballX > _bottomBatX && _ballX < _bottomBatX+_batLength
                && _ballY + _ballSize/2 > _bottomBatY - _batHeight)
        {
            xDirectionBounce(_ballX - findCenter(_topBatX, _batLength));
            _ballVelocityY *= -1.01;
        }

    }

    private int findCenter(int xCoordinate, int length)
    {
        return (xCoordinate + length)/2;
    }

    private void xDirectionBounce(float bounceAngle) {
        if (bounceAngle > 0) {
            _ballVelocityX *= -1.01;
        }
        else
            _ballVelocityX *= 1.01;
    }

    public boolean keyPressed(int keyCode, KeyEvent msg)
    {
        if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) //left
        {
            _topBatX += _batSpeed; _bottomBatX -= _batSpeed;
        }

        if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) //right
        {
            _topBatX -= _batSpeed; _bottomBatX += _batSpeed;
        }

        return true;
    }

    /*
    * Implemented in GameState.java, needs an import of android.view.MotionEvent
    *
    * If screen is touched, make bottom paddle center paddle on X coordinate
    */
    public boolean motionDetected(MotionEvent event) {
        float x = event.getX();
        _bottomBatX = (int)x -(_batLength/2);
        _topBatX = (int)x -(_batLength/2);
        return true;
    }

    //the draw method
    public void draw(Canvas canvas, Paint paint) {

        if(canvas != null && paint != null)
        {
//Clear the screen
            canvas.drawRGB(20, 20, 20);

//set the colour
            paint.setARGB(200, 0, 200, 0);

//draw the ball
            canvas.drawCircle(_ballX, _ballY, _ballSize, paint);
        /*canvas.drawRect(new Rect(_ballX,_ballY,_ballX + _ballSize,_ballY + _ballSize),
                paint);*/

//draw the bats
            canvas.drawRect(new Rect(_topBatX, _topBatY, _topBatX + _batLength,
                    _topBatY + _batHeight), paint); //top bat
            canvas.drawRect(new Rect(_bottomBatX, _bottomBatY, _bottomBatX + _batLength,
                    _bottomBatY + _batHeight), paint); //bottom bat
        }
    }

    public boolean isGameWon()
    {
        return gameBeenOne;
    }


}

