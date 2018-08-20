package ru.eyelog.gamefield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ViewField extends View {

    Paint paint, paintLine;
    Path path;

    float screenX, screenY, centerX, centerY, textSize;

    int textColor = Color.GRAY;

    boolean showSignal = false;
    boolean screenGet = true;
    boolean portraitMode;

    // Матрица координат
    float[] x_matrix = new float[180];
    float[] y_matrix = new float[180];

    float last = 0;
    float radius;

    // План позиций
    int start_00=0, stop_00=90;
    int start_01=45, stop_01=135;

    // Пакет координат для расчета ограничения передвижения.
    float[][] boardCoordinates = new float[4][2];

    public ViewField(Context context) {
        super(context);
        init();
    }

    public ViewField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){

        if(screenX>screenY){ // Landscape

            portraitMode=false;
            textSize = screenX/24;

            radius = centerX*3/4;

        }else{ // Portrait

            portraitMode=true;
            textSize = screenY/16;

            radius = centerX;

        }

        // Заполняем матрицу координат.
        int i = 0;
        float pi = 0.0f;
        double step = Math.PI/90;

        if(portraitMode){ // Portrait

            do{
                x_matrix[i] = (float) Math.sin(pi)*radius+centerX;
                if(i>45&&i<135){
                    y_matrix[i] = (float) Math.cos(pi)*radius/4+centerY*3/2;
                }else{
                    y_matrix[i] = (float) Math.cos(pi)*radius/2+centerY*3/2;
                }
                i++;
                pi += step;
            }while (i<180);

        }else { // Landscape

            do{
                x_matrix[i] = (float) Math.sin(pi)*radius+centerX;
                if(i>45&&i<135){
                    y_matrix[i] = (float) Math.cos(pi)*radius/4+centerY;
                }else{
                    y_matrix[i] = (float) Math.cos(pi)*radius/2+centerY;
                }
                i++;
                pi += step;
            }while (i<180);

        }

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setColor(textColor);

        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setColor(textColor);
        paintLine.setStrokeWidth(3);

    }

    public void drawUpdater(float x, boolean showSignal){

        this.showSignal = showSignal;

        if(last<x){
            start_00 -= 2;
            start_01 -= 2;
        }else {
            start_00 += 2;
            start_01 += 2;
        }

        if(start_00<0){
            start_00=179;
            stop_00=89;
        }else if(start_00>179){
            start_00=0;
            stop_00=90;
        }

        if(start_01<0){
            start_01=179;
            stop_01=89;
        }else if(start_01>179){
            start_01=0;
            stop_01=90;
        }

        if(start_00>89){
            stop_00 = start_00-90;
        }else {
            stop_00 = start_00+90;
        }

        if(start_01>89){
            stop_01 = start_01-90;
        }else {
            stop_01 = start_01+90;
        }

        last = x;
    }

    public float[][] getBorderCoordinates(){

        // собираем пакет для расчета границ
        boardCoordinates[0][0] = x_matrix[start_00];
        boardCoordinates[0][1] = y_matrix[start_00];

        boardCoordinates[1][0] = x_matrix[start_01];
        boardCoordinates[1][1] = y_matrix[start_01];

        boardCoordinates[2][0] = x_matrix[stop_00];
        boardCoordinates[2][1] = y_matrix[stop_00];

        boardCoordinates[3][0] = x_matrix[stop_01];
        boardCoordinates[3][1] = y_matrix[stop_01];

        return boardCoordinates;
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
            path = new Path();
            path.moveTo(x_matrix[start_00], y_matrix[start_00]);
            path.lineTo(x_matrix[start_01], y_matrix[start_01]);
            path.lineTo(x_matrix[stop_00], y_matrix[stop_00]);
            path.lineTo(x_matrix[stop_01], y_matrix[stop_01]);
            path.lineTo(x_matrix[start_00], y_matrix[start_00]);
            canvas.drawPath(path, paintLine);
        }
    }
}
