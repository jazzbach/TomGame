package com.vfs.tom.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.vfs.tom.model.Player;

public class PersistenceManager {
    //TODO add more filenames for diferent persistence files if needed, add mechanism to choose destination file
    private static final String PREFS_FILE_NAME = "com.vfs.tom.PREFS";
    private static final String USER_NAME = "user_name";
    private static final String USER_WEAPON = "user_weapon";
    private static final String USER_DAMAGE = "user_damage";
    private static final String USER_SCORE = "user_score";
    private static final String SHOULD_SHOW_TUTORIAL = "should_show_tutorial";
    private SharedPreferences sharedPref;
    private static PersistenceManager persistenceManager;

    public PersistenceManager() {
    }

    public static PersistenceManager getInstance(Context context) {
        if (persistenceManager == null) {
            persistenceManager = new PersistenceManager();
            persistenceManager.init(context, PREFS_FILE_NAME);
        }

        return persistenceManager;

    }

    public void init(@NonNull Context context, @NonNull String prefFile) {
        sharedPref = context.getSharedPreferences(
                prefFile, Context.MODE_PRIVATE);
    }

    public void persist(String key, String value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String retrive(String key){
        return sharedPref.getString(key,null);
    }

    public void persistPlayer(Player user){
        persist(USER_NAME,user.getUserName());
        persist(USER_WEAPON,user.getWeapon());
        persist(USER_DAMAGE,String.valueOf(user.getDamageLevel()));
        persist(USER_SCORE,String.valueOf(user.getScore()));

    }

    public Player retrivePlayer(){
        return new Player(retrive(USER_NAME),Double.parseDouble(retrive(USER_DAMAGE)),retrive(USER_WEAPON),Double.parseDouble(retrive(USER_SCORE)));
    }

    public boolean retriveShouldShowTutorial(){
        return sharedPref.getBoolean(SHOULD_SHOW_TUTORIAL,true);
    }

    public void persistShouldSHowTutorial(boolean value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SHOULD_SHOW_TUTORIAL, value);
        editor.apply();
    }

}
