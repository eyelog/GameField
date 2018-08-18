package ru.eyelog.gamefield;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ViewField viewField;
    ViewChar viewChar;

    float x, y;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.id_textView);

        viewField = findViewById(R.id.id_view_game_field);
        viewField.drawUpdater(0, true);

        viewChar = findViewById(R.id.id_view_game_char);

        viewField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_MOVE){
                    x = motionEvent.getX();
                    //y = motionEvent.getY();

                    viewField.drawUpdater(x,true);
                    viewField.invalidate();
                }else if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    x = motionEvent.getX();
                    y = motionEvent.getY();
                    viewChar.drawUpdater(x, y, true);
                    viewChar.invalidate();

                    //Log.e("Motion", "x: " + x + ", y: " + y);
                }

                return true;
            }
        });


    }

    public void onRefresh(View view){
        viewField.start_00=0;
        viewField.stop_00=90;
        viewField.start_01=45;
        viewField.stop_01=135;
        viewField.invalidate();
    }

}
