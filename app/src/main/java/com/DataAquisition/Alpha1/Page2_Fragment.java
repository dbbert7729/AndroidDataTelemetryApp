package com.DataAquisition.Alpha1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.DataAquisition.Alpha1.HelperClasses.DataConnector;
import com.DataAquisition.Alpha1.Widgets.LargeGauge;

public class Page2_Fragment extends Fragment{



////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.chart1_fragment_layout, container,false);
        rootView.setBackgroundColor(Color.BLACK);
        LargeGauge largeGauge = (LargeGauge)rootView.findViewById(R.id.largeGauge);
        MainActivity mainActivity = (MainActivity)getActivity();
        DataConnector.WidgetObjStruct widgetObjStruct = new DataConnector.WidgetObjStruct();
        widgetObjStruct.input = 3;
        widgetObjStruct.widgetObj = largeGauge;
        mainActivity.dataConnector.addWidgetObject(widgetObjStruct);
        return rootView;
    }
}
