package com.developerrr.fitnesstracker.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.activities.AddInfoActivity;
import com.developerrr.fitnesstracker.activities.LoginAcitviy;
import com.developerrr.fitnesstracker.activities.LoginFragment;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {


    Button addInfoBtn,logoutBtn;
    TextView badgesEarned;
    EditText userNameField;
    TextView weight,height,gender,age;

    Spinner distanceSpinner;
    String[] country = { "Meters", "Miles"};
    private String selectedUnit="Meters";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_settings, container, false);

        badgesEarned=view.findViewById(R.id.numbersthree);
        userNameField=view.findViewById(R.id.userField);

        age=view.findViewById(R.id.ageval);
        logoutBtn=view.findViewById(R.id.logoutBtn);
        weight=view.findViewById(R.id.weightval);
        height=view.findViewById(R.id.heightval);
        gender=view.findViewById(R.id.genderval);
        distanceSpinner=view.findViewById(R.id.distanceSpinner);



        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(aa);




        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.Editor editor=sharedPreferences.edit();

        SharedPreferences preferences=getActivity().getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor2=preferences.edit();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor2.remove("User");
                editor2.commit();
                editor2.apply();
                startActivity(new Intent(getContext(), LoginAcitviy.class));
                getActivity().finish();
            }
        });



        int badges=sharedPreferences.getInt("badgesToday",0);
        badgesEarned.setText(String.valueOf(badges));

        String name=sharedPreferences.getString("username","");
        userNameField.setText(name);


        String aget=sharedPreferences.getString("age","");
        String weightt=sharedPreferences.getString("weight","");
        String heightt=sharedPreferences.getString("height","");
        String gendert=sharedPreferences.getString("gender","");


        gender.setText(gendert+" ");
        height.setText(heightt+" Cm");
        weight.setText(weightt+" Kg");
        age.setText(aget+" Years");


        addInfoBtn=view.findViewById(R.id.addInfoBtn);
        addInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddInfoActivity.class);
                intent.putExtra("age",aget);
                intent.putExtra("weight",weightt);
                intent.putExtra("height",heightt);
                intent.putExtra("name",userNameField.getText().toString());

                startActivity(intent);
            }
        });

        selectedUnit=sharedPreferences.getString("unit","Meters");

        distanceSpinner.setSelection(aa.getPosition(selectedUnit));


        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUnit=country[position];
                editor.putString("unit",selectedUnit);
                editor.commit();
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",userNameField.getText().toString());
        editor.commit();
    }
}