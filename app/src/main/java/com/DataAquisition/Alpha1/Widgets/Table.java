package com.DataAquisition.Alpha1.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

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
        int lineCount = 1;
        for(Map.Entry<String,Object> entry : this.dataMap.entrySet())
        {
            float centerX = canvas.getWidth()/4;
            float centerY = lineCount*((canvas.getHeight()/this.dataMap.size()));
            Rect bounds = new Rect();
            paint.getTextBounds(entry.getKey(),0,entry.getKey().length(),bounds);
            //Draw the Name
            canvas.drawText(entry.getKey(),centerX,centerY+bounds.centerY(),paint);
            //Draw the Value
            canvas.drawText(String.valueOf(entry.getValue()),centerX+(canvas.getWidth()/2),centerY+bounds.centerY(),paint);
            canvas.drawLine(0,lineCount*((canvas.getHeight()/this.dataMap.size())),canvas.getWidth(),lineCount*(canvas.getHeight()/this.dataMap.size()),paint);
            lineCount++;
        }

        paint.setColor(Color.RED);
        canvas.drawLine(canvas.getWidth()/2,0,canvas.getWidth()/2,canvas.getHeight(),paint);
    }

}
