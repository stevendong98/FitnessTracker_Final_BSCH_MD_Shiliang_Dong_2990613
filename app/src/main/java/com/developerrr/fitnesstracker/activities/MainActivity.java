package com.developerrr.fitnesstracker.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.fragments.BadgesFragment;
import com.developerrr.fitnesstracker.fragments.HistoryFragment;
import com.developerrr.fitnesstracker.fragments.HomeFragment;
import com.developerrr.fitnesstracker.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        container=findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment=null;

            switch (item.getItemId()){
                case R.id.page_1:
                    selectedFragment=new HomeFragment();
                   break;
                case R.id.page_2:
                    selectedFragment=new HistoryFragment();
                    break;
                case R.id.page_3:
                    selectedFragment=new BadgesFragment();
                   break;
                case R.id.page_5:
                    selectedFragment=new SettingsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();


            return true;
        });


        showCustomUI();
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    private void showCustomUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}