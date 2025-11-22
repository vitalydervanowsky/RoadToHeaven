package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.MapObjects.MapObject;

//Объекты данного класса - это протяженные задания на карте, которые начал делать герой.
//Например, подвезти пилигрима, отвезти посылку
public class Task {
    MapObject fromObject; //Объект, в котором получено задание
    public MyMap.MapCell targetCell; //Целевая ячейка, куда нужно приехать
    public String targetType ="";     //Целевой тип ячейки. Для заданий, в которых не важно, в какой, например, магазин нужно приехать
    public int targetValue1 =0;
    public int targetValue2 =0;
    public boolean isStarted =false;    //Заущено
    public boolean isFinished =false;   //Завершено
    public String text="";           //Текст задания
    public int bonus=100;            //Сумма вознаграждения (монет) за выполнение задания

    public int messageIconMap=0;
    public int messageIconSource=0;
    public String  messageText="";

    public Task(MapObject Object){
        fromObject =Object;
    }
    public void startTask() {
        isStarted = true;
        isFinished = false;
        //fromObject.mapActivity.myTasks.add(this);
        if (!messageText.isEmpty()){
            DialogMessage.showMessage(messageIconMap,messageIconSource,messageText,Messages.getMessageGoodLuck(), fromObject.mapActivity);
        }
    }

    public void finishTask(){
        isFinished = true;
        fromObject.finishTask();
        //Завершение задания реализуется в Объекте - источнике задания. Там же
    }

    public static Task checkTasks(MapActivity mapActivity, MyMap.MapCell Cell){
        for (int i = 1; i<= mapActivity.myTasks.size(); i++){
            Task Task = mapActivity.myTasks.get(i-1);
            //Проверяем задания, которые направлены на любой объект какого-то типа (в них заполнен тип (targetType), но не заполнена ячейка (targetCell))
            if (Task.targetType.equals(Cell.type)&& !Task.targetType.equals("")) {
                Task.finishTask();
                return Task;
            }

            //Проверяем задания, которые направлены на конкретный объект какого-то типа (в них НЕ заполнен тип (targetType), но заполнена ячейка (targetCell))
            if (Task.targetCell !=null && !Task.isFinished && ((Task.targetCell.x ==Cell.x && Task.targetCell.y ==Cell.y) || (Task.targetCell.object ==Cell.object && Cell.object!=null))) {
                Task.finishTask();
                return Task;
            }
        }
        return checkTasks(mapActivity);
    }

    public static Task checkTasks(MapActivity mapActivity){
        for (int i = 1; i<= mapActivity.myTasks.size(); i++){
            Task Task = mapActivity.myTasks.get(i-1);

            //проверяем задания по сбору ресурсов
            if (Task.targetType.equals("count_fuel") && !Task.isFinished && Task.isStarted && Constants.DATAGAME.getFuel()>=Task.targetValue1) {
                Task.finishTask();
                return Task;
            }
            if (Task.targetType.equals("count_ruby") && !Task.isFinished && Task.isStarted && Constants.DATAGAME.getRubies()>=Task.targetValue1) {
                Task.finishTask();
                return Task;
            }
            if (Task.targetType.equals("count_ruby") && !Task.isFinished && Task.isStarted && Constants.DATAGAME.getRubies()>=Task.targetValue1) {
                Task.finishTask();
                return Task;
            }
            if (Task.targetType.equals("count_stones") && !Task.isFinished && Task.isStarted && Constants.DATAGAME.getStones()>=Task.targetValue1) {
                Task.finishTask();
                return Task;
            }
        }
        return null;
    }

}
