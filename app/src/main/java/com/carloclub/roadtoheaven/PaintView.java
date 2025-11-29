package com.carloclub.roadtoheaven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class PaintView extends View {

    Paint p;
    Paint paintGRAY;
    Rect rect;

    int BackColor = Color.rgb(245,245,245);
    int MarginColor = Color.rgb(253, 253, 250);

    public int [][] Cells;
    public int pixels1Cell=38;
    public int FieldWidth=12;
    public int FieldHeight=18;
    Canvas mcanvas;
    public Figure ActualFigure;
    public Figure NextFigure;


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        rect = new Rect();
        paintGRAY = new Paint();
        paintGRAY.setARGB(100,245,245,245); // Цвет квадрата
        paintGRAY.setStrokeWidth(3); // Толщина линии
        paintGRAY.setStyle(Paint.Style.STROKE); // Режим рисования (обводка)

        Cells = new int[FieldHeight][FieldWidth];
        ActualFigure = new Figure();
        NextFigure = new Figure();

        //сразу инициируем нижние ряды

        Random random = new Random();
        for (int i=FieldHeight-4;i<FieldHeight;i++){
            int empt= random.nextInt(FieldWidth);
            for (int j=0;j<FieldWidth;j++){
                if (j!=empt) Cells [i][j]=Figure.colors[1];


            }
        }

    }

    public void ShowFigure(Canvas canvas, Figure f, int OffsetX) {
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                if (f.Cells[i][j]==true) {
                    p.setColor(f.color);
                    canvas.drawRect(3 + (pixels1Cell+8) * (j+f.X+OffsetX), 3 + pixels1Cell * (i+f.Y), (pixels1Cell+8) * (j+f.X+OffsetX + 1)-4, pixels1Cell * (i+f.Y + 1)-4, p);
                }
//                else{
//                    p.setColor(BackColor);
//                    canvas.drawRect(3 + pixels1Cell * (j+f.X+OffsetX), 3 + pixels1Cell * (i+f.Y), pixels1Cell * (j+f.X+OffsetX + 1)-4, pixels1Cell * (i+f.Y + 1)-4, p);
//                }
            }
        }
    }

    public void cleanALL(){
          for (int i=0;i<FieldHeight;i++){
            int j=0;
            for (j=0;j<FieldWidth;j++){
                Cells[i][j]=0;
            }
        }
    }

    public void FixFigure(Figure f){
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                if (f.Cells[i][j]==true){
                    Cells[i+f.Y][j+f.X] = f.color;
                }
            }
        }
    }

    public boolean ShowPossible(Figure f){
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                if (f.Cells[i][j]==true && (j+f.X<0||j+f.X>=FieldWidth||i+f.Y<0||i+f.Y>=FieldHeight)) {
                    return false;
                }
                if (j+f.X<0||j+f.X>=FieldWidth||i+f.Y<0||i+f.Y>=FieldHeight) continue;
                if (f.Cells[i][j]==true && Cells[i+f.Y][j+f.X]!=0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int AnalizFullLines(){
        int Lines =0;
        for (int i=FieldHeight-1;i>=0;i--){
            boolean AllTrue = true;
            for (int j=0; j<FieldWidth;j++){
                if (Cells[i][j]==0) {AllTrue=false; break;}
            }
            if (AllTrue==true){
                Lines++;
                //Сдвигаем все строки кроме первой выше вниз
                for (int ii=i; ii>0;ii--){
                    for (int j=0; j<FieldWidth;j++){
                        Cells[ii][j]=Cells[ii-1][j];
                    }
                }
                // а первую заполняем нулями
                for (int j=0; j<FieldWidth;j++){
                    Cells[0][j]=0;
                }
                i++;//пусть ещё раз проверит эту строку
            }
        }

        switch(Lines) {
            case 0:
                return 0;
            case 1:
                return 100;
            case 2:
                return 300;
            case 3:
                return 600;
            case 4:
                return 1000;
        }
        return 0;
    }

    public int OneColorLines(){
        int Lines =0;
        for (int i=FieldHeight-1;i>=0;i--){
            boolean AllTrue = true;
            if (Cells[i][0]==0) continue;

            for (int j=1; j<FieldWidth;j++){
                if (Cells[i][j]!=Cells[i][0]) {AllTrue=false; break;}
            }
            if (AllTrue==true)  Lines++;
        }

        return Lines;
    }

    public void CleanFigure(Figure f) {
        p.setColor(BackColor);
        for (int i=0;i<4;i++){
            int j=0;
            for (j=0;j<4;j++){
                if (f.Cells[i][j]==true) {
                    mcanvas.drawRect(3 + (pixels1Cell+8) * (j+f.X), 3 + pixels1Cell * (i+f.Y), (pixels1Cell+8) * (j+f.X + 1)-4, pixels1Cell * (i+f.Y + 1)-4, p);
                }
            }
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

            //canvas.drawARGB(100, 253, 253, 250);
            mcanvas = canvas;


//            p.setColor(Color.GRAY);
//            // толщина линии = 10
//            p.setStrokeWidth(1);
//            canvas.drawRect(1, 1, pixels1Cell*FieldWidth+5, pixels1Cell*FieldHeight+7, p);

//            p.setColor(BackColor);
//            // толщина линии = 10
//            canvas.drawRect(3, 3, pixels1Cell*FieldWidth+3, pixels1Cell*FieldHeight+3, p);

            p.setColor(Color.GRAY);


            for (int i=0;i<FieldHeight;i++){
                int j=0;
                for (j=0;j<FieldWidth;j++){
                    if (Cells [i][j]!=0) {
                        p.setColor(Cells [i][j]);
                        canvas.drawRect(2 + (pixels1Cell+8) * j, 2 + pixels1Cell * i, (pixels1Cell+8) * (j + 1)-3, pixels1Cell * (i + 1)-3, p);

                    }
                    else {
                        canvas.drawRect((pixels1Cell+8) * j, pixels1Cell * i, (pixels1Cell+8) * (j + 1), pixels1Cell * (i + 1), paintGRAY);
                    }
                }
            }


        if (ActualFigure!=null) ShowFigure(canvas, ActualFigure,0);
        //if (ActualFigure!=null) ShowFigure(canvas, NextFigure,7);
    }
}
