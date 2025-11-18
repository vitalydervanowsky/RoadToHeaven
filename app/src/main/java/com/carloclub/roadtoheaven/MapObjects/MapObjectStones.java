package com.carloclub.roadtoheaven.MapObjects;

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

import com.carloclub.roadtoheaven.BridgeActivity;
import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.CustomImageView;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Victorina;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MapObjectStones extends MapObject {
    private TextView textMoney;
    Random random;
    Puzzle puzzle;

    Date lastSuccess;
    TextView buttonAnswer1;
    TextView buttonAnswer2;
    TextView buttonAnswer3;
    TextView buttonAnswer4;
    public MediaPlayer correctMediaPlayer;
    private MediaPlayer incorrectMediaPlayer;
    private MediaPlayer triumfMediaPlayer;
    int trueAnswer;
    String [] answers;

    boolean isEnterAnswer;
    Victorina victorina;

    public MapObjectStones(int X, int Y, MapActivity activity) {
        super(X, Y, activity);

        random = new Random();
        type = "stones";
        answers = new String[4];
        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
            dialog.setContentView(R.layout.dialog_puzzle);
            Button buttonStop = dialog.findViewById(R.id.close);
            buttonStop.setOnClickListener(v -> endFill());

            buttonAnswer1 = dialog.findViewById(R.id.buttonAnswer1);
            buttonAnswer2 = dialog.findViewById(R.id.buttonAnswer2);
            buttonAnswer3 = dialog.findViewById(R.id.buttonAnswer3);
            buttonAnswer4 = dialog.findViewById(R.id.buttonAnswer4);
        }
        //ObjectMediaPlayer = MediaPlayer.create(activity, R.raw.organ);
        puzzle = new Puzzle(this);

        if (correctMediaPlayer == null) {
            correctMediaPlayer = MediaPlayer.create(mapActivity, R.raw.ok);
        }
        if (incorrectMediaPlayer == null) {
            incorrectMediaPlayer = MediaPlayer.create(mapActivity, R.raw.error);
        }
        if (triumfMediaPlayer == null) {
            triumfMediaPlayer = MediaPlayer.create(mapActivity, R.raw.triumf);
        }


        victorina = new Victorina(this, dialog.getWindow().getDecorView());
    }
     public void startHelp() {
        buttonAnswer1.setVisibility(View.INVISIBLE);
        buttonAnswer2.setVisibility(View.INVISIBLE);
        buttonAnswer3.setVisibility(View.INVISIBLE);
        buttonAnswer4.setVisibility(View.INVISIBLE);
    }
    public void showAnswers() {
        int r = random.nextInt(mapActivity.map.mStones.length - 4);
        int i=1;
        while (i<=4){
            if (mapActivity.map.mStones[r].answer.equals(puzzle.stone.answer)) {
                r++;
                continue;
            }
            answers[i-1] = mapActivity.map.mStones[r].answer;
            i++;
            r++;
        }

        i = random.nextInt(3);
        answers[i] = puzzle.stone.answer;
        trueAnswer=i+1;

        victorina.loadQuestion(answers[0], answers[1], answers[2], answers[3],trueAnswer);
        victorina.showAnswers();

        isEnterAnswer=true;
    }
    @Override
    public void endVictorina(boolean isOK) {
        if (!isOK) {
            //incorrectMediaPlayer.start();
        } else {
            triumfMediaPlayer.start();
            Constants.DATAGAME.setStones(Constants.DATAGAME.getStones() + 1);
            mapActivity.updateBar();
            ((TextView) dialog.findViewById(R.id.textView3)).setText(Messages.getMessageGotStone());
            if (Constants.DATAGAME.getStones() == 7) {
                DialogMessage.showMessage(R.drawable.bridge, R.drawable.stones1, Messages.getMessageGotAllStones(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            } else {
                DialogMessage.showMessage(R.drawable.gratulation, R.drawable.stones1, Messages.getMessageGotStone(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            }
            lastSuccess = Calendar.getInstance().getTime();
            mapActivity.showRubies();
        }
        dialog.hide();

    }

    public void endFill() {
        mediaPlayer.pause();
        dialog.dismiss();
    }

    @Override
    public void runAction() {
        if (lastSuccess != null && (Calendar.getInstance().getTime().getTime() - lastSuccess.getTime()) < 180000) { //чаще 3 минут не давать
            DialogMessage.showMessage(R.drawable.fail, R.drawable.fail, Messages.getMessageGalleryTechnicalBreak(), "", mapActivity);
            return;
        }


        showPuzzle();

        buttonAnswer1.setText(Messages.getMessageKnowAnswer());
        buttonAnswer2.setText(Messages.getMessageGetHint());
        buttonAnswer3.setVisibility(View.INVISIBLE);
        buttonAnswer4.setVisibility(View.INVISIBLE);
        startHelp();

        isEnterAnswer=false;
        //dialog.show();

    }

    @Override
    public boolean isActual(){
        if (lastSuccess!=null && (Calendar.getInstance().getTime().getTime()-lastSuccess.getTime())<180000) { //чаще 3 минут не давать
            return false;
        }
        return true;
    }
    public void showPuzzle() {
        puzzle.startPuzzle(mapActivity.map.mStones[random.nextInt(mapActivity.map.mStones.length - 1)]);
        //dialog.show();
    }

    @Override
    public void loadAttributes(String[] attributes) {

    }


    public class Puzzle {
        MapObject mapObject;

        public MyMap.Stone stone;

        CustomImageView[] imageViews;
        Bitmap[] fragments;
        int[] indexes;
        //public Dialog dialog;
        int attempts = 14; //осталось попыток

        CustomImageView currentView;

        public Puzzle(MapObject Stones) {
            mapObject = Stones;
            //dialog = new Dialog(mapObject.mapActivity);// todo use mapActivity
            //dialog.setContentView(R.layout.dialog_puzzle);
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

            Button buttonStopPuzzle = dialog.findViewById(R.id.close);
            buttonStopPuzzle.setOnClickListener(v -> {
                dialog.dismiss();
                //this = null;
            });
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
//                Constants.DATAGAME.setStones(Constants.DATAGAME.getStones() + 1);
//                mapObject.mapActivity.updateBar();
//                attempts = 0;
//                ((TextView) dialog.findViewById(R.id.textViewTrials)).setText("");
//                ((TextView) dialog.findViewById(R.id.textView3)).setText("Поздравляем! Вы добыли 1 камень");
//                if (Constants.DATAGAME.getStones() == 7) {
//                    DialogMessage.showMessage(R.drawable.bridge, R.drawable.stones1, "Поздравляем! Все камни собраны. Можно ехать строить мост.", "Собрано: " + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
//                } else {
//                    DialogMessage.showMessage(R.drawable.gratulation, R.drawable.stones1, "Поздравляем! Вы добыли 1 камень", "Собрано: " + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
//                }
//
//                lastSuccess = Calendar.getInstance().getTime();

            } else if (attempts == 0) {
                //((TextView)dialog.findViewById(R.id.textView3)).setText("Не получилось((( Попробуйте ещё раз");
                DialogMessage.showMessage(R.drawable.gratulation, 0, Messages.getMessageFail(), "", mapActivity);
                dialog.dismiss();
            }

        }

        public void updateViews() {
            for (int i = 0; i < 16; i++) {
                // Устанавливаем фрагмент изображения в ImageView
                imageViews[i].setImageBitmap(fragments[indexes[i]]);
            }
            ((TextView) dialog.findViewById(R.id.textViewTrials)).setText(String.valueOf(attempts));
        }

        public void startPuzzle(MyMap.Stone stone) {
            this.stone = stone;
            attempts = 1000;
            ((TextView) dialog.findViewById(R.id.textView3)).setText(Messages.getMessageDoMosaic());
            //Разрезаем изображение и по-очереди вкладываем в каждый Вью
            ((TextView) dialog.findViewById(R.id.textViewQuestion)).setText(stone.question);
            //идентификатор картинки 400х400
            int IDImage = mapObject.mapActivity.getResources().getIdentifier(stone.data, "drawable", mapObject.mapActivity.getPackageName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap originalBitmap = BitmapFactory.decodeResource(mapObject.mapActivity.getResources(), IDImage, options); // Определяем координаты и размеры фрагмента

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
}
