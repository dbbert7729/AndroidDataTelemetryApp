package com.DataAquisition.Alpha1.HelperClasses;

import android.annotation.SuppressLint;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.util.Log;

import com.DataAquisition.Alpha1.Page1_Fragment;
import com.DataAquisition.Alpha1.Page2_Fragment;
import com.DataAquisition.Alpha1.Widgets.LargeGauge;
import com.DataAquisition.Alpha1.Widgets.RoundGauge;
import com.DataAquisition.Alpha1.Widgets.SmallBarGraph;
import com.DataAquisition.Alpha1.Widgets.Table;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Updates Widgets on Fragments
 *
 * @param  url  an absolute URL giving the base location of the image
 * @param  name the location of the image, relative to the url argument
 * @return      the image at the specified URL
 * @see         Image
 */

public class DataConnector extends AsyncTask<Float,Float,Float> {
    private String resp;
    LineChart chart;
    public static Page2_Fragment chartFragment = null;
    public static Page1_Fragment page1_fragment = null;

    public static class WidgetObjStruct {
        public WidgetObjStruct()
        {

        }
        public Object widgetObj;
        public int input;
    }
    List<WidgetObjStruct> widgetObjects;
    public DataConnector()
    {
        widgetObjects = new ArrayList<WidgetObjStruct>();

    }

    public void addWidgetObject(WidgetObjStruct obj)
    {
        this.widgetObjects.add(obj);
    }

    @Override
    protected void onProgressUpdate(Float... params)//Update Respective Elements
    {
        super.onProgressUpdate(params);

        int COUNT = 0;
        for(int index = 0; index < this.widgetObjects.size(); index++)
        {
            WidgetObjStruct widgetObjStruct = this.widgetObjects.get(index);
            if(widgetObjStruct.widgetObj.getClass().equals(RoundGauge.class))
            {
                RoundGauge gauge = (RoundGauge)widgetObjStruct.widgetObj;
                gauge.setValue(COUNT);
            }
            if(widgetObjStruct.widgetObj.getClass().equals(LargeGauge.class))
            {
                LargeGauge gauge = (LargeGauge)widgetObjStruct.widgetObj;
                gauge.setValue(COUNT);
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    float[] processMessageString(String message)
    {
        String[] Strvalues = message.split(",");
        float[] fltValues = new float[Strvalues.length];
        for(int index = 0; index < Strvalues.length; index++)
        {
            fltValues[index] = Float.parseFloat(Strvalues[index]);
        }
        return fltValues;
    }

    @SuppressLint("WrongThread")
    @Override
    protected Float doInBackground(Float... params)
    {
        float INPUT_0 = 0;
        float INPUT_1 = 50;
        while(true)
        {
            for(int index = 0; index < this.widgetObjects.size(); index++)
            {
                WidgetObjStruct widgetObjStruct = this.widgetObjects.get(index);

                    if(widgetObjStruct.widgetObj.getClass().equals(RoundGauge.class)) {
                        if (widgetObjStruct.input == 0) {
                            RoundGauge gauge = (RoundGauge) widgetObjStruct.widgetObj;
                            gauge.setValue(INPUT_0);
                            //INPUT_0++;
                            INPUT_0 = INPUT_0 % 100;
                        }

                        if (widgetObjStruct.input == 1) {
                            RoundGauge gauge = (RoundGauge) widgetObjStruct.widgetObj;
                            gauge.setValue(INPUT_1);
                            INPUT_1++;
                            INPUT_1 = INPUT_1 % 100;
                        }
                    }
                    if(widgetObjStruct.widgetObj.getClass().equals(SmallBarGraph.class)) {
                        if (widgetObjStruct.input == 0) {
                            SmallBarGraph smallBarGraph = (SmallBarGraph) widgetObjStruct.widgetObj;
                            smallBarGraph.setValue(INPUT_0);
                            //INPUT_0++;
                            INPUT_0 = INPUT_0 % 100;
                        }

                        if (widgetObjStruct.input == 1) {
                            SmallBarGraph smallBarGraph = (SmallBarGraph) widgetObjStruct.widgetObj;
                            smallBarGraph.setValue(INPUT_1);
                            INPUT_1 = INPUT_1 + 25;
                            INPUT_1 = INPUT_1 % 550;
                        }
                    }
                    if(widgetObjStruct.widgetObj.getClass().equals(LargeGauge.class))
                    {
                        if(widgetObjStruct.input == 0) {
                            LargeGauge largeGauge = (LargeGauge) widgetObjStruct.widgetObj;
                            largeGauge.setName(String.valueOf(INPUT_0));
                            INPUT_0 = INPUT_0 + 0.001f;
                            //INPUT_0 = INPUT_0 % 100;
                            largeGauge.setValue(INPUT_0);
                        }
                        else if(widgetObjStruct.input == 1) {
                            LargeGauge largeGauge = (LargeGauge) widgetObjStruct.widgetObj;
                            largeGauge.setName("Rpm");
                            INPUT_1++;
                            INPUT_1 = INPUT_1 % 100;
                            largeGauge.setValue(INPUT_1);
                        }
                    }
                    if(widgetObjStruct.widgetObj.getClass().equals(Table.class))
                    {
                        if(widgetObjStruct.input == 0)
                        {
                            Table table = (Table)widgetObjStruct.widgetObj;
                            INPUT_0 = INPUT_0 + 1;
                            table.setValue("Value", (int)INPUT_0);
                            table.setValue("Trans", (int)INPUT_0);
                        }
                    }
                }
                try
                {
                    Thread.sleep(5);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }

