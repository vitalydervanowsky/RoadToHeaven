package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LessonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertLesson(Lesson lesson);

    @Query("SELECT * FROM Lesson")
    List<Lesson> getAllLesson();

    @Query("SELECT * FROM Lesson WHERE id = :id")
    Lesson getById(int id);

    @Query("SELECT * FROM Lesson WHERE mapID = :mapID AND LanguageID = :LanguageID AND typeObject = :typeObject ORDER BY 'order'")
    List<Lesson> getRecordsOfObject(int mapID, int LanguageID, String typeObject);
}
