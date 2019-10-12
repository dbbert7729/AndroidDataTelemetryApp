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
        widgetObjStruct.input = 3;
        widgetObjStruct.widgetObj = largeGauge;
        mainActivity.dataConnector.addWidgetObject(widgetObjStruct);
        RoundGauge roundGauge = new RoundGauge(getContext());
        LinearLayout linearLayout = rootView.findViewById(R.id.page2Layout);
        linearLayout.addView(roundGauge);
        return rootView;
    }
}
