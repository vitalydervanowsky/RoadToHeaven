package com.carloclub.roadtoheaven.maps;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;

import java.util.ArrayList;

public class Sokolka {
    public static void loadTexts(MyMap Map) {
        if (Constants.isRu()) {
            loadTextsRU(Map);
        }
        if (Constants.isBy()) {
            loadTextsBY(Map);
        }
    }
    public static void loadTextsRU(MyMap Map) {
        Map.mStones = new MyMap.Stone[14];
        Map.addStone(0, "Дела милосердия для тела1: ","Накормить голодного","Мозайка","galery1_1");
        Map.addStone(1, "Дела милосердия для тела2","Напоить жаждущего","Мозайка","galery1_2");
        Map.addStone(2, "Дела милосердия для тела 3","Одеть нагого","Мозайка","galery1_3");
        Map.addStone(3, "Дела милосердия для тела 4","Принять странника в свой дом","Мозайка","galery1_4");
        Map.addStone(4, "Дела милосердия для тела 5","Посетить заключенного.","Мозайка","galery1_5");
        Map.addStone(5, "Дела милосердия для тела 6","Навестить больного","Мозайка","galery1_6");
        Map.addStone(6, "Дела милосердия для тела 7","Похоронить умерших","Мозайка","galery1_7");
        Map.addStone(7, "Дела милосердия для души 1","Обратить грешника.","Мозайка","galery1_8");
        Map.addStone(8, "Дела милосердия для души 2","Научить непосвященного.","Мозайка","galery1_9");
        Map.addStone(9, "Дела милосердия для души 3","Дать совет сомневающемуся.","Мозайка","galery1_10");
        Map.addStone(10, "Дела милосердия для души 4","Утешить скорбящего.","Мозайка","galery1_11");
        Map.addStone(11, "Дела милосердия для души 5","Терпеливо переносить тяготы.","Мозайка","galery1_12");
        Map.addStone(12, "Дела милосердия для души 6","Прощать от всего сердца обиды.","Мозайка","galery1_13");
        Map.addStone(13, "Дела милосердия для души 7","Молиться за живых и усопших.","Мозайка","galery1_14");
        Map.mQuestions = new MyMap.Question[12];
        Map.addQuestion(0, "Имя Спасителя человечества","Робин Гуд","Илья Муромец","Гарри Потер","Иисус Христос",4,0);
        Map.addQuestion(1, "Как мы называем Бога?","Отцом","Дядей","Дедушкой","Коллегой",1,1);
        Map.addQuestion(2, "Как называются разумные бестелесные существа, созданные Богом?","Боты","Андроиды","Ангелы","Священники",3,2);
        Map.addQuestion(3, "Кто создал мир?","Человек","Бог","Сам возник","Обезьяна",2,3);
        Map.addQuestion(4, "Сколько настоящих Богов?","1","7","3","12",1,4);
        Map.addQuestion(5, "Какое дело милосердия для тела ближнего?","Утешить скорбящего.","Молитва за ближнего","Накормить голодного","Посоветовать спортзал",3,5);
        Map.addQuestion(6, "Какое дело милосердия для души?","Напоить жаждущего","Накормить голодного","Проучить обидчика","Утешить скорбящего",4,6);
        Map.addQuestion(7, "Ваня попросил у Паши ручку и сломал её. Какой поступок будет милосердным?","Забрать себе ручку Вани","Сломать ручку Вани","Побить Ваню","Простить Ваню",4,7);
        Map.addQuestion(8, "Безработный дядя Миша уже 3 дня не ел. Какой поступок будет милосердным для дяди Миши?","Поругать его за лень","Посоветовать найти рабту","Поделиться своим обедом","Спрятать свой обед  от него надёжнее",3,8);
        Map.addQuestion(9, "Если мы даём другим то, что им нужно, а не то, что им должны, то это","Справедливость","Милосердие","Слабость","Глупость",2,9);
        Map.addQuestion(10, "Моя соседка тётя Маша заболела и целый день не встаёт с кровати. Я хочу отнести ей горячего супа, чтобы она скорее поправилась. Что это со мной?","Это милосердие","Это гражданский долг","Это страх","Это глупость",1,10);
        Map.addQuestion(11, "Что из этого не является делом милосердия для тела ближнего?","Похоронить умершего","Посетить заключенного","Навестить больного","Посадить дерево",4,11);
        Map.Missions = new MyMap.Mission[4];
        Map.addMission(0);
        Map.Missions[0].messageText="Начнём наш путь. Едь на заправку и заправь полный бак бензина.";
        Map.Missions[0].messageIconMap="AZS";
        Map.Missions[0].messageIconSource="icon_fuel";
        Map.Missions[0].missionType="count_fuel";
        Map.Missions[0].targetValue1=16000;
        Map.addMission(1);
        Map.Missions[1].messageText="Отлично! Теперь найди человека, которому нужна помощь. Ищи на карте такой рубин.";
        Map.Missions[1].messageIconMap="";
        Map.Missions[1].messageIconSource="icon_ruby";
        Map.Missions[1].missionType="count_ruby";
        Map.Missions[1].targetValue1=1;
        Map.addMission(2);
        Map.Missions[2].messageText="Отлично! Начнем собирать камни знаний. Найди на карте здания с такими камнями. ";
        Map.Missions[2].messageIconMap="";
        Map.Missions[2].messageIconSource="stones1";
        Map.Missions[2].missionType="count_stones";
        Map.Missions[2].targetValue1=1;
        Map.addMission(3);
        Map.Missions[3].messageText="Отлично! Теперь собери 7 камней или рубинов и построй из них разрушенный мост.";
        Map.Missions[3].messageIconMap="icon_bridge";
        Map.Missions[3].messageIconSource="stones1";
        Map.Missions[3].missionType="count_stones";
        Map.Missions[3].targetValue1=7;
        Map.wordsForBook = new MyMap.Words[8];
        Map.addWordsForBook(0, "ХЛЕБА","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь воля Твая як у небе, так i на зямлi.","нашага штодзённага дай нам сёння, і адпусцi нам правіны нашы, як i мы адпускаем вiнаватым нашым, і не ўводзь нас у спакусу, але збаў нас ад злога. Амэн.");
        Map.addWordsForBook(1, "ВОЛЯ","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь","Твая як у небе, так i на зямлi. Хлеба нашага штодзённага дай нам сёння, і адпусцi нам правіны нашы, як i мы адпускаем вiнаватым нашым, і не ўводзь нас у спакусу, але збаў нас ад злога. Амэн.");
        Map.addWordsForBook(2, "АМЭН","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь воля Твая як у небе, так i на зямлi. Хлеба нашага штодзённага дай нам сёння, і адпусцi нам правіны нашы, як i мы адпускаем вiнаватым нашым, і не ўводзь нас у спакусу, але збаў нас ад злога","");
        Map.addWordsForBook(3, "СВЯТАЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус.","Марыя, Маці Божая, маліся за нас, грэшных, цяпер і ў хвіліну смерці нашай. Амэн.");
        Map.addWordsForBook(4, "МАРЫЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус. Святая","Маці Божая, маліся за нас, грэшных, цяпер і ў хвіліну смерці нашай. Амэн.");
        Map.addWordsForBook(5, "МАЛІСЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус. Святая Марыя, Маці Божая,","за нас, грэшных, цяпер і ў хвіліну смерці нашай. Амэн.");
        Map.addWordsForBook(6, "ДРУЖА","Анёле Божы,","мой, Ты заўсёды будзь са мной.\nРана, увечар і ўначы Будзь ласкаў мне памагчы\nЗмагаць духа злога, Верна служыць Богу.\nДушу, цела сцеражы, Да жыцця вечнага давядзі. Амэн.");
        Map.addWordsForBook(7, "ЗМАГАЦЬ","Анёле Божы, дружа мой, Ты заўсёды будзь са мной.\nРана, увечар і ўначы Будзь ласкаў мне памагчы","духа злога, Верна служыць Богу.\nДушу, цела сцеражы, Да жыцця вечнага давядзі. Амэн.");
        Map.wordsForRM = new MyMap.Words[8];
        Map.addWordsForRM(0, "ХЛЕБА","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь воля Твая як у небе, так i на зямлi.","нашага штодзённага дай нам сёння, і адпусцi нам правіны нашы, як i мы адпускаем вiнаватым нашым, і не ўводзь нас у спакусу, але збаў нас ад злога. Амэн.");
        Map.addWordsForRM(1, "ВОЛЯ","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь","Твая як у небе, так i на зямлi. Хлеба нашага штодзённага дай нам сёння, і адпусцi нам правіны нашы, як i мы адпускаем вiнаватым нашым, і не ўводзь нас у спакусу, але збаў нас ад злога. Амэн.");
        Map.addWordsForRM(2, "АМЭН","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь воля Твая як у небе, так i на зямлi. Хлеба нашага штодзённага дай нам сёння, і адпусцi нам правіны нашы, як i мы адпускаем вiнаватым нашым, і не ўводзь нас у спакусу, але збаў нас ад злога","");
        Map.addWordsForRM(3, "СВЯТАЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус.","Марыя, Маці Божая, маліся за нас, грэшных, цяпер і ў хвіліну смерці нашай. Амэн.");
        Map.addWordsForRM(4, "МАРЫЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус. Святая","Маці Божая, маліся за нас, грэшных, цяпер і ў хвіліну смерці нашай. Амэн.");
        Map.addWordsForRM(5, "МАЛІСЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус. Святая Марыя, Маці Божая,","за нас, грэшных, цяпер і ў хвіліну смерці нашай. Амэн.");
        Map.addWordsForRM(6, "ДРУЖА","Анёле Божы,","мой, Ты заўсёды будзь са мной.\nРана, увечар і ўначы Будзь ласкаў мне памагчы\nЗмагаць духа злога, Верна служыць Богу.\nДушу, цела сцеражы, Да жыцця вечнага давядзі. Амэн.");
        Map.addWordsForRM(7, "ЗМАГАЦЬ","Анёле Божы, дружа мой, Ты заўсёды будзь са мной.\nРана, увечар і ўначы Будзь ласкаў мне памагчы","духа злога, Верна служыць Богу.\nДушу, цела сцеражы, Да жыцця вечнага давядзі. Амэн.");

    }

    public static void loadTextsBY(MyMap Map) {
        Map.mStones = new MyMap.Stone[14];
        Map.addStone(0, "Дела милосердия для тела1: ","Накормить голодного","Мозайка","galery1_1");
        Map.addStone(1, "Дела милосердия для тела2","Напоить жаждущего","Мозайка","galery1_2");
        Map.addStone(2, "Дела милосердия для тела 3","Одеть нагого","Мозайка","galery1_3");
        Map.addStone(3, "Дела милосердия для тела 4","Принять странника в свой дом","Мозайка","galery1_4");
        Map.addStone(4, "Дела милосердия для тела 5","Посетить заключенного.","Мозайка","galery1_5");
        Map.addStone(5, "Дела милосердия для тела 6","Навестить больного","Мозайка","galery1_6");
        Map.addStone(6, "Дела милосердия для тела 7","Похоронить умерших","Мозайка","galery1_7");
        Map.addStone(7, "Дела милосердия для души 1","Обратить грешника.","Мозайка","galery1_8");
        Map.addStone(8, "Дела милосердия для души 2","Научить непосвященного.","Мозайка","galery1_9");
        Map.addStone(9, "Дела милосердия для души 3","Дать совет сомневающемуся.","Мозайка","galery1_10");
        Map.addStone(10, "Дела милосердия для души 4","Утешить скорбящего.","Мозайка","galery1_11");
        Map.addStone(11, "Дела милосердия для души 5","Терпеливо переносить тяготы.","Мозайка","galery1_12");
        Map.addStone(12, "Дела милосердия для души 6","Прощать от всего сердца обиды.","Мозайка","galery1_13");
        Map.addStone(13, "Дела милосердия для души 7","Молиться за живых и усопших.","Мозайка","galery1_14");
        Map.mQuestions = new MyMap.Question[7];
        Map.addQuestion(0, "Какое дело милосердия для тела ближнего?","Утешить скорбящего.","Молитва за ближнего","Накормить голодного","Посоветовать спортзал",3,0);
        Map.addQuestion(1, "Какое дело милосердия для души?","Напоить жаждущего","Накормить голодного","Проучить обидчика","Утешить скорбящего",4,1);
        Map.addQuestion(2, "Ваня попросил у Паши ручку и сломал её. Какой поступок будет милосердным?","Забрать себе ручку Вани","Сломать ручку Вани","Побить Ваню","Простить Ваню",4,2);
        Map.addQuestion(3, "Безработный дядя Миша уже 3 дня не ел. Какой поступок будет милосердным для дяди Миши?","Поругать его за лень","Посоветовать найти рабту","Поделиться своим обедом","Спрятать свой обед  от него надёжнее",3,3);
        Map.addQuestion(4, "Если мы даём другим то, что им нужно, а не то, что им должны, то это","Справедливость","Милосердие","Слабость","Глупость",2,4);
        Map.addQuestion(5, "Моя соседка тётя Маша заболела и целый день не встаёт с кровати. Я хочу отнести ей горячего супа, чтобы она скорее поправилась. Что это со мной?","Это милосердие","Это гражданский долг","Это страх","Это глупость",1,5);
        Map.addQuestion(6, "Что из этого не является делом милосердия для тела ближнего?","Похоронить умершего","Посетить заключенного","Навестить больного","Посадить дерево",4,6);
        Map.Missions = new MyMap.Mission[5];
        Map.addMission(0);
        Map.Missions[0].messageText="Пачнем наша падарожжа. Едзь на запраўку і здабудзь поўны бак бензіна.";
        Map.Missions[0].messageIconMap="AZS";
        Map.Missions[0].messageIconSource="icon_fuel";
        Map.Missions[0].missionType="count_fuel";
        Map.Missions[0].targetValue1=16000;
        Map.addMission(1);
        Map.Missions[1].messageText="Выдатна! Цяпер накіроўвайся ў школу, каб здабыць неабходныя веды.";
        Map.Missions[1].messageIconMap="";
        Map.Missions[1].messageIconSource="icon_ruby";
        Map.Missions[1].missionType="school1";
        Map.Missions[1].targetValue1=0;
        Map.addMission(2);
        Map.Missions[2].messageText="Выдатна! Цяпер знайдзі чалавека, якому патрэбна дапамога. Шукай на мапе такі рубін.";
        Map.Missions[2].messageIconMap="";
        Map.Missions[2].messageIconSource="icon_ruby";
        Map.Missions[2].missionType="count_ruby";
        Map.Missions[2].targetValue1=1;
        Map.addMission(3);
        Map.Missions[3].messageText="Выдатна! Пачнем збіраць камяні веда. Знайдзі на мапе людзей, я кія трымаюць у руках камяні ведаў.";
        Map.Missions[3].messageIconMap="";
        Map.Missions[3].messageIconSource="stones1";
        Map.Missions[3].missionType="count_stones";
        Map.Missions[3].targetValue1=1;
        Map.addMission(4);
        Map.Missions[4].messageText="Выдатна! Цяпер збяры 7 такіх камянёў і пабудуй з іх разбураны мост.";
        Map.Missions[4].messageIconMap="icon_bridge";
        Map.Missions[4].messageIconSource="stones1";
        Map.Missions[4].missionType="count_stones";
        Map.Missions[4].targetValue1=7;
        Map.wordsForBook = new MyMap.Words[3];
        Map.addWordsForBook(0, "ШЧАСЦЕ","Калі тваё імя прамаўляе той, каго ты любіш, - гэта самае вялікае","на свеце.");
        Map.addWordsForBook(1, "УСЕ","Няпросты лёс прыгатаваны ўсялякаму мышаняці і чалавекуб, які адважыўся быць не такім, як","");
        Map.addWordsForBook(2, "СВЯТЛО","Сусвет пагружаны ў цемру, і","ў ім-на вагу золата.");
        Map.wordsForRM = new MyMap.Words[7];
        Map.addWordsForRM(0, "ХЛЕБА","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь воля Твая як у небе, так i на зямлi.","нашага штодзённага дай нам сёння");
        Map.addWordsForRM(1, "ВОЛЯ","Ойча наш, каторы ёсць у небе, свяціся iмя Тваё, прыйдзi Валадарства Тваё, будзь","Твая як у небе, так i на зямлi.");
        Map.addWordsForRM(2, "СВЯТАЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус.","Марыя, Маці Божая, маліся за нас, грэшных, цяпер і ў хвіліну смерці нашай.");
        Map.addWordsForRM(3, "МАРЫЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус. Святая","Маці Божая, маліся за нас, грэшных, цяпер і ў хвіліну смерці нашай.");
        Map.addWordsForRM(4, "МАЛІСЯ","Вітай, Марыя, поўная ласкі, Пан з Табою, благаслаўлёная Ты між жанчынамі і благаслаўлёны плод улоння Твайго, Езус. Святая Марыя, Маці Божая,","за нас, грэшных, цяпер і ў хвіліну смерці нашай.");
        Map.addWordsForRM(5, "ДРУЖА","Анёле Божы,","мой, Ты заўсёды будзь са мной.\nРана, увечар і ўначы Будзь ласкаў мне памагчы");
        Map.addWordsForRM(6, "ЗМАГАЦЬ","Анёле Божы, дружа мой, Ты заўсёды будзь са мной.\nРана, увечар і ўначы Будзь ласкаў мне памагчы","духа злога, Верна служыць Богу.\nДушу, цела сцеражы, Да жыцця вечнага давядзі.");

        Map.tetrisQuestion = new ArrayList<>();
        String dataQuestion;
        dataQuestion = "Разбяры цаглінкі і ўбачыш: Хто шчаслівы, бо спазнае міласэрнасць?";
        Map.tetrisQuestion.add(new MyMap.Question(dataQuestion,"Справядлівыя","Чыстыя сэрцам","Міласэрныя","Міратворцы",3,0, R.drawable.back_tetris));

        Map.cinemaQuestion = new ArrayList<>();
        dataQuestion = "Які ўчынак міласэрнасці паказаны на гэтым кадры фільма \"Паліяна\"";
        Map.cinemaQuestion.add(new MyMap.Question(dataQuestion,"Накарміць галоднага","Напаіць прагнучага","Наведаць хворага","Наставіць грэшніка",3,0, R.drawable.polianna));

        Map.schoolQuestions = new ArrayList<>();
        dataQuestion = "Які ўчынак міласэрнасці паказаны на гэтым малюнку?";
        Map.schoolQuestions.add(new MyMap.Question(dataQuestion,"Накарміць галоднага","Напаіць прагнучага","Наведаць хворага","Наставіць грэшніка",3,0, R.drawable.mercy_act_body_gallery_5));
        Map.schoolQuestions.add(new MyMap.Question(dataQuestion,"Накарміць галоднага","Напаіць прагнучага","Маліцца за жывых і памерлых","Наставіць грэшніка",3,0, R.drawable.mercy_act_soul_gallery_7));

        Map.churchQuestions = new ArrayList<>();
        Map.churchQuestions.add(new MyMap.Question(dataQuestion,"Вучыць тых, хто не ведае","Напаіць прагнучага","Маліцца за жывых і памерлых","Наставіць грэшніка",1,0, R.drawable.mercy_act_soul_gallery_2));

        Map.wellQuestions = new ArrayList<>();
        Map.wellQuestions.add(new MyMap.Question("Які ўчынак міласэрнасці ўчыніў Косця?","Накарміў галоднага","Наставіў грэшніка","Адведаў вязня","Ніякага",2,0));

        Map.kidsQuestion = new ArrayList<>();
        Map.kidsQuestion.add(new MyMap.Question(dataQuestion,"Накарміць галоднага","Напаіць прагнучага","Наведаць хворага","Наставіць грэшніка",1,0, R.drawable.drawing1));
    }

    public static void LoadMap(MyMap Map) {
        Map.mMapCells[0][1].type = "Road";
        Map.mMapCells[0][2].type = "fuel";
        Map.mMapCells[0][3].type = "pilgrim";
        Map.mMapCells[0][4].type = "forest";
        Map.mMapCells[0][5].type = "forest";
        Map.mMapCells[0][6].type = "forest";
        Map.mMapCells[0][7].type = "fuel";
        Map.mMapCells[0][8].type = "forest";
        Map.mMapCells[0][9].type = "river";
        Map.mMapCells[1][0].type = "Road";
        Map.mMapCells[1][1].type = "Road";
        Map.mMapCells[1][2].type = "Road";
        Map.mMapCells[1][3].type = "Road";
        Map.mMapCells[1][4].type = "Road";
        Map.mMapCells[1][5].type = "Road";
        Map.mMapCells[1][6].type = "Road";
        Map.mMapCells[1][7].type = "Road";
        Map.mMapCells[1][8].type = "Road";
        Map.mMapCells[1][9].type = "river";
        Map.mMapCells[2][0].type = "school";
        Map.mMapCells[2][2].type = "Road";
        Map.mMapCells[2][3].type = "forest";
        Map.mMapCells[2][4].type = "forest";
        Map.mMapCells[2][5].type = "Road";
        Map.mMapCells[2][6].type = "hospital";
        Map.mMapCells[2][8].type = "Road";
        Map.mMapCells[2][9].type = "river";
        Map.mMapCells[3][2].type = "Road";
        Map.mMapCells[3][3].type = "forest";
        Map.mMapCells[3][5].type = "Road";
        Map.mMapCells[3][8].type = "Road";
        Map.mMapCells[3][9].type = "river";
        Map.mMapCells[4][0].type = "forest";
        Map.mMapCells[4][1].type = "forest";
        Map.mMapCells[4][2].type = "Road";
        Map.mMapCells[4][3].type = "stones";
        Map.mMapCells[4][5].type = "Road";
        Map.mMapCells[4][6].type = "hunger";
        Map.mMapCells[4][7].type = "forest";
        Map.mMapCells[4][8].type = "Road";
        Map.mMapCells[4][9].type = "river";
        Map.mMapCells[5][0].type = "build";
        Map.mMapCells[5][2].type = "Road";
        Map.mMapCells[5][5].type = "Road";
        Map.mMapCells[5][6].type = "Road";
        Map.mMapCells[5][7].type = "well";
        Map.mMapCells[5][8].type = "Road";
        Map.mMapCells[5][9].type = "river";
        Map.mMapCells[6][2].type = "Road";
        Map.mMapCells[6][3].type = "forest";
        Map.mMapCells[6][4].type = "burger";
        Map.mMapCells[6][6].type = "Road";
        Map.mMapCells[6][7].type = "forest";
        Map.mMapCells[6][8].type = "Road";
        Map.mMapCells[6][9].type = "river";
        Map.mMapCells[7][0].type = "build";
        Map.mMapCells[7][2].type = "Road";
        Map.mMapCells[7][3].type = "forest";
        Map.mMapCells[7][6].type = "Road";
        Map.mMapCells[7][7].type = "Road";
        Map.mMapCells[7][8].type = "Road";
        Map.mMapCells[7][9].type = "bridge";
        Map.mMapCells[8][2].type = "Road";
        Map.mMapCells[8][3].type = "church";
        Map.mMapCells[8][6].type = "Road";
        Map.mMapCells[8][7].type = "forest";
        Map.mMapCells[8][8].type = "Road";
        Map.mMapCells[8][9].type = "river";
        Map.mMapCells[9][0].type = "build";
        Map.mMapCells[9][2].type = "Road";
        Map.mMapCells[9][6].type = "Road";
        Map.mMapCells[9][7].type = "forest";
        Map.mMapCells[9][8].type = "Road";
        Map.mMapCells[9][9].type = "river";
        Map.mMapCells[10][2].type = "Road";
        Map.mMapCells[10][6].type = "Road";
        Map.mMapCells[10][7].type = "helpboy";
        Map.mMapCells[10][8].type = "Road";
        Map.mMapCells[10][9].type = "river";
        Map.mMapCells[11][0].type = "build";
        Map.mMapCells[11][2].type = "Road";
        Map.mMapCells[11][3].type = "forest";
        Map.mMapCells[11][4].type = "RM";
        Map.mMapCells[11][6].type = "Road";
        Map.mMapCells[11][7].type = "forest";
        Map.mMapCells[11][8].type = "Road";
        Map.mMapCells[11][9].type = "river";
        Map.mMapCells[12][2].type = "Road";
        Map.mMapCells[12][3].type = "forest";
        Map.mMapCells[12][6].type = "Road";
        Map.mMapCells[12][8].type = "Road";
        Map.mMapCells[12][9].type = "river";
        Map.mMapCells[13][0].type = "build";
        Map.mMapCells[13][2].type = "Road";
        Map.mMapCells[13][3].type = "books";
        Map.mMapCells[13][5].type = "Road";
        Map.mMapCells[13][6].type = "Road";
        Map.mMapCells[13][7].type = "kids";
        Map.mMapCells[13][8].type = "Road";
        Map.mMapCells[13][9].type = "river";
        Map.mMapCells[14][2].type = "Road";
        Map.mMapCells[14][5].type = "Road";
        Map.mMapCells[14][6].type = "build";
        Map.mMapCells[14][8].type = "Road";
        Map.mMapCells[14][9].type = "river";
        Map.mMapCells[15][0].type = "build";
        Map.mMapCells[15][2].type = "Road";
        Map.mMapCells[15][3].type = "forest";
        Map.mMapCells[15][4].type = "forest";
        Map.mMapCells[15][5].type = "Road";
        Map.mMapCells[15][8].type = "Road";
        Map.mMapCells[15][9].type = "river";
        Map.mMapCells[16][2].type = "Road";
        Map.mMapCells[16][3].type = "cinema";
        Map.mMapCells[16][5].type = "Road";
        Map.mMapCells[16][6].type = "building";
        Map.mMapCells[16][8].type = "Road";
        Map.mMapCells[16][9].type = "river";
        Map.mMapCells[17][0].type = "build";
        Map.mMapCells[17][2].type = "Road";
        Map.mMapCells[17][5].type = "Road";
        Map.mMapCells[17][8].type = "Road";
        Map.mMapCells[17][9].type = "river";
        Map.mMapCells[18][2].type = "Road";
        Map.mMapCells[18][3].type = "Road";
        Map.mMapCells[18][4].type = "Road";
        Map.mMapCells[18][5].type = "Road";
        Map.mMapCells[18][6].type = "Road";
        Map.mMapCells[18][7].type = "Road";
        Map.mMapCells[18][8].type = "Road";
        Map.mMapCells[18][9].type = "river";
        Map.mMapCells[19][0].type = "forest";
        Map.mMapCells[19][1].type = "forest";
        Map.mMapCells[19][2].type = "fuel";
        Map.mMapCells[19][3].type = "forest";
        Map.mMapCells[19][4].type = "forest";
        Map.mMapCells[19][5].type = "forest";
        Map.mMapCells[19][6].type = "forest";
        Map.mMapCells[19][7].type = "fuel";
        Map.mMapCells[19][8].type = "forest";
        Map.mMapCells[19][9].type = "river";
        Map.mMapCells[8][3].attributes[0] = "Костёл Св. Антония";  	//Титул:
        Map.mMapCells[8][3].attributes[2] = "churchsokolka2";  	//Интерьер:
        Map.mMapCells[0][3].attributes[0] = "Пол";  	//Имя
        Map.mMapCells[0][3].attributes[1] = "9";  	//Таргет X:
        Map.mMapCells[0][3].attributes[2] = "4";  	//Таргет Y:
        Map.mMapCells[0][3].attributes[3] = "Привет! Меня зовут Каролина. Я еду автоспом в санктуарий святого Антония. Если ты меня подвезешь, я буду тебе очень благодарна.";  	//Приветствие:
        Map.mMapCells[0][3].attributes[4] = "1000";  	//Награда:
    }


}
