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
    int _screenWidth = 1000;
    int _screenHeight = 1600;

    //The ball
    int _ballSize;
    float _ballX;
    float _ballY;
    float _ballVelocityX;
    float _ballVelocityY;

    //The bats
    int _batLength;
    int _batHeight;
    int _topBatX;
    int _topBatY;
    int _bottomBatX;

    int _bottomBatY;
    int _batSpeed;

    boolean gameBeenOne = false;

    public GameState(int screenHeight, int screenWidth)
    {
        _screenHeight = screenHeight;
        _screenWidth = screenWidth;

        //The ball
        _ballSize = 30;
        _ballX = 100;
        _ballY = 100;
        _ballVelocityX = 6;
        _ballVelocityY = 6;

        //The bats
        _batLength = 300;
        _batHeight = 10;
        _topBatX = (_screenWidth/2) - (_batLength / 2);
        _topBatY = 20;
        _bottomBatX = (_screenWidth/2) - (_batLength / 2);
        _bottomBatY = _screenHeight - 80;
        _batSpeed = 3;
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
            //xDirectionBounceTop(_ballX - findCenter(_topBatX, _batLength));
            _ballVelocityY *= -1.01;
        }


        //Collisions with the bats
        if(_ballX > _bottomBatX && _ballX < _bottomBatX+_batLength
                && _ballY + _ballSize/2 > _bottomBatY - _batHeight)
        {
            //xDirectionBounceBottom(_ballX - findCenter(_topBatX, _batLength));
            _ballVelocityY *= -1.01;
        }

    }

    private int findCenter(int xCoordinate, int length)
    {
        return (xCoordinate + length)/2;
    }

    private void xDirectionBounceTop(float bounceAngle) {
        if (bounceAngle > 0) {
            _ballVelocityX *= -1.01;
        }
        else
            _ballVelocityX *= 1.01;
    }

    private void xDirectionBounceBottom(float bounceAngle) {
        if (bounceAngle > 0) {
            _ballVelocityX *= 1.01;
        }
        else
            _ballVelocityX *= -1.01;
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

