package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

import java.util.Timer;
import java.util.TimerTask;

public class MapObjectZOO extends MapObject {
    public Dialog dialog2;
    public Dialog dialog3;
    public com.carloclub.roadtoheaven.Task Task2;

    ImageView cat;
    Timer timer;
    MoveCat mMoveCat;
    int Trend=0; // 1 лево, 2 вверх, 3 право, 4 низ, 0 стоим на месте

    int objectX=0;
    int objectY=0;

    private TextView TextFuel;
    public MapObjectZOO(int X, int Y, MapActivity MainActivity){
        super(X, Y, MainActivity);
        objectX = X;
        objectY = Y;
        type = "ZOO";
        timer = new Timer();
        task = new Task((MapObject)this);
        task.bonus = 0;
        task.text = Messages.getMessageCatchPinkCat();
        Task2 = new Task((MapObject)this);
        Task2.bonus = 1000;
        Task2.text = Messages.getMessageCarryCatInZoo();
        Task2.targetCell = MainActivity.map.mMapCells[X][Y]; //Во втором задании сразу известна ячейка: нужо вернуть кота назад сюда

        cat = MainActivity.findViewById(R.id.cat);

        dialog.setContentView(R.layout.dialog_zoo);
        dialog2 = new Dialog(MainActivity);
        dialog2.setContentView(R.layout.dialog_zoo2);
        Button buttonStart = dialog.findViewById(R.id.starthelp);
        buttonStart.setOnClickListener(v -> StartFill());
        Button buttonClose= dialog.findViewById(R.id.close);
        buttonClose.setOnClickListener(v -> EndFill());

        Button buttonClose2= dialog2.findViewById(R.id.OK);
        buttonClose2.setOnClickListener(v -> NoListen());

        dialog3 = new Dialog(MainActivity);
        dialog3.setContentView(R.layout.dialog_message);
        Button buttonClose3= dialog3.findViewById(R.id.OK);
        buttonClose3.setOnClickListener(v -> OK());
    }

    public void StartFill(){
        dialog.hide();
        mapActivity.myTasks.add(task);
        task.startTask();
        Task2.isStarted =false;
        //dialog2.show();
        //Запускаем бегать кота
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) cat.getLayoutParams();
        int startX = (x)*mapActivity.map.scale;
        int startY = (y +1)*mapActivity.map.scale;
        params.setMargins(startX,startY,0,0);
        cat.setLayoutParams(params);
        cat.setVisibility(View.VISIBLE);

        if (mMoveCat!=null) {mMoveCat.cancel(); mMoveCat=null;}
        mMoveCat = new MoveCat();
        timer.schedule(mMoveCat, 20, 16);
    }

    public void EndFill(){
        dialog.hide();
    }

    public void NoListen(){
        dialog2.hide();
    }

    public void OK(){
        dialog3.hide();
    }
    @Override
    public void runAction(){
        if (Task2.isStarted)
            return;
        if (task.isStarted)
            return;

        dialog.show();

    }

    @Override
    public void finishTask(){
        if (Task2.isStarted &&!Task2.isFinished) {
            cat.setVisibility(View.INVISIBLE);
            Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() + Task2.bonus);
            //dialog3.show();
            Task2.isFinished = true;
            mapActivity.updateBar();

            DialogMessage.showMessage(R.drawable.dialog_back_zoo,R.drawable.icon_money,Messages.getMessageThanksGoodManGetPrise(),"+1 000",mapActivity);
        } else {
            Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() + task.bonus);
            dialog2.show();
            task.isFinished = true;
            mapActivity.myTasks.add(Task2);
            Task2.startTask();
            mapActivity.updateBar();

            //останавливаем и убираем с формы кота
            if (mMoveCat!=null) {mMoveCat.cancel(); mMoveCat=null;}
            cat.setVisibility(View.INVISIBLE);
        }
    }

    public String[] split(String txt, String sym){ //txt="Carlo Club"  sym=" "
        String[] res = new String[100];
        int count=0; int start=0;
        for (int i =0; i<txt.length();i=i+1){
            String s=txt.substring(i,i+1);
            if (s.equals(sym)){   //s==sym
                if (start<i) {
                    res[count] = txt.substring(start, i );
                    count++;
                }

                start=i+1;
            }
        }

        if (start<txt.length()) {
            res[count] = txt.substring(start, txt.length() );
            count++;
        }

        String[] res2 = new String[count];
        for (int i=0; i<count; i++) {
            res2[i]=res[i];
        }

        int Res=1;
        int N=6;
        for (int i=1; i<=N; i++) {
            Res=Res*i;
        }

        return res2;
    }

    //i=6
    //s=" "
    //count=1
    //start=6

    public void onClick1(){
        EditText text = new EditText(mapActivity);

        String input = text.getText().toString();
        String[] numbers = input.split(" ");//input.split(" ");
        String result = "";
        for (int i = numbers.length - 1; i >= 0; i--) {
            result = result + numbers[i] + " ";
        }

        text.setText(result);

    }


    class MoveCat extends TimerTask {

        @Override
        public void run() {
            mapActivity.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) cat.getLayoutParams();
                    ConstraintLayout.LayoutParams newparams = new Constraints.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    MyMap.MapCell CurrentCell = mapActivity.map.findCellByXY(params.leftMargin, params.topMargin);
                    //Координаты верхней левой точки текущей ячейки на карте
                    int startX = (CurrentCell.x)* mapActivity.map.scale;
                    int startY = (CurrentCell.y)* mapActivity.map.scale;
                    MyMap.MapCell CarCell = mapActivity.map.findCellByXY(mapActivity.carX, mapActivity.carY);
                    int danger =0; //опасность: 1-слева; 2-сверху; 3-справа; 4-снизу
                    int toLeft =0; //перспектива налево: 0-нет дороги; 1-впереди тупик; 10-впереди машина; 2-впереди повороты
                    int toUp =0;   //перспектива вверх: 0-нет дороги; 1-впереди тупик; 10-впереди машина; 2-впереди повороты
                    int toRight=0;   //перспектива вправо: 0-нет дороги; 1-впереди тупик; 10-впереди машина; 2-впереди повороты
                    int toDown =0;   //перспектива вниз: 0-нет дороги; 1-впереди тупик; 10-впереди машина; 2-впереди повороты

                    //сканируем дорогу вправо           x и y - это индексы
                    for (int x = CurrentCell.x +1; x< mapActivity.map.mLength; x++){
                        if (!mapActivity.map.mMapCells[x][CurrentCell.y].type.equals("Road"))
                            //это не дорога. поіск останвліваем
                            break;

                        if (toRight==0) toRight=1; //как минимум в тупик, но дорога куда-то едет
                        if (CurrentCell.y <(mapActivity.map.mHeight -1))
                            if (mapActivity.map.mMapCells[x][CurrentCell.y +1].type.equals("Road"))
                                toRight=2; //с этой дороги можно будет свернуть (вниз)
                        if (CurrentCell.y >0)
                            if (mapActivity.map.mMapCells[x][CurrentCell.y -1].type.equals("Road"))
                                toRight=2; //с этой дороги можно будет свернуть (вверх)

                        if (CarCell.x ==(x) && CarCell.y ==CurrentCell.y){
                            //в этой клетке сейчас машина.
                            danger=3;
                            //toRight=toRight*10;
                            break;
                        }
                    }

                    //сканируем дорогу влево
                    for (int x = CurrentCell.x -1; x>=0; x--){
                        if (!mapActivity.map.mMapCells[x][CurrentCell.y].type.equals("Road"))
                            //это не дорога. поіск останвліваем
                            break;

                        if (toLeft==0) toLeft=1; //как минимум в тупик, но дорога куда-то едет
                        if (CurrentCell.y <(mapActivity.map.mHeight -1))
                            if (mapActivity.map.mMapCells[x][CurrentCell.y +1].type.equals("Road"))
                                toLeft=2; //с этой дороги можно будет свернуть (вниз)
                        if (CurrentCell.y >0)
                            if (mapActivity.map.mMapCells[x][CurrentCell.y -1].type.equals("Road"))
                                toLeft=2; //с этой дороги можно будет свернуть (вверх)

                        if (CarCell.x ==(x) && CarCell.y ==CurrentCell.y){
                            //в этой клетке сейчас машина.
                            danger=1;
                            //toLeft=toLeft*10;
                            break;
                        }
                    }

                    //сканируем дорогу вниз
                    for (int y = CurrentCell.y +1; y< mapActivity.map.mHeight; y++){
                        if (!mapActivity.map.mMapCells[CurrentCell.x][y].type.equals("Road"))
                            //это не дорога. поіск останвліваем
                            break;

                        if (toDown==0) toDown=1; //как минимум в тупик, но дорога куда-то едет
                        if (CurrentCell.x <(mapActivity.map.mLength -1))
                            if (mapActivity.map.mMapCells[CurrentCell.x +1][y].type.equals("Road"))
                                toDown=2; //с этой дороги можно будет свернуть (вправо)
                        if (CurrentCell.x >0)
                            if (mapActivity.map.mMapCells[CurrentCell.x -1][y].type.equals("Road"))
                                toDown=2; //с этой дороги можно будет свернуть (влево)

                        if (CarCell.x ==(CurrentCell.x) && CarCell.y ==y){
                            //в этой клетке сейчас машина.
                            danger=4;
                            //toUp=toUp*10;
                            break;
                        }
                    }

                    //сканируем дорогу вверх  toUp
                    for (int y = CurrentCell.y -1; y>=0; y--){
                        if (!mapActivity.map.mMapCells[CurrentCell.x][y].type.equals("Road"))
                            //это не дорога. поіск останвліваем
                            break;

                        if (toUp==0) toUp=1; //как минимум в тупик, но дорога куда-то едет
                        if (CurrentCell.x <(mapActivity.map.mLength -1))
                            if (mapActivity.map.mMapCells[CurrentCell.x +1][y].type.equals("Road"))
                                toUp=2; //с этой дороги можно будет свернуть (вправо)
                        if (CurrentCell.x >0)
                            if (mapActivity.map.mMapCells[CurrentCell.x -1][y].type.equals("Road"))
                                toUp=2; //с этой дороги можно будет свернуть (влево)

                        if (CarCell.x ==(CurrentCell.x) && CarCell.y ==y){
                            //в этой клетке сейчас машина.
                            danger=2;
                            //toDown=toDown*10;
                            break;
                        }
                    }

                    //дальше принимаем решение
                    int newTrend=Trend; //

                    if (danger==0){
                        //можно ли продолжать в том же направлении
                        if (Trend>0){

                            if (params.leftMargin>(startX+10) || params.leftMargin<(startX-10)|| params.topMargin<(startY-10)|| params.topMargin>(startY+10))
                                newTrend=newTrend; //если котик между клетками, то пусть движется пока дальше
                            else if (newTrend==1 && toLeft==0) newTrend=0; //нужно менять направление
                            else if (newTrend==2 && toUp==0) newTrend=0; //нужно менять направление
                            else if (newTrend==3 && toRight==0) newTrend=0; //нужно менять направление
                            else if (newTrend==4 && toDown==0) newTrend=0; //нужно менять направление
                        }
                    }
                    else {
                        if (params.leftMargin>(startX+10) || params.leftMargin<(startX-10)|| params.topMargin<(startY-10)|| params.topMargin>(startY+10)){
                            //если котик между клетками, то нужно продолжать двигаться в этом либо противоположном направлении
                            //пусть добежит до начала клетки, а потом думает, куда дальше
                            if (Trend==danger){
                                newTrend=Trend+2;
                                if (newTrend>4)newTrend=newTrend-4;
                            }

                        }
                        else newTrend=0;
                    }

                    if (newTrend==0){
                        //выбираем новое направление
                        if (danger==1||danger==3){
                            //опасность по оси Х. Лучше повернуть вверх или вниз, но не в тупик
                            if (toUp>1) newTrend=2;
                            else if (toDown>1) newTrend=4;
                            else if (danger==1&&toRight>1) newTrend=3; //лучше в противоположном направлении, чем в тупик
                            else if (danger==3&&toLeft>1) newTrend=1; //лучше в противоположном направлении, чем в тупик
                            else if (toUp>0) newTrend=2;
                            else if (toDown>0) newTrend=4;
                            else if (danger==1&&toRight>0) newTrend=3; //лучше в противоположном направлении, чем под машину
                            else if (danger==3&&toLeft>0) newTrend=1; //лучше в противоположном направлении, чем под машину
                        }
                        else if (danger==2||danger==4){
                            //опасность по оси y. Лучше повернуть влево или вправо, но не в тупик
                            if (toLeft>1) newTrend=1;
                            else if (toRight>1) newTrend=3;
                            else if (danger==2&&toDown>1) newTrend=4; //лучше в противоположном направлении, чем в тупик
                            else if (danger==4&&toUp>1) newTrend=2; //лучше в противоположном направлении, чем в тупик
                            else if (toLeft>0) newTrend=1;
                            else if (toRight>0) newTrend=3;
                            else if (danger==2&&toDown>0) newTrend=4; //лучше в противоположном направлении, чем под машину
                            else if (danger==4&&toUp>0) newTrend=2; //лучше в противоположном направлении, чем под машину
                        }
                        else {
                            if(Trend==3 && toDown>1)newTrend=4;
                            if(Trend==1 && toUp>1)newTrend=2;
                            else if (toLeft>1) newTrend=1;
                            else if (toUp>1) newTrend=2;
                            else if (toRight>1) newTrend=3;
                            else if (toDown>1) newTrend=4;
                            else if (toLeft>0) newTrend=1;
                            else if (toUp>0) newTrend=2;
                            else if (toRight>0) newTrend=3;
                            else if (toDown>0) newTrend=4;
                        }
                    }

                    //Бежим в направлении
                    int newX=params.leftMargin;
                    int newY=params.topMargin;
                    int stepPix = (int)(mapActivity.map.scale *2.5/ Constants.DATAGAME.SCALE);

                    if (newTrend==1) newX=newX-stepPix;
                    else if (newTrend==2) newY=newY-stepPix;
                    else if (newTrend==3) newX=newX+stepPix;
                    else if (newTrend==4) newY=newY+stepPix;
                    Trend=newTrend;

                    task.targetCell = mapActivity.map.findCellByXY(newX, newY);
                    params.setMargins(newX,newY,0,0);
                    cat.setLayoutParams(params);
                    if (task == task.checkTasks(mapActivity,CarCell))
                        task.finishTask();

                }
            });
        }
    }

}
