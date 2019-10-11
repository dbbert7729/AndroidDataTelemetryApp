package com.DataAquisition.Alpha1;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DataAquisition.Alpha1.HelperClasses.DataConnector;
import com.DataAquisition.Alpha1.Widgets.LargeGauge;
import com.DataAquisition.Alpha1.Widgets.RoundGauge;
import com.DataAquisition.Alpha1.Widgets.SmallBarGraph;

public class Page1_Fragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity)getActivity();
        //mainActivity.getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page1_fragment_layout,container,false);
        rootView.setBackgroundColor(Color.BLACK);
        RecyclerView page1RecyclerView = (RecyclerView)rootView.findViewById(R.id.page1RecyclerView);
        if(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("gaugeLayoutPref",null)!=null) {
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter((MainActivity) getActivity(), PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("gaugeLayoutPref", null), getContext());
            page1RecyclerView.setAdapter(recyclerAdapter);
            page1RecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        }
        return rootView;
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
{
    private String[] mDataset;
    private MainActivity mainActivity;
    private Context mContext;
    private LayoutInflater mInflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        public MyViewHolder(View v) {
            super(v);
            linearLayout = v.findViewById(R.id.rowLayout);
        }
    }

    public RecyclerAdapter(MainActivity mainActivity, String myDataset, Context context) {
        mDataset = myDataset.split(",");
        mainActivity = mainActivity;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        if(mDataset[position].split(":")[2].equals("RoundGauge"))//Add RoundGauge Widget
        {
            RoundGauge gauge = new RoundGauge(mContext);
            gauge.setText(mDataset[position].split(":")[0]);
            holder.linearLayout.addView(gauge);
            //Add to the DataConnector class so the widget can be updated from a seperate thread.
            DataConnector.WidgetObjStruct struct = new DataConnector.WidgetObjStruct();
            struct.widgetObj = gauge;
            struct.input = Integer.parseInt(mDataset[position].split(":")[1]);
            this.mainActivity.dataConnector.addWidgetObject(struct);
        }
        else if(mDataset[position].split(":")[2].equals("SmallBarGraph"))//Add SmallBarGraph Widget
        {
            SmallBarGraph smallBarGraph = new SmallBarGraph(mContext);
            smallBarGraph.setNameAndUnit(mDataset[position].split(":")[0],"|UNIT|");
            holder.linearLayout.addView(smallBarGraph);
            //Add to the DataConnector class so the widget can be updated from a seperate thread.
            DataConnector.WidgetObjStruct struct = new DataConnector.WidgetObjStruct();
            struct.widgetObj = smallBarGraph;
            struct.input = Integer.parseInt(mDataset[position].split(":")[1]);
            this.mainActivity.dataConnector.addWidgetObject(struct);
        }
        else if(mDataset[position].split(":")[2].equals("LargeGauge"))//Add a LargeGauge to the layout
        {
            LargeGauge largeGauge = new LargeGauge(mContext);
            largeGauge.setName(mDataset[position].split(":")[0]);
            holder.linearLayout.addView(largeGauge);
            //Add to the DataConnector class so the widget can be updated from a seperate thread.
            DataConnector.WidgetObjStruct struct = new DataConnector.WidgetObjStruct();
            struct.widgetObj = largeGauge;
            struct.input = Integer.parseInt(mDataset[position].split(":")[1]);
            this.mainActivity.dataConnector.addWidgetObject(struct);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}


/*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.gauges1Layout);
        if(sharedPreferences.getString("gaugeLayoutPref",null) != null)
        {
            String[] layoutStr = sharedPreferences.getString("gaugeLayoutPref",null).split(",");
            for(int index = 0; index < layoutStr.length; index++)
            {
                RoundGauge roundGauge = new RoundGauge(getContext());
                String Title = layoutStr[index].split(":")[0];
                roundGauge.setText(Title);
                roundGauge.setId(index);
                roundGauge.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1000));
                linearLayout.addView(roundGauge);
            }
        }

 */