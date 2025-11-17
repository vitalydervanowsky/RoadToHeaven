package com.carloclub.roadtoheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
    public MediaPlayer correctMediaPlayer;

    int clickX;
    int clickY;

    int navX=0;
    int navY=0;
    MoveCar mMoveCar;
    MoveWalpaper moveWalpaper;
    private AnimationDrawable isAnimation;
    public MyMap map;// = new MyMap(28, 21);
    Timer timer;
    int currentX = 1;
    int currentY = 1;

    public int carX=0;
    public int carY=0;

    public int stones=0;
    int lastY =0;   //служебный. Для параллельной прокрутки по вертикали
    int lastX =0;   //служебный. Для параллельной прокрутки по горизонтали
    int trend =3;   //0 право, 1 лево, 2 вверх, 3 вниз
    public ArrayList<Task> myTasks;
    private MediaPlayer rrrMediaPlayer;
    ScrollView sv;
    HorizontalScrollView sh;
    boolean fuelDanger= false;
    public City city;

    public float displayDensity=0;

    ScaleGestureDetector scaleDetector;

    boolean isEvacuation=false;
    FuelView fuelView;


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


        myTasks =new ArrayList <Task>();
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.CITY_ARG)) {
            city = (City) getIntent().getSerializableExtra(Constants.CITY_ARG);
            if (city == null) {
                city = City.SOKULKA;
            }
            map = MyMap.getMap(city);
        }
        displayDensity = getApplicationContext().getResources().getDisplayMetrics().density;
        map.scale = (int) (Constants.DATAGAME.SCALE*displayDensity); //
        map.createObjects(MapActivity.this);

        ImageView imageBack = findViewById(R.id.imageView);
        //ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imageBack.getLayoutParams();
        imageBack.setImageDrawable(getDrawable(map.mBackgroundId));
        //int realScale = (int)params.height *Constants.DATAGAME.SCALE/ 21  /48; //в образце 21 вертикальная клетка
        imageBack.setLayoutParams(new ConstraintLayout.LayoutParams(map.mLength * map.scale, map.mHeight * map.scale));

        scaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (mMoveCar!=null) return true;
                float factor = detector.getScaleFactor();

                if (factor > 1.0f && map.scale <700) {
                    //MyMap.MapCell CellInCenter = whyCellInCenter();
                    zoomMapToSCALE((int)(map.scale *1.03));
                    //scrollToCell(CellInCenter);
                } else if (factor < 1.0f && map.scale >72) {
                    //MyMap.MapCell CellInCenter = whyCellInCenter();
                    zoomMapToSCALE((int)(map.scale /1.03));
                    //scrollToCell(CellInCenter);
                }
                return true;
            }
        });


        timer = new Timer();
        car = findViewById(R.id.car);
        nav = findViewById(R.id.nav);
        correctMediaPlayer = MediaPlayer.create(this, R.raw.ok);
        textFuel = findViewById(R.id.textFuel);
        textMoney = findViewById(R.id.textMoney);
        textStones = findViewById(R.id.textStones);
        textRubi = findViewById(R.id.textRubi);

        fuelView = findViewById(R.id.fuelView);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        fuelView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fuel , options));
        fuelView.invalidate();


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
                    //zoomMapToSCALE(map.scale+5);
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
                    //zoomMapToSCALE(map.scale+5);
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

        findViewById(R.id.imageViewtruck).setOnClickListener(v -> {
            startEvacuation();
        });

        //включаем анимацию текущего положения машины (картинки)
        isAnimation = (AnimationDrawable)car.getDrawable();
        isAnimation.start();



        //DialogMessage.showMessage(R.drawable.stones, R.drawable.icon_bridge, "Собери 7 камней знаний на каменных рудниках, чтобы построить разрушенный мост и выехать из города", "Удачи!" , MapActivity.this);

        showRubies();

        zoomMapToSCALE(72);
        findViewById(R.id.cloudView).setVisibility(View.VISIBLE);
        moveWalpaper = new MoveWalpaper();
        timer.schedule(moveWalpaper, 700, 20);
    }



    public void startEvacuation(){
        MyMap.MapCell CurrentCell = map.findCellByXY(currentX, currentY);

        ArrayList<MyMap.MapCell> Rote = new ArrayList<MyMap.MapCell>();
        for (int x = 0; x < map.mLength; x++) {
            for (int y = 0; y < map.mHeight; y++) {
                if (map.mMapCells[x][y].type.equals("fuel")){
                    ArrayList<MyMap.MapCell> currentRote=map.buildRoute(CurrentCell,map.mMapCells[x][y]);
                    if (Rote==null) continue;
                    if (Rote.size()==0 || currentRote.size()<Rote.size()){
                        Rote=currentRote;
                    }
                }
            }
        }
        if (Rote.size()==0) return;
        isEvacuation=true;
        map.mRoute = Rote;
//        navX=NewCell.x;
//        navY=NewCell.y;
        if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}
        mMoveCar = new MoveCar();
        trend=-1;
        twistCar();
        timer.schedule(mMoveCar, 20, 20-(Constants.DATAGAME.getSpeed()-60)/10);
        //rrrMediaPlayer.reset();
        rrrMediaPlayer.start();

    }

    public void showRubies(){
        ArrayList<MyMap.MapCell> CR = map.getCellsRubies();
        showAnimateViev(findViewById(R.id.ruby1), CR, 1);
        showAnimateViev(findViewById(R.id.ruby2), CR, 2);
        showAnimateViev(findViewById(R.id.ruby3), CR, 3);
        showAnimateViev(findViewById(R.id.ruby4), CR, 4);


        CR = map.getCellsStones();
        showAnimateViev(findViewById(R.id.stones1), CR, 1);
        showAnimateViev(findViewById(R.id.stones2), CR, 2);
        showAnimateViev(findViewById(R.id.stones3), CR, 3);
        showAnimateViev(findViewById(R.id.stones4), CR, 4);
        showAnimateViev(findViewById(R.id.stones5), CR, 5);
        showAnimateViev(findViewById(R.id.stones6), CR, 6);
        showAnimateViev(findViewById(R.id.stones7), CR, 7);
    }

    private void showAnimateViev(ImageView IV, ArrayList<MyMap.MapCell> CR, int numCell){
        if (CR.size()>=numCell){
            IV.setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) IV.getLayoutParams();
            int x = CR.get(numCell-1).x * map.scale;
            int y = CR.get(numCell-1).y * map.scale;
            params.setMargins(x,y+ map.scale /2,0,0);
            params.width = (int)(map.scale *0.6);
            params.height = params.width;
            IV.setLayoutParams(params);
            AnimationDrawable  IVAnimation = (AnimationDrawable)IV.getDrawable();
            IVAnimation.start();
        }
        else IV.setVisibility(View.INVISIBLE);


    }

    private void moveImageView(int positionY, int positionX) {
        if  (isEvacuation) return;
        //Клик по карте.
        //проверяем, можно ли  в эту ячейку поехать.
        MyMap.MapCell CurrentCell = map.findCellByXY(currentX, currentY);
        MyMap.MapCell NewCell = map.findCellByXY(positionX, positionY);
        if (NewCell.object==null && !NewCell.type.equals("Road"))
            return;

        //Строим маршрут и запускаем движение машины
        ArrayList<MyMap.MapCell> Rote = map.buildRoute(CurrentCell,NewCell);
        if (Rote==null) return;
        map.mRoute = Rote;

        if (NewCell.x ==navX && NewCell.y ==navY ){

        }
        else {
            startNextTask();

            //Устанавливаем навигационную ссылку
            if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}

            navX=NewCell.x;
            navY=NewCell.y;
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) nav.getLayoutParams();
            int startX = (navX)* map.scale;
            int startY = (navY)* map.scale;
            params.setMargins(startX,startY,0,0);
            nav.setLayoutParams(params);
            nav.setVisibility(View.VISIBLE);
            correctMediaPlayer.start();

        }

        //сразу начинаем ехать
        if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}
        mMoveCar = new MoveCar();
        twistCar();
        timer.schedule(mMoveCar, 20, 27-(Constants.DATAGAME.getSpeed()-60)/10);
        //rrrMediaPlayer.reset();
        rrrMediaPlayer.start();
        //zoomMapToSCALE(Constants.DATAGAME.SCALE*5);




    }

    private void zoomMapToSCALE(int newSCALE){
        //Постараемся картінку, которая в центре, оставіть в центре
        int scrollY = sv.getScrollY();
        int scrollX = sh.getScrollX();
        int centerX = scrollX+(int)(getApplicationContext().getResources().getDisplayMetrics().widthPixels/2);
        int centerY = scrollY+(int)(getApplicationContext().getResources().getDisplayMetrics().heightPixels/2);

        int newCenterX = (int)(centerX*newSCALE/ map.scale);
        int newCenterY = (int)(centerY*newSCALE/ map.scale);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) car.getLayoutParams();
        MyMap.MapCell CurrentCell = map.findCellByXY(currentX, currentY);

        map.scale = newSCALE;
        ImageView imageBack = findViewById(R.id.imageView);
        imageBack.setLayoutParams(new ConstraintLayout.LayoutParams(map.mLength * map.scale, map.mHeight * map.scale));

        currentX=CurrentCell.x * map.scale;
        currentY=CurrentCell.y * map.scale;

        params.setMargins(currentX,currentY,0,0);
        params.width = (int)(map.scale *0.9);
        params.height = params.width;
        car.setLayoutParams(params);

        params = (ConstraintLayout.LayoutParams) nav.getLayoutParams();
        int startX = (navX)* map.scale;
        int startY = (navY)* map.scale;
        params.setMargins(startX,startY,0,0);
        params.width = map.scale;
        params.height = map.scale;
        nav.setLayoutParams(params);

        carX=currentX;
        carY=currentY;

        showRubies();

        int newScrollY = Math.max(0,newCenterY-(int)(getApplicationContext().getResources().getDisplayMetrics().heightPixels/2));
        int newScrollX = Math.max(0,newCenterX-(int)(getApplicationContext().getResources().getDisplayMetrics().widthPixels/2));

        sv.scrollTo(newScrollX, newScrollY);
        sh.scrollTo(newScrollX, newScrollY);


        //подгоним размер машинки
        //car.setLayoutParams(new ConstraintLayout.LayoutParams(map.mLength*map.scale, map.mHeight*map.scale));
    }

    public void startNextTask() {
        //Если есть незавершенные, то новый не запускаем
        for (int i = 1; i <= myTasks.size(); i++) {
            Task task = myTasks.get(i - 1);
            if (task.isStarted && !task.isFinished) return;
        }

        //Если нет незвавершенных, то запускаем очередное неначатое
        for (int i = 1; i <= myTasks.size(); i++) {
            Task task = myTasks.get(i - 1);
            if (!task.isStarted) {
                task.startTask();
                return;
            }
        }
    }

    private MyMap.MapCell whyCellInCenter(){
        int scrollY = sv.getScrollY()+400;
        int scrollX = sh.getScrollX()+700;

        return map.findCellByXY(scrollX, scrollY);
    }

    private void scrollToCell(MyMap.MapCell cell){
        //метод "ведёт" экран так, чтобы фокусная ячейка оставалась прімерно в центре: в координате: 400х; 200у

        int scrollY = sv.getScrollY();
        int scrollX = sh.getScrollX();
        boolean isMove=false;

        int x = (cell.x)* map.scale;
        int y = (cell.y)* map.scale;

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
        else if (CurrentX<(scrollX+60) && scrollX>0) {
            scrollX = CurrentX-60;
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
        fuelView.invalidate();
    }

    public void twistCar(){
        //Поворачиваем машину в нужном направлении
        MyMap.MapCell NextCell = map.mRoute.get(0);
        if (NextCell.y * map.scale < currentY && trend !=2){
            trend =2;
            if (isEvacuation)
                car.setImageDrawable(getDrawable(R.drawable.truckanimationup));
            else
                car.setImageDrawable(getDrawable(R.drawable.caranimationup));
            isAnimation = (AnimationDrawable)car.getDrawable();
            isAnimation.start();
        }

        if (NextCell.x * map.scale < currentX && trend !=1){
            trend =1;
            if (isEvacuation)
                car.setImageDrawable(getDrawable(R.drawable.truckanimationleft));
            else
                car.setImageDrawable(getDrawable(R.drawable.caranimationleft));

            isAnimation = (AnimationDrawable)car.getDrawable();
            isAnimation.start();
        }

        if (NextCell.x * map.scale > currentX && trend !=0){
            trend =0;
            if (isEvacuation)
                car.setImageDrawable(getDrawable(R.drawable.truckanimation));
            else
                car.setImageDrawable(getDrawable(R.drawable.caranimation));

            isAnimation = (AnimationDrawable)car.getDrawable();
            isAnimation.start();
        }
        if (NextCell.y * map.scale > currentY && trend !=3) {
            trend =3;
            if (isEvacuation)
                car.setImageDrawable(getDrawable(R.drawable.truckanimationdown));
            else
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
            //i.putExtra("CityName", "Sokolka");
            startActivityForResult(i, 0);

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
                    if (Constants.DATAGAME.getFuel() <=0 && !isEvacuation) {
                        if (mMoveCar!=null) {mMoveCar.cancel(); mMoveCar=null;}
                        rrrMediaPlayer.pause();
                        return;
                    }
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) car.getLayoutParams();
                    //ConstraintLayout.LayoutParams newparams = new Constraints.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                    int stepPix = (int)(map.scale *6/Constants.DATAGAME.SCALE);
                    int metr = 10;//(int)(1600/map.scale);
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
                        if (!isEvacuation)
                            Constants.DATAGAME.setFuel(Constants.DATAGAME.getFuel()  -metr +(int)((100-Constants.DATAGAME.getTire())/100*metr));
                        updateBar();
                        scrollToCar(newX,newY);
                        carX=newX;
                        carY=newY;
                        if (Constants.DATAGAME.getFuel() <5000 && !isEvacuation){
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
                    if (map.mRoute.size()==0)
                        isEnd=true;
                    else {
                        MyMap.MapCell NextCell = map.mRoute.get(0);
                        if (NextCell.x ==navX && NextCell.y ==navY && ! NextCell.type.equals("Road"))
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
                        MyMap.MapCell currentCell = map.mMapCells[navX][navY]; //]Map.FindCellByXY(currentX, currentY);
                        if (currentCell.object != null) {
                            currentCell.object.runAction();
                        }

                        //Если есть задания к этой ячейке, запусаем их завершение
                        Task Task = com.carloclub.roadtoheaven.Task.checkTasks(MapActivity.this, map.mMapCells[navX][navY]); //map.findCellByXY(currentX, currentY));
                        //if (task!=null) task.finishTask();
                        //task.startNextTask(MapActivity.this);

                        //zoomMapToSCALE(Constants.DATAGAME.SCALE*2);
                        scrollToCar(currentX,currentY);
                        if (isEvacuation){
                            isEvacuation=false;
                            trend=-1;
                            car.setImageDrawable(getDrawable(R.drawable.caranimationup));
                            isAnimation = (AnimationDrawable)car.getDrawable();
                            isAnimation.start();
                        }
                        return;
                    }

                    //Ещё остались шаги в маршруте. едем дальше.
                    twistCar();

                    //Фиксируем координаты следующей ячейки
                    MyMap.MapCell NextCell = map.mRoute.get(0);
                    currentX = NextCell.x * map.scale;
                    currentY = NextCell.y * map.scale;

                    //и удаляем текуший шаг (который начали ехать)
                    map.mRoute.remove(0);
                }
            });
        }
    }

    class MoveWalpaper extends TimerTask {
        ////////////int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    if (moveWalpaper==null)
                        return;
                    ImageView cloud = findViewById(R.id.cloudView);
                    if (map.scale >=Constants.DATAGAME.SCALE*displayDensity) {
                        moveWalpaper.cancel();
                        moveWalpaper = null;

                        startNextTask();
                        return;

                    }


                    int alfaCloud = (int)(cloud.getAlpha()*100);
                    if (alfaCloud>0){
                        cloud.setAlpha((float)(alfaCloud-2)/100);
                        zoomMapToSCALE(map.scale +1);
                        sv.scrollTo(0, 0);
                        sh.scrollTo(0, 0);
                    }
                    else {
                        cloud.setVisibility(View.INVISIBLE);
                        zoomMapToSCALE(map.scale +1);
                        sv.scrollTo(0, 0);
                        sh.scrollTo(0, 0);
                    }
                    //nav.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}