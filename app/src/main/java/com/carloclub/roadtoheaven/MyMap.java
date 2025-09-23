package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.MapObjects.MapObject;
import com.carloclub.roadtoheaven.MapObjects.MapObjectBridge;
import com.carloclub.roadtoheaven.MapObjects.MapObjectBurgerJoint;
import com.carloclub.roadtoheaven.MapObjects.MapObjectChurch;
import com.carloclub.roadtoheaven.MapObjects.MapObjectFuel;
import com.carloclub.roadtoheaven.MapObjects.MapObjectHelpBoy;
import com.carloclub.roadtoheaven.MapObjects.MapObjectHunger;
import com.carloclub.roadtoheaven.MapObjects.MapObjectPilgrim;
import com.carloclub.roadtoheaven.MapObjects.MapObjectRM;
import com.carloclub.roadtoheaven.MapObjects.MapObjectSTO;
import com.carloclub.roadtoheaven.MapObjects.MapObjectStones;
import com.carloclub.roadtoheaven.MapObjects.MapObjectZOO;

import java.util.ArrayList;
import java.util.Random;

public class MyMap {
    public int scale = Constants.DATAGAME.SCALE;
    ArrayList<MapCell> mRoute;
    private ArrayList<MapCell> mBuildRoute;
    public MapCell[][] mMapCells;
    public Stone[] mStones;
    public Question[] mQuestions;
    public int mLength;
    public int mHeight;
    public int mBackgroundId;

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

    private boolean tryGo(MapCell cell, MapCell toCell) {
        if (cell.used) return false;
        cell.used = true;
        mBuildRoute.add(cell);
        if (cell.x > 0) {
            //пробуем влево
            MapCell newCell = mMapCells[cell.x - 1][cell.y];
            if (newCell.x == toCell.x && newCell.y == toCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (tryGo(newCell, toCell)) {
                    return true;
                }
            }
        }
        if (cell.y > 0) {
            //пробуем вверх
            MapCell newCell = mMapCells[cell.x][cell.y - 1];
            if (newCell.x == toCell.x && newCell.y == toCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (tryGo(newCell, toCell)) {
                    return true;
                }
            }
        }
        if (cell.y < (mHeight - 2)) {
            //пробуем вниз
            MapCell newCell = mMapCells[cell.x][cell.y + 1];
            if (newCell.x == toCell.x && newCell.y == toCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (tryGo(newCell, toCell)) {
                    return true;
                }
            }
        }
        if (cell.x < (mLength - 2)) {
            //пробуем вправо
            MapCell newCell = mMapCells[cell.x + 1][cell.y];
            if (newCell.x == toCell.x && newCell.y == toCell.y) {
                mBuildRoute.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (tryGo(newCell, toCell)) {
                    return true;
                }
            }
        }
        mBuildRoute.remove(mBuildRoute.size() - 1);
        return false;
    }

    public void createObjects(MapActivity activity) {
        for (int x = 0; x < this.mLength; x++)
            for (int y = 0; y < this.mHeight; y++) {
                if (mMapCells[x][y].object != null) {
                    mMapCells[x][y].object = null;
                }
                switch (mMapCells[x][y].type) {
                    case "fuel":
                        mMapCells[x][y].object = new MapObjectFuel(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "pilgrim": {
                        MapObjectPilgrim mapObjectPilgrim = new MapObjectPilgrim(x, y, activity);
                        //P1.Task.TargetCell = Cells[12][7];
                        mMapCells[x][y].object = mapObjectPilgrim;
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                        break;
                    }
                    case "helpboy": {
                        MapObjectHelpBoy mapObjectHelpBoy = new MapObjectHelpBoy(x, y, activity);
                        //P1.Task.TargetCell = Cells[12][7];
                        mMapCells[x][y].object = mapObjectHelpBoy;
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);

                        break;
                    }
                    case "hospital":
                        mMapCells[x][y].object = new MapObject(x, y, activity);
                        break;
                    case "church":
                        mMapCells[x][y].object = new MapObjectChurch(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "zoo":
                        mMapCells[x][y].object = new MapObjectZOO(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "sto":
                        mMapCells[x][y].object = new MapObjectSTO(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "stones":
                        mMapCells[x][y].object = new MapObjectStones(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "bridge":
                        mMapCells[x][y].object = new MapObjectBridge(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "RM":
                        mMapCells[x][y].object = new MapObjectRM(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "hunger":
                        mMapCells[x][y].object = new MapObjectHunger(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                    case "burger":
                        mMapCells[x][y].object = new MapObjectBurgerJoint(x, y, activity);
                        mMapCells[x][y].object.loadAttributes(mMapCells[x][y].attributes);
                        break;
                }


            }


    }

    public void addStone(int i, String question, String answer, String type, String data) {
        mStones[i] = new Stone(question, answer, type, data);
    }

    public void addQuestion(int i, String question, String answer1, String answer2, String answer3, String answer4, int trueAnswer, int id) {
        mQuestions[i] = new Question(question, answer1, answer2, answer3, answer4, trueAnswer, id);
    }

    public Question[] getQuestions(int count, String language) {
        for (Question question : mQuestions) {
            question.used = false;
        }

        Question[] questions = new Question[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(mQuestions.length);
            if (mQuestions[index].used) {
                //тогда пробуем взять первый попавшийся неиспользованный
                for (int q = 0; q < mQuestions.length; q++) {
                    if (!mQuestions[q].used) {
                        index = q;
                        break;
                    }
                }
            }
            mQuestions[index].used = true;
            questions[i] = mQuestions[index];
        }

        return questions;
    }

    /// ///////////////////////////////// ВСПОМОГАТЕЛЬНЫЕ КЛАССЫ ///////////////////////////////////
    public static class MapCell {
        public int x;
        public int y;
        boolean used = false;
        public MapObject object;
        public String type = "";
        public String[] attributes;

        public MapCell(int X, int Y) {
            this.x = X;
            this.y = Y;
            attributes = new String[15];
        }
    }

    public static class Stone {
        public String question;
        public String answer;
        public String type;
        public String data;

        public Stone(String question, String answer, String type, String data) {
            this.question = question;
            this.answer = answer;
            this.type = type;
            this.data = data;
        }
    }

    public static class Question {

        String question;
        String answer1;
        String answer2;
        String answer3;
        String answer4;
        int trueAnswer;
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
    }

}
