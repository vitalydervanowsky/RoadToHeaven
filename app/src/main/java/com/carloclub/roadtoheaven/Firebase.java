package com.carloclub.roadtoheaven;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.carloclub.roadtoheaven.databases.DataGallery;
import com.carloclub.roadtoheaven.databases.DataQuestions;
import com.carloclub.roadtoheaven.databases.DataWords;
import com.carloclub.roadtoheaven.databases.Image;
import com.carloclub.roadtoheaven.databases.Lesson;
import com.carloclub.roadtoheaven.databases.MapCells;
import com.carloclub.roadtoheaven.databases.Mission;
import com.carloclub.roadtoheaven.databases.MissionQuestion;
import com.carloclub.roadtoheaven.databases.RthBase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Firebase {

    public static volatile ArrayList<UUID> startedQueryes = new ArrayList<UUID>();
    public static volatile ArrayList<String> startedProblems = new ArrayList<String>();

    public static String loadFile(int imageID, Context context,UUID guid){
        if (imageID==0) return null;
        RthBase RTHB = RthBase.instance;
        Image record = RTHB.imageDao().getById(Integer.valueOf(imageID));

        //Тест. скачаем файл картінкі
        try {

            HttpURLConnection conn = (HttpURLConnection) new URL("https://webdav.yandex.ru/RTH/"+record.fileName).openConnection();
            conn.setRequestProperty("Authorization", "Basic Y2FybG9jbHViOm5zeWJ4bmlmbHJ4bGpobm4=");
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try {
                    // Получаем путь к внутренней папке приложения
                    File file = new File(context.getFilesDir(), record.fileName);

                    // Записываем поток в этот файл
                    InputStream inputStream = conn.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();

                    record.dateLoad = System.currentTimeMillis()-673228800000L;
                    RTHB.imageDao().upsertImage(record);

                    for (int i=0;i<startedQueryes.size();i++){
                        if (startedQueryes.get(i)==guid){
                            startedQueryes.remove(i);
                            break;
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (MalformedURLException e) { // сюда попадёшь, если строка URL действительно некорректна
            //e.printStackTrace();
        } catch (IOException e) { // сетевые ошибки, права на запись и т.п.
            //e.printStackTrace();
        }

        return record.fileName;
    }

    public static void updateImage(int id){
        if (id==0) return;
        RthBase RTHB = RthBase.instance;
        Image record = RTHB.imageDao().getById(id);
        if (record==null) return;
        if (record.dateLoad>=record.dateUpdate)
            return;
        UUID guid = UUID.randomUUID();
        startedQueryes.add(guid);
        new Thread(() -> {
                    loadFile(id, RthBase.RthContext,guid);
                }).start();
    }
    public static void updateQuestion(int id){
        if (id==0) return;
        RthBase RTHB = RthBase.instance;
        DataQuestions record = RTHB.dataQuestionsDao().getById(id);
        if (record==null)
            getTable("Questions", null,"id",String.valueOf(id),0L);
    }

    public static boolean getTable(String tableName, AppCompatActivity activity){
        return getTable(tableName, activity, null, null, (long)0);
    }

    public static boolean getTable(String tableName, AppCompatActivity activity, String filterID, Object filterValue, Long dateActual){
        return getTable(tableName, activity, filterID, filterValue, dateActual, UUID.randomUUID());
    }

    public static boolean getTable(String tableName, AppCompatActivity activity, String filterID, Object filterValue, Long dateActual, UUID guid) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Log.d("Time", Calendar.getInstance().getTime().toString());//whereArrayContains("id","0")
        Query query = db.collection(tableName);

        if (filterID=="id"){
            query = query.whereEqualTo(FieldPath.documentId(), filterValue);}
        else if (filterID!=null){
            query=query.whereEqualTo(filterID, filterValue);
        }
        if (dateActual>0){
            query=query.whereGreaterThan("DateUpdate", (dateActual-1673228800000L));  //673228800000 Дата рожденія св. Карло. Храним время обновления в миллисекундах от этой даты
        }
        startedQueryes.add(guid);
        query.get() .addOnCompleteListener(task -> {
            for (int i=0;i<startedQueryes.size();i++){
                if (startedQueryes.get(i)==guid){
                    startedQueryes.remove(i);
                    break;
                }
            }

            if (task.isSuccessful()) {
//                new Thread(() -> {
//                    loadFile("abc.jpg", activity.getApplicationContext());
//                }).start();
                RthBase RTHB = RthBase.instance;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (tableName=="Questions") {
                        DataQuestions question = new DataQuestions();
                        question.id = Integer.valueOf(document.getId());
                        question.TextQuestion = document.getString("TextQuestion");
                        question.Answer1 = document.getString("Answer1");
                        question.Answer2 = document.getString("Answer2");
                        question.Answer3 = document.getString("Answer3");
                        question.Answer4 = document.getString("Answer4");
                        question.ImageID = Integer.valueOf(document.getString("Image"));
                        question.OriginalID = Integer.valueOf(document.getString("Original"));
                        question.LanguageID = Integer.valueOf(document.getString("Language"));
                        question.Level = Integer.valueOf(document.getString("Level"));
                        question.NumOK = Integer.valueOf(document.getString("NumOK"));
;
                        RTHB.dataQuestionsDao().upsertQuestion(question);
                        updateImage(question.ImageID);
                    }

                    else if (tableName=="Mission") {
                        Mission question = RTHB.missionDao().getById(Integer.valueOf(document.getId()));
                        if (question==null)  question = new Mission();
                        question.id = Integer.valueOf(document.getId());
                        question.Name = document.getString("Name");
                        question.Theme = document.getString("Theme");
                        question.ThemeClassA = document.getString("ThemeClassA");
                        question.ThemeClassB = document.getString("ThemeClassB");
                        question.HelloClassA = document.getString("HelloClassA");
                        question.HelloClassB = document.getString("HelloClassB");
                        question.HelloChurch = document.getString("HelloChurch");


                        question.imageId = Integer.valueOf(document.getString("imageId"));
                        question.mapImageId = Integer.valueOf(document.getString("mapImageId"));
                        question.level = Integer.valueOf(document.getString("level"));
                        question.mapHeight = Integer.valueOf(document.getString("mapHeight"));
                        question.mapWidth = Integer.valueOf(document.getString("mapWidth"));

                        //question.dateActual = System.currentTimeMillis(); //ТУТ НЕ НАДО! ТОЛЬКО ПРИ ОБНОВЛЕНИИ ДАННЫХ О МИССИИ loadMap()
                        ;
                        RTHB.missionDao().upsertMission(question);

                        updateImage(question.imageId);
                    }

                    else if (tableName=="Images") {
                        Image record = RTHB.imageDao().getById(Integer.valueOf(document.getId()));
                        if (record==null)  record = new Image();
                        record.id = Integer.valueOf(document.getId());
                        record.name = document.getString("Name");
                        record.fileName = document.getString("FileName");
                        record.dateUpdate = document.getLong("DateUpdate");

                        RTHB.imageDao().upsertImage(record);
                    }

                    else if (tableName=="MissionQuestions") {
                        MissionQuestion record = new MissionQuestion();
                        record.id = Integer.valueOf(document.getId());
                        record.idQuestion = Integer.valueOf(document.getString("idQuestion"));
                        record.typeObject = document.getString("typeObject");
                        record.mapID = Integer.valueOf(document.getString("mapID"));

                        RTHB.missionQuestionDao().upsertMissionRecord(record);
                        updateQuestion(record.idQuestion); // на всякий случай, если вдруг ещё не загрузили, но вообще должны уже быть загружены
                    }

                    else if (tableName=="DataWords") {
                        DataWords record = new DataWords();
                        record.id = Integer.valueOf(document.getId());
                        record.targetWord = document.getString("targetWord");
                        record.textBefor = document.getString("textBefor");
                        record.textAfter = document.getString("textAfter");
                        record.typeObject = document.getString("typeObject");
                        record.mapID = Integer.valueOf(document.getString("mapID"));
                        record.LanguageID = Integer.valueOf(document.getString("LanguageID"));

                        RTHB.dataWordsDao().upsertWord(record);
                    }

                    else if (tableName=="Lesson") {
                        Lesson record = new Lesson();
                        record.id = Integer.valueOf(document.getId());
                        record.typeObject = document.getString("typeObject");
                        record.text = document.getString("text");
                        record.mapID = Integer.valueOf(document.getString("mapID"));
                        record.languageID = Integer.valueOf(document.getString("LanguageID"));
                        record.imageId = Integer.valueOf(document.getString("imageId"));
                        record.audioId = Integer.valueOf(document.getString("audioId"));
                        record.order = Integer.valueOf(document.getString("order"));

                        RTHB.lessonDao().upsertLesson(record);

                        updateImage(record.imageId);
                        updateImage(record.audioId);
                    }

                    else if (tableName=="Gallery") {
                        DataGallery record = new DataGallery();
                        record.id = Integer.valueOf(document.getId());
                        record.typeObject = document.getString("typeObject");
                        record.text = document.getString("text");
                        record.mapID = Integer.valueOf(document.getString("mapID"));
                        record.imageId = Integer.valueOf(document.getString("imageId"));

                        RTHB.galleryDao().upsertGallery(record);

                        updateImage(record.imageId);
                    }

                    else if (tableName=="MapCells") {
                        MapCells record = new MapCells();
                        record.id = Integer.valueOf(document.getId());
                        record.mapID = Integer.valueOf(document.getString("mapID"));
                        record.x = Integer.valueOf(document.getString("x"));
                        record.y = Integer.valueOf(document.getString("y"));
                        record.type = document.getString("type");

                        RTHB.mapCellsDao().upsertQuestion(record);
                    }
                }
            } else {
                //Log.w("Firestore", "Ошибка при получении документов.", task.getException());
                startedProblems.add(task.getException().getMessage());
            }

        });
                return true;

    }
}
