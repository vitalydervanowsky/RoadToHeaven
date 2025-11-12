package com.carloclub.roadtoheaven;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.gallery.GalleryActivity;
import com.carloclub.roadtoheaven.model.DialogButton;

public class DialogMessage {
    public static void showMessage(int imageBig, int imageSmall, String text, String bonus, Activity activity) {
        showMessage(imageBig, imageSmall, text, bonus, activity, 0, null, null);
    }

    public static void showMessage(int imageBig, int imageSmall, String text, String bonus, Activity activity, int idSourcePersona) {
        showMessage(imageBig, imageSmall, text, bonus, activity, idSourcePersona, null, null);
    }

    public static void showMessage(
            int imageBig,
            int imageSmall,
            String text,
            String bonus,
            Activity activity,
            int idSourcePersona,
            DialogButton yesDialogButton,
            DialogButton noDialogButton
    ) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_message);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            WindowCompat.setDecorFitsSystemWindows(window, false);
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
        if (imageBig!=0)
            ((ImageView)dialog.findViewById(R.id.imageViewBig)).setImageResource(imageBig);
        else
            ((ImageView)dialog.findViewById(R.id.imageViewBig)).setVisibility(View.GONE);

        if (imageSmall!=0)
            ((ImageView)dialog.findViewById(R.id.imageViewSmall)).setImageDrawable(activity.getDrawable(imageSmall));
        else
            ((ImageView)dialog.findViewById(R.id.imageViewSmall)).setVisibility(View.GONE);
        if (idSourcePersona!=0)
            ((ImageView)dialog.findViewById(R.id.imagePersona)).setImageResource(idSourcePersona);

        ((TextView) dialog.findViewById(R.id.textView)).setText(text);

        if (bonus != null) {
            TextView bonusView = dialog.findViewById(R.id.textViewBonus);
            bonusView.setText(bonus);
            bonusView.setVisibility(View.VISIBLE);
        }

        Button yesButton = dialog.findViewById(R.id.yesButton);
        yesButton.setText(yesDialogButton != null ? yesDialogButton.getTitle() : "OK");
        yesButton.setOnClickListener(v -> {
            if (yesDialogButton != null) {
                yesDialogButton.getAction().invoke();
            }
            dialog.dismiss();
        });

        if (noDialogButton != null) {
            Button noButton = dialog.findViewById(R.id.noButton);
            noButton.setText(noDialogButton.getTitle());
            noButton.setVisibility(View.VISIBLE);
            noButton.setOnClickListener(v -> {
                noDialogButton.getAction().invoke();
            });
        }

        dialog.setOnDismissListener(thisDialog -> {
            // выполнится при закрытии диалога
            if (activity instanceof MapActivity) {
                ((MapActivity) activity).startNextTask();
            }
            if (activity instanceof GalleryActivity) {
                activity.finish();
            }
        });

        dialog.show();
    }
}
