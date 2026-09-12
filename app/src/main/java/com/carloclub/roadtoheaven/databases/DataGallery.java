package com.carloclub.roadtoheaven.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//(autoGenerate = false)
@Entity(tableName = "Gallery")
public class DataGallery {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public int mapID;
    public String text;
    public String typeObject;
    public int imageId;
}
