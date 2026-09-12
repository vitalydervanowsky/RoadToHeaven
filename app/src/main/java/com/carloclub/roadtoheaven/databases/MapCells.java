package com.carloclub.roadtoheaven.databases;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MapCells")
public class MapCells {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public int mapID;
    public int x;
    public int y;
    public String type;
}
