package com.example.mmacdonald.pitrainer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import static android.os.Build.ID;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by MMacDonald on 05/09/2017.
 */

public class MyDB {

    private SQLiteDatabase myDB;
    private Cursor c;

    public MyDB(Context context, String userID) {

        try {

            myDB = context.openOrCreateDatabase("PiDB", Context.MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS scores (userID VARCHAR, score INT(5), id INTEGER PRIMARY KEY)");

            if (checkIfHasExistingScore(userID) == 0){
                myDB.execSQL("INSERT INTO scores (userID, score) VALUES ('" + userID + "', 0)");
            }

        } catch (Exception e){

            e.printStackTrace();

        }


    }

    private String highestScore(String userID){

        int score = 0;

        String query = "SELECT * FROM scores WHERE userID =?";

        Cursor c = myDB.rawQuery(query, new String[]{userID});

        int scoreIndex = c.getColumnIndex("score");

        if (c.getCount() != 0){

            c.moveToFirst();
            score = c.getInt(scoreIndex);

        }
        c.close();
        return Integer.toString(score);
    }

    public void updateScore(int score, String userID){

        int existingHighScore = Integer.parseInt(highestScore(userID));

        if (score > existingHighScore){

            myDB.execSQL("UPDATE scores SET score =" + score + " WHERE userID ='" + userID + "'");
            Log.i("Test", "Test");
        }

    }

    public String getHighestScore(String userID){

        return highestScore(userID);
    }

    private int checkIfHasExistingScore(String userID){

        try{
            String query = "SELECT * FROM scores WHERE userID =?";
            c = myDB.rawQuery(query, new String[]{userID});
            return c.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.close();
        return 0;
    }

}


