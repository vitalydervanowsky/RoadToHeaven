package com.carloclub.roadtoheaven.databases;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.carloclub.roadtoheaven.model.ClassType;
import com.carloclub.roadtoheaven.model.GalleryImage;
import com.carloclub.roadtoheaven.model.PageData;
import com.carloclub.roadtoheaven.model.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//(autoGenerate = false)
@Entity(tableName = "Missions")
public class Mission {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public String Name;
    public String Theme;
    public int imageId;
    public int mapImageId;
    public int level;
    public int mapHeight;
    public int mapWidth;
    public long dateActual;
    public boolean isEnabled=true;
    public String ThemeClassA="Тема 1";
    public String ThemeClassB="Тема 2";
    public String HelloClassA="Hello 1";
    public String HelloClassB="Hello 2";
    public String HelloChurch="Hello 2";

    public String getLessonTitles(String classType){
        return getLessonTitles(0, classType);
    }
    public String getLessonTitles(int idLanguage, String classType){
        if (classType=="classA") return ThemeClassA;
        else return ThemeClassB;
    }

    public String getLessonHello(String classType){
        return getLessonHello(0, classType);
    }
    public String getLessonHello(int idLanguage, String classType){
        if (classType=="classA") return HelloClassA;
        else if (classType=="classB") return HelloClassB;
        else return HelloChurch;
    }

    public List<PageData> getLessonData(String classType){
        List<PageData> result = new ArrayList<PageData>();
        List<Lesson> lessons = RthBase.instance.lessonDao().getRecordsOfObject(id,1, classType);
        for (int i=0; i<lessons.size();i++){
            Lesson lesson = lessons.get(i);

            Image im= RthBase.instance.imageDao().getById(lesson.imageId);
            String imPatch = (im==null) ? null : im.getPatch();

            im= RthBase.instance.imageDao().getById(lesson.audioId);
            String auPatch = (im==null) ? null : im.getPatch();
            result.add(new PageData(lesson.text, imPatch,auPatch));

        }
        return result;

    }
    public List<GalleryImage> getGalleryData(){
        List<GalleryImage> result = new ArrayList<GalleryImage>();
        List<DataGallery> galleryList = RthBase.instance.galleryDao().getRecordsOfObject(id);
        for (int i=0; i<galleryList.size();i++){
            DataGallery dataGallery = galleryList.get(i);

            Image im= RthBase.instance.imageDao().getById(dataGallery.imageId);
            String imPatch = (im==null) ? null : im.getPatch();

            result.add(new GalleryImage(dataGallery.id,imPatch,dataGallery.text, (dataGallery.typeObject.equals( "classA")) ? ClassType.A : ClassType.B , State.DEFAULT));

        }
        return result;

    }
}
