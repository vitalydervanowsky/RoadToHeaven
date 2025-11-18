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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.gallery.GalleryActivity;
import com.carloclub.roadtoheaven.model.DialogButton;

public class DialogMessage {
    public static void showMessage(int imageBig, int imageSmall, String text, String bonus, Activity activity) {
        showMessage(imageBig, imageSmall, text, bonus, activity, 0, null, null, null);
    }

    public static void showMessage(int imageBig, int imageSmall, String text, String bonus, Activity activity, int idSourcePersona) {
        showMessage(imageBig, imageSmall, text, bonus, activity, idSourcePersona, null, null, null);
    }

    public static void showMessage(
            int imageBig,
            int imageSmall,
            String text,
            String bonus,
            Activity activity,
            int idSourcePersona,
            DialogButton yesDialogButton,
            DialogButton noDialogButton,
            Integer backgroundRes
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
        ConstraintLayout root = dialog.findViewById(R.id.root);
        if (backgroundRes != null) {
            if (backgroundRes == 0) {
                root.setBackground(null);
            } else {
                root.setBackgroundResource(backgroundRes);
            }
        }
        ImageView imageViewBig = dialog.findViewById(R.id.imageViewBig);
        if (imageBig != 0) {
            imageViewBig.setImageResource(imageBig);
        } else {
            imageViewBig.setVisibility(View.GONE);
        }

        ImageView imageViewSmall = dialog.findViewById(R.id.imageViewSmall);
        if (imageSmall != 0) {
            imageViewSmall.setImageResource(imageSmall);
        } else {
            imageViewSmall.setVisibility(View.GONE);
        }

        if (idSourcePersona != 0) {
            ImageView imageViewPersona = dialog.findViewById(R.id.imagePersona);
            imageViewPersona.setImageResource(idSourcePersona);
        }

        ((TextView) dialog.findViewById(R.id.textView)).setText(text);

        if (bonus != null) {
            TextView bonusView = dialog.findViewById(R.id.textViewBonus);
            bonusView.setText(bonus);
            bonusView.setVisibility(View.VISIBLE);
        }

        Button yesButton = dialog.findViewById(R.id.yesButton);
        yesButton.setText(yesDialogButton != null ? yesDialogButton.getTitle() : "OK");
        yesButton.setOnClickListener(v -> {
            if (yesDialogButton != null && yesDialogButton.getListener() != null) {
                yesDialogButton.getListener().onButtonClicked();
            }
            dialog.dismiss();
        });

        if (noDialogButton != null) {
            Button noButton = dialog.findViewById(R.id.noButton);
            noButton.setText(noDialogButton.getTitle());
            noButton.setVisibility(View.VISIBLE);
            noButton.setOnClickListener(v -> {
                if (noDialogButton.getListener() != null) {
                    noDialogButton.getListener().onButtonClicked();
                }
                dialog.dismiss();
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
