package com.carloclub.roadtoheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MapActivity extends AppCompatActivity {
    ImageView imageView; //Карта
    ImageView car;
    ImageView nav;
    TextView textFuel;
    TextView textMoney;
    TextView textStones;
    TextView textRubi;
    private MediaPlayer correctMediaPlayer;

    int clickX;
    int clickY;

    int navX=0;
    int navY=0;
    MoveCar mMoveCar;
    private AnimationDrawable isAnimation;
    public MyMap Map;// = new MyMap(28, 21);
    Timer timer;
    int currentX = 1;
    int currentY = 1;

    public int carX=0;
    public int carY=0;

    public int stones=0;
    int lastY =0;   //служебный. Для параллельной прокрутки по вертикали
    int lastX =0;   //служебный. Для параллельной прокрутки по горизонтали
    int trend =3;   //0 право, 1 лево, 2 вверх, 3 вниз
    ArrayList<Tasks> myTasks;
    private MediaPlayer rrrMediaPlayer;
    ScrollView sv;
    HorizontalScrollView sh;
    boolean fuelDanger= false;

    ScaleGestureDetector scaleDetector;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );



        String CityName = getIntent().getStringExtra("CityName");
        Map = Constants.getMap(CityName);
        float level = getApplicationContext().getResources().getDisplayMetrics().density;
        Map.Scale = (int) (Constants.DATAGAME.SCALE*level); //
        Map.createObjects(MapActivity.this);

        ImageView imageBack = findViewById(R.id.imageView);
        //ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imageBack.getLayoutParams();
        imageBack.setImageDrawable(getDrawable(Map.BackgroundID));
        //int realScale = (int)params.height *Constants.DATAGAME.SCALE/ 21  /48; //в образце 21 вертикальная клетка
        imageBack.setLayoutParams(new ConstraintLayout.LayoutParams(Map.Length*Map.Scale, Map.Height*Map.Scale));

        scaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (mMoveCar!=null) return true;
                float factor = detector.getScaleFactor();

                if (factor > 1.0f && Map.Scale<700) {
                    //MyMap.MapCell CellInCenter = whyCellInCenter();
                    zoomMapToSCALE((int)(Map.Scale*1.03));
                    //scrollToCell(CellInCenter);
                } else if (factor < 1.0f && Map.Scale>35) {
                    //MyMap.MapCell CellInCenter = whyCellInCenter();
                    zoomMapToSCALE((int)(Map.Scale/1.03));
                    //scrollToCell(CellInCenter);
                }
                return true;
            }
        });

        myTasks =new ArrayList <Tasks>();
        timer = new Timer();
        car = findViewById(R.id.car);
        nav = findViewById(R.id.nav);
        correctMediaPlayer = MediaPlayer.create(this, R.raw.ok);
        textFuel = findViewById(R.id.textFuel);
        textMoney = findViewById(R.id.textMoney);
        textStones = findViewById(R.id.textStones);
        textRubi = findViewById(R.id.textRubi);

        rrrMediaPlayer = MediaPlayer.create(this, R.raw.rr);
        Constants.DATAGAME.setActivity(MapActivity.this);
        Constants.DATAGAME.setFuel(15000);

        sv = (ScrollView)findViewById(R.id.scrollViewV);
        sh = (HorizontalScrollView)findViewById(R.id.scrollViewH);
        sv.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int pointerCount = event.getPointerCount();
                if (pointerCount>=2){
                    //zoomMapToSCALE(Map.Scale+5);
                    scaleDetector.onTouchEvent(event);
                    return true;
                }
                //При начале движения пальцем запускается слушание события только у одного из элементов: либо ScrollView либо HorizontalScrollView
                //но координаты движения можно прослеживать по обеим осям
                //В обработкиче движения пальцем в вертикальном ScrollView будет отслеживать изменение координаты Х и прокручивать параллельно прогрммно HorizontalScrollView
                //Таким образом получаем диагональную прогрутку
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //lastY = (int) event.getY();
                        //lastX = (int) event.getX();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        int newX = (int) event.getX();
                        if (lastX==-1){  //Первый піксель теряем, чтобы начать считать при начале ведения пальцем, а не раньше. Иначе ерунда получалась
                            lastX=newX;
                            return false;
                        }
                        int scrollX = sh.getScrollX();
                        if (newX!=lastX){
                            scrollX=scrollX+lastX-newX;
                            scrollX = Math.max(0,scrollX);
                            lastX=newX;
                            sh.scrollTo(scrollX, sv.getScrollY());
                        }
                        return false;
                    case MotionEvent.ACTION_UP:
                        lastX=-1;
                        return false;
                }
                return false;
            }
        });
        sh.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int pointerCount = event.getPointerCount();
                if (pointerCount>=2){
                    //zoomMapToSCALE(Map.Scale+5);
                    scaleDetector.onTouchEvent(event);
                    return true;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //lastY = (int) event.getY();
                        //lastX = (int) event.getX();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        int newY = (int) event.getY();
                        if (lastY==-1){
                            lastY=newY;
                            return false;
                        }
                        int scrollY = sv.getScrollY();
                        if (newY!=lastY){
                            int newScrollY=scrollY+lastY-newY;
                            newScrollY = Math.max(0,newScrollY);
                            lastY=newY;
                            sv.scrollTo(sh.getScrollX(), newScrollY);
                            lastY = lastY+newScrollY-scrollY;  //Запоминаем с учетом смещения, т.к. sh лежит в sv и при прокрутке sv, меняются координаты в sh
                        }
                        return false;
                    case MotionEvent.ACTION_UP:
                        lastY=-1;
                        return false;
                }
                return false;
            }
        });


        currentX = 1;
        currentY = 1;

        imageView = findViewById(R.id.imageView);
        //imageView.getLayoutParams();

        imageView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Запоминаем координаты клика по экрану, чтобы использовать их потом в обработчике setOnClickListener
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickX = (int) event.getX();
                        clickY = (int) event.getY();
                        break;
                }
                return false;
            }
        });
        imageView.setOnClickListener(v -> {
            moveImageView(clickY, (clickX));
        });

        //включаем анимацию текущего положения машины (картинки)
        isAnimation = (AnimationDrawable)car.getDrawable();
        isAnimation.start();

        DialogMessage.showMessage(R.drawable.stones, R.drawable.icon_bridge, "Собери 7 камней знаний на каменных рудниках, чтобы построить разрушенный мост и выехать из города", "Удачи!" , MapActivity.this);

    }

    private void moveImageView(int positionY, int positionX) {
        //Клик по карте.
        //проверяем, можно ли  в эту ячейку поехать.
        MyMap.MapCell CurrentCell = Map.FindCellByXY(currentX, currentY);
        MyMap.MapCell NewCell = Map.FindCellByXY(positionX, positionY);
        if (NewCell.object==null && !NewCell.type.equals("Road"))
            return;

        //Строим маршрут и запускаем движение машины
        ArrayList<MyMap.MapCell> Rote = Map.BuildRote(CurrentCell,NewCell);
        if (Rote==null) return;
        Map.Rote = Rote;

        if (NewCell.X==navX && NewCell.Y==navY ){
            //начинаем ехать
            if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}
            mMoveCar = new MoveCar();
            twistCar();
            timer.schedule(mMoveCar, 20, 27-(Constants.DATAGAME.getSpeed()-60)/10);
            //rrrMediaPlayer.reset();
            rrrMediaPlayer.start();
        }
        else {
            //Устанавливаем навигационную ссылку
            if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}

            navX=NewCell.X;
            navY=NewCell.Y;
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) nav.getLayoutParams();
            int startX = (navX)*Map.Scale;
            int startY = (navY)*Map.Scale;
            params.setMargins(startX,startY,0,0);
            nav.setLayoutParams(params);
            nav.setVisibility(View.VISIBLE);
            correctMediaPlayer.start();

        }

        //zoomMapToSCALE(Constants.DATAGAME.SCALE*5);




    }

    private void zoomMapToSCALE(int newSCALE){
        //Постараемся картінку, которая в центре, оставіть в центре
        int scrollY = sv.getScrollY();
        int scrollX = sh.getScrollX();
        int centerX = scrollX+(int)(getApplicationContext().getResources().getDisplayMetrics().widthPixels/2);
        int centerY = scrollY+(int)(getApplicationContext().getResources().getDisplayMetrics().heightPixels/2);

        int newCenterX = (int)(centerX*newSCALE/Map.Scale);
        int newCenterY = (int)(centerY*newSCALE/Map.Scale);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) car.getLayoutParams();
        MyMap.MapCell CurrentCell = Map.FindCellByXY(currentX, currentY);

        Map.Scale = newSCALE;
        ImageView imageBack = findViewById(R.id.imageView);
        imageBack.setLayoutParams(new ConstraintLayout.LayoutParams(Map.Length*Map.Scale, Map.Height*Map.Scale));

        currentX=CurrentCell.X*Map.Scale;
        currentY=CurrentCell.Y*Map.Scale;

        params.setMargins(currentX,currentY,0,0);
        params.width = Map.Scale;
        params.height = Map.Scale;
        car.setLayoutParams(params);

        params = (ConstraintLayout.LayoutParams) nav.getLayoutParams();
        int startX = (navX)*Map.Scale;
        int startY = (navY)*Map.Scale;
        params.setMargins(startX,startY,0,0);
        params.width = Map.Scale;
        params.height = Map.Scale;
        nav.setLayoutParams(params);

        carX=currentX;
        carY=currentY;

        int newScrollY = Math.max(0,newCenterY-(int)(getApplicationContext().getResources().getDisplayMetrics().heightPixels/2));
        int newScrollX = Math.max(0,newCenterX-(int)(getApplicationContext().getResources().getDisplayMetrics().widthPixels/2));

        sv.scrollTo(newScrollX, newScrollY);
        sh.scrollTo(newScrollX, newScrollY);


        //подгоним размер машинки
        //car.setLayoutParams(new ConstraintLayout.LayoutParams(Map.Length*Map.Scale, Map.Height*Map.Scale));
    }

    private MyMap.MapCell whyCellInCenter(){
        int scrollY = sv.getScrollY()+400;
        int scrollX = sh.getScrollX()+700;

        return Map.FindCellByXY(scrollX, scrollY);
    }

    private void scrollToCell(MyMap.MapCell cell){
        //метод "ведёт" экран так, чтобы фокусная ячейка оставалась прімерно в центре: в координате: 400х; 200у

        int scrollY = sv.getScrollY();
        int scrollX = sh.getScrollX();
        boolean isMove=false;

        int x = (cell.X)*Map.Scale;
        int y = (cell.Y)*Map.Scale;

        if (scrollX<(x-720) || scrollX>(x-680)){
            scrollX = x-700;
            isMove=true;
        }
        if (scrollY<(y-420) || scrollY>(y-380)){
            scrollY = y-400;
            isMove=true;
        }



        if (isMove) {
            scrollY = Math.max(0,scrollY);
            scrollX = Math.max(0,scrollX);
            sv.scrollTo(scrollX, scrollY);
            sh.scrollTo(scrollX, scrollY);
        }

    }

    private void scrollToCar(int CurrentX, int CurrenrY) {
        //метод "ведёт" экран за машинкой, чтобы она не уезжала за границы окна

        int scrollY = sv.getScrollY();
        int scrollX = sh.getScrollX();
        boolean isMove=false;

        //Если позиция машины у правой границы или за ней (CurrentX больше, чем scrollX+ширина экрана, то  перемещаем карту на позицию машины
        if (CurrentX>(scrollX+700)){
            scrollX = CurrentX-700; //sh.scrollTo(CurrentX, CurrenrY);
            isMove=true;
        }  //Если позиция машины у левой границы или за ней (CurrentX меньше, чем scrollX, то  перемещаем карту на позицию машины+ширина экрана
        else if (CurrentX<(scrollX+30) && scrollX>0) {
            scrollX = CurrentX-30;
            isMove=true;
        }

        //аналогично по вертикали
        if (CurrenrY>(scrollY+400)){
            scrollY = CurrenrY-400; //sh.scrollTo(CurrentX, CurrenrY);
            isMove=true;
        }
        else if (CurrenrY<(scrollY+60) && scrollY>0) {
            scrollY = CurrenrY-60;
            isMove=true;
        }

        if (isMove) {
            CurrenrY = Math.max(0,CurrenrY);
            scrollX = Math.max(0,scrollX);
            sv.scrollTo(scrollX, scrollY);
            sh.scrollTo(scrollX, scrollY);
        }
    }

    public void updateBar(){
        textFuel.setText(String.valueOf(Constants.DATAGAME.getFuel() /1000)+" км");
        textMoney.setText(String.valueOf(Constants.DATAGAME.getMoney())+" p.");
        textStones.setText(String.valueOf(Constants.DATAGAME.getStones()));
        textRubi.setText(String.valueOf(Constants.DATAGAME.getRubies()));
    }


    public Tasks ChekTusk(MyMap.MapCell Cell){
        //Поиск заданя
        for (int i = 1; i<= myTasks.size(); i++){
            Tasks Task = myTasks.get(i-1);
            //Проверяем задания, которые направлены на любой объект какого-то типа (в них заполнен тип (TargetType), но не заполнена ячейка (TargetCell))
            if (Task.TargetType.equals(Cell.type)&& !Task.TargetType.equals(""))
                return Task;

            //Проверяем задания, которые направлены на конкретный объект какого-то типа (в них НЕ заполнен тип (TargetType), но заполнена ячейка (TargetCell))
            if (Task.TargetCell!=null && Task.TargetCell.X==Cell.X && Task.TargetCell.Y==Cell.Y && !Task.finished)
                return Task;
        }
        return null;
    }

    public void twistCar(){
        //Поворачиваем машину в нужном направлении
        MyMap.MapCell NextCell = Map.Rote.get(0);
        if (NextCell.Y*Map.Scale< currentY && trend !=2){
            trend =2;
            car.setImageDrawable(getDrawable(R.drawable.caranimationup));
            isAnimation = (AnimationDrawable)car.getDrawable();
            isAnimation.start();
        }

        if (NextCell.X*Map.Scale< currentX && trend !=1){
            trend =1;
            car.setImageDrawable(getDrawable(R.drawable.caranimationleft));
            isAnimation = (AnimationDrawable)car.getDrawable();
            isAnimation.start();
        }

        if (NextCell.X*Map.Scale> currentX && trend !=0){
            trend =0;
            car.setImageDrawable(getDrawable(R.drawable.caranimation));
            isAnimation = (AnimationDrawable)car.getDrawable();
            isAnimation.start();
        }
        if (NextCell.Y*Map.Scale> currentY && trend !=3) {
            trend =3;
            car.setImageDrawable(getDrawable(R.drawable.caranimationdown));
            isAnimation = (AnimationDrawable)car.getDrawable();
            isAnimation.start();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //String resultData = data.getStringExtra("key"); // Получаем данные

            Intent i = new Intent(this, DialogActivity.class);
            i.putExtra("videoPath", "android.resource://" + getPackageName() + "/" + R.raw.complete);
            //i.putExtra("CityName", "Sokulka");
            startActivityForResult(i,0);

            finish();
        }
    }
    class MoveCar extends TimerTask {
        ////////////int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    if (Constants.DATAGAME.getFuel() <=0) {
                        if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}
                        rrrMediaPlayer.pause();
                        return;
                    }
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) car.getLayoutParams();
                    //ConstraintLayout.LayoutParams newparams = new Constraints.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                    int stepPix = (int)(Map.Scale*3/Constants.DATAGAME.SCALE);
                    int metr = 6;//(int)(1600/Map.Scale);
                    //Получили координаты машинки, теперь сравниваем их с координатами следующей ячейки и решаем, что делать дальше
                    if (params.topMargin!= currentY || params.leftMargin!= currentX){
                        //Не доехали до следующей ячейки продолжаем двигать
                        int newX=params.leftMargin;
                        int newY=params.topMargin;
                        if (newX< currentX) newX = Math.min(newX+stepPix, currentX);
                        else if (newX> currentX) newX =Math.max(newX-stepPix, currentX);
                        if (newY< currentY) newY=Math.min(newY+stepPix, currentY);
                        else if (newY> currentY) newY=Math.max(newY-stepPix, currentY);

                        params.setMargins(newX,newY,0,0);
                        car.setLayoutParams(params);
                        Constants.DATAGAME.setFuel(Constants.DATAGAME.getFuel()  -metr +(int)((100-Constants.DATAGAME.getTire())/100*metr));
                        updateBar();
                        scrollToCar(newX,newY);
                        carX=newX;
                        carY=newY;
                        if (Constants.DATAGAME.getFuel() <5000){
                            if (!fuelDanger) {
                                DialogMessage.showMessage(R.drawable.icon_fuel, R.drawable.icon_fuel, "ВНИМАНИЕ! Осталось мало топлива! Пора ехать на заправку", "Остаток: " + String.valueOf(Constants.DATAGAME.getFuel()  / 1000), MapActivity.this);
                                if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}
                                rrrMediaPlayer.pause();
                            }
                            fuelDanger=true;
                        }
                        else fuelDanger = false;
                        return;
                    }

                    //Проехали очередной шаг. Решаем дальше
                    boolean isEnd=false;
                    if (Map.Rote.size()==0)
                        isEnd=true;
                    else {
                        MyMap.MapCell NextCell = Map.Rote.get(0);
                        if (NextCell.X==navX && NextCell.Y==navY)
                            isEnd=true;
                    }
                    if (isEnd){
                        //Если больше шагов не осталось, То цель достугнута.
                        //ОСТАНАВЛИВАЕМСЯ:
                        //Останавливаем движение
                        if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}
                        rrrMediaPlayer.pause();
                        nav.setVisibility(View.INVISIBLE);

                        //Активизируем объект, на который попали
                        MyMap.MapCell CurrentCell = Map.Cells[navX][navY]; //]Map.FindCellByXY(currentX, currentY);
                        if (CurrentCell.object!=null) CurrentCell.object.RunAction();

                        //Если есть задания к этой ячейке, запусаем их завершение
                        Tasks Task = ChekTusk(Map.Cells[navX][navY]); //Map.FindCellByXY(currentX, currentY));
                        if (Task!=null) Task.FinishTask();

                        //zoomMapToSCALE(Constants.DATAGAME.SCALE*2);
                        scrollToCar(currentX,currentY);
                        return;
                    }

                    //Ещё остались шаги в маршруте. едем дальше.
                    twistCar();

                    //Фиксируем координаты следующей ячейки
                    MyMap.MapCell NextCell = Map.Rote.get(0);
                    currentX = NextCell.X*Map.Scale;
                    currentY = NextCell.Y*Map.Scale;

                    //и удаляем текуший шаг (который начали ехать)
                    Map.Rote.remove(0);

                }
            });
        }
    }

}