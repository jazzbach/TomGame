package com.vfs.tom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.vfs.tom.managers.PersistenceManager;
import com.vfs.tom.model.Enemy;
import com.vfs.tom.model.Player;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
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

    private AdView mAdView;

    private LinearLayout pauseButton;

    private Profile profile;

    private PersistenceManager persistenceManager;

    private TextView userName, userScore, userWeapon;

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
                        player.setUserName(Profile.getCurrentProfile().getName());
                        persistenceManager.persistPlayer(player);
                        userName.setText(player.getUserName());
                        loginButton.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();

                        // App code
                    }
                });
        //FBEND
        //FB
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        //FBEND
        MobileAds.initialize(this, "ca-app-pub-2993800684617551~8713895821");
        loadAd();

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
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        userName = findViewById(R.id.user_name);
        userScore = findViewById(R.id.user_score);
        userWeapon = findViewById(R.id.user_weapon);
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
        if(isLoggedIn){
            loginButton.setVisibility(View.GONE);
        }else{
            loginButton.setVisibility(View.VISIBLE);
        }

        mAdView = findViewById(R.id.adView);
        pauseButton = findViewById(R.id.pause_button);
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
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), StoreActivity.class);
                startActivity(myIntent);
            }
        });
    }
    private void setUpGame(){
        persistenceManager = PersistenceManager.getInstance(getApplicationContext());
        Player tmpPlayer = null;
        try{
            tmpPlayer = persistenceManager.retrivePlayer();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(tmpPlayer!=null){
            player = tmpPlayer;
            userName.setText(player.getUserName());
            userScore.setText(String.valueOf((int)player.getScore()));
            userWeapon.setText(player.getWeapon());
        }else{
            player = new Player("guest",1.0,"Arrow",0);
            userName.setText(player.getUserName());
            userScore.setText(String.valueOf((int)player.getScore()));
            persistenceManager.persistPlayer(player);
            userWeapon.setText(player.getWeapon());
        }
        generateRound();

    }
    private void generateRound(){
        //TODO RANDOM ENEMY TYPE
        //TODO RANDOM ENEMY ENERGY
        int enemyType = (int) (Math.random() * (9 - 1)) + 1;
        double enemyTempEnergy =0.0;
        String enemyNameTemp="";
        switch (enemyType){
            case 1:
                enemyNameTemp = "bat";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.bat));
                break;
            case 2:
                enemyNameTemp = "beholder";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.beholder));
                break;
            case 3:
                enemyNameTemp = "crow";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.crow));
                break;
            case 4:
                enemyNameTemp = "cyclops";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.cyclops));
                break;
            case 5:
                enemyNameTemp = "demon";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.demon));
                break;
            case 6:
                enemyNameTemp = "goblin";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.goblin));
                break;
            case 7:
                enemyNameTemp = "orc";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.orc));
                break;
            case 8:
                enemyNameTemp = "skeleton";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.skeleton));
                break;
            case 9:
                enemyNameTemp = "slime";
                enemyBody.setImageDrawable(getResources().getDrawable(R.drawable.slime));
                break;
        }

        enemyTempEnergy = (double) (Math.random() * (player.getDamageLevel()*300 - player.getDamageLevel()*100)) + player.getDamageLevel()*100;
        currentEnemy = new Enemy(enemyNameTemp, enemyTempEnergy);
        //TODO MECHANISM FOR CHOOSING IMAGE en funcion de enemy type

        enemyName.setText(currentEnemy.getEnemyType());
        tempEnergy.setText(String.valueOf(currentEnemy.getEnergy()));
    }
    private void handleAttack(){
        double baseDamage = player.getDamageLevel();
        double multiplyer;
        switch(player.getWeapon()){
            case "arrow":
                multiplyer=1.0;
                break;
            case "bow":
                multiplyer=1.5;
                break;
            case "bukler":
                multiplyer=2.0;
                break;
            case "cub":
                multiplyer=2.5;
                break;
            case "dagger":
                multiplyer=3.0;
                break;
            case "katana":
                multiplyer=3.5;
                break;
            case "polearm":
                multiplyer=4.0;
                break;
            case "rod":
                multiplyer=4.5;
                break;
            case "shield":
                multiplyer=5.0;
                break;
            case "staff":
                multiplyer=5.5;
                break;
            case "sword":
                multiplyer=6.0;
                break;
            case "trident":
                multiplyer=6.5;
                break;
            default:
                multiplyer=1.0;
                break;
        }

        currentEnemy.onDamageTaken(baseDamage*multiplyer);
        tempEnergy.setText(String.valueOf(currentEnemy.getEnergy()));
    }
    private void enemyDefeated(){
        player.setScore(player.getScore()+1);
        player.setDamageLevel(player.getDamageLevel()+1.0);
        persistenceManager.persistPlayer(player);
        userScore.setText(String.valueOf((int)player.getScore()));
        generateRound();
    }
    private void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
