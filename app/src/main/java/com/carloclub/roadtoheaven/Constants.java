package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.model.Language;

public class Constants {

    public final static int TANK_SIZE = 4000;
    public final static int SCALE = 60;
    public static boolean UNLOCK = false;
    public final static DataGame DATAGAME= new DataGame();
    public final static String CITY_ARG = "CITY_ARG";

    public static boolean isBy() {
        return DATAGAME.currentLang == Language.BY;
    }

    public static boolean isRu() {
        return DATAGAME.currentLang == Language.RU;
    }

}
