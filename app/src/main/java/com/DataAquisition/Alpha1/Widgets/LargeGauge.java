package com.DataAquisition.Alpha1.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class LargeGauge extends View {
    public LargeGauge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    float count = 0;
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);

        RectF outerRect = new RectF(50,50,canvas.getWidth()-50,canvas.getWidth()-50);
        RectF innerRect = new RectF(outerRect.left+300, outerRect.top+300,outerRect.right-300,outerRect.bottom-300);
        //canvas.drawRect(outerRect,paint);
        //canvas.drawRect(innerRect,paint);
        Path circle = new Path();
        circle.addArc(outerRect,0,360);
        canvas.drawPath(circle,paint);
        paint.setTextSize(150);
        canvas.drawLine(outerRect.left,outerRect.centerY(),outerRect.left+75,outerRect.centerY(),paint);
        canvas.rotate(-30,outerRect.centerX(),outerRect.centerY());
        paint.setColor(Color.YELLOW);
        for(int i = 0; i < 49; i++)
        {
            canvas.drawLine(outerRect.left,outerRect.centerY(),outerRect.left+50,outerRect.centerY(),paint);
            canvas.rotate(5,outerRect.centerX(),outerRect.centerY());
        }
        canvas.setMatrix(new Matrix());
        paint.setStrokeWidth(paint.getStrokeWidth()+2);
        paint.setColor(Color.BLUE);
        for(int i = 0; i < 8; i++)
        {
            canvas.drawLine(outerRect.left,outerRect.centerY(),outerRect.left+75,outerRect.centerY(),paint);
            canvas.rotate(30,outerRect.centerX(),outerRect.centerY());
        }
        canvas.setMatrix(new Matrix());
        paint.setTextSize(125);
        paint.setTextAlign(Paint.Align.CENTER);
        //Draw the Needle
        float x1 = (float) (((outerRect.width()/2)-0)*Math.cos(count));
        float y1 = (float) (((outerRect.width()/2)-0)*Math.sin(count));
        canvas.drawLine(outerRect.centerX(),outerRect.centerY(),outerRect.centerX()+x1,outerRect.centerY()+y1,paint);
        int numCount = 0;
        for(double i = -Math.PI/6; i < Math.PI+Math.PI/6; i = i + Math.PI /6)
        {

            Rect bounds = new Rect();
            paint.getTextBounds(String.valueOf(5),0,String.valueOf(5).length(),bounds);
            float x = (float) (((outerRect.width()/2)-150)*Math.cos(i));
            float y = (float) (((outerRect.width()/2)-150)*Math.sin(i));
            x = x*-1;
            y = y*-1;
            //canvas.drawLine(outerRect.centerX(),outerRect.centerY(),outerRect.centerX()+x,outerRect.centerY()+y,paint);
            paint.setColor(Color.RED);
            canvas.drawText(String.valueOf(numCount),outerRect.centerX()+x-bounds.centerX()+30,outerRect.centerY()+y-bounds.centerY()+0,paint);
            numCount++;
        }

        //Draw Gauge Name
        canvas.drawText("NAME",outerRect.centerX(),outerRect.centerY()+400,paint);

        count = count + 0.1f;
        try {
            Thread.sleep(50);
            this.invalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
