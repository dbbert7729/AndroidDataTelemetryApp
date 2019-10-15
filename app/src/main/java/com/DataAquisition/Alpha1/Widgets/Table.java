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
        this.dataMap.put("Oil",55);
        this.dataMap.put("Temp",150);
        this.dataMap.put("TEST",155);
    }

    public void setValue(String Row, String Value)
    {
        this.dataMap.put(Row,Value);
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
        //Draw the lines for the table
        paint.setColor(Color.GREEN);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(150);
        for(int i = 1; i < this.dataMap.size(); i++)
        {
            float centerX = canvas.getWidth()/4;
            float centerY = i*((canvas.getHeight()/this.dataMap.size()));
            canvas.drawText("Test",centerX,centerY,paint);
            canvas.drawLine(0,i*((canvas.getHeight()/this.dataMap.size())),canvas.getWidth(),i*(canvas.getHeight()/this.dataMap.size()),paint);
        }
        paint.setColor(Color.RED);
        canvas.drawLine(canvas.getWidth()/2,0,canvas.getWidth()/2,canvas.getHeight(),paint);
    }

}
