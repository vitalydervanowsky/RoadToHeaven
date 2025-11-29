package com.carloclub.roadtoheaven;

import android.graphics.Color;

import java.util.Random;

public class Figure {
    public int color;
    public boolean [][] Cells;

    public int X;
    public int Y;

    public static int colors[] = {Color.GRAY, Color.rgb(163,32,22), Color.rgb(150,100,100), Color.rgb(0,130,0)};

    public Figure(){
        Cells = new boolean [4][4];
        GenerateColor();
        GenerateFigure();
        Clean();
        X=5;
        Y=0;
    }

    public void Clean(){
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                Cells [i][j]=false;
            }
        }
    }
    public void GenerateColor(){
        Random random = new Random();
        color = colors[random.nextInt(2)];
        color = colors[1];
    }
    public void GenerateFigure(){
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                Cells [i][j]=false;
            }
        }
        Random random = new Random();
        int num = random.nextInt(8);
        if (num==0||num==8){
            //   **
            //   **
            Cells [1][1]=true;
            Cells [1][2]=true;
            Cells [2][1]=true;
            Cells [2][2]=true;
        }else if(num==1){
            //   **
            //   *
            //   *
            Cells [0][1]=true;
            Cells [0][2]=true;
            Cells [1][1]=true;
            Cells [2][1]=true;
        }else if(num==2){
            //   ***
            //    *
            Cells [1][0]=true;
            Cells [1][1]=true;
            Cells [1][2]=true;
            Cells [2][1]=true;
        }else if(num==3){
            //    *
            //   **
            //   *
            Cells [0][2]=true;
            Cells [1][1]=true;
            Cells [1][2]=true;
            Cells [2][1]=true;
        }else if(num==4){
            //    *
            Cells [1][1]=true;
        }else if(num==5){
            //    *
            //    *
            //    *
            //    *
            Cells [0][1]=true;
            Cells [1][1]=true;
            Cells [2][1]=true;
            Cells [3][1]=true;
        }else if(num==6){
            //  **
            //   *
            //   *
            Cells [0][1]=true;
            Cells [0][2]=true;
            Cells [1][2]=true;
            Cells [2][2]=true;
        }else if(num==7){
            //   *
            //   **
            //    *
            Cells [0][1]=true;
            Cells [1][1]=true;
            Cells [1][2]=true;
            Cells [2][2]=true;
        }
    }
    public void CopyFrom(Figure inFigure){
        color = inFigure.color;
        X = inFigure.X;
        Y = inFigure.Y;
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                Cells [i][j]=inFigure.Cells [i][j];
            }
        }
    }

    public void Twist(){
        //поворачиваем
        boolean [][] Temt = new boolean [4][4];
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                Temt [i][j]=Cells [3-j][i];
            }
        }
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                Cells [i][j]=Temt [i][j];
            }
        }
    }
}
