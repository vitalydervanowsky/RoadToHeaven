package com.carloclub.roadtoheaven.databases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity(tableName = "DataQuestions")
public class DataQuestions {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String Answer1;
    public String Answer2;
    public String Answer3;
    public String Answer4;
    public int ImageID;
    public int OriginalID;
    public int LanguageID;
    public int Level;
    public int NumOK;
    public String TextQuestion;

    public DataQuestions translate(int idLanguage){
        if (idLanguage==LanguageID) return this; //и так нужный
        RthBase RTHB = RthBase.instance;
        int idQuestion = OriginalID;
        if (idQuestion==0) idQuestion = id;

        DataQuestions dataQuestion = RTHB.dataQuestionsDao().getById(idQuestion);
        if (idLanguage!=1){
            //получаем перевод
            DataQuestions translateQuestions = RTHB.dataQuestionsDao().getTranslate(idQuestion, idLanguage);
            if (translateQuestions!=null)
                dataQuestion = translateQuestions;
        }

        return dataQuestion;
    }
    public Bitmap getImage(){
        RthBase RTHB = RthBase.instance;
        Image image = RTHB.imageDao().getById(ImageID);
        return image.getImage();
    }
}


