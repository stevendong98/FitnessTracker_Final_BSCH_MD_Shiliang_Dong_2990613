package com.developerrr.fitnesstracker.roomdb;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StepDao {

    @Query("Select * from StepTable")
    List<StepTable> getAllSteps();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStep(StepTable... steps);

    @Query("Select * from steptable WHERE day LIKE :day")
    StepTable getStepsByDay(int day);

    @Delete
    void deleteStep(StepTable stepTable);

    @Update
    void update(StepTable stepTable);

}
