package com.developerrr.fitnesstracker.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.models.MyDate;
import com.developerrr.fitnesstracker.roomdb.MyDatabase;
import com.developerrr.fitnesstracker.roomdb.StepTable;

import java.util.Date;


public class BadgesFragment extends Fragment {

    SeekBar levelSeekbar;
    TextView levelTV, levelNumTv;
    TextView levelNumDailyTv, levelNumWeeklyTv, levelNumMonthlyTv;
    LinearLayout silverDaily, bronzeDaily, goldDaily;
    LinearLayout silverWeekly, bronzeWeeklyWeekly, goldWeekly;
    LinearLayout silverMonthly, bronzeMonthly, goldMonthly;

    private int stepsThisDay, stepsThisWeek, stepsThisMonth;

    int badgesEarned;
    private String loggedInUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_badges, container, false);

        InitializeComponents(view);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        loggedInUser=sharedPref.getString("User","none");

        getStepsTaken();

        return view;
    }

    private void getStepsTaken() {


        stepsThisDay = getStepsToday();
        stepsThisWeek = getStepsThisWeek();
        stepsThisMonth = getStepsThisMonth();

        levelSeekbar.setProgress(stepsThisMonth);

        levelNumTv.setText(stepsThisMonth + "/360000");

        levelNumDailyTv.setText(stepsThisDay + "/12000");
        levelNumWeeklyTv.setText(stepsThisWeek + "/84000");
        levelNumMonthlyTv.setText(stepsThisMonth + "/360000");


        configureBadgesAccordingly();

    }

    private void configureBadgesAccordingly() {
        if (stepsThisDay >= 4000 && stepsThisDay < 8000) {
            silverDaily.setBackgroundResource(R.drawable.awardbgshape_success);
            badgesEarned=1;
        } else if (stepsThisDay >= 8000 && stepsThisDay < 12000) {
            bronzeDaily.setBackgroundResource(R.drawable.awardbgshape_success);
            silverDaily.setBackgroundResource(R.drawable.awardbgshape_success);
            badgesEarned=2;
        } else if (stepsThisDay >= 12000) {
            goldDaily.setBackgroundResource(R.drawable.awardbgshape_success);
            bronzeDaily.setBackgroundResource(R.drawable.awardbgshape_success);
            silverDaily.setBackgroundResource(R.drawable.awardbgshape_success);
            badgesEarned=3;
        }

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("badgesToday",badgesEarned);
        editor.commit();

        if (stepsThisWeek >= 28000 && stepsThisWeek < 56000) {
            silverWeekly.setBackgroundResource(R.drawable.awardbgshape_success);
        } else if (stepsThisWeek >= 56000 && stepsThisWeek < 84000) {
            bronzeWeeklyWeekly.setBackgroundResource(R.drawable.awardbgshape_success);
            silverWeekly.setBackgroundResource(R.drawable.awardbgshape_success);
        } else if (stepsThisWeek >= 84000) {
            goldWeekly.setBackgroundResource(R.drawable.awardbgshape_success);
            bronzeWeeklyWeekly.setBackgroundResource(R.drawable.awardbgshape_success);
            silverWeekly.setBackgroundResource(R.drawable.awardbgshape_success);
        }


        if (stepsThisMonth >= 120000 && stepsThisMonth < 240000) {
            silverMonthly.setBackgroundResource(R.drawable.awardbgshape_success);
        } else if (stepsThisMonth >= 240000 && stepsThisMonth < 360000) {
            bronzeMonthly.setBackgroundResource(R.drawable.awardbgshape_success);
            silverMonthly.setBackgroundResource(R.drawable.awardbgshape_success);
        } else if (stepsThisMonth >= 360000) {
            goldMonthly.setBackgroundResource(R.drawable.awardbgshape_success);
            bronzeMonthly.setBackgroundResource(R.drawable.awardbgshape_success);
            silverMonthly.setBackgroundResource(R.drawable.awardbgshape_success);
        }

    }

    private int getStepsToday() {
        MyDatabase database = MyDatabase.getiNSTANCE(getContext());
        int stepsToday = 0;
        for (StepTable s : database.StepDao().getAllSteps()) {
            if(s.userName.equals(loggedInUser)) {
                if (s.month == getCurrentDate().getMonthOfYear()) {
                    if (s.day == getCurrentDate().getDayOfMonth()) {
                        if (s.year == getCurrentDate().getYear()) {
                            stepsToday += s.steps;
                        }
                    }
                }
            }
        }

        return stepsToday;
    }

    private int getStepsThisWeek() {
        MyDatabase database = MyDatabase.getiNSTANCE(getContext());
        int stepsThisWeek = 0;
        for (StepTable s : database.StepDao().getAllSteps()) {
            if(s.userName.equals(loggedInUser)) {
                if (s.month == getCurrentDate().getMonthOfYear()) {
                    int today = getCurrentDate().getDayOfMonth();
                    stepsThisWeek = getSteps(today);
                }
            }
        }

        return stepsThisWeek;
    }

    private int getSteps(int today) {
        int steps = 0;
        MyDatabase database = MyDatabase.getiNSTANCE(getContext());

        if (today <= 7 && today >= 1) {

            for (StepTable s : database.StepDao().getAllSteps()) {
                if(s.userName.equals(loggedInUser)) {
                    if (s.month == getCurrentDate().getMonthOfYear()) {
                        if (s.day <= 7 && s.day >= 1) {
                            steps += s.steps;
                        }

                    }
                }
            }
            return steps;
        } else if (today <= 14 && today >= 8) {
            for (StepTable s : database.StepDao().getAllSteps()) {
                if(s.userName.equals(loggedInUser)) {
                    if (s.month == getCurrentDate().getMonthOfYear()) {
                        if (s.day <= 14 && s.day >= 8) {
                            steps += s.steps;
                        }

                    }
                }
            }
            return steps;
        } else if (today <= 21 && today >= 15) {
            for (StepTable s : database.StepDao().getAllSteps()) {
                if(s.userName.equals(loggedInUser)) {
                    if (s.month == getCurrentDate().getMonthOfYear()) {
                        if (s.day <= 21 && s.day >= 15) {
                            steps += s.steps;
                        }

                    }
                }
            }
            return steps;
        } else {
            for (StepTable s : database.StepDao().getAllSteps()) {
                if(s.userName.equals(loggedInUser)) {
                    if (s.month == getCurrentDate().getMonthOfYear()) {
                        if (s.day <= 31 && s.day >= 22) {
                            steps += s.steps;
                        }

                    }
                }
            }
            return steps;
        }
    }

    private int getStepsThisMonth() {
        MyDatabase database = MyDatabase.getiNSTANCE(getContext());
        int stepsThisMonth = 0;
        for (StepTable s : database.StepDao().getAllSteps()) {
            if(s.userName.equals(loggedInUser)) {
                if (s.month == getCurrentDate().getMonthOfYear()) {
                    stepsThisMonth += s.steps;
                }
            }
        }

        return stepsThisMonth;
    }


    private void InitializeComponents(View view) {
        levelSeekbar = view.findViewById(R.id.levelSeekBar);
        levelTV = view.findViewById(R.id.yourlevelTv);
        levelNumTv = view.findViewById(R.id.levelNumTv);
        levelSeekbar.setEnabled(false);
        levelNumDailyTv = view.findViewById(R.id.levelNumDailyTv);
        levelNumWeeklyTv = view.findViewById(R.id.levelNumWeeklyTv);
        levelNumMonthlyTv = view.findViewById(R.id.levelNumMonthlyTv);

        silverDaily = view.findViewById(R.id.silverDaily);
        silverWeekly = view.findViewById(R.id.silverWeekly);
        silverMonthly = view.findViewById(R.id.silverMonthly);

        bronzeDaily = view.findViewById(R.id.bronzeDaily);
        bronzeWeeklyWeekly = view.findViewById(R.id.bronzeWeekly);
        bronzeMonthly = view.findViewById(R.id.bronzeMonthly);

        goldDaily = view.findViewById(R.id.golddaily);
        goldWeekly = view.findViewById(R.id.goldWeekly);
        goldMonthly = view.findViewById(R.id.goldMonthly);
    }

    private MyDate getCurrentDate() {
        Date d = new Date();
        CharSequence s = DateFormat.format("d MMMM, yyyy ", d.getTime());
        //this sequence will return something like this April 6 2022
        String dayOfTheWeek = (String) DateFormat.format("EEEE", d); // Thursday
        String day = (String) DateFormat.format("dd", d); // 20
        String monthString = (String) DateFormat.format("MMMM", d); // Jun
        String monthNumber = (String) DateFormat.format("MM", d); // 06
        String year = (String) DateFormat.format("yyyy", d);
        MyDate myDate = new MyDate(monthString, dayOfTheWeek, Integer.parseInt(day), Integer.parseInt(monthNumber), Integer.parseInt(year), s);
        return myDate;
    }
}