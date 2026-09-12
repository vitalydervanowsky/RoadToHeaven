package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GalleryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertGallery(DataGallery gallery);

    @Query("SELECT * FROM Gallery")
    List<DataGallery> getAllGallery();

    @Query("SELECT * FROM Gallery WHERE id = :id")
    DataGallery getById(int id);

    @Query("SELECT * FROM Gallery WHERE mapID = :mapID")
    List<DataGallery> getRecordsOfObject(int mapID);
}
