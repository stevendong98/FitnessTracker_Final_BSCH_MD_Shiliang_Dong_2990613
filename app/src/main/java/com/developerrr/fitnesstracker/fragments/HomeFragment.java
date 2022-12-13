package com.developerrr.fitnesstracker.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.models.MyDate;
import com.developerrr.fitnesstracker.roomdb.MyDatabase;
import com.developerrr.fitnesstracker.roomdb.StepTable;
import com.developerrr.fitnesstracker.utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements SensorEventListener{

    //every number of minutes handler will save number of steps to room database.
    private static final long SAVEINTERVAL = 2;

    private Integer stepCount = 0;
    TextView stepsTv, speedTv,caloriesTv,distanceTv,lastRebootTV;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean isSensorPresent = false;
    String age,height,weight,name,gender;
    private float totalStepsTeken;
    private String lastRebootTVText;

    Handler handler;
    Runnable runnable;

    String unit="Meters";

    SharedPreferences.Editor editor;
    private String loggedInUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)
                getContext().getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
                != null)
        {
            mSensor =
                    mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isSensorPresent = true;
        }
        else
        {
            isSensorPresent = false;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        doFuncitonality(view);



        return view;
    }

    private String getTimeSinceBoot(long durationInMillis) {

        //getting date from millis
        Date date=new Date(durationInMillis);
        String[] time=date.toString().split(" ");
        String timet=time[0]+" "+time[1]+" "+time[2]+" "+time[3];

        return timet;
    }

    private void doFuncitonality(View view) {

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        loggedInUser=sharedPref.getString("User","none");

        editor=sharedPreferences.edit();

        stepsTv = view.findViewById(R.id.stepsTv);
        speedTv = view.findViewById(R.id.speedTv);
        caloriesTv=view.findViewById(R.id.caloriesTv);
        distanceTv=view.findViewById(R.id.distanceTv);
        lastRebootTV=view.findViewById(R.id.lastRebootTime);

        stepsTv.setText("0.0");
        speedTv.setText("0.0 \nk/hour ");
        caloriesTv.setText("0.0 calories\n burned");
        unit=sharedPref.getString("unit","Meters");
        distanceTv.setText("0.0 \n"+unit);



        age=sharedPreferences.getString("age","");
        height=sharedPreferences.getString("height","");
        weight=sharedPreferences.getString("weight","");
        name=sharedPreferences.getString("name","");
        gender=sharedPreferences.getString("gender","Male");

        stepCount = sharedPreferences.getInt(getCurrentDate().getFullDate().toString(), 0);
        stepsTv.setText(String.valueOf(stepCount));

        int yestSteps = sharedPreferences.getInt(getYesterdayDateString().toString(), 0);
        lastRebootTV.setText("Yesterday Steps: "+yestSteps);

        stepsTv.setText(String.valueOf(stepCount));
        caloriesTv.setText(getCaloriesBurned(stepCount));
        distanceTv.setText(getDistanceCovered(stepCount));
        speedTv.setText("4.82 km/h \n Speed");

        totalStepsTeken=stepCount;


        //this will save total steps taken to room database every 2 minutes
        handler = new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, SAVEINTERVAL * 60 * 1000); // every 2 minutes
                addStep(totalStepsTeken,getCurrentDate());
            }
        };
        handler.postDelayed(runnable, SAVEINTERVAL * 60 * 1000);
    }
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    private CharSequence getYesterdayDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM, yyyy ");
        return dateFormat.format(yesterday());
    }
    private String getDaywithDate(int date){
        List<String> dates=new ArrayList<>();
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");


        return dates.get(date);
    }


    public void onPause() {
        super.onPause();

        if(isSensorPresent)
        {
            mSensorManager.unregisterListener(this);
        }

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getCurrentDate().toString());
        editor.apply();
        editor.putInt(getCurrentDate().getFullDate().toString(), stepCount);
        editor.apply();
    }

    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getCurrentDate().toString());
        editor.commit();
        editor.apply();
        editor.putInt(getCurrentDate().getFullDate().toString(), stepCount);
        editor.apply();

        handler.removeCallbacks(runnable);


    }

    public void onResume() {
        super.onResume();
        if(isSensorPresent)
        {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt(getCurrentDate().getFullDate().toString(), 0);

        handler.postDelayed(runnable,SAVEINTERVAL * 60 * 1000);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        totalStepsTeken=(float) (totalStepsTeken+event.values[0]);
        stepCount=(int)totalStepsTeken;
        stepsTv.setText(String.valueOf(stepCount));
        caloriesTv.setText(getCaloriesBurned(totalStepsTeken));
        distanceTv.setText(getDistanceCovered(totalStepsTeken));
        speedTv.setText("4.82 km/h \n Speed");



        addStep(totalStepsTeken,getCurrentDate());


    }

    private String getStepsYesterday() {
        MyDatabase database=MyDatabase.getiNSTANCE(getContext());
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int yesterdaySteps = 0;
        for(StepTable a : database.StepDao().getAllSteps()){
            if(a.day==1){
                int month=a.month-1;
                yesterdaySteps=getPreviousMonthSteps(month);
                lastRebootTVText="Steps Last Month :"+yesterdaySteps;
                editor.putInt("lastmonthsteps",yesterdaySteps);
                editor.commit();
                editor.apply();
            }else {
                if (a.day == getCurrentDate().getDayOfMonth() - 1) {
                    if (a.month == getCurrentDate().getMonthOfYear()) {
                        if (a.year == getCurrentDate().getYear()) {
                            yesterdaySteps = a.steps;
                            lastRebootTVText="Steps Yesterday :"+yesterdaySteps;
                        }
                    }
                }
            }

        }
        return lastRebootTVText;
    }

    private int getPreviousMonthSteps(int month) {
        int toSteps=0;
        MyDatabase database=MyDatabase.getiNSTANCE(getContext());
        for(StepTable s :database.StepDao().getAllSteps()){
            if(s.month==month){
                toSteps+=s.steps;
            }
        }

        return toSteps;
    }

    private void addStep(float steps, MyDate myDate) {
        MyDatabase database=MyDatabase.getiNSTANCE(getContext());

        StepTable step=new StepTable();
        step.dayName=myDate.getDay();
        step.day=myDate.getDayOfMonth();
        step.month=myDate.getMonthOfYear();
        step.monthName=myDate.getMonth();
        step.year=myDate.getYear();
        step.steps=(int)steps;
        step.userName=loggedInUser;
        database.StepDao().insertStep(step);


        Constants.totalSteps++;
    }


    private MyDate getCurrentDate(){
        Date d = new Date();
        CharSequence s  = DateFormat.format("d MMMM, yyyy ", d.getTime());
        //this sequence will return something like this April 6 2022
        String dayOfTheWeek = (String) DateFormat.format("EEEE", d); // Thursday
        String day          = (String) DateFormat.format("dd",   d); // 20
        String monthString  = (String) DateFormat.format("MMMM",  d); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   d); // 06
        String year         = (String) DateFormat.format("yyyy", d);
        MyDate myDate=new MyDate(monthString,dayOfTheWeek,Integer.parseInt(day),Integer.parseInt(monthNumber),Integer.parseInt(year),s);
        return myDate;
    }

    private String getDistanceCovered(float steps) {

        double steplen=0.762;

        if(unit.equals("Miles")){
            double dis = steplen * steps;
            dis = dis / 1609.34;
            return new DecimalFormat("##.##").format(dis) + "\nMiles";
        }else {

            double dis = steplen * steps;
            if (dis < 1000) {
                return dis + "\n" + unit;
            } else {
                dis = dis / 1609.34;
                return new DecimalFormat("##.##").format(dis) + "\nMiles";
            }
        }

    }

    private String getCaloriesBurned(float steps) {
        double caloriesburned;

        caloriesburned=steps * 0.04;
        if(caloriesburned < 1000){
            return new DecimalFormat("##.##").format(caloriesburned) +" calories burned";
        }else {
            caloriesburned=caloriesburned/1000;
            return new DecimalFormat("##.##").format(caloriesburned) + "kilo calories burned";
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}