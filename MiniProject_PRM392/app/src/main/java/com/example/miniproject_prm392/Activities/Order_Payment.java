package com.example.miniproject_prm392.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject_prm392.Api.CreateOrder;
import com.example.miniproject_prm392.R;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class Order_Payment extends AppCompatActivity {

    private TextView tv_back_home, tv_totalPrice, tv_confirm;
    private Button tv_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_payment);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        initUI();
        Intent intent = getIntent();
        String totalMoney = intent.getStringExtra("total");
        System.out.println(totalMoney);
        tv_totalPrice.setText("Total price: " + formatPrice(Integer.parseInt(totalMoney)));
        tv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(totalMoney);
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(Order_Payment.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Intent intent1 = new Intent(Order_Payment.this, PaymentNotification.class);
                                intent1.putExtra("result", "Payment Success");
                                startActivity(intent1);
                                finish();
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent1 = new Intent(Order_Payment.this, PaymentNotification.class);
                                intent1.putExtra("result", "Cancel");
                                startActivity(intent1);
                                finish();
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent1 = new Intent(Order_Payment.this, PaymentNotification.class);
                                intent1.putExtra("result", "Error");
                                startActivity(intent1);
                                finish();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        tv_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Order_Payment.this, CartActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void initUI() {
        tv_payment = findViewById(R.id.tv_payment_order);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_back_home = findViewById(R.id.tv_back_home);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static String formatPrice(int price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormat.format(price);
    }
}