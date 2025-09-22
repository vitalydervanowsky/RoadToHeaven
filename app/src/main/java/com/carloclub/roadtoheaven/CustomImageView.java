package com.carloclub.roadtoheaven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {
    private Paint paint;
    public boolean isCurrent=false;

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED); // Цвет квадрата
        paint.setStrokeWidth(8); // Толщина линии
        paint.setStyle(Paint.Style.STROKE); // Режим рисования (обводка)
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isCurrent) return;

        int left = 0;
        int top = 0;
        int right = getWidth();
        int bottom = getHeight();
        canvas.drawRect(left, top, right, bottom, paint); // Рисуем квадрат
    }
}

