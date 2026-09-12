package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MissionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertMission(Mission mission);

    @Query("SELECT * FROM Missions")
    List<Mission> getAllMissions();

    @Query("SELECT * FROM Missions WHERE id = :id")
    Mission getById(int id);
}
