package com.daniel.fakecaller;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;

public class DonateActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        bp = BillingProcessor.newBillingProcessor(this, Constants.LICENSE_KEY, this);
        bp.initialize();

        findViewById(R.id.silver).setOnClickListener((View.OnClickListener) view -> {
            bp.purchase(DonateActivity.this, Constants.FIFTY_DOLLAR_PRODUCT);
        });

        findViewById(R.id.gold).setOnClickListener((View.OnClickListener) view -> {
            bp.purchase(DonateActivity.this, Constants.ONE_FIFTY_DOLLAR_PRODUCT);
        });

        findViewById(R.id.platinum).setOnClickListener((View.OnClickListener) view ->
                bp.purchase(DonateActivity.this, Constants.TWO_SIXTY_DOLLAR_PRODUCT)
        );
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        Toast.makeText(DonateActivity.this, "Purchase successful", Toast.LENGTH_SHORT).show();
        TextView textView = findViewById(R.id.purchasedTextView);

        if (productId.equals(Constants.FIFTY_DOLLAR_PRODUCT))
            textView.setText("Silver package purchased");
        if (productId.equals(Constants.ONE_FIFTY_DOLLAR_PRODUCT))
            textView.setText("Gold package purchased");
        if (productId.equals(Constants.TWO_SIXTY_DOLLAR_PRODUCT))
            textView.setText("Platinum package purchased");

        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(DonateActivity.this, "onBillingError: code: " + errorCode + " \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}