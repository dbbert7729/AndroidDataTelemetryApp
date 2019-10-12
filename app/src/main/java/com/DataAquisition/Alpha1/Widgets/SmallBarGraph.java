package com.DataAquisition.Alpha1.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class SmallBarGraph extends View
{
    private  String Name = "Name";
    private  String Unit = "Unit";
    private  float Value = 0.0f;

    public SmallBarGraph(Context context) {
        super(context);
    }

    public void setNameAndUnit(String NAME, String UNIT)
    {
        Name = NAME;
        Unit = UNIT;
    }

    public void setValue(float value)
    {
        this.Value = value;
        this.invalidate();
    }
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        float[] ARRAY = {5.0f,4.0f,3.0f,2.0f,1.0f,0.0f};
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.YELLOW);
        //canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
        //Draw Scale Lines and Scale Numbers
        paint.setStrokeWidth(4);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.LEFT);
        for(int i = 1; i < 5; i++)
        {
            paint.setColor(Color.RED);
            canvas.drawLine(canvas.getWidth()*.2f,i*150,canvas.getWidth()-canvas.getWidth()*.2f,i*150,paint);
            Rect bounds = new Rect();
            paint.getTextBounds(String.valueOf(ARRAY[i]),0,String.valueOf(ARRAY[i]).length(),bounds);
            paint.setColor(Color.rgb(40,150,80));
            canvas.drawText(String.valueOf(ARRAY[i]),bounds.centerX(),i*150-bounds.centerY(),paint);
        }
        //Draw Name
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Name,(canvas.getWidth()/2),canvas.getHeight()*.93f,paint);
        //Draw Unit
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(Unit,canvas.getWidth()*0.93f,canvas.getHeight()*.93f,paint);
        //Draw Bar
        //when Value = canvas.getHeight()*.76f the bar is 0 height.
        paint.setColor(Color.YELLOW);
        canvas.drawRect((canvas.getWidth()/2)-75,canvas.getHeight()*.76f-Value,(canvas.getWidth()/2)+75,canvas.getHeight()*.76f,paint);
    }

}
