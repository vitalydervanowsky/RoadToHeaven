package com.carloclub.roadtoheaven;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carloclub.roadtoheaven.gallery.GalleryActivity;

public class DialogMessage {
    public static void showMessage(int imageBig, int imageSmall, String text, String bonus, Activity activity) {
        showMessage(imageBig, imageSmall, text, bonus, activity, 0);
    }

    public static void showMessage(int imageBig, int imageSmall, String text, String bonus, Activity activity, int idSourcePersona) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_message);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (imageBig!=0)
            ((ImageView)dialog.findViewById(R.id.imageViewBig)).setImageResource(imageBig);
        else
            ((ImageView)dialog.findViewById(R.id.imageViewBig)).setVisibility(View.INVISIBLE);

        if (imageSmall!=0)
            ((ImageView)dialog.findViewById(R.id.imageViewSmall)).setImageDrawable(activity.getDrawable(imageSmall));
        else
            ((ImageView)dialog.findViewById(R.id.imageViewSmall)).setVisibility(View.INVISIBLE);
        if (idSourcePersona!=0)
            ((ImageView)dialog.findViewById(R.id.imagePersona)).setImageResource(idSourcePersona);

        ((TextView)dialog.findViewById(R.id.textView)).setText(text);

        ((TextView)dialog.findViewById(R.id.textViewBonus)).setText(bonus);


        Button buttonClose3= dialog.findViewById(R.id.OK);
        buttonClose3.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setOnDismissListener(thisDialog -> {
            // выполнится при закрытии диалога
            if (activity instanceof MapActivity) {
                Task.startNextTask((MapActivity) activity);
            }
            if (activity instanceof GalleryActivity) {
                activity.finish();
            }
        });

        dialog.show();
    }
}
