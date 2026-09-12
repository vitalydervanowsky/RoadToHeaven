package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MapCellsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertQuestion(MapCells mapCells);

    @Query("SELECT * FROM MapCells")
    List<MapCells> getAllMapCells();

    @Query("SELECT * FROM MapCells WHERE mapID = :mapID")
    List<MapCells> getCellsOfMap(int mapID);
}
