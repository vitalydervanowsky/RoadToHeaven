package com.carloclub.roadtoheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class Messages{

    public static String getMessageGotStone() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Віншуем! Вы здабылі 1 камень";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Поздравляем! Вы добыли 1 камень";
        }
        return "";
    }

    public static String getMessageGotAllStones() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Віншуем! Усе камяні сабрнаыя. Можна ехаць будаваць мост.";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Поздравляем! Все камни собраны. Можно ехать строить мост.";
        }
        return "";
    }

    public static String getMessageHowManyStonesGot() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Сабрана: ";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Собрано: ";
        }
        return "";
    }

    public static String getMessageTechnicalBreak() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Тэхнічны перапынак: 5 хвілін";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Технический перерыв: 5 минут";
        }
        return "";
    }

    public static String getMessageGalleryTechnicalBreak() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "В галерее тэхнічны перапынак: 5 хвілін";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "В галерее технический перерыв: 5 минут";
        }
        return "";
    }

    public static String getMessageNotEnoughStones() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Недастаткова камянёў! Каб пачаць будаваць мост, трэба сабраць 7 камянёў";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Недостаточно камней! Чтобы начать строить мост, нужно собрать 7 камней";
        }
        return "";
    }

    public static String getMessageMistake() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "На жаль, ты памыліўся. Паспрабуй яшчэ раз";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "К сожалению, ты ошибся. Попробуй ещё раз";
        }
        return "";
    }

    public static String getMessageFail() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Не атрымалася((( Паспрабуйце яшчэ раз";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Не получилось((( Попробуйте ещё раз";
        }
        return "";
    }

    public static String getMessageNotEnoughMoneyForBurger() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Недастаткова сродкаў для пакупкі бургера";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Недостаточно средств для покупки бургера";
        }
        return "";
    }

    public static String getMessageMistakeWithRuby() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Нажаль, ты памыліўся, але рубін дапамогі цябе ўратаваў";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "К сожалению, ты ошибся, но рубин помощи тебя спас";
        }
        return "";
    }

    public static String getMessageYouHaveSnack() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Цяпер у цябе ёсць перакус у дарогу або...  ";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Теперь у тебя есть перекус в дорогу или...  ";
        }
        return "";
    }

    public static String getMessageYouGetRubyHelp() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "За тваё добрае сэрца ты атрымліваеш 1 Рубін Дапамогі";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "За Твоё доброе сердце Ты полчаешь 1 Рубин Помощи";
        }
        return "";
    }

    public static String getMessageTankIsFull() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Бак напоўнены. Можна ехаць!";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Бак наполнен. Можно ехать!";
        }
        return "";
    }

    public static String getMessageYouHaveNoBurgers() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "У заплечніку няма бургераў. Але ты можаш купіць іх у бургернай";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "В рюкзаке нет бургеров. Но их можно купить в бургерной";
        }
        return "";
    }

    public static String getMessageFromPilgrim() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Вітанкі! Мяне завуць Караліна. Я еду аўтастопам у санктуарый святога Антонія. Калі ты мяне падвязеш, я буду табе вельмі ўдзячна.";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Привет! Меня зовут Каролина. Я еду автоспом в санктуарий святого Антония. Если ты меня подвезешь, я буду тебе очень благодарна.";
        }
        return "";
    }

    public static String getMessageNotEnoughMoney() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "У Вас недастаткова манет";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "У Вас недостаточно монет";
        }
        return "";
    }

    public static String getMessageGetBalance() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Астача: ";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Остаток: ";
        }
        return "";
    }

    public static String getMessageThanks() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Дзякуй!";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Спасибо!";
        }
        return "";
    }

    public static String getMessageKnowAnswer() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Ведаю адказ";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Знаю ответ";
        }
        return "";
    }

    public static String getMessageGetHint() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Адкрыць падказку";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Открыть подсказку";
        }
        return "";
    }

    public static String getMessageDoMosaic() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Збярыце мазайку, перасунуўшы фрагменты не больш за 14 разоў";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Соберите мозайку, передвинув фрагменты не более 14 раз";
        }
        return "";
    }

    public static String getMessageCatchPinkCat() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Злавіце ружовага ката";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Словите розового кота";
        }
        return "";
    }

    public static String getMessageCarryCatInZoo() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Адвязіце ката ў ЗОО краму";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Отвезите кота в ЗОО магазин";
        }
        return "";
    }

    public static String getMessageThanksGoodManGetPrise() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Дзякуй, добры чалавек! Атрымай сваю заслужанаю ўзнагароду: 1 000 манет.";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Спасибо, добрый человек! Получи же свою заслуженную награду: 1 000 монет.";
        }
        return "";
    }

    public static String getMessageGoodLuck() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Поспехаў!";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "Удачи!";
        }
        return "";
    }

    public static String getMessageAttentionFewFuel() {
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_BY)) {
            return "Увага! засталося мала паліва! Час ехаць на запраўку";
        }
        if (Constants.DATAGAME.currentLang.equals(Constants.LANG_RU)) {
            return "ВНИМАНИЕ! Осталось мало топлива! Пора ехать на заправку";
        }
        return "";
    }

}