package com.developerrr.fitnesstracker.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//asigning this as database and user as table of database
@Database(entities = {StepTable.class},version = 5,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract StepDao StepDao();

    private static MyDatabase iNSTANCE;


    public  static MyDatabase getiNSTANCE(Context context){
        if(iNSTANCE==null){
            iNSTANCE= Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,"DB_NAME")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return iNSTANCE;
    }

}