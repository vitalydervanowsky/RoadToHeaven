package com.carloclub.roadtoheaven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FuelView extends ImageView {
    private Paint paint;
    public boolean isCurrent=false;

    public FuelView(Context context) {
        super(context);
        init();
    }

    public FuelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FuelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN); // Цвет квадрата
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //if (!isCurrent) return;

        int w = getWidth();
        int h = getHeight();
        int left = (int)(140*w/640);
        int right = (int)(30*w/640);
        int top = (int)(31*h/117);

        float prc = (float) Constants.DATAGAME.getFuel()/Constants.DATAGAME.getTank()/1000;

        int widthOfBar = w-left-right;
        canvas.drawRect(left, top, left+(int)(widthOfBar*prc), h-top-2, paint); // Рисуем квадрат
    }
}

