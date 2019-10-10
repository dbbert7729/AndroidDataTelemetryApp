package com.DataAquisition.Alpha1.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class LargeGauge extends View {
    public LargeGauge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    int count = 0;
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);

        RectF outerRect = new RectF(50,50,canvas.getWidth()-50,canvas.getWidth()-50);
        RectF innerRect = new RectF(outerRect.left+150, outerRect.top+150,outerRect.right-150,outerRect.bottom-150);

        Path path = new Path();
        int arc_sweep = +180;
        int arc_ofset = 180;
        path.arcTo(outerRect,arc_ofset,arc_sweep);
        path.arcTo(innerRect,arc_ofset+arc_sweep,-arc_sweep);
        path.close();
        //canvas.drawRect(innerRect,paint);
        //canvas.drawRect(outerRect,paint);
        Paint fill = new Paint();
        fill.setStrokeWidth(3);
        fill.setStyle(Paint.Style.STROKE);
        fill.setColor(Color.GREEN);
        //canvas.drawPath(path,fill);
        //Draw more detail

        path = new Path();
        count++;
        arc_sweep = +count;
        count = count %180;
        arc_ofset = 180;

        outerRect.left = outerRect.left +5;
        outerRect.top = outerRect.top + 5;
        outerRect.right = outerRect.right -5;
        outerRect.bottom = outerRect.bottom - 5;
        innerRect.left = innerRect.left -5;
        innerRect.top = innerRect.top - 5;
        innerRect.right = innerRect.right +5;
        innerRect.bottom = innerRect.bottom + 5;

        path.arcTo(outerRect,arc_ofset,arc_sweep);
        path.arcTo(innerRect,arc_ofset+arc_sweep,-arc_sweep);
        path.close();
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(Color.rgb(0,255,150));
        canvas.drawPath(path,fill);
        paint.setStrokeWidth(2);
        for(int i = 1; i < 12; i++)
        {
            paint.setColor(Color.YELLOW);
            canvas.rotate(15, innerRect.centerX(), innerRect.centerY());
            canvas.drawLine(innerRect.left,innerRect.centerY(),outerRect.left,outerRect.centerY(), paint);
        }
        canvas.setMatrix(new Matrix());
        for(int i = 1; i < 6; i++)
        {
            paint.setColor(Color.RED);
            canvas.rotate(30, innerRect.centerX(), innerRect.centerY());
            canvas.drawLine(innerRect.left+35,innerRect.centerY(),outerRect.left-35,outerRect.centerY(), paint);
        }
        //Draw the Value and Units
        canvas.setMatrix(new Matrix());
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(150);
        canvas.drawText(String.valueOf(count),innerRect.centerX(),innerRect.centerY(),paint);



        try {
            Thread.sleep(5);
            this.invalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
