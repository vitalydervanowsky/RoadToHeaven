package com.carloclub.roadtoheaven.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//(autoGenerate = false)
@Entity(tableName = "MissionQuestions")
public class MissionQuestion {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public int idQuestion;
    public String typeObject;
    public int mapID;
}
