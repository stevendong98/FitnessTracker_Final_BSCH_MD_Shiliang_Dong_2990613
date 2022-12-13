package com.developerrr.fitnesstracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.adapters.ViewPagerAdapter;


public class OnboardingActivity extends AppCompatActivity {

    //Variables
    ViewPager viewPager;
    LinearLayout dotsLayout;
    ViewPagerAdapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted;
    Animation animation;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);

        //Hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_started_btn);

        int[] images=new int[]{
                R.drawable.one,
                R.drawable.two,
                R.drawable.three,
                R.drawable.four
        };

        String[] headings=new String[]{
                "Life is like a ten speed bicycle",
                "It's Not about perfect Its about effort",
                "Sweat Smile And Repeat",
                "Never Give Up is Key to Success",
        };
        String[] descs=new String[]{
                "Ride as much or as little as long or as short as you feel But ride",
                "If it doesn't challenge you it won't change you.",
                "Your body can do it. It's Time to convince your mind.",
                "Mindset is what separates the best from the rest",
        };

        //Call adapter
        sliderAdapter = new ViewPagerAdapter(this,images,headings,descs);
        viewPager.setAdapter(sliderAdapter);

        //Dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        letsGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnboardingActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    public void skip(View view) {
        startActivity(new Intent(this, LoginAcitviy.class));
        finish();
    }

    public void next(View view) {
        viewPager.setCurrentItem(currentPos + 1);
    }

    private void addDots(int position) {

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.greedard));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;

            if (position == 0) {
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else if (position == 2) {
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(OnboardingActivity.this, R.anim.bottom_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}