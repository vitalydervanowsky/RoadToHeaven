package com.carloclub.roadtoheaven.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//(autoGenerate = false)
@Entity(tableName = "DataWords")
public class DataWords {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public String targetWord;
    public String textBefor;
    public String textAfter;
    public String typeObject;
    public int LanguageID;
    public int mapID;
}
