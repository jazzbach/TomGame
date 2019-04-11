package com.vfs.tom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class StoreActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;
    Button buttonPrueba, buttonPrueba2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        initView();
        bp = new BillingProcessor(this, null, this);
        bp.initialize();
    }

    protected void initView(){
        buttonPrueba = findViewById(R.id.button_prueba);
        buttonPrueba2 = findViewById(R.id.button_prueba2);
        buttonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(StoreActivity.this, "android.test.purchased");
            }
        });
        buttonPrueba2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(StoreActivity.this, "android.test.purchased");
            }
        });
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

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
}
