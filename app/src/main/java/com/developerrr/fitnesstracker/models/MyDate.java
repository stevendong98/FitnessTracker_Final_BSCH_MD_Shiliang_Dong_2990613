package com.developerrr.fitnesstracker.models;

public class MyDate {
    String month;
    String day;
    int dayOfMonth;
    int monthOfYear;
    int year;
    CharSequence fullDate;


    public MyDate(String month, String day, int dayOfMonth, int monthOfYear, int year,CharSequence fullDate) {
        this.month = month;
        this.day = day;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.fullDate=fullDate;
    }


    public CharSequence getFullDate() {
        return fullDate;
    }

    public void setFullDate(CharSequence fullDate) {
        this.fullDate = fullDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
