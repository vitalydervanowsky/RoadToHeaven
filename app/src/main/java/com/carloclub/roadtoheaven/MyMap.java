package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.MapObjects.MapObject;
import com.carloclub.roadtoheaven.MapObjects.MapObjectBooks;
import com.carloclub.roadtoheaven.MapObjects.MapObjectBridge;
import com.carloclub.roadtoheaven.MapObjects.MapObjectBurgerJoint;
import com.carloclub.roadtoheaven.MapObjects.MapObjectChurch;
import com.carloclub.roadtoheaven.MapObjects.MapObjectFuel;
import com.carloclub.roadtoheaven.MapObjects.MapObjectGallery;
import com.carloclub.roadtoheaven.MapObjects.MapObjectHelpBoy;
import com.carloclub.roadtoheaven.MapObjects.MapObjectHunger;
import com.carloclub.roadtoheaven.MapObjects.MapObjectPilgrim;
import com.carloclub.roadtoheaven.MapObjects.MapObjectSTO;
import com.carloclub.roadtoheaven.MapObjects.MapObjectSchool;
import com.carloclub.roadtoheaven.MapObjects.MapObjectStones;
import com.carloclub.roadtoheaven.MapObjects.MapObjectTetris;
import com.carloclub.roadtoheaven.MapObjects.MapObjectZOO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MyMap {
    public int scale = Constants.DATAGAME.SCALE;
    ArrayList<MapCell> mRoute;
    private ArrayList<MapCell> mBuildRoute;
    public MapCell[][] mMapCells;
    public Stone[] mStones;
    public Question[] mQuestions;

    public Mission[] Missions;
    public Words[] wordsForBook;
    public Words[] wordsForRM;
    public int mLength;
    public int mHeight;

    int mBackgroundId;


    public MyMap(int length, int height, int backgroundId) {
        mLength = length;
        mHeight = height;
        mBackgroundId = backgroundId;

        mMapCells = new MapCell[length][height];
        for (int x = 0; x < this.mLength; x++) {
            for (int y = 0; y < this.mHeight; y++) {
                mMapCells[x][y] = new MapCell(x, y);
            }
        }
    }

    public MapCell findCellByXY(int X, int Y) {
        return mMapCells[(int) (X / scale)][(int) (Y / scale)];
    }
    public ArrayList<MapCell> getCellsRubies() {
        ArrayList<MapCell> CR = new ArrayList<MapCell>();
        for (int x = 0; x < this.mLength; x++)
            for (int y = 0; y < this.mHeight; y++){
                if (mMapCells[x][y].type.equals("pilgrim")|| mMapCells[x][y].type.equals("hunger")|| mMapCells[x][y].type.equals("helpboy")){
                    if (mMapCells[x][y].object.isActual())
                        CR.add(mMapCells[x][y]);
                }

            }

        return CR;
    }
    public ArrayList<MapCell> getCellsStones() {
        ArrayList<MapCell> CR = new ArrayList<MapCell>();
        for (int x = 0; x < this.mLength; x++)
            for (int y = 0; y < this.mHeight; y++){
                if (mMapCells[x][y].type.equals("books")|| mMapCells[x][y].type.equals("stones")|| mMapCells[x][y].type.equals("RM")|| mMapCells[x][y].type.equals("tetris")){
                    if (mMapCells[x][y].object.isActual())
                        CR.add(mMapCells[x][y]);
                }

            }

        return CR;
    }
    public ArrayList<MapCell> buildRoute(MapCell fromCell, MapCell toCell) {
        mBuildRoute = new ArrayList<MapCell>();
        for (int x = 0; x < this.mLength; x++) {
            for (int y = 0; y < this.mHeight; y++) {
                mMapCells[x][y].used = false;
            }
        }
        //R.remove()
        fromCell.used = true;
        ArrayList<ArrayList> routes = new ArrayList();
        if (canMove(fromCell.x - 1, fromCell.y, toCell)) {
            ArrayList mapCells = new ArrayList<MapCell>();
            mMapCells[fromCell.x - 1][fromCell.y].used = true;
            mapCells.add(mMapCells[fromCell.x - 1][fromCell.y]);
            routes.add(mapCells);
        }
        if (canMove(fromCell.x + 1, fromCell.y, toCell)) {
            ArrayList mapCells = new ArrayList<MapCell>();
            mMapCells[fromCell.x + 1][fromCell.y].used = true;
            mapCells.add(mMapCells[fromCell.x + 1][fromCell.y]);
            routes.add(mapCells);
        }
        if (canMove(fromCell.x, fromCell.y - 1, toCell)) {
            ArrayList mapCells = new ArrayList<MapCell>();
            mMapCells[fromCell.x][fromCell.y - 1].used = true;
            mapCells.add(mMapCells[fromCell.x][fromCell.y - 1]);
            routes.add(mapCells);
        }
        if (canMove(fromCell.x, fromCell.y + 1, toCell)) {
            ArrayList mapCells = new ArrayList<MapCell>();
            mMapCells[fromCell.x][fromCell.y + 1].used = true;
            mapCells.add(mMapCells[fromCell.x][fromCell.y + 1]);
            routes.add(mapCells);
        }

        int step = 0;
        while (true) {
            if (routes.isEmpty()) return null; //не осталось вариантов
            int i = 0;
            step++;
            //Добавляем по 1 шагу во всех направлениях
            while (i < routes.size()) {
                ArrayList arrayList = routes.get(i);
                if (arrayList.size() > step) break; //это новые направленія
                MapCell currentCell = (MapCell) arrayList.get(arrayList.size() - 1);
                if (currentCell.x == toCell.x && currentCell.y == toCell.y) {
                    return arrayList;
                }
                ArrayList canCells = new ArrayList<MapCell>();

                if (canMove(currentCell.x - 1, currentCell.y, toCell)) {
                    canCells.add(mMapCells[currentCell.x - 1][currentCell.y]);
                }
                if (canMove(currentCell.x + 1, currentCell.y, toCell)) {
                    canCells.add(mMapCells[currentCell.x + 1][currentCell.y]);
                }
                if (canMove(currentCell.x, currentCell.y - 1, toCell)) {
                    canCells.add(mMapCells[currentCell.x][currentCell.y - 1]);
                }
                if (canMove(currentCell.x, currentCell.y + 1, toCell)) {
                    canCells.add(mMapCells[currentCell.x][currentCell.y + 1]);
                }
                if (canCells.size() == 0) {
                    //в данном направленіі больше нет варіантов. удаляем направленіе
                    routes.remove(i);
                    continue;
                }
                for (int n = 2; n <= canCells.size(); n++) {
                    //еслі нашло несколько варіантов, то для варіантов с 2 по 4 создадим новые направления, скопировав все шаги с текущего
                    ArrayList newRoute = new ArrayList<MapCell>();
                    for (int c = 1; c <= arrayList.size(); c++)
                        newRoute.add(arrayList.get(c - 1));
                    //и, конечно, добавляем новую ячейку
                    MapCell Cell = (MapCell) canCells.get(n - 1);
                    Cell.used = true;
                    newRoute.add(Cell);
                    routes.add(newRoute);
                }
                // а в текущее направление добавляем первую из найденных
                MapCell mapCell = (MapCell) canCells.get(0);
                mapCell.used = true;
                arrayList.add(mapCell);
                i++;
            }
        }

    }

    private boolean canMove(int x, int y, MapCell toCell) {
        if (x < 0 || y < 0 || x >= mLength || y >= mHeight)
            return false;
        MapCell newCell = mMapCells[x][y];
        if (newCell.used) return false;
        if (newCell.x == toCell.x && newCell.y == toCell.y) {
            return true;
        }
        if (newCell.type.equals("Road")) {
            return true;
        }
        return false;
    }

    private boolean TryGo(MapCell Cell, MapCell ToCell) {
        if (Cell.used) return false;
        Cell.used = true;
        mBuildRoute.add(Cell);
        if (Cell.x > 0) {
            //пробуем влево
            MapCell newCell = mMapCells[Cell.x - 1][Cell.y];
            if (newCell.x == ToCell.x && newCell.y == ToCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        if (Cell.y > 0) {
            //пробуем вверх
            MapCell newCell = mMapCells[Cell.x][Cell.y - 1];
            if (newCell.x == ToCell.x && newCell.y == ToCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        if (Cell.y < (mHeight - 2)) {
            //пробуем вниз
            MapCell newCell = mMapCells[Cell.x][Cell.y + 1];
            if (newCell.x == ToCell.x && newCell.y == ToCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        if (Cell.x < (mLength - 2)) {
            //пробуем вправо
            MapCell newCell = mMapCells[Cell.x + 1][Cell.y];
            if (newCell.x == ToCell.x && newCell.y == ToCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        mBuildRoute.remove(mBuildRoute.size() - 1);
        return false;
    }

    public void createObjects(MapActivity MainActivity) {
        for (int x = this.mLength -1; x>=0; x--)
            for (int y = this.mHeight -1; y>=0 ; y--){
                if (mMapCells[x][y].object != null){
                    mMapCells[x][y].object= null;
                }
                if (mMapCells[x][y].type.equals("fuel")){
                    mMapCells[x][y].object = new MapObjectFuel(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                }

                else if (mMapCells[x][y].type.equals("pilgrim")){
                    MapObjectPilgrim P1 = new MapObjectPilgrim(x, y, MainActivity);
                    //P1.task.targetCell = mMapCells[12][7];
                    mMapCells[x][y].object = P1;
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                }
                else if (mMapCells[x][y].type.equals("school")){
                    mMapCells[x][y].object = new MapObjectSchool(x, y, MainActivity);;
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;

                }
                else if (mMapCells[x][y].type.equals("building")){
                    mMapCells[x][y].object = new MapObjectTetris(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }

                else if (mMapCells[x][y].type.equals("helpboy")){
                    MapObjectHelpBoy P1 = new MapObjectHelpBoy(x, y, MainActivity);
                    //P1.task.targetCell = mMapCells[12][7];
                    mMapCells[x][y].object = P1;
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                }
                else if (mMapCells[x][y].type.equals("hospital")) {
                    mMapCells[x][y].object = new MapObject(x, y, MainActivity);
                    mMapCells[x][y].type = "hospital_";

                    mMapCells[x+1][y].type = "hospital_";
                    mMapCells[x+1][y].object= mMapCells[x][y].object;

                    mMapCells[x][y+1].type = "hospital_";
                    mMapCells[x][y+1].object= mMapCells[x][y].object;

                    mMapCells[x+1][y+1].type = "hospital_";
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("church")) {
                    mMapCells[x][y].object = new MapObjectChurch(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                    mMapCells[x+1][y].type = "church1";
                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x+2][y].type = "church1";
                    mMapCells[x+2][y].object= mMapCells[x][y].object;

                    mMapCells[x][y+1].type = "church1";
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].type = "church1";
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+2][y+1].type = "church1";
                    mMapCells[x+2][y+1].object= mMapCells[x][y].object;

                    mMapCells[x][y+2].type = "church1";
                    mMapCells[x][y+2].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+2].type = "church1";
                    mMapCells[x+1][y+2].object= mMapCells[x][y].object;
                    mMapCells[x+2][y+2].type = "church1";
                    mMapCells[x+2][y+2].object= mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("zoo")) {
                    mMapCells[x][y].object = new MapObjectZOO(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                }
                else if (mMapCells[x][y].type.equals("sto")) {
                    mMapCells[x][y].object = new MapObjectSTO(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                }
                else if (mMapCells[x][y].type.equals("gallery")) {
                    mMapCells[x][y].object = new MapObjectGallery(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                    mMapCells[x + 1][y].object = mMapCells[x][y].object;
                    mMapCells[x][y + 1].object = mMapCells[x][y].object;
                    mMapCells[x + 1][y + 1].object = mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("stones")) {
                    mMapCells[x][y].object = new MapObjectStones(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("bridge")) {
                    mMapCells[x][y].object = new MapObjectBridge(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                }
                else if (mMapCells[x][y].type.equals("RM")) {
                    mMapCells[x][y].object = new MapObjectBooks(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                    mMapCells[x][y].object.type = "RM";

                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("hunger")) {
                    mMapCells[x][y].object = new MapObjectHunger(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                }
                else if (mMapCells[x][y].type.equals("burger")) {
                    mMapCells[x][y].object = new MapObjectBurgerJoint(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("books")) {
                    mMapCells[x][y].object = new MapObjectBooks(x, y, MainActivity);
                    mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("build")) {
                    mMapCells[x][y].object = new MapObject(x, y, MainActivity);
                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }
                else if (mMapCells[x][y].type.equals("market")) {
                    mMapCells[x][y].object = new MapObject(x, y, MainActivity);
                }
                else if (mMapCells[x][y].type.equals("cafe")) {
                    mMapCells[x][y].object = new MapObject(x, y, MainActivity);

                    mMapCells[x+1][y].object= mMapCells[x][y].object;
                    mMapCells[x][y+1].object= mMapCells[x][y].object;
                    mMapCells[x+1][y+1].object= mMapCells[x][y].object;
                }
            }

        MapObject emptyObject = new MapObject(0,0, MainActivity);
        for (int i = 0; i<Missions.length;i++){
            Task Task = new Task(emptyObject);
            Task.messageText = Missions[i].messageText;
            Task.targetType = Missions[i].missionType;
            Task.targetValue1 = Missions[i].targetValue1;
            Task.targetValue2 = Missions[i].targetValue2;
            Task.messageIconMap = MainActivity.getResources().getIdentifier(Missions[i].messageIconMap,"drawable", MainActivity.getPackageName());
            Task.messageIconSource = MainActivity.getResources().getIdentifier(Missions[i].messageIconSource,"drawable", MainActivity.getPackageName());

            MainActivity.myTasks.add(Task);
        }


    }

    public void addStone(int i, String question,String answer,String type,String data){
        mStones[i] = new Stone(question,answer,type,data);
    }
    public void addWordsForBook(int i,String targetWord,String textBefor,String textAfter){
        wordsForBook[i] = new Words(targetWord,textBefor,textAfter);
    }
    public void addWordsForRM(int i,String targetWord,String textBefor,String textAfter){
        wordsForRM[i] = new Words(targetWord,textBefor,textAfter);
    }
    public void addQuestion(int i, String question, String answer1, String answer2, String answer3, String answer4,int trueAnswer,int id){
        mQuestions[i] = new Question(question, answer1, answer2, answer3, answer4, trueAnswer, id);
    }

    public void addMission(int i){
        Missions[i] = new Mission();
    }
    public Question[] getQuestions(int count, String language) {
        for (int q = 0; q< mQuestions.length; q++)
            mQuestions[q].used = false;

        Question[] AQ = new Question[count];
        Random random = new Random();
        for (int i=0; i<count; i++){
            int index = random.nextInt(mQuestions.length);
            if (mQuestions[index].used){
                //тогда пробуем взять первый попавшийся неиспользованный
                for (int q = 0; q< mQuestions.length; q++)
                    if (mQuestions[q].used == false){
                        index=q;
                        break;
                    }
            }
            mQuestions[index].used=true;
            if (language.equals(Constants.LANG_BY)) {
                AQ[i] = mQuestions[index];
            } else {
                AQ[i] = mQuestions[index];
            }
        }

        return AQ;
    }
    //////////////////////////////////// ВСПОМОГАТЕЛЬНЫЕ КЛАССЫ ///////////////////////////////////
    public static class MapCell {
        public int x = 0;
        public int y = 0;
        boolean used = false;
        public MapObject object;
        public String type = "";
        public String [] attributes;
        public MapCell(int X, int Y) {
            this.x = X;
            this.y = Y;
            attributes = new String[15];
        }
    }

    public static class Stone {
        public String question="";
        public String answer="";
        public String type="";
        public String data="";
        public Stone(String question,String answer,String type,String data) {
            this.question = question;
            this.answer = answer;
            this.type = type;
            this.data = data;
        }
    }

    public static class Words {
        public String targetWord="";
        public String textBefor="";
        public String textAfter="";

        public Words(String targetWord,String textBefor,String textAfter) {
            this.targetWord = targetWord;
            this.textBefor = textBefor;
            this.textAfter = textAfter;
        }
    }

    public static class Question {

        public String question;
        public String answer1;
        public String answer2;
        public String answer3;
        public String answer4;
        public int trueAnswer;
        int id;

        boolean used = false;

        public Question(
                String question,
                String answer1,
                String answer2,
                String answer3,
                String answer4,
                int trueAnswer,
                int id
        ) {
            this.question = question;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.answer4 = answer4;
            this.trueAnswer = trueAnswer;
            this.id = id;
        }

        public Question(
                String question,
                String answer1,
                String answer2,
                String answer3,
                String answer4,
                int trueAnswer,
                String help1,
                String help2,
                int id
        ) {
            this.question = question;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.answer4 = answer4;
            this.trueAnswer = trueAnswer;
            this.id = id;
        }
    }

    public static class Mission{
        public String missionType="";
        public int targetValue1 =0;
        public int targetValue2 =0;
        public String messageIconMap="";
        public String messageIconSource="";
        public String  messageText="";

        public Mission(){

        }
    }

}
