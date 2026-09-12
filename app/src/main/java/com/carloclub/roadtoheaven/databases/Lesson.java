package com.carloclub.roadtoheaven.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//(autoGenerate = false)
@Entity(tableName = "Lesson")
public class Lesson {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public int mapID;
    public String text;
    public String typeObject;
    public int imageId;
    public int audioId;
    public int languageID;
    public int order;
}
