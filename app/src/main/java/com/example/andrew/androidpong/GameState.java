package com.example.andrew.androidpong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Andrew on 2/9/2016.
 */
public class GameState {

    //screen width and height
    int screenWidth = 1000;
    int screenHeight = 1600;

    //The ball
    int ballSize;
    float ballX;
    float ballY;
    float ballVelocityX;
    float ballVelocityY;

    //The bats
    int batLength;
    int batHeight;
    int topBatX;
    int topBatY;
    int bottomBatX;

    int bottomBatY;
    int batSpeed;

    boolean gameBeenOne = false;

    public GameState(int screenHeight, int screenWidth)
    {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        //The ball
        ballSize = 30;
        ballX = 100;
        ballY = 100;
        ballVelocityX = 6;
        ballVelocityY = 6;

        //The bats
        batLength = 300;
        batHeight = 10;
        topBatX = (this.screenWidth /2) - (batLength / 2);
        topBatY = 20;
        bottomBatX = (this.screenWidth /2) - (batLength / 2);
        bottomBatY = this.screenHeight - 80;
        batSpeed = 3;
    }

    //The update method
    public void update() {

        ballX += ballVelocityX;
        ballY += ballVelocityY;

//DEATH!

        if(ballY < 0)
        {
            ballX = screenWidth /2;
            ballY = screenHeight /2;
            ballVelocityX = 4;
            ballVelocityY = 7;

            //Bottom Wins!
            gameBeenOne = true;
        }
        else if(ballY > screenHeight)
        {
            ballX = screenWidth /2;
            ballY = screenHeight /2;
            ballVelocityX = -4;
            ballVelocityY = -7;

            //Top Wins!
            gameBeenOne = true;
        }

        //Collisions with the sides
        if(ballX > screenWidth || ballX < 0)
            ballVelocityX *= -1.01;

        //Collisions with the bats
        if(ballX  > topBatX && ballX < topBatX+batLength && ballY - ballSize < topBatY  + batHeight)
        {
            //xDirectionBounceTop(_ballX - findCenter(_topBatX, _batLength));
            ballVelocityY *= -1.01;
        }


        //Collisions with the bats
        if(ballX > bottomBatX && ballX < bottomBatX + batLength
                && ballY + ballSize/2 > bottomBatY - batHeight)
        {
            //xDirectionBounceBottom(_ballX - findCenter(_topBatX, _batLength));
            ballVelocityY *= -1.01;
        }

    }

    private int findCenter(int xCoordinate, int length)
    {
        return (xCoordinate + length)/2;
    }

    private void xDirectionBounceTop(float bounceAngle) {
        if (bounceAngle > 0) {
            ballVelocityX *= -1.01;
        }
        else
            ballVelocityX *= 1.01;
    }

    private void xDirectionBounceBottom(float bounceAngle) {
        if (bounceAngle > 0) {
            ballVelocityX *= 1.01;
        }
        else
            ballVelocityX *= -1.01;
    }

    /*
    * Implemented in GameState.java, needs an import of android.view.MotionEvent
    *
    * If screen is touched, make bottom paddle center paddle on X coordinate
    */
    public boolean motionDetected(MotionEvent event) {
        float x = event.getX();
        bottomBatX = (int)x -(batLength/2);
        topBatX = (int)x -(batLength/2);
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
            canvas.drawCircle(ballX, ballY, ballSize, paint);

//draw the bats
            canvas.drawRect(new Rect(topBatX, topBatY, topBatX + batLength,
                    topBatY + batHeight), paint); //top bat
            canvas.drawRect(new Rect(bottomBatX, bottomBatY, bottomBatX + batLength,
                    bottomBatY + batHeight), paint); //bottom bat
        }
    }

    public boolean isGameWon()
    {
        return gameBeenOne;
    }


}

