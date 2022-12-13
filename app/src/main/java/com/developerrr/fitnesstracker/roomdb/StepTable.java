package com.developerrr.fitnesstracker.roomdb;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StepTable {

    @PrimaryKey
    @ColumnInfo(name = "day")
    public int day;

    @ColumnInfo(name = "dayname")
    public String dayName;

    @ColumnInfo(name = "username")
    public String userName;


    @ColumnInfo(name = "month")
    public int month;

    @ColumnInfo(name = "monthName")
    public String monthName;

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "steps")
    public int steps;
}
