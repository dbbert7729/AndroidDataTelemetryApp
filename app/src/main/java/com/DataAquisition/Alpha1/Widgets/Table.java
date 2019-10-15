package com.DataAquisition.Alpha1.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;

public class Table extends View {
    private HashMap<String,Object> dataMap;
    public Table(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.dataMap = new HashMap<String,Object>();
    }

    public Table(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,canvas.getWidth()-1,canvas.getHeight()-1,paint);
    }

}
