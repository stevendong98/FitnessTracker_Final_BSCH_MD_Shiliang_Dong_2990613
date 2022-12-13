package com.developerrr.fitnesstracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.developerrr.fitnesstracker.R;


public class SplashActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 101;
    final int SPLASH_DELAY_TIME=3;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean isFirstTime=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        preferences=this.getSharedPreferences("myprefs",MODE_PRIVATE);
        editor=preferences.edit();

        isFirstTime=preferences.getBoolean("isFirstTime",false);

      askForPermission();





    }

    private void askForPermission() {
        String[] permissions = new String[]{Manifest.permission.ACTIVITY_RECOGNITION};
        ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION) {
            String permission = permissions[0];
            int grantResult = grantResults[0];

            if (permission.equals(Manifest.permission.ACTIVITY_RECOGNITION)) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    startActivityNow();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
                }
            }
        }
    }

    private void startActivityNow() {
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_DELAY_TIME*1000);

                    if(!isFirstTime) {
                        editor.putBoolean("isFirstTime",true);
                        editor.commit();
                        editor.apply();
                        Intent i = new Intent(SplashActivity.this, OnboardingActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        String user=preferences.getString("User","none");

                        if(user.equals("none")) {
                            Intent i = new Intent(SplashActivity.this, LoginAcitviy.class);
                            startActivity(i);
                            finish();
                        }else {
                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                } catch (Exception e) {

                }
            }
        };
        // start thread
        background.start();
    }

}