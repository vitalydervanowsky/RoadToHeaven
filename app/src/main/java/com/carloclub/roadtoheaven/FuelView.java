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

        int left = 0;
        int top = 0;
        int right = getWidth();
        int bottom = getHeight();

        float prc = (float) Constants.DATAGAME.getFuel()/Constants.DATAGAME.getTank()/1000;
        canvas.drawRect(left+(int)(140*right/640), top+(int)(31*bottom/117), (int)((right-(int)(30*right/640))*prc), bottom-(int)(32*bottom/117), paint); // Рисуем квадрат
    }
}

