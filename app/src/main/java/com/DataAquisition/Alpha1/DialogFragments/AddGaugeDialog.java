package com.DataAquisition.Alpha1.DialogFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.DataAquisition.Alpha1.R;

public  class AddGaugeDialog extends DialogFragment {

    private EditText mEditText;
    SharedPreferences sharedPreferences;
    public AddGaugeDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddGaugeDialog newInstance(String title) {
        AddGaugeDialog frag = new AddGaugeDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialogtest, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Create Submit Listener
        Button submitButton = (Button)view.findViewById(R.id.newGaugeSubmit);
        final EditText gaugeName = (EditText)view.findViewById(R.id.newGaugedialogName);
        final EditText gaugeInput = (EditText)view.findViewById(R.id.newGaugedialogInput);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the values from the dialog
                String Name = gaugeName.getText().toString();
                String Input = gaugeInput.getText().toString();
                //Add to a list to dynamically generate the gauge view.
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String gaugeLayoutStringList = "";
                if(sharedPreferences.getString("gaugeLayoutPref", null) != null)
                {
                    gaugeLayoutStringList = sharedPreferences.getString("gaugeLayoutPref",null);
                    gaugeLayoutStringList = gaugeLayoutStringList + Name + ":" + Input + ":" + "RoundGauge" + ",";
                    editor.putString("gaugeLayoutPref",gaugeLayoutStringList);
                    editor.apply();
                }
                else//Initialize gaugeLayoutPref
                {
                    gaugeLayoutStringList = Name+":"+Input+":"+"RoundGauge"+",";
                    editor.putString("gaugeLayoutPref",gaugeLayoutStringList);
                    editor.apply();
                }

            }
        });
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.newGaugedialogInput);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
