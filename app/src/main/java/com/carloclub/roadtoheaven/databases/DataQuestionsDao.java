package com.carloclub.roadtoheaven.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataQuestionsDao {
    //    @Insert
//    void insertQuestion(QuestionTable question);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertQuestion(DataQuestions question);

    @Query("SELECT * FROM DataQuestions WHERE id = :id")
    DataQuestions getById(int id);

    @Query("SELECT * FROM DataQuestions WHERE OriginalID = :OriginalID AND LanguageID = :LanguageID ")
    DataQuestions getTranslate(int OriginalID, int LanguageID);

    @Query("SELECT * FROM DataQuestions")
    List<DataQuestions> getAllQuestionTable();
}


