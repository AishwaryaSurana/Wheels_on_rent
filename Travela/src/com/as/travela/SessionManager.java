package com.as.travela;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "settings";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
     
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
     
    // Contact (make variable public to access from outside)
    public static final String KEY_CONTACT = "contact_no";
    
    //Surname 
    public static final String KEY_SURNAME="surname";
    
    //Email
    public static final String KEY_EMAIL="email";
    
    //Owner 
    public static final String KEY_OWNER="owner_id";
     
    // Constructor
    @SuppressLint("CommitPrefEdits")
	public SessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void createLoginSession(String name, String contact,String email,String surname,int ownerId){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing name in pref
        editor.putString(KEY_NAME, name);
         
        // Storing contact in pref
        editor.putString(KEY_CONTACT, contact);
        
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        
        //Storing surname in pref
        editor.putString(KEY_SURNAME,surname);
        
        //storing ownerId in pref
        editor.putInt(KEY_OWNER, ownerId);
        
        // commit changes
        editor.commit();
    }   
   
    public void createLoginSession(String name, String contact,String surname,int ownerId){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing name in pref
        editor.putString(KEY_NAME, name);
         
        // Storing contact in pref
        editor.putString(KEY_CONTACT, contact);
        
        //Storing surname in pref
        editor.putString(KEY_SURNAME,surname);
        
        //storing ownerId in pref
        editor.putInt(KEY_OWNER, ownerId);
        
        // commit changes
        editor.commit();
    }   
   
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin()
    {
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context,AboutVehicleActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
          //finish login activity
            LoginActivity.fa.finish();
            
            // Staring Login Activity
            _context.startActivity(i);
        }
         
    }
     
     
     
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
         
        // user email id
        user.put(KEY_CONTACT, pref.getString(KEY_CONTACT, null));
         
        // return user
        return user;
    }
     
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        //clear shared pref
        pref.edit().clear().commit();
        
        // Staring Login Activity
        
        _context.startActivity(i);
    }
     
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}

