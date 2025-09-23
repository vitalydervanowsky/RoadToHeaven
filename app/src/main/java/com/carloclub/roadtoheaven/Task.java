package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.MapObjects.MapObject;

//Объекты данного класса - это протяженные задания на карте, которые начал делать герой.
//Например, подвезти пилигрима, отвезти посылку
public class Task {
    MapObject fromObject; //Объект, в котором получено задание
    public MyMap.MapCell targetCell; //Целевая ячейка, куда нужно приехать
    public String targetType = "";     //Целевой тип ячейки. Для заданий, в которых не важно, в какой, например, магазин нужно приехать
    public boolean isStarted = false;    //Запущено
    public boolean isFinished = false;   //Завершено
    public String text = "";           //Текст задания
    public int bonus = 100;            //Сумма вознаграждения (монет) за выполнение задания

    public Task(MapObject mapObject) {
        fromObject = mapObject;
    }

    public void startTask() {
        isStarted = true;
        isFinished = false;
        fromObject.mapActivity.myTasks.add(this);
    }

    public void finishTask() {
        fromObject.finishTask();
        //Завершение задания реализуется в Объекте - источнике задания. Там же
    }

}
