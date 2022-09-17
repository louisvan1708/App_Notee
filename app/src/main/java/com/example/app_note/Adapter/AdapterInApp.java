package com.example.app_note.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ProductDetails;
import com.example.app_note.ItemClick;
import com.example.app_note.R;
import com.example.app_note.ThanhToanActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterInApp extends RecyclerView.Adapter<AdapterInApp.InAppViewHolder> {

    AppCompatActivity appCompatActivity;
    List<ProductDetails> skuDetailsList;
    BillingClient billingClient;

    public AdapterInApp(AppCompatActivity appCompatActivity, List<ProductDetails> skuDetailsList, BillingClient billingClient) {
        this.appCompatActivity = appCompatActivity;
        this.skuDetailsList = skuDetailsList;
        this.billingClient = billingClient;
    }

    public AdapterInApp(ThanhToanActivity appCompatActivity, BillingClient billingClient) {
        this.appCompatActivity = appCompatActivity;
        this.skuDetailsList = new ArrayList<>();
        this.billingClient =billingClient;
    }

    public void loadData(List<ProductDetails> list){
        if (list != null && list.size() > 0){
            this.skuDetailsList.clear();
            this.skuDetailsList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterInApp.InAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder: ");

        return new InAppViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_app, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInApp.InAppViewHolder holder, int position) {

        ProductDetails productDetails = skuDetailsList.get(position);
        holder.tvPrive.setText(productDetails.getTitle());
        holder.tvPrive.setText(productDetails.getOneTimePurchaseOfferDetails().getFormattedPrice());

        holder.setOnClick(new ItemClick() {
            @Override
            public void onClick(View view, int position) {

                BillingFlowParams.ProductDetailsParams params = BillingFlowParams.ProductDetailsParams
                        .newBuilder()
                        .setProductDetails(productDetails)
                        .build();

                List<BillingFlowParams.ProductDetailsParams> list = new ArrayList<>();
                list.add(params);
                BillingFlowParams billingFlowParams = BillingFlowParams
                        .newBuilder()
                        .setProductDetailsParamsList(list)
                        .build();

                int response = billingClient.launchBillingFlow(appCompatActivity, billingFlowParams).getResponseCode();

                switch (response){
                    case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                        Toast.makeText(appCompatActivity, "BILLING_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                        break;
                    case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                        Toast.makeText(appCompatActivity, "DEVELOPER_ERROR", Toast.LENGTH_SHORT).show();
                        break;
                    case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                        Toast.makeText(appCompatActivity, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
                        break;
                    case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                        Toast.makeText(appCompatActivity, "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show();
                        break;

                    case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                        Toast.makeText(appCompatActivity, "SERVICE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                        break;
                    case BillingClient.BillingResponseCode.SERVICE_TIMEOUT:
                        Toast.makeText(appCompatActivity, "SERVICE_TIMEOUT", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.skuDetailsList.size();
    }

    public class InAppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTile, tvPrive;
        ItemClick itemClick;

        public void setOnClick(ItemClick onClick){
            this.itemClick = onClick;
        }

        public InAppViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTile = itemView.findViewById(R.id.tvTille);
            tvPrive = itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemClick.onClick(view, getAdapterPosition());
        }
    }
}
