package com.example.app_note;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;

import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;

import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;

import com.android.billingclient.api.QueryProductDetailsParams;
import com.example.app_note.Adapter.AdapterInApp;


import java.util.ArrayList;
import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    BillingClient billingClient;
    PurchasesUpdatedListener purchasesUpdatedListener;
    ConsumeResponseListener listener;
    AdapterInApp adapterInApp;
    RecyclerView recyclerView;

    TextView tvPremium;
    Button btnTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        btnTT = findViewById(R.id.btnLoad);
        tvPremium = findViewById(R.id.tvPrema);
        recyclerView = findViewById(R.id.rcyViewInapp);

        setupbilling();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        adapterInApp = new AdapterInApp(this, billingClient);
        recyclerView.setAdapter(adapterInApp);

        btnTT.setOnClickListener(view -> {
            if (billingClient.isReady()){
                loadInappItem();
            }
        });



    }

    private void handleItemAlrealyPurchases(List<Purchase> purchases) {
        StringBuilder inappItem = new StringBuilder(tvPremium.getText());
        loadInappItem();

    }


    private void loadInappItem() {
      List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
      productList.add(QueryProductDetailsParams.Product.newBuilder()
              .setProductId("id_product_inapp1")
              .setProductType(BillingClient.ProductType.INAPP)
              .build());
        productList.add(QueryProductDetailsParams.Product.newBuilder()
                .setProductId("id_product_inapp2")
                .setProductType(BillingClient.ProductType.INAPP)
                .build());
        productList.add(QueryProductDetailsParams.Product.newBuilder()
                .setProductId("id_product_inapp3")
                .setProductType(BillingClient.ProductType.INAPP)
                .build());
        productList.add(QueryProductDetailsParams.Product.newBuilder()
                .setProductId("id_product_inapp4")
                .setProductType(BillingClient.ProductType.INAPP)
                .build());
        productList.add(QueryProductDetailsParams.Product.newBuilder()
                .setProductId("id_product_inapp5")
                .setProductType(BillingClient.ProductType.INAPP)
                .build());

        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList).build();
        billingClient.queryProductDetailsAsync(params, new ProductDetailsResponseListener() {
            @Override
            public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull List<ProductDetails> list) {
                loadRecyclerView(list);
            }
        });


    }

    private void loadRecyclerView(List<ProductDetails> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterInApp.loadData(list);
            }
        });
    }

    private void setupbilling() {
        listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(ThanhToanActivity.this, "consume OK", Toast.LENGTH_SHORT).show();
                }
            }
        };

        billingClient = BillingClientSetup.getInstance(this,  this);

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(ThanhToanActivity.this, "Succer to commert billing", Toast.LENGTH_SHORT).show();
                    loadInappItem();

                }else {
                    Toast.makeText(ThanhToanActivity.this, "Error code: ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(ThanhToanActivity.this, "You are disconnet from billing service", Toast.LENGTH_SHORT).show();
            }


        });

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }
}
