package com.DataAquisition.Alpha1;

import android.os.AsyncTask;

import androidx.preference.PreferenceManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataConnector extends AsyncTask<Float,Float,Float> {
    private String resp;
    LineChart chart;
    public static Chart_Fragment chartFragment = null;
    public static Page1_Fragment page1_fragment = null;
    List<Entry> entries;
    DataConnector()
    {
        entries = new ArrayList<Entry>();
    }
    int datacount = 0;

    @Override
    protected void onProgressUpdate(Float... params)//Update Respective Elements
    {
        super.onProgressUpdate(params);
        if(this.chartFragment != null && this.page1_fragment != null) {
            chart = (LineChart) chartFragment.getView().findViewById(R.id.chart);
            entries.add(new Entry(datacount, params[2]));
            LineDataSet dataSet = new LineDataSet(entries, "OIL Pressure");
            dataSet.setLineWidth(10);
            LineData lineData = new LineData(dataSet);
            datacount++;
            if (datacount > 60) {
                datacount = 0;
                entries = new ArrayList<Entry>();
            }
            chart.setData(lineData);
            chart.invalidate();
            //Update Gauge(s)
            //this.page1_fragment.gasGauge.setValue(params[2]);
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

    @Override
    protected Float doInBackground(Float... params){
        try {
            while(true) {
                InetAddress host = InetAddress.getLocalHost();
                Socket socket = null;
                ObjectOutputStream oos = null;
                BufferedReader ois = null;
                //establish socket connection to server
                String ip = PreferenceManager.getDefaultSharedPreferences(chartFragment.getActivity()).getString("serverAddress",null);
                socket = new Socket(ip, 8080);
                System.out.println(socket.getInetAddress().toString());
                //write to socket using ObjectOutputStream
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending request to Socket DataConnector");
                oos.writeObject("%");
                ois = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = (String) ois.readLine();
                System.out.println("Message: " + message);
                if(message != null) {
                    float[] data = processMessageString(message);
                    publishProgress(data[0], data[1], data[2]);
                }

                //close resources
                ois.close();
                oos.close();
                Thread.sleep(10);
            }
        } catch (Exception ex) {
            int i = 0;
        }

        return Float.valueOf(1);
    }
}