package com.developerrr.fitnesstracker.roomdb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developerrr.fitnesstracker.models.Profile;


import java.util.ArrayList;

public class ApplicationDB {

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    private static final String DB_NAME = "usersaccounts.db";
    private static final int DB_VERSION = 2;

    //------------------------------------------------------------------- PROFILE TABLE ----------------------- \\
    private static final String PROFILES_TABLE = "Profiles";

    private static final String PROFILE_ID = "_ProfileID";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String COUNTRY = "country";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final int PROFILE_ID_COLUMN = 0;
    private static final int FIRST_NAME_COLUMN = 1;
    private static final int LAST_NAME_COLUMN = 2;
    private static final int COUNTRY_COLUMN = 3;
    private static final int USERNAME_COLUMN = 4;
    private static final int PASSWORD_COLUMN = 5;
    //------------------------------------------------------------------- PROFILE TABLE ----------------------- \\



    private static final String CREATE_PROFILES_TABLE =
            "CREATE TABLE " + PROFILES_TABLE + " (" +
                    PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIRST_NAME + " TEXT, " +
                    LAST_NAME + " TEXT, " +
                    COUNTRY + " TEXT, " +
                    USERNAME + " TEXT, " +
                    PASSWORD + " TEXT)";


    public ApplicationDB(Context context){
        openHelper = new DBHelper(context, DB_NAME, DB_VERSION);
    }

    public void saveNewProfile(Profile profile) {

        database = openHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIRST_NAME, profile.getFirstName());
        cv.put(LAST_NAME, profile.getLastName());
        cv.put(COUNTRY, profile.getCountry());
        cv.put(USERNAME, profile.getUsername());
        cv.put(PASSWORD, profile.getPassword());

        long id = database.insert(PROFILES_TABLE, null, cv);

        profile.setDbId(id);

        database.close();
    }



    public void deleteProfile(Profile profile){
        database=openHelper.getWritableDatabase();
        database.delete(PROFILES_TABLE,USERNAME +"=?",new String[]{profile.getUsername()});
    }



    public ArrayList<Profile> getAllProfiles(){

        ArrayList<Profile> profiles = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(PROFILES_TABLE, null,null,null,null,
                null, null);
        getProfilesFromCursor(profiles, cursor);

        cursor.close();
        database.close();

        return profiles;
    }
    private void getProfilesFromCursor(ArrayList<Profile> profiles, Cursor cursor) {
        // returns true if pointed to a record
        while (cursor.moveToNext()){

            long id = cursor.getLong(PROFILE_ID_COLUMN);
            String firstName = cursor.getString(FIRST_NAME_COLUMN);
            String lastName = cursor.getString(LAST_NAME_COLUMN);
            String country = cursor.getString(COUNTRY_COLUMN);
            String username = cursor.getString(USERNAME_COLUMN);
            String password = cursor.getString(PASSWORD_COLUMN);


            profiles.add(new Profile(firstName, lastName, country, username, password, id));
        }
    }



    private static class DBHelper extends SQLiteOpenHelper {

        private DBHelper(Context context, String name, int version) {
            super(context, name, null, version);
        }

        // if the db doesn't exist , the runtime calls this fn . we dont have to check if it exists
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PROFILES_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // drop the table
            db.execSQL("DROP TABLE IF EXISTS " + PROFILES_TABLE);

            onCreate(db);
        }
    }
}
