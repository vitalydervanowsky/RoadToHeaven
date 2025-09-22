package com.carloclub.roadtoheaven;

import com.carloclub.roadtoheaven.MapObjects.MapObject;
import com.carloclub.roadtoheaven.MapObjects.MapObjectBridge;
import com.carloclub.roadtoheaven.MapObjects.MapObjectBurgerJoint;
import com.carloclub.roadtoheaven.MapObjects.MapObjectChurch;
import com.carloclub.roadtoheaven.MapObjects.MapObjectFuel;
import com.carloclub.roadtoheaven.MapObjects.MapObjectHelpBoy;
import com.carloclub.roadtoheaven.MapObjects.MapObjectHunger;
import com.carloclub.roadtoheaven.MapObjects.MapObjectPiligrim;
import com.carloclub.roadtoheaven.MapObjects.MapObjectRM;
import com.carloclub.roadtoheaven.MapObjects.MapObjectSTO;
import com.carloclub.roadtoheaven.MapObjects.MapObjectStones;
import com.carloclub.roadtoheaven.MapObjects.MapObjectZOO;

import java.util.ArrayList;
import java.util.Random;

public class MyMap {
    public int Scale = Constants.DATAGAME.SCALE;
    ArrayList<MapCell> Rote;
    private ArrayList<MapCell> BuildRote;
    public MapCell[][] Cells;
    public Stone[] Stones;
    public Question[] Questions;
    public int Length;
    public int Height;

    int BackgroundID;


    public MyMap(int L, int H, int BackID) {
        Length = L;
        Height = H;
        BackgroundID = BackID;

        Cells = new MapCell[Length][Height];
        for (int x = 0; x < this.Length; x++)
            for (int y = 0; y < this.Height; y++)
                Cells[x][y] = new MapCell(x, y);
    }

    public MapCell FindCellByXY(int X, int Y) {
        return Cells[(int) (X / Scale)][(int) (Y / Scale)];
    }

    public ArrayList<MapCell> BuildRote(MapCell FromCell, MapCell ToCell) {
        BuildRote = new ArrayList<MapCell>();
        for (int x = 0; x < this.Length; x++)
            for (int y = 0; y < this.Height; y++)
                Cells[x][y].used = false;
        //R.remove()
        FromCell.used = true;
        ArrayList<ArrayList> Rotes = new ArrayList();
        if (canmove(FromCell.X - 1, FromCell.Y, ToCell)) {
            ArrayList R = new ArrayList<MapCell>();
            Cells[FromCell.X - 1][FromCell.Y].used = true;
            R.add(Cells[FromCell.X - 1][FromCell.Y]);
            Rotes.add(R);
        }
        if (canmove(FromCell.X + 1, FromCell.Y, ToCell)) {
            ArrayList R = new ArrayList<MapCell>();
            Cells[FromCell.X + 1][FromCell.Y].used = true;
            R.add(Cells[FromCell.X + 1][FromCell.Y]);
            Rotes.add(R);
        }
        if (canmove(FromCell.X, FromCell.Y - 1, ToCell)) {
            ArrayList R = new ArrayList<MapCell>();
            Cells[FromCell.X][FromCell.Y - 1].used = true;
            R.add(Cells[FromCell.X][FromCell.Y - 1]);
            Rotes.add(R);
        }
        if (canmove(FromCell.X, FromCell.Y + 1, ToCell)) {
            ArrayList R = new ArrayList<MapCell>();
            Cells[FromCell.X][FromCell.Y + 1].used = true;
            R.add(Cells[FromCell.X][FromCell.Y + 1]);
            Rotes.add(R);
        }

        int Step = 0;
        while (true) {
            if (Rotes.size() == 0) return null; //не осталось вариантов
            int i = 0;
            Step++;
            //Добавляем по 1 шагу во всех направлениях
            while (i < Rotes.size()) {
                ArrayList R = Rotes.get(i);
                if (R.size() > Step) break; //это новые направленія
                MapCell currentCell = (MapCell) R.get(R.size() - 1);
                if (currentCell.X == ToCell.X && currentCell.Y == ToCell.Y)
                    return R;
                ArrayList CanCells = new ArrayList<MapCell>();

                if (canmove(currentCell.X - 1, currentCell.Y, ToCell)) {
                    CanCells.add(Cells[currentCell.X - 1][currentCell.Y]);
                }
                if (canmove(currentCell.X + 1, currentCell.Y, ToCell)) {
                    CanCells.add(Cells[currentCell.X + 1][currentCell.Y]);
                }
                if (canmove(currentCell.X, currentCell.Y - 1, ToCell)) {
                    CanCells.add(Cells[currentCell.X][currentCell.Y - 1]);
                }
                if (canmove(currentCell.X, currentCell.Y + 1, ToCell)) {
                    CanCells.add(Cells[currentCell.X][currentCell.Y + 1]);
                }
                if (CanCells.size() == 0) {
                    //в данном направленіі больше нет варіантов. удаляем направленіе
                    Rotes.remove(i);
                    continue;
                }
                for (int n = 2; n <= CanCells.size(); n++) {
                    //еслі нашло несколько варіантов, то для варіантов с 2 по 4 создадим новые направления, скопировав все шаги с текущего
                    ArrayList newR = new ArrayList<MapCell>();
                    for (int c = 1; c <= R.size(); c++)
                        newR.add(R.get(c - 1));
                    //и, конечно, добавляем новую ячейку
                    MapCell Cell = (MapCell) CanCells.get(n - 1);
                    Cell.used = true;
                    newR.add(Cell);
                    Rotes.add(newR);
                }
                // а в текущее направление добавляем первую из найденных
                MapCell Cell = (MapCell) CanCells.get(0);
                Cell.used = true;
                R.add(Cell);
                i++;
            }
        }

    }

    private boolean canmove(int X, int Y, MapCell ToCell) {
        if (X < 0 || Y < 0 || X >= Length || Y >= Height)
            return false;
        MapCell newCell = Cells[X][Y];
        if (newCell.used) return false;
        if (newCell.X == ToCell.X && newCell.Y == ToCell.Y) {
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
        BuildRote.add(Cell);
        if (Cell.X > 0) {
            //пробуем влево
            MapCell newCell = Cells[Cell.X - 1][Cell.Y];
            if (newCell.X == ToCell.X && newCell.Y == ToCell.Y) {
                BuildRote.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        if (Cell.Y > 0) {
            //пробуем вверх
            MapCell newCell = Cells[Cell.X][Cell.Y - 1];
            if (newCell.X == ToCell.X && newCell.Y == ToCell.Y) {
                BuildRote.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        if (Cell.Y < (Height - 2)) {
            //пробуем вниз
            MapCell newCell = Cells[Cell.X][Cell.Y + 1];
            if (newCell.X == ToCell.X && newCell.Y == ToCell.Y) {
                BuildRote.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        if (Cell.X < (Length - 2)) {
            //пробуем вправо
            MapCell newCell = Cells[Cell.X + 1][Cell.Y];
            if (newCell.X == ToCell.X && newCell.Y == ToCell.Y) {
                BuildRote.add(newCell);
                return true;
            }
            if (newCell.type.equals("Road")) {
                if (TryGo(newCell, ToCell))
                    return true;
            }
        }
        BuildRote.remove(BuildRote.size() - 1);
        return false;
    }

    public void createObjects(MapActivity MainActivity) {
        for (int x = 0; x < this.Length; x++)
            for (int y = 0; y < this.Height; y++){
                if (Cells[x][y].object != null){
                    Cells[x][y].object= null;
                }
                if (Cells[x][y].type.equals("fuel")){
                    Cells[x][y].object = new MapObjectFuel(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }

                else if (Cells[x][y].type.equals("piligrim")){
                    MapObjectPiligrim P1 = new MapObjectPiligrim(x, y, MainActivity);
                        //P1.Task.TargetCell = Cells[12][7];
                    Cells[x][y].object = P1;
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);

                }

                else if (Cells[x][y].type.equals("helpboy")){
                    MapObjectHelpBoy P1 = new MapObjectHelpBoy(x, y, MainActivity);
                    //P1.Task.TargetCell = Cells[12][7];
                    Cells[x][y].object = P1;
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);

                }
                else if (Cells[x][y].type.equals("hospital")) {
                    Cells[x][y].object = new MapObject(x, y, MainActivity);
                }
                else if (Cells[x][y].type.equals("church")) {
                    Cells[x][y].object = new MapObjectChurch(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }
                else if (Cells[x][y].type.equals("zoo")) {
                    Cells[x][y].object = new MapObjectZOO(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }
                else if (Cells[x][y].type.equals("sto")) {
                    Cells[x][y].object = new MapObjectSTO(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }
                else if (Cells[x][y].type.equals("stones")) {
                    Cells[x][y].object = new MapObjectStones(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }
                else if (Cells[x][y].type.equals("bridge")) {
                    Cells[x][y].object = new MapObjectBridge(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }
                else if (Cells[x][y].type.equals("RM")) {
                    Cells[x][y].object = new MapObjectRM(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }
                else if (Cells[x][y].type.equals("hunger")) {
                    Cells[x][y].object = new MapObjectHunger(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }
                else if (Cells[x][y].type.equals("burger")) {
                    Cells[x][y].object = new MapObjectBurgerJoint(x, y, MainActivity);
                    Cells[x][y].object.loadAttributes(Cells[x][y].attributes);
                }





            }


    }

    public void addStone(int i, String question,String answer,String type,String data){
        Stones[i] = new Stone(question,answer,type,data);
    }
    public void addQuestion(int i, String question, String answer1, String answer2, String answer3, String answer4,int trueAnswer,int id){
        Questions[i] = new Question(question, answer1, answer2, answer3, answer4, trueAnswer, id);
    }

    public Question[] getQuestions(int count, String language) {
        for (int q=0; q<Questions.length; q++)
            Questions[q].used = false;

        Question[] AQ = new Question[count];
        Random random = new Random();
        for (int i=0; i<count; i++){
            int index = random.nextInt(Questions.length);
            if (Questions[index].used){
                //тогда пробуем взять первый попавшийся неиспользованный
                for (int q=0; q<Questions.length; q++)
                    if (Questions[q].used == false){
                        index=q;
                        break;
                    }
            }
            Questions[index].used=true;
            if (language.equals(Constants.LANG_BY)) {
                AQ[i] = Questions[index];
            } else {
                AQ[i] = Questions[index];
            }
        }

        return AQ;
    }
    //////////////////////////////////// ВСПОМОГАТЕЛЬНЫЕ КЛАССЫ ///////////////////////////////////
    public class MapCell {
        public int X = 0;
        public int Y = 0;
        boolean used = false;
        public MapObject object;
        public String type = "";
        public String [] attributes;
        public MapCell(int X, int Y) {
            this.X = X;
            this.Y = Y;
            attributes = new String[15];
        }
    }

    public class Stone {
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

    public class Question {

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
