package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataWordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertWord(DataWords words);

    @Query("SELECT * FROM DataWords")
    List<DataWords> getAllDataWords();

    @Query("SELECT * FROM DataWords WHERE id = :id")
    DataWords getById(int id);

    @Query("SELECT * FROM DataWords WHERE mapID = :mapID AND LanguageID = :LanguageID")
    List<DataWords> getRecordsOfMap(int mapID, int LanguageID);
}
