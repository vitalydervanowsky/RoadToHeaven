package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.MapObjects.MapObject;

//Объекты данного класса - это протяженные задания на карте, которые начал делать герой.
//Например, подвезти пилигрима, отвезти посылку
public class Tasks {
    MapObject FromObject; //Объект, в котором получено задание
    public MyMap.MapCell TargetCell; //Целевая ячейка, куда нужно приехать
    public String TargetType="";     //Целевой тип ячейки. Для заданий, в которых не важно, в какой, например, магазин нужно приехать
    public boolean Started=false;    //Заущено
    public boolean finished=false;   //Завершено
    public String text="";           //Текст задания
    public int bonus=100;            //Сумма вознаграждения (монет) за выполнение задания

    public Tasks(MapObject Object){
        FromObject=Object;
    }
    public void StartTask(){
        Started = true;
        finished = false;
        FromObject.mapActivity.myTasks.add(this);
    }

    public void FinishTask(){
        FromObject.FinishTask();
        //Завершение задания реализуется в Объекте - источнике задания. Там же
    }

}
