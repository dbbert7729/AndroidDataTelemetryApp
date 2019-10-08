package com.DataAquisition.Alpha1.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class RoundGauge extends View
{
    private float gaugeValue = 0.0f;
    private String gaugeName = "TEST";
    public RoundGauge(Context context) {
        super(context);

    }

    public void setValue(float value)
    {
        this.gaugeValue = value;
        this.invalidate();
    }

    public void setText(String name)
    {
        this.gaugeName = name;
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        //Draw White Outline Rectangle
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
        //Draw Gauge
        RectF r = new RectF((getWidth()/2)-350,(getHeight()/2)-350,(getWidth()/2)+350,(getHeight()/2)+350);

        paint.setColor(Color.RED);
        canvas.drawArc(r,0,-30,false,paint);
        paint.setColor(Color.YELLOW);
        canvas.drawArc(r,-30,-40,false,paint);
        paint.setColor(Color.GREEN);
        canvas.drawArc(r,-70,-110,false,paint);
        //Draw Text
        paint.setTextSize(150);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Roboto", Typeface.ITALIC));
        canvas.drawText(this.gaugeName,canvas.getWidth()/2,(canvas.getHeight()/2)+250,paint);
        paint.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
        paint.setTextSize(75);
        paint.setColor(Color.RED);
        canvas.drawText(String.valueOf(this.gaugeValue),canvas.getWidth()*0.88f,canvas.getHeight()*0.92f,paint);
        //Draw the Arrow
        canvas.save();
        canvas.rotate(this.gaugeValue,r.centerX(),r.centerY());

        canvas.drawLine(r.centerX(),r.centerY(),r.left,r.centerY(),paint);

    }

}
