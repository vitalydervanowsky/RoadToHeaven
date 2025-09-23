package com.carloclub.roadtoheaven;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogMessage {
    public static Dialog dialog;
    public final static void showMessage(int imageBig, int imageSmall, String text, String bonus, MapActivity activity){
        showMessage(imageBig, imageSmall, text, bonus, activity, 0);
    }

    public final static void showMessage(int imageBig, int imageSmall, String text, String bonus, MapActivity activity, int idSourcePersona){
        dialog = new Dialog(activity);
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
            Task.startNextTask(activity);
        });

        dialog.show();
    }
}
