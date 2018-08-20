package ru.eyelog.gamefield;

public class BorderCounter {

    float[] borderCoordinates = new float[2]; // координаты точки на границе пути x, y

    int wayQuarter; // 0/1/2/3

    float[] motionWay = new float[2]; // показывет направление движения.


    float[] forWays = new float[4];

    float x1, x2, x3, x4, y1, y2, y3, y4;
    float x, y;

    BorderCounter(){

    }

    public float[] reachWayCoordinates(float[][] fieldCoordinates, float[][] wayCoordinates){

        /*
        for (int i=0; i<4; i++){
            Log.e("fieldCoordinates", "fieldCoordinates x" + i + " = " + fieldCoordinates[i][0] +
                    "; fieldCoordinates y" + i + " = " + fieldCoordinates[i][1]);
        }

        for (int i=0; i<2; i++){
            Log.e("wayCoordinates", "wayCoordinates x" + i + " = " + wayCoordinates[i][0] +
                    "; wayCoordinates y" + i + " = " + wayCoordinates[i][1]);
        }
        */

        // Далее определяем координаты границы которую мы хотим достич
        // Сначала находим четыре расстояния о вершин платформы до цели пути.
        for(int i=0; i<4; i++){
            forWays[i] = (float) Math.sqrt(Math.pow((fieldCoordinates[i][0]-wayCoordinates[1][0]),2) +
                    Math.pow((fieldCoordinates[i][1]-wayCoordinates[1][1]),2));
            //Log.e("forWays", "forWays " + i + " = " + forWays[i]);
        }

        // Далее выбираем два наименьших расстояния
        int a, b;
        a=0;
        for(int i=0; i<4; i++){
            if(forWays[a]>=forWays[i]){
                a=i;
            }
        }

        b=0;
        for (int i=0; i<4; i++){
            if(forWays[b]>=forWays[i]&&forWays[a]!=forWays[i]){
                b=i;
            }
        }

        //Log.e("indexes", "a = " + a + ", b = " + b);

        //Log.e("Border 00", "fieldCoordinates[a][0] = " + fieldCoordinates[a][0] + ", fieldCoordinates[a][1] = " + fieldCoordinates[a][1]);
        //Log.e("Border 01", "fieldCoordinates[b][0] = " + fieldCoordinates[b][0] + ", fieldCoordinates[b][1] = " + fieldCoordinates[b][1]);

        // координаты ближайших вершин к мнимому концу пути:
        x1 = fieldCoordinates[a][0];
        y1 = fieldCoordinates[a][1];
        x2 = fieldCoordinates[b][0];
        y2 = fieldCoordinates[b][1];

        // координаты пути:
        x3 = wayCoordinates[0][0];
        y3 = wayCoordinates[0][1];
        x4 = wayCoordinates[1][0];
        y4 = wayCoordinates[1][1];

        // Теперь находим точку пересечения диагоналей получившегося четырёхугольника.

        //Log.e("c1", String.valueOf(c1));
        //Log.e("c2", String.valueOf(c2));
        //x:=((x1*y2-x2*y1)*(x4-x3)-(x3*y4-x4*y3)*(x2-x1))/((y1-y2)*(x4-x3)-(y3-y4)*(x2-x1));
        //y:=((y3-y4)*x-(x3*y4-x4*y3))/(x4-x3);

        float	dx1 = x2 - x1;
        float	dy1 = y2 - y1;
        float	dx2 = x4 - x3;
        float	dy2 = y4 - y3;
        x = dy1 * dx2 - dy2 * dx1;
        y = x3 * y4 - y3 * x4;
        x = ((x1 * y2 - y1 * x2) * dx2 - y * dx1) / x;
        y = (dy2 * x - y) / dx2;

        borderCoordinates[0] = wayCoordinates[1][0];
        borderCoordinates[1] = wayCoordinates[1][1];

        return borderCoordinates;
    }

}
