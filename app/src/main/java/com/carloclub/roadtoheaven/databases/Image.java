package com.carloclub.roadtoheaven.databases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;

//(autoGenerate = false)
@Entity(tableName = "Images")
public class Image {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public String name;
    public String fileName;
    public long dateLoad;
    public long dateUpdate;


    public Bitmap getImage(){
        File file = new File(RthBase.RthContext.getFilesDir(), fileName);
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    public File getFile(){
        return new File(RthBase.RthContext.getFilesDir(), fileName);
    }
    public String getPatch(){
        File f = new File(RthBase.RthContext.getFilesDir(), fileName);
        if (f.exists()) return f.getAbsolutePath();
        else return null;
    }
}
