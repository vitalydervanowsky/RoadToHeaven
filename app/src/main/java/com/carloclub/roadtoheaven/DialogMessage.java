package com.carloclub.roadtoheaven;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DialogMessage {
    public static Dialog dialog;
    public final static void showMessage(int imageBig, int imageSmall, String text, String bonus, AppCompatActivity activity){
        showMessage(imageBig, imageSmall, text, bonus, activity, R.layout.dialog_message);
    }

    public final static void showMessage(int imageBig, int imageSmall, String text, String bonus, AppCompatActivity activity, int idSource){
        dialog = new Dialog(activity);
        dialog.setContentView(idSource);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (imageBig!=0)
            ((ImageView)dialog.findViewById(R.id.imageViewBig)).setImageResource(imageBig);
        else
            ((ImageView)dialog.findViewById(R.id.imageViewBig)).setVisibility(View.INVISIBLE);

        if (imageSmall!=0)
            ((ImageView)dialog.findViewById(R.id.imageViewSmall)).setImageDrawable(activity.getDrawable(imageSmall));
        else
            ((ImageView)dialog.findViewById(R.id.imageViewSmall)).setVisibility(View.INVISIBLE);

        ((TextView)dialog.findViewById(R.id.textView)).setText(text);

        ((TextView)dialog.findViewById(R.id.textViewBonus)).setText(bonus);


        Button buttonClose3= dialog.findViewById(R.id.OK);
        buttonClose3.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
