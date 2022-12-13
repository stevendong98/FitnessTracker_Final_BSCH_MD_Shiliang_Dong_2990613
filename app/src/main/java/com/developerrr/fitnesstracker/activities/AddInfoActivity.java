package com.developerrr.fitnesstracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.developerrr.fitnesstracker.R;

import java.util.ArrayList;
import java.util.List;

public class AddInfoActivity extends AppCompatActivity {

    AppCompatSpinner genderSpinner;
    EditText et_username,et_age,et_height,et_weight;
    private String selectedGender;
    Button saveBtn;

    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        InitializeViews();

    }

    private void InitializeViews() {
        genderSpinner=findViewById(R.id.ed_infogender);
        et_age=findViewById(R.id.ed_infoage);
        et_height=findViewById(R.id.ed_infoheight);
        et_weight=findViewById(R.id.ed_infoweight);
        et_username=findViewById(R.id.et_infoname);
        saveBtn=findViewById(R.id.info_submitBtn);

        backArrow=findViewById(R.id.backArrow);


        String age=getIntent().getStringExtra("age");
        String weight=getIntent().getStringExtra("weight");
        String height=getIntent().getStringExtra("height");
        String name=getIntent().getStringExtra("name");

        et_age.setText(age);
        et_height.setText(height);
        et_weight.setText(weight);
        et_username.setText(name);




        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setupSpinner();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!notEmptyFields()){
                    if(Double.valueOf(et_age.getText().toString()) < 99 && Double.valueOf(et_age.getText().toString()) > 5 ){
                        if(Double.valueOf(et_height.getText().toString()) > 45 && Double.valueOf(et_height.getText().toString()) < 250){
                            if(Double.valueOf(et_weight.getText().toString().trim()) > 10 && Double.valueOf(et_weight.getText().toString().trim()) < 200){
                                SaveDateCorrectly();
                            }else {
                                Toast.makeText(AddInfoActivity.this, "Weight is Not Correct", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(AddInfoActivity.this, "Height is Not Correct", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddInfoActivity.this, "Age is Not Correct", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddInfoActivity.this, "Must Fill All The Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setupSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add("Male");
        categories.add("Female");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(dataAdapter);

        genderSpinner.setSelection(0);
        selectedGender="Male";
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedGender=adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void SaveDateCorrectly() {

        String name=et_username.getText().toString().trim();

        //SharedPreferences sharedPreferences = this.getPreferences(MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("age",et_age.getText().toString().trim());
        editor.putString("height",et_height.getText().toString().trim());
        editor.putString("weight",et_weight.getText().toString().trim());
        editor.putString("username",name);
        editor.putString("gender",selectedGender);
        editor.commit();
        editor.apply();




        Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();

    }

    private boolean notEmptyFields() {
        return TextUtils.isEmpty(et_username.getText().toString().trim()) ||
                TextUtils.isEmpty(et_weight.getText().toString().trim()) ||
                TextUtils.isEmpty(et_height.getText().toString().trim()) ||
                TextUtils.isEmpty(et_age.getText().toString().trim());
    }
}