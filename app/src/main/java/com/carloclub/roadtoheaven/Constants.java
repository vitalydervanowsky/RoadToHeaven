package com.carloclub.roadtoheaven;

import static androidx.core.content.res.ResourcesCompat.getDrawable;

import com.carloclub.roadtoheaven.Maps.Sokulka;

public class Constants {

    public final static String LANG_BY = "BY";
    public final static String LANG_RU = "RU";
    public final static int TANK_SIZE = 4000;
    public final static int SCALE = 60;
    public final static DataGame DATAGAME= new DataGame();
    public final static MyMap sokulka = new MyMap(30, 21, R.drawable.mapsokulka);

    public final static MyMap getMap(String CityName){
        if (CityName.equals("Sokulka")) {
            Sokulka.LoadMap(sokulka);
            return sokulka;
        }
        return null;
    }
}
