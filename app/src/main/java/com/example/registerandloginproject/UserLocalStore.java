package com.example.registerandloginproject;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {
    private static final String SP_Name = "userDetails";
    private SharedPreferences userLocalDatabase;

    //constructor
    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_Name, Context.MODE_PRIVATE);
    }

    //save userdata to phone when login successful
    public void storeUserData(User user)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putLong("userId", user.userId);
        spEditor.putString("name", user.name);
        spEditor.putString("email", user.email);
        spEditor.putString("birthday", user.birthday);
        spEditor.putString("password", user.password);
        spEditor.apply();
    }

    //retrieve stored user data
    public User getLoggedInUser()
    {
        long userId = userLocalDatabase.getLong("userId", 0);
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String birthday = userLocalDatabase.getString("birthday", "");
        String password = userLocalDatabase.getString("password", "");

        return new User(userId, name, email, birthday, password);
    }

    //set true when login successful
    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public boolean getUserLoggedIn()
    {
        return userLocalDatabase.getBoolean("loggedIn", false);
    }

    //clear user data when logout
    public void clearUserData()
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}
