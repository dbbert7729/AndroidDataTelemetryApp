package com.DataAquisition.Alpha1.HelperClasses;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.DataAquisition.Alpha1.Page1_Fragment;
import com.DataAquisition.Alpha1.Page2_Fragment;
import com.DataAquisition.Alpha1.Widgets.LargeGauge;
import com.DataAquisition.Alpha1.Widgets.RoundGauge;
import com.DataAquisition.Alpha1.Widgets.SmallBarGraph;
import com.DataAquisition.Alpha1.Widgets.Table;
import com.github.mikephil.charting.charts.LineChart;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Provides a mechanism to connect to a uC; Updates Widgets on Fragments
 *
 */

public class DataConnector extends AsyncTask<Float,Float,Float> implements SerialInputOutputManager.Listener {
    private String resp;
    LineChart chart;
    public static Page2_Fragment chartFragment = null;
    public static Page1_Fragment page1_fragment = null;

    float DATA_CHANNEL_0 = 0;
    float DATA_CHANNEL_1 = 50;

    @Override
    public void onNewData(byte[] data) {
        String s = new String(data);
        int i = 0;
    }

    @Override
    public void onRunError(Exception e) {

    }


    public static class WidgetObjStruct {
        public WidgetObjStruct()
        {

        }
        public Object widgetObj;
        public int input;
    }


    List<WidgetObjStruct> widgetObjects;
    public DataConnector(Context context) throws IOException {
        widgetObjects = new ArrayList<WidgetObjStruct>();
        //Setup USB device
        UsbManager manager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        UsbSerialDriver driver = availableDrivers.get(0);

        final Boolean[] granted = {null};
        BroadcastReceiver usbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                granted[0] = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
            }
        };
        PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
        IntentFilter filter = new IntentFilter("com.android.example.USB_PERMISSION");
        context.registerReceiver(usbReceiver, filter);
        manager.requestPermission(driver.getDevice(),permissionIntent);

        UsbSerialPort usbSerialPort = driver.getPorts().get(0);
        UsbDeviceConnection usbConnection = manager.openDevice(driver.getDevice());
        usbSerialPort.open(usbConnection);
        usbSerialPort.setParameters(9600,UsbSerialPort.DATABITS_8,UsbSerialPort.STOPBITS_1,UsbSerialPort.PARITY_NONE);
        usbSerialPort.setDTR(true);
        usbSerialPort.setRTS(true);
        SerialInputOutputManager inputOutputManager = new SerialInputOutputManager(usbSerialPort,this);
        //Start the receiving thread for serial
        Executors.newSingleThreadExecutor().submit(inputOutputManager);
    }

    public void addWidgetObject(WidgetObjStruct obj)
    {
        this.widgetObjects.add(obj);
    }

    @Override
    protected void onProgressUpdate(Float... params)//Update Respective Elements
    {
        super.onProgressUpdate(params);
    }

    @SuppressLint("WrongThread")
    @Override
    protected Float doInBackground(Float... params)
    {

        while(true)
        {
            for(int index = 0; index < this.widgetObjects.size(); index++)
            {
                WidgetObjStruct widgetObjStruct = this.widgetObjects.get(index);

                    if(widgetObjStruct.widgetObj.getClass().equals(RoundGauge.class)) {
                        if (widgetObjStruct.input == 0) {
                            RoundGauge gauge = (RoundGauge) widgetObjStruct.widgetObj;
                            gauge.setValue(DATA_CHANNEL_0);
                            //DATA_CHANNEL_0++;
                            DATA_CHANNEL_0 = DATA_CHANNEL_0 % 100;
                        }

                        if (widgetObjStruct.input == 1) {
                            RoundGauge gauge = (RoundGauge) widgetObjStruct.widgetObj;
                            gauge.setValue(DATA_CHANNEL_1);
                            DATA_CHANNEL_1++;
                            DATA_CHANNEL_1 = DATA_CHANNEL_1 % 100;
                        }
                    }
                    if(widgetObjStruct.widgetObj.getClass().equals(SmallBarGraph.class)) {
                        if (widgetObjStruct.input == 0) {
                            SmallBarGraph smallBarGraph = (SmallBarGraph) widgetObjStruct.widgetObj;
                            smallBarGraph.setValue(DATA_CHANNEL_0);
                            //DATA_CHANNEL_0++;
                            DATA_CHANNEL_0 = DATA_CHANNEL_0 % 100;
                        }

                        if (widgetObjStruct.input == 1) {
                            SmallBarGraph smallBarGraph = (SmallBarGraph) widgetObjStruct.widgetObj;
                            smallBarGraph.setValue(DATA_CHANNEL_1);
                            DATA_CHANNEL_1 = DATA_CHANNEL_1 + 25;
                            DATA_CHANNEL_1 = DATA_CHANNEL_1 % 550;
                        }
                    }
                    if(widgetObjStruct.widgetObj.getClass().equals(LargeGauge.class))
                    {
                        if(widgetObjStruct.input == 0) {
                            LargeGauge largeGauge = (LargeGauge) widgetObjStruct.widgetObj;
                            largeGauge.setName(String.valueOf(DATA_CHANNEL_0));
                            DATA_CHANNEL_0 = DATA_CHANNEL_0 + 0.001f;
                            //DATA_CHANNEL_0 = DATA_CHANNEL_0 % 100;
                            largeGauge.setValue(DATA_CHANNEL_0);
                        }
                        else if(widgetObjStruct.input == 1) {
                            LargeGauge largeGauge = (LargeGauge) widgetObjStruct.widgetObj;
                            largeGauge.setName("Rpm");
                            DATA_CHANNEL_1++;
                            DATA_CHANNEL_1 = DATA_CHANNEL_1 % 100;
                            largeGauge.setValue(DATA_CHANNEL_1);
                        }
                    }
                    if(widgetObjStruct.widgetObj.getClass().equals(Table.class))
                    {
                        if(widgetObjStruct.input == 0)
                        {
                            Table table = (Table)widgetObjStruct.widgetObj;
                            DATA_CHANNEL_0 = DATA_CHANNEL_0 + 1;
                            table.setValue("Value", (int)DATA_CHANNEL_0);
                            table.setValue("Trans", (int)DATA_CHANNEL_0);
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

