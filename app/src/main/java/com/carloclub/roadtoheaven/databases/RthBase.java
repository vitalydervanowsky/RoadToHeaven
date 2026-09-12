package com.carloclub.roadtoheaven.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Mission.class, DataQuestions.class, MapCells.class, Image.class,MissionQuestion.class,DataWords.class,Lesson.class, DataGallery.class}, version = 2)
public abstract class RthBase extends RoomDatabase {

    public static volatile RthBase instance;
    public static volatile Context RthContext;

    // DAO
    public abstract MissionDao missionDao();
    public abstract DataQuestionsDao dataQuestionsDao();
    public abstract MapCellsDao mapCellsDao();
    public abstract ImageDao imageDao();
    public abstract MissionQuestionDao missionQuestionDao();
    public abstract DataWordsDao dataWordsDao();
    public abstract LessonDao lessonDao();
    public abstract GalleryDao galleryDao();

    // Singleton для получения экземпляра базы
    public static synchronized RthBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            RthBase.class, "rthBase")
                    .allowMainThreadQueries() // лучше убрать и работать асинхронно
                    .build();
            RthContext = context.getApplicationContext();
        }
        return instance;
    }


}
