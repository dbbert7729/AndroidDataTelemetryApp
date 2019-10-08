package com.DataAquisition.Alpha1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Page2_Fragment extends Fragment{



////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.chart1_fragment_layout, container,false);
        rootView.setBackgroundColor(Color.BLACK);
        return rootView;
    }
}
