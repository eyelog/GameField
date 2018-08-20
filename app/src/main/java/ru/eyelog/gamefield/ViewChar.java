package ru.eyelog.gamefield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class ViewChar extends View {

    private Movie mStay, mRunLeft, mRunRight;
    private long mMoviestart;

    float screenX, screenY, centerX, centerY, textSize;

    int textColor = Color.GRAY;

    boolean showSignal = true;
    boolean screenGet = true;
    int anim_way = 0; // 0 - stay, 1 - run left, 2 - run right

    float x_last=0, y_last=0;
    float x, y;

    boolean animFinish = false;

    TranslateAnimation translateAnimation;

    float[] startCoordinates = new float[2];



    public ViewChar(Context context) {
        super(context);
        init();
    }

    public ViewChar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewChar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){

        if(screenX>screenY){ // Landscape

            x_last = centerX;
            y_last = centerY;

        }else{ // Portrait

            x_last = centerX;
            y_last = centerY*3/2;

        }


        mStay = Movie.decodeStream(getResources().openRawResource(R.raw.anim_stay));
        mRunLeft = Movie.decodeStream(getResources().openRawResource(R.raw.anim_left));
        mRunRight = Movie.decodeStream(getResources().openRawResource(R.raw.anim_right));
    }

    public void drawUpdater(final float x, final float y, boolean showSignal){

        this.showSignal = showSignal;
        this.x = x;
        this.y = y;

        invalidate();

        if(x<x_last){ // Run left
            anim_way = 1;
        }else {
            anim_way = 2;
        }

        translateAnimation = new TranslateAnimation(0, x-x_last, 0, y-y_last);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(1000);
        startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                Log.e("animFinish", String.valueOf(animFinish));


                if(!animFinish){

                    /*


                    translateAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), transformation);
                    transformation.getMatrix().getValues(matrix);
                    */
                    Transformation transformation = new Transformation();
                    float[] matrix = new float[9];

                    translateAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), transformation);

                    transformation.getMatrix().getValues(matrix);
                    float y = matrix[Matrix.MTRANS_Y];

                    Log.e("Matrix", "y = " + y);

                    //x_last = matrix[Matrix.MTRANS_X];
                    //y_last = matrix[Matrix.MTRANS_Y];

                    //clearAnimation();
                    //invalidate();
                }

                animFinish = false;

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                anim_way = 0;

                Log.e("onAnimationEnd", "got");
                animFinish = true;
                Log.e("animFinish", String.valueOf(animFinish));

                x_last = x;
                y_last = y;
                clearAnimation();
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        invalidate();

    }

    public float[] getStartCoordinates(){

        startCoordinates[0] = x_last;
        startCoordinates[1] = y_last;


        return startCoordinates;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(screenGet){
            screenX = getWidth();
            screenY = getHeight();
            centerX = screenX/2;
            centerY = screenY/2;



            screenGet = false;
            init();
            invalidate();
        }

        if(showSignal){

            switch (anim_way){

                case 0: // Stay

                    final long now00 = SystemClock.uptimeMillis();
                    if (mMoviestart == 0) {
                        mMoviestart = now00;
                    }

                    final int relTime00 = (int) ((now00 - mMoviestart) % mStay.duration());
                    mStay.setTime(relTime00);
                    mStay.draw(canvas, x_last, y_last);

                    break;

                case 1: // Run left

                    final long now01 = SystemClock.uptimeMillis();
                    if (mMoviestart == 0) {
                        mMoviestart = now01;
                    }

                    final int relTime01 = (int) ((now01 - mMoviestart) % mRunLeft.duration());
                    mRunLeft.setTime(relTime01);
                    mRunLeft.draw(canvas, x_last, y_last);

                    break;

                case 2: // Run Right

                    final long now02 = SystemClock.uptimeMillis();
                    if (mMoviestart == 0) {
                        mMoviestart = now02;
                    }

                    final int relTime02 = (int) ((now02 - mMoviestart) % mRunRight.duration());
                    mRunRight.setTime(relTime02);
                    mRunRight.draw(canvas, x_last, y_last);

                    break;

            }
            this.invalidate();
        }
    }
}