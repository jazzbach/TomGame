package com.vfs.tom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vfs.tom.model.Enemy;

public class MainActivity extends AppCompatActivity {
    //TODO generate player class (damage done, current weapon, ?)
    //enemy_name
    //temp_energy
    private ImageButton enemyBody;
    private Enemy currentEnemy;
    //VALOR TEMPORAL
    private int playerDamage;
    private TextView enemyName;
    private TextView tempEnergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setUpGame();
        setListeners();
    }

    protected void initView(){
        enemyBody = findViewById(R.id.enemy_body);
        enemyName = findViewById(R.id.enemy_name);
        tempEnergy = findViewById(R.id.temp_energy);
    }
    private void setListeners(){
        enemyBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e("pruebita","down");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("pruebita","up");
                        handleAttack();
                        break;
                }
                return true;
            }
        });
    }
    private void setUpGame(){
        //TODO GET PLAYER PARAMS AND ENEMY VALUES (LEVEL?)
        playerDamage = 10;
        /*if(){

        }else{

        }*/
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
        if(currentEnemy.getEnergy()>0){
            currentEnemy.onDamageTaken(playerDamage);
            tempEnergy.setText(String.valueOf(currentEnemy.getEnergy()));
        }else{
            enemyDefeated();
        }

    }
    private void enemyDefeated(){
        generateRound();
    }
}
