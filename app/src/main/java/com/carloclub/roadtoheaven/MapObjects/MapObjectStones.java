package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.CustomImageView;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MapObjectStones extends MapObject {
    private TextView TextMoney;
    Random random;
    Puzle puzle;
    Date lastOK;
    private TextView TextFuel;
    Date lastSuccess;
    public MapObjectStones(int X, int Y, MapActivity MainActivity){
        super(X, Y, MainActivity);
        dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
        random = new Random();
        type = "stones";
        dialog.setContentView(R.layout.dialog_church);
        Button buttonStop = dialog.findViewById(R.id.close);
        buttonStop.setOnClickListener(v -> EndFill());
        //ObjectMediaPlayer = MediaPlayer.create(MainActivity, R.raw.organ);
        puzle = new Puzle(this);
    }
    public void EndFill(){
        ObjectMediaPlayer.pause();
        dialog.hide();
    }

    @Override
    public void RunAction(){
        if (lastSuccess!=null && (Calendar.getInstance().getTime().getTime()-lastSuccess.getTime())<180000) { //чаще 3 минут не давать
            DialogMessage.showMessage(R.drawable.fail,R.drawable.fail,"На этом руднике новые камни ещё не выросли","", mapActivity);
            return;
        }

        showPuzle();
        //dialog.show();

    }

    public void showPuzle(){
        puzle.startPuzle(mapActivity.Map.Stones[random.nextInt(mapActivity.Map.Stones.length-1)]);
        //dialog.show();
    }
    @Override
    public void loadAttributes(String[] attributes){

    }


    public class Puzle {
        MapObject parrent;

        CustomImageView[] imageViews;
        Bitmap [] fragments;
        int [] idnexes;
        public Dialog dialog;
        int trials=14; //осталось попыток

        CustomImageView currentView;

        public Puzle(MapObject Stones) {
            parrent = Stones;
            dialog = new Dialog(parrent.mapActivity);
            dialog.setContentView(R.layout.dialog_puzle);
            imageViews = new CustomImageView[16];
            idnexes = new int[16];
            fragments = new Bitmap[16];
            imageViews[0]=dialog.findViewById(R.id.puzleView1);
            imageViews[1]=dialog.findViewById(R.id.puzleView2);
            imageViews[2]=dialog.findViewById(R.id.puzleView3);
            imageViews[3]=dialog.findViewById(R.id.puzleView4);
            imageViews[4]=dialog.findViewById(R.id.puzleView5);
            imageViews[5]=dialog.findViewById(R.id.puzleView6);
            imageViews[6]=dialog.findViewById(R.id.puzleView7);
            imageViews[7]=dialog.findViewById(R.id.puzleView8);
            imageViews[8]=dialog.findViewById(R.id.puzleView9);
            imageViews[9]=dialog.findViewById(R.id.puzleView10);
            imageViews[10]=dialog.findViewById(R.id.puzleView11);
            imageViews[11]=dialog.findViewById(R.id.puzleView12);
            imageViews[12]=dialog.findViewById(R.id.puzleView13);
            imageViews[13]=dialog.findViewById(R.id.puzleView14);
            imageViews[14]=dialog.findViewById(R.id.puzleView15);
            imageViews[15]=dialog.findViewById(R.id.puzleView16);

            for (int i=0; i<16;i++){
                imageViews[i].setOnClickListener(v -> {
                    onClick(v.getId());
                });
            }

            Button buttonStopPuzle = dialog.findViewById(R.id.close);
            buttonStopPuzle.setOnClickListener(v -> {
                dialog.hide();
                //this = null;
            });
        }

        public void onClick(int ID){
            if (trials==0) {
                dialog.hide();
                //Toast.makeText(parrent.MainActivity, "К сожалению, 10 попыток закончились. Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                return;
            }
            CustomImageView v = dialog.findViewById(ID);
            if (currentView==null){
                currentView = v;
                currentView.isCurrent=true;
                currentView.invalidate();
                v.setAlpha((float) 0.4);
                return;
            }

            currentView.setAlpha((float) 0.9);
            if (v==currentView){
                currentView.isCurrent=false;
                currentView.invalidate();
                currentView = null;
                return;
            }


            int from = 0;
            int to = 0;
            for (int i=0; i<16;i++){
                if (currentView==imageViews[i]) from=i;
                if (v==imageViews[i]) to=i;
            }

            //меняем
            int tmp = idnexes[from];
            idnexes[from] = idnexes[to];
            idnexes[to] = tmp;

            currentView.isCurrent=false;
            currentView.invalidate();
            currentView = null;
            trials--;
            updateViews();

            //проверяем
            boolean OK=true;
            for (int i=0; i<16;i++){
                if (idnexes[i]!=i) OK = false;
            }

            if (OK){
                Constants.DATAGAME.setStones(Constants.DATAGAME.getStones()+1);
                parrent.mapActivity.updateBar();
                trials=0;
                ((TextView)dialog.findViewById(R.id.textViewTrials)).setText("");
                ((TextView)dialog.findViewById(R.id.textView3)).setText("Поздравляем! Вы добыли 1 камень");
                if (Constants.DATAGAME.getStones()==7) {
                    DialogMessage.showMessage(R.drawable.bridge, R.drawable.icon_stones, "Поздравляем! Все камни собраны. Можно ехать строить мост.", "Собрано: " + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
                }
                else
                    DialogMessage.showMessage(R.drawable.gratulation,R.drawable.icon_stones,"Поздравляем! Вы добыли 1 камень","Собрано: "+String.valueOf(Constants.DATAGAME.getStones()), mapActivity);

                lastSuccess = Calendar.getInstance().getTime();
                //Toast.makeText(parrent.MainActivity, "Поздравляем! Вы добыли 1 камень", Toast.LENGTH_SHORT).show();
                //dialog.hide();
            }
            else if (trials==0){
                //((TextView)dialog.findViewById(R.id.textView3)).setText("Не получилось((( Попробуйте ещё раз");
                DialogMessage.showMessage(R.drawable.gratulation,0,"Не получилось((( Попробуйте ещё раз","", mapActivity);
                dialog.hide();
            }

        }

        public void updateViews(){
            for (int i=0; i<16;i++){
                // Устанавливаем фрагмент изображения в ImageView
                imageViews[i].setImageBitmap(fragments[idnexes[i]]);
            }
            ((TextView)dialog.findViewById(R.id.textViewTrials)).setText(String.valueOf(trials));
        }
        public void startPuzle(MyMap.Stone stone){
            trials=14;
            ((TextView)dialog.findViewById(R.id.textView3)).setText("Соберите мозайку, передвинув фрагменты не более 14 раз");
            //Разрезаем изображение и по-очереди вкладываем в каждый Вью
            ((TextView)dialog.findViewById(R.id.textViewQuestion)).setText(stone.question);
            //идентификатор картинки 400х400
            int IDImage = parrent.mapActivity.getResources().getIdentifier(stone.data, "drawable",parrent.mapActivity.getPackageName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap originalBitmap = BitmapFactory.decodeResource(parrent.mapActivity.getResources(), IDImage, options); // Определяем координаты и размеры фрагмента

            for (int y=0; y<4; y++)
                for (int x=0; x<4; x++){
                    int num = y*4+x+1;
                    // Создаем фрагмент изображения
                    fragments[num-1] = Bitmap.createBitmap(originalBitmap, x*100, y*100, 100, 100);

                }

            //Перемешиваем вьюшки случайным образом за 8 перемещений
            for (int i=0; i<16;i++){
                idnexes[i] = i;
            }
            //Random random = new Random();
            for (int i=0; i<8;i++){ //делаем 8 итераций, меняя 2 случайных фрагмента
                int from = random.nextInt(15);
                int to = random.nextInt(15);

                int tmp = idnexes[from];
                idnexes[from] = idnexes[to];
                idnexes[to] = tmp;
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
