package com.DataAquisition.Alpha1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.DataAquisition.Alpha1.HelperClasses.DataConnector;
import com.DataAquisition.Alpha1.Widgets.LargeGauge;
import com.DataAquisition.Alpha1.Widgets.RoundGauge;
import com.DataAquisition.Alpha1.Widgets.Table;

public class Page2_Fragment extends Fragment{



////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page2_fragment_layout, container,false);
        rootView.setBackgroundColor(Color.BLACK);
        LargeGauge largeGauge = (LargeGauge)rootView.findViewById(R.id.largeGauge);
        MainActivity mainActivity = (MainActivity)getActivity();
        DataConnector.WidgetObjStruct widgetObjStruct = new DataConnector.WidgetObjStruct();
        widgetObjStruct.input = 0;
        widgetObjStruct.widgetObj = largeGauge;
        mainActivity.dataConnector.addWidgetObject(widgetObjStruct);
        Table table = rootView.findViewById(R.id.Page2TableWidget);
        DataConnector.WidgetObjStruct tableObjectStruct = new DataConnector.WidgetObjStruct();
        tableObjectStruct.widgetObj = table;
        tableObjectStruct.input = 0;
        mainActivity.dataConnector.addWidgetObject(tableObjectStruct);
        return rootView;
    }
}
