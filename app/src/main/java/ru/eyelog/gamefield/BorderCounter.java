package ru.eyelog.gamefield;

public class BorderCounter {

    float[] borderCoordinates = new float[2]; // координаты точки на границе пути x, y

    int wayQuarter; // 0/1/2/3

    float[] motionWay = new float[2]; // показывет направление движения.

    float[][] currentBorder = new float[2][2];

    float[] xForChoose = new float[4];

    BorderCounter(){

    }

    public float[] reachWayCoordinates(float[][] fieldCoordinates, float[][] wayCoordinates){

        // Сначала нужно определить, а мы вообще выходим за границы.
        // Если какой либо "х" цели не

        // fieldCoordinates 4-х сторонняя платформа.
        // координаты её вершин: {{x,y},{x,y},{x,y},{x,y}}

        // wayCoordinates координаты мнимого пути
        // начало и конец {{x,y}, {x,y}}


        // Сначала определим в какую четверть собрался персонаж
        /**
         *  / 0 | 1 \
         * -----------
         *  \ 3 | 2 /
         */

        // И не забываем, что начало оси коодинат в верхнем левом углу.

        motionWay[0] = wayCoordinates[0][0] - wayCoordinates[1][0];
        motionWay[1] = wayCoordinates[0][1] - wayCoordinates[1][1];

        if(motionWay[0]<=0&&motionWay[1]<=0){
            // Идем в 0-ю черверть.
            wayQuarter = 0;

        }else if(motionWay[0]>0&&motionWay[1]<=0){
            // Идем в 1-ю черверть.
            wayQuarter = 1;

        }else if(motionWay[0]>0&&motionWay[1]>=0){
            // Идем в 2-ю черверть.
            wayQuarter = 2;

        }else if(motionWay[0]<=0&&motionWay[1]>0){
            // Идем в 3-ю черверть.
            wayQuarter = 3;

        }


        // Далее определяем координаты границы которую мы хотим достич
        // Сначала исчем самый дальний х - и прилегающие к нему границы - точно не те.
        for(int i=0; i<4; i++){

        }


        return borderCoordinates;
    }

}
