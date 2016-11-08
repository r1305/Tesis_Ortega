package com.ulima.tesis_ortega.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.ulima.tesis_ortega.LoginActivity;

import java.util.HashMap;

/**
 * Created by Julian on 23/08/2016.
 */
public class SessionManager {

    SharedPreferences pref;
    SharedPreferences pref1;

    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;

    Context _context;

    int PRIVATE_MODE=0;

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NIVEL = "email";


    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(KEY_EMAIL, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void createLevel(String nivel){
        editor1.putString(KEY_NIVEL,nivel);
        editor1.commit();
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
//        user.put(KEY_NIVEL,pref1.getString(KEY_NIVEL,null));

        // return user
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
