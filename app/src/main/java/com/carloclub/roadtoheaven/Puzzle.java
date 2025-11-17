package com.carloclub.roadtoheaven;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.MapObjects.MapObject;

import java.util.Random;

public class Puzzle {
    MapObject mapObject;

    public Dialog dialog;
    Random random;
    public MediaPlayer correctMediaPlayer;
    private MediaPlayer incorrectMediaPlayer;

    public MyMap.Stone stone;

    CustomImageView[] imageViews;
    Bitmap[] fragments;
    int[] indexes;
    //public Dialog dialog;
    int attempts = 14; //осталось попыток
    Victorina victorina;

    CustomImageView currentView;
    MyMap.Question question;

    public Puzzle(MapObject mapObject, MyMap.Question question) {
        this.mapObject = mapObject;
        this.question = question;
        random = new Random();
        dialog = new Dialog(mapObject.mapActivity, R.style.FullScreenDialog);
        dialog.setContentView(R.layout.dialog_puzzle);
        dialog.findViewById(R.id.buttonAnswer1).setVisibility(View.INVISIBLE);
        dialog.findViewById(R.id.buttonAnswer2).setVisibility(View.INVISIBLE);
        dialog.findViewById(R.id.buttonAnswer3).setVisibility(View.INVISIBLE);
        dialog.findViewById(R.id.buttonAnswer4).setVisibility(View.INVISIBLE);


        imageViews = new CustomImageView[16];
        indexes = new int[16];
        fragments = new Bitmap[16];
        imageViews[0] = dialog.findViewById(R.id.puzzleView1);
        imageViews[1] = dialog.findViewById(R.id.puzzleView2);
        imageViews[2] = dialog.findViewById(R.id.puzzleView3);
        imageViews[3] = dialog.findViewById(R.id.puzzleView4);
        imageViews[4] = dialog.findViewById(R.id.puzzleView5);
        imageViews[5] = dialog.findViewById(R.id.puzzleView6);
        imageViews[6] = dialog.findViewById(R.id.puzzleView7);
        imageViews[7] = dialog.findViewById(R.id.puzzleView8);
        imageViews[8] = dialog.findViewById(R.id.puzzleView9);
        imageViews[9] = dialog.findViewById(R.id.puzzleView10);
        imageViews[10] = dialog.findViewById(R.id.puzzleView11);
        imageViews[11] = dialog.findViewById(R.id.puzzleView12);
        imageViews[12] = dialog.findViewById(R.id.puzzleView13);
        imageViews[13] = dialog.findViewById(R.id.puzzleView14);
        imageViews[14] = dialog.findViewById(R.id.puzzleView15);
        imageViews[15] = dialog.findViewById(R.id.puzzleView16);

        for (int i = 0; i < 16; i++) {
            imageViews[i].setOnClickListener(v -> {
                onClick(v.getId());
            });
        }
        if (correctMediaPlayer == null) {
            correctMediaPlayer = MediaPlayer.create(mapObject.mapActivity, R.raw.correct);
            incorrectMediaPlayer = MediaPlayer.create(mapObject.mapActivity, R.raw.incorrect);
        }
        victorina = new Victorina(mapObject, dialog);
    }

    public void onClick(int ID) {
        if (attempts == 0) {
            dialog.dismiss();
            //Toast.makeText(parrent.MainActivity, "К сожалению, 10 попыток закончились. Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
            return;
        }
        CustomImageView v = dialog.findViewById(ID);
        if (currentView == null) {
            currentView = v;
            currentView.isCurrent = true;
            currentView.invalidate();
            //v.setAlpha((float) 0.4);
            return;
        }

        //currentView.setAlpha((float) 0.9);
        if (v == currentView) {
            currentView.isCurrent = false;
            currentView.invalidate();
            currentView = null;
            return;
        }


        int from = 0;
        int to = 0;
        for (int i = 0; i < 16; i++) {
            if (currentView == imageViews[i]) from = i;
            if (v == imageViews[i]) to = i;
        }

        //меняем
        int tmp = indexes[from];
        indexes[from] = indexes[to];
        indexes[to] = tmp;

        currentView.isCurrent = false;
        currentView.invalidate();
        currentView = null;
        attempts--;
        updateViews();

        //проверяем
        boolean isOk = true;
        for (int i = 0; i < 16; i++) {
            if (indexes[i] != i) isOk = false;
        }

        if (isOk) {
            showAnswers();
            correctMediaPlayer.start();
            return;

        } else if (attempts == 0) {
            //((TextView)dialog.findViewById(R.id.textView3)).setText("Не получилось((( Попробуйте ещё раз");
            DialogMessage.showMessage(R.drawable.gratulation, 0, Messages.getMessageFail(), "", mapObject.mapActivity);
            dialog.dismiss();
        }

    }

    public void showAnswers() {

        victorina.loadQuestion(question.answer1, question.answer2, question.answer3, question.answer4, question.trueAnswer);
        victorina.showAnswers();

    }

    public void updateViews() {
        for (int i = 0; i < 16; i++) {
            // Устанавливаем фрагмент изображения в ImageView
            imageViews[i].setImageBitmap(fragments[indexes[i]]);
        }
        ((TextView) dialog.findViewById(R.id.textViewTrials)).setText(String.valueOf(attempts));
    }

    public void startPuzzle() {
        attempts = 1000;

        ((TextView) dialog.findViewById(R.id.textView3)).setText(Messages.getMessageDoMosaic());
        ((TextView) dialog.findViewById(R.id.textViewQuestion)).setText(question.question);
        //Разрезаем изображение и по-очереди вкладываем в каждый Вью
        //идентификатор картинки 400х400
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap originalBitmap = BitmapFactory.decodeResource(mapObject.mapActivity.getResources(), question.imageID, options); // Определяем координаты и размеры фрагмента

        for (int y = 0; y < 4; y++)
            for (int x = 0; x < 4; x++) {
                int num = y * 4 + x + 1;
                // Создаем фрагмент изображения
                fragments[num - 1] = Bitmap.createBitmap(originalBitmap, x * 100, y * 100, 100, 100);

            }

        //Перемешиваем вьюшки случайным образом за 8 перемещений
        for (int i = 0; i < 16; i++) {
            indexes[i] = i;
        }
        //Random random = new Random();
        for (int i = 0; i < 8; i++) { //делаем 8 итераций, меняя 2 случайных фрагмента
            int from = random.nextInt(15);
            int to = random.nextInt(15);

            int tmp = indexes[from];
            indexes[from] = indexes[to];
            indexes[to] = tmp;
        }

        //игрок должен будет вернуть всё за 14  манипуляций

        updateViews();
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        WindowCompat.setDecorFitsSystemWindows(window, false);
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

    }
}