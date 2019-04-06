package com.vfs.tom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vfs.tom.managers.PersistenceManager;
import com.vfs.tom.model.Enemy;
import com.vfs.tom.model.Player;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //TODO generate player class (damage done, current weapon, ?)
    //enemy_name
    //temp_energy
    private ImageButton enemyBody;
    private Enemy currentEnemy;
    //VALOR TEMPORAL
    //private int playerDamage;
    private static Player player;
    private TextView enemyName;
    private TextView tempEnergy;

    //FB
    private CallbackManager callbackManager = CallbackManager.Factory.create();
    private static final String EMAIL = "email";
    private LoginButton loginButton;
    //FBEND

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setUpGame();
        setListeners();

        //FB
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e("yameloguee","jiji");
                    }

                    @Override
                    public void onCancel() {
                        Log.e("yameloguee","no");
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("yameloguee","error");
                        Log.e("yameloguee",exception.getStackTrace().toString());
                        exception.printStackTrace();

                        // App code
                    }
                });
        //FBEND
        //FB
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        //FBEND

    }

    //FB
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    //Every activity and fragment that you integrate with the FacebookSDK Login or Share should forward onActivityResult to the callbackManager.
    //FBEND

    protected void initView(){
        enemyBody = findViewById(R.id.enemy_body);
        enemyName = findViewById(R.id.enemy_name);
        tempEnergy = findViewById(R.id.temp_energy);
        //FB
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        // If you are using in a fragment, call loginButton.setFragment(this);
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        //FBEND
    }
    private void setListeners(){
        enemyBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        if(currentEnemy.getEnergy()>0){
                            handleAttack();
                        }else{
                            enemyDefeated();
                        }

                        break;
                }
                return true;
            }
        });
    }
    private void setUpGame(){
        PersistenceManager persistenceManager = PersistenceManager.getInstance(getApplicationContext());
        Player tmpPlayer = null;
        try{
            tmpPlayer = persistenceManager.retrivePlayer();
            String a ="d";
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO GET PLAYER PARAMS AND ENEMY VALUES (LEVEL?)
        if(tmpPlayer!=null){
            player = tmpPlayer;
        }else{
            player = new Player("guest",1.0,"sword",0);
            persistenceManager.persistPlayer(player);
        }
        generateRound();

    }
    private void generateRound(){
        //TODO RANDOM ENEMY TYPE
        //TODO RANDOM ENEMY ENERGY
        currentEnemy = new Enemy("Bat", 500);
        //TODO MECHANISM FOR CHOOSING IMAGE en funcion de enemy type
        enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.bat));
        enemyName.setText(currentEnemy.getEnemyType());
        tempEnergy.setText(String.valueOf(currentEnemy.getEnergy()));
    }
    private void handleAttack(){
        //TODO calculate real player damage damage+weaponDamage
        currentEnemy.onDamageTaken(player.getDamageLevel());
        tempEnergy.setText(String.valueOf(currentEnemy.getEnergy()));
    }
    private void enemyDefeated(){
        //TODO augment stats, persist stats
        generateRound();
    }
}
