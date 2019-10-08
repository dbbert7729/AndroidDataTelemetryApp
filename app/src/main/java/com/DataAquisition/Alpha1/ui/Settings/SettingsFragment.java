package com.DataAquisition.Alpha1.ui.Settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.DataAquisition.Alpha1.DialogFragments.AddGaugeDialog;
import com.DataAquisition.Alpha1.R;

public class SettingsFragment extends Fragment {

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity){
        myContext = (FragmentActivity)activity;
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        myContext.getSupportFragmentManager().beginTransaction().replace(R.id.settings,new SettingsPreferences(myContext)).commit();

        return root;
    }

    public static class SettingsPreferences extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
        SharedPreferences sharedPreferences;
        private  FragmentActivity myContext;

        SettingsPreferences(Activity activity)
        {
            myContext = (FragmentActivity)activity;
        }
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Preference layoutModifier = (Preference)findPreference("layoutModifier");
            if(sharedPreferences.getString("gaugeLayoutPref",null) != null)//Display the gaugeLayoutPref string for debugging purposes.
            {
                layoutModifier.setSummary(sharedPreferences.getString("gaugeLayoutPref",null));
            }

            layoutModifier.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    FragmentManager fm = myContext.getSupportFragmentManager();
                    AddGaugeDialog addGaugeDialog = AddGaugeDialog.newInstance("Hello");
                    addGaugeDialog.show(fm,"dialogtest");
                    return true;
                }
            });
        }
        @Override
        public void onResume(){
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference preference = findPreference(key);
            if(preference != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String input = sharedPreferences.getString(key, "");
                editor.putString(key, input);
                editor.apply();
            }
            Toast.makeText(myContext.getApplicationContext(),"Updated"+key+":" +sharedPreferences.getString(key,null), Toast.LENGTH_SHORT).show();
        }


    }

}

