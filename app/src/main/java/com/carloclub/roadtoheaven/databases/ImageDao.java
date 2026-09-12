package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertImage(Image image);

    @Query("SELECT * FROM Images")
    List<Image> getAllImages();

    @Query("SELECT * FROM Images WHERE id = :id")
    Image getById(int id);
}
