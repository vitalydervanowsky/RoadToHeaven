package com.carloclub.roadtoheaven;

public class Messages {

    public static String getMessageGotStone() {
        if (Constants.isBy()) {
            return "Віншуем! Вы здабылі 1 камень";
        }
        if (Constants.isRu()) {
            return "Поздравляем! Вы добыли 1 камень";
        }
        return "";
    }

    public static String getMessageGotAllStones() {
        if (Constants.isBy()) {
            return "Віншуем! Усе камяні сабрнаыя. Можна ехаць будаваць мост.";
        }
        if (Constants.isRu()) {
            return "Поздравляем! Все камни собраны. Можно ехать строить мост.";
        }
        return "";
    }

    public static String getMessageHowManyStonesGot() {
        if (Constants.isBy()) {
            return "Сабрана: ";
        }
        if (Constants.isRu()) {
            return "Собрано: ";
        }
        return "";
    }

    public static String getMessageTechnicalBreak() {
        if (Constants.isBy()) {
            return "Тэхнічны перапынак: 5 хвілін";
        }
        if (Constants.isRu()) {
            return "Технический перерыв: 5 минут";
        }
        return "";
    }

    public static String getMessageGalleryTechnicalBreak() {
        if (Constants.isBy()) {
            return "У галерэi тэхнічны перапынак: 5 хвілін";
        }
        if (Constants.isRu()) {
            return "В галерее технический перерыв: 5 минут";
        }
        return "";
    }

    public static String getMessageNotEnoughStones() {
        if (Constants.isBy()) {
            return "Недастаткова камянёў! Каб пачаць будаваць мост, трэба сабраць 7 камянёў";
        }
        if (Constants.isRu()) {
            return "Недостаточно камней! Чтобы начать строить мост, нужно собрать 7 камней";
        }
        return "";
    }

    public static String getMessageMistake() {
        if (Constants.isBy()) {
            return "На жаль, ты памыліўся. Паспрабуй яшчэ раз";
        }
        if (Constants.isRu()) {
            return "К сожалению, ты ошибся. Попробуй ещё раз";
        }
        return "";
    }

    public static String getMessageFail() {
        if (Constants.isBy()) {
            return "Не атрымалася((( Паспрабуйце яшчэ раз";
        }
        if (Constants.isRu()) {
            return "Не получилось((( Попробуйте ещё раз";
        }
        return "";
    }

    public static String getMessageNotEnoughMoneyForBurger() {
        if (Constants.isBy()) {
            return "Недастаткова сродкаў для пакупкі бургера";
        }
        if (Constants.isRu()) {
            return "Недостаточно средств для покупки бургера";
        }
        return "";
    }

    public static String getMessageMistakeWithRuby() {
        if (Constants.isBy()) {
            return "Нажаль, ты памыліўся, але рубін дапамогі цябе ўратаваў";
        }
        if (Constants.isRu()) {
            return "К сожалению, ты ошибся, но рубин помощи тебя спас";
        }
        return "";
    }

    public static String getMessageYouHaveSnack() {
        if (Constants.isBy()) {
            return "Цяпер у цябе ёсць перакус у дарогу або...  ";
        }
        if (Constants.isRu()) {
            return "Теперь у тебя есть перекус в дорогу или...  ";
        }
        return "";
    }

    public static String getMessageYouGetRubyHelp() {
        if (Constants.isBy()) {
            return "За тваё добрае сэрца ты атрымліваеш 1 Рубін Дапамогі";
        }
        if (Constants.isRu()) {
            return "За Твоё доброе сердце Ты полчаешь 1 Рубин Помощи";
        }
        return "";
    }

    public static String getMessageTankIsFull() {
        if (Constants.isBy()) {
            return "Бак напоўнены. Можна ехаць!";
        }
        if (Constants.isRu()) {
            return "Бак наполнен. Можно ехать!";
        }
        return "";
    }

    public static String getMessageYouHaveNoBurgers() {
        if (Constants.isBy()) {
            return "У заплечніку няма бургераў. Але ты можаш купіць іх у бургернай";
        }
        if (Constants.isRu()) {
            return "В рюкзаке нет бургеров. Но их можно купить в бургерной";
        }
        return "";
    }

    public static String getMessageFromPilgrim() {
        if (Constants.isBy()) {
            return "Вітанкі! Мяне завуць Караліна. Я еду аўтастопам у санктуарый святога Антонія. Калі ты мяне падвязеш, я буду табе вельмі ўдзячна.";
        }
        if (Constants.isRu()) {
            return "Привет! Меня зовут Каролина. Я еду автоспом в санктуарий святого Антония. Если ты меня подвезешь, я буду тебе очень благодарна.";
        }
        return "";
    }

    public static String getMessageNotEnoughMoney() {
        if (Constants.isBy()) {
            return "У Вас недастаткова манет";
        }
        if (Constants.isRu()) {
            return "У Вас недостаточно монет";
        }
        return "";
    }

    public static String getMessageGetBalance() {
        if (Constants.isBy()) {
            return "Астача: ";
        }
        if (Constants.isRu()) {
            return "Остаток: ";
        }
        return "";
    }

    public static String getMessageThanks() {
        if (Constants.isBy()) {
            return "Дзякуй!";
        }
        if (Constants.isRu()) {
            return "Спасибо!";
        }
        return "";
    }

    public static String getMessageKnowAnswer() {
        if (Constants.isBy()) {
            return "Ведаю адказ";
        }
        if (Constants.isRu()) {
            return "Знаю ответ";
        }
        return "";
    }

    public static String getMessageGetHint() {
        if (Constants.isBy()) {
            return "Адкрыць падказку";
        }
        if (Constants.isRu()) {
            return "Открыть подсказку";
        }
        return "";
    }

    public static String getMessageDoMosaic() {
        if (Constants.isBy()) {
            return "Збярыце мазайку, перасунуўшы фрагменты не больш за 14 разоў";
        }
        if (Constants.isRu()) {
            return "Соберите мозайку, передвинув фрагменты не более 14 раз";
        }
        return "";
    }

    public static String getMessageCatchPinkCat() {
        if (Constants.isBy()) {
            return "Злавіце ружовага ката";
        }
        if (Constants.isRu()) {
            return "Словите розового кота";
        }
        return "";
    }

    public static String getMessageCarryCatInZoo() {
        if (Constants.isBy()) {
            return "Адвязіце ката ў ЗОО краму";
        }
        if (Constants.isRu()) {
            return "Отвезите кота в ЗОО магазин";
        }
        return "";
    }

    public static String getMessageThanksGoodManGetPrise() {
        if (Constants.isBy()) {
            return "Дзякуй, добры чалавек! Атрымай сваю заслужанаю ўзнагароду: 1 000 манет.";
        }
        if (Constants.isRu()) {
            return "Спасибо, добрый человек! Получи же свою заслуженную награду: 1 000 монет.";
        }
        return "";
    }

    public static String getMessageGoodLuck() {
        if (Constants.isBy()) {
            return "Поспехаў!";
        }
        if (Constants.isRu()) {
            return "Удачи!";
        }
        return "";
    }

    public static String getMessageAttentionFewFuel() {
        if (Constants.isBy()) {
            return "Увага! засталося мала паліва! Час ехаць на запраўку";
        }
        if (Constants.isRu()) {
            return "ВНИМАНИЕ! Осталось мало топлива! Пора ехать на заправку";
        }
        return "";
    }

}