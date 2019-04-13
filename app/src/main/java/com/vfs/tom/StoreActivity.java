package com.vfs.tom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.vfs.tom.managers.PersistenceManager;
import com.vfs.tom.model.Player;

public class StoreActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;
    Button buttonPrueba, buttonPrueba2;
    private LinearLayout arma1, arma2, arma3, arma4, arma5, arma6, arma7, arma8,arma9,arma10, arma11, arma12;
    private RelativeLayout arma2Bloq,arma3Bloq,arma4Bloq,arma5Bloq,arma6Bloq,arma7Bloq,arma8Bloq,arma9Bloq,arma10Bloq,arma11Bloq,arma12Bloq;
    private Player player;
    private PersistenceManager persistenceManager;
    private boolean showWeapons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        persistenceManager = PersistenceManager.getInstance(getApplicationContext());
        try{
            showWeapons = persistenceManager.retriveWasPackPurchased();
        }catch (Exception e){
            e.printStackTrace();
        }
        initView();
        player = persistenceManager.retrivePlayer();
        bp = new BillingProcessor(this, null, this);
        bp.initialize();
    }

    protected void initView(){
        buttonPrueba = findViewById(R.id.button_prueba);
        arma1=findViewById(R.id.arma_1);
        arma2=findViewById(R.id.arma_2);
        arma2Bloq=findViewById(R.id.arma_2_bloq);
        arma3=findViewById(R.id.arma_3);
        arma3Bloq=findViewById(R.id.arma_3_bloq);
        arma4=findViewById(R.id.arma_4);
        arma4Bloq=findViewById(R.id.arma_4_bloq);
        arma5=findViewById(R.id.arma_5);
        arma5Bloq=findViewById(R.id.arma_5_bloq);
        arma6=findViewById(R.id.arma_6);
        arma6Bloq=findViewById(R.id.arma_6_bloq);
        arma7=findViewById(R.id.arma_7);
        arma7Bloq=findViewById(R.id.arma_7_bloq);
        arma8=findViewById(R.id.arma_8);
        arma8Bloq=findViewById(R.id.arma_8_bloq);
        arma9=findViewById(R.id.arma_9);
        arma9Bloq=findViewById(R.id.arma_9_bloq);
        arma10=findViewById(R.id.arma_10);
        arma10Bloq=findViewById(R.id.arma_10_bloq);
        arma11=findViewById(R.id.arma_11);
        arma11Bloq=findViewById(R.id.arma_11_bloq);
        arma12=findViewById(R.id.arma_12);
        arma12Bloq=findViewById(R.id.arma_12_bloq);

        if(showWeapons){
            showWeapons();
        }else{
            hideWeapons();
        }
        setListeners();
    }

    public void setListeners(){
        buttonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(StoreActivity.this, "android.test.purchased");
            }
        });
        final Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        arma1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("arrow");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        persistenceManager.persistWasPackPurchased(true);
        showWeapons();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        //Log.e("pene"+error.toString(),error.toString());

    }

    @Override
    public void onBillingInitialized() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    public void showWeapons(){
        buttonPrueba.setVisibility(View.GONE);

        arma2.setClickable(true);
        arma2.setFocusable(true);
        arma2Bloq.setVisibility(View.GONE);

        arma3.setClickable(true);
        arma3.setFocusable(true);
        arma3Bloq.setVisibility(View.GONE);

        arma4.setClickable(true);
        arma4.setFocusable(true);
        arma4Bloq.setVisibility(View.GONE);

        arma5.setClickable(true);
        arma5.setFocusable(true);
        arma5Bloq.setVisibility(View.GONE);

        arma6.setClickable(true);
        arma6.setFocusable(true);
        arma6Bloq.setVisibility(View.GONE);

        arma7.setClickable(true);
        arma7.setFocusable(true);
        arma7Bloq.setVisibility(View.GONE);

        arma8.setClickable(true);
        arma8.setFocusable(true);
        arma8Bloq.setVisibility(View.GONE);

        arma9.setClickable(true);
        arma9.setFocusable(true);
        arma9Bloq.setVisibility(View.GONE);

        arma10.setClickable(true);
        arma10.setFocusable(true);
        arma10Bloq.setVisibility(View.GONE);

        arma11.setClickable(true);
        arma11.setFocusable(true);
        arma11Bloq.setVisibility(View.GONE);

        arma12.setClickable(true);
        arma12.setFocusable(true);
        arma12Bloq.setVisibility(View.GONE);

        final Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        arma2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("bow");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("bukler");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("cub");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("dagger");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("katana");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("polearm");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("rod");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("shield");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("staff");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("sword");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
        arma12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setWeapon("trident");
                persistenceManager.persistPlayer(player);
                startActivity(myIntent);
                finish();
            }
        });
    }
    public void hideWeapons(){
        buttonPrueba.setVisibility(View.VISIBLE);

        arma2.setClickable(false);
        arma2.setFocusable(false);
        arma2Bloq.setVisibility(View.VISIBLE);

        arma3.setClickable(false);
        arma3.setFocusable(false);
        arma3Bloq.setVisibility(View.VISIBLE);

        arma4.setClickable(false);
        arma4.setFocusable(false);
        arma4Bloq.setVisibility(View.VISIBLE);

        arma5.setClickable(false);
        arma5.setFocusable(false);
        arma5Bloq.setVisibility(View.VISIBLE);

        arma6.setClickable(false);
        arma6.setFocusable(false);
        arma6Bloq.setVisibility(View.VISIBLE);

        arma7.setClickable(false);
        arma7.setFocusable(false);
        arma7Bloq.setVisibility(View.VISIBLE);

        arma8.setClickable(false);
        arma8.setFocusable(false);
        arma8Bloq.setVisibility(View.VISIBLE);

        arma9.setClickable(false);
        arma9.setFocusable(false);
        arma9Bloq.setVisibility(View.VISIBLE);

        arma10.setClickable(false);
        arma10.setFocusable(false);
        arma10Bloq.setVisibility(View.VISIBLE);

        arma11.setClickable(false);
        arma11.setFocusable(false);
        arma11Bloq.setVisibility(View.VISIBLE);

        arma12.setClickable(false);
        arma12.setFocusable(false);
        arma12Bloq.setVisibility(View.VISIBLE);
    }
}
