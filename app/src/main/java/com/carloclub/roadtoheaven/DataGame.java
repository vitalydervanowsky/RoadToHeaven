package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.model.Language;

public class DataGame {
    private int speed = 60;
    private int tank=40;
    private int tire = 100;
    private int currentX = 0;
    private int currentY = 0;
    private int fuel =15000;
    private int money =3000;
    private int stones=0;
    private int rubies=0;
    private int burgers=0;

    private MapActivity mapActivity;
    public MyMap map;
    public String currentMap="";

    public int SCALE = 100;
    public Language currentLang = Language.BY;

    public DataGame(){
        loadData("AutoSave");
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getBurgers() {
        return burgers;
    }

    public void setBurgers(int burgers) {
        this.burgers = burgers;
        updateActivityFields();
    }

    public int getRubies() {
        return rubies;
    }

    public void setRubies(int rubies) {
        this.rubies = rubies;
        updateActivityFields();
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
        updateActivityFields();
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        updateActivityFields();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStones() {
        return stones;
    }

    public void setStones(int stones) {
        this.stones = stones;
        updateActivityFields();
    }

    public int getTank() {
        return tank;
    }

    public void setTank(int tank) {
        this.tank = tank;
    }

    public int getTire() {
        return tire;
    }

    public void setTire(int tire) {
        this.tire = tire;
    }

    public void loadData(String recordName){
        //...Загружаем сохраненные данные из базы

    }

    public void saveData(String recordName){

    }

    public void setActivity(MapActivity mapActivity){
        this.mapActivity = mapActivity;
        map = mapActivity.map;
        updateActivityFields();
    }

    public void updateActivityFields(){
        if (mapActivity==null) return;

        mapActivity.updateBar();

        //Task.checkTasks(mapActivity);
    }
}
