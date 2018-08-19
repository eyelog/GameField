package ru.eyelog.gamefield;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ViewField viewField;
    ViewChar viewChar;

    float x, y;
    float[] aimCoordinates = new float[2];

    GestureDetector mGestureDetector;
    BorderCounter borderCounter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.id_textView);

        viewField = findViewById(R.id.id_view_game_field);
        viewField.drawUpdater(0, true);

        viewChar = findViewById(R.id.id_view_game_char);

        borderCounter = new BorderCounter();

        mGestureDetector = new GestureDetector(this, new MyGestureDetector());

        viewField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return mGestureDetector.onTouchEvent(motionEvent);
            }
        });

    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapUp(MotionEvent e) {
            x = e.getX();
            y = e.getY();

            aimCoordinates = borderCounter.reachWayCoordinates(viewField.boardCoordinates,
                    new float[][]{{viewChar.x_last, viewChar.y_last}, {x, y}});

            viewChar.drawUpdater(aimCoordinates[0], aimCoordinates[1], true);
            viewChar.invalidate();
            return false;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;

        }

        public void onShowPress(MotionEvent e) {

        }

        public boolean onDown(MotionEvent e) {
            // Must return true to get matching events for this down event.
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, final float distanceX, float distanceY) {
            x = e2.getX();

            viewField.drawUpdater(e2.getX(),true);
            viewField.invalidate();

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // do something

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    public void onRefresh(View view){
        viewField.start_00=0;
        viewField.stop_00=90;
        viewField.start_01=45;
        viewField.stop_01=135;
        viewField.invalidate();
    }

}
