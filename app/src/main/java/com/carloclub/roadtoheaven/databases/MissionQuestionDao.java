package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MissionQuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertMissionRecord(MissionQuestion missionQuestion);

    @Query("SELECT * FROM MissionQuestions")
    List<MissionQuestion> getAllRecords();

    @Query("SELECT * FROM MissionQuestions WHERE id = :id")
    MissionQuestion getById(int id);

    @Query("SELECT * FROM MissionQuestions WHERE mapID = :mapID")
    List<MissionQuestion> getRecordsOfMap(int mapID);
}
