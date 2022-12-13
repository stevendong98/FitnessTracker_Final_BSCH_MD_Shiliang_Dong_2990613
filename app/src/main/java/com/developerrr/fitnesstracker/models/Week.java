package com.developerrr.fitnesstracker.models;

import com.developerrr.fitnesstracker.roomdb.StepTable;

import java.util.List;

public class Week {
    List<StepTable> days;

    public Week(List<StepTable> days) {
        this.days = days;
    }

    public List<StepTable> getDays() {
        return days;
    }

    public void setDays(List<StepTable> days) {
        this.days = days;
    }
}
