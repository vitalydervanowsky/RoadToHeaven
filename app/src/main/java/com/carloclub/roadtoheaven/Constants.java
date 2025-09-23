package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.Maps.Sokolka;

public class Constants {

    public final static String LANG_BY = "BY";
    public final static String LANG_RU = "RU";
    public final static int TANK_SIZE = 4000;
    public final static int SCALE = 60;
    public final static DataGame DATAGAME= new DataGame();

    public static MyMap getMap(String cityName){
        if (cityName.equals("Sokolka")) {
            MyMap sokolka = new MyMap(30, 21, R.drawable.map_sokolka);
            Sokolka.LoadMap(sokolka);
            return sokolka;
        }
        return null;
    }
}
