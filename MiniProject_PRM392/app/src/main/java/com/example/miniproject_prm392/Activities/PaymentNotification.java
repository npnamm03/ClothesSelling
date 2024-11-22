package com.example.miniproject_prm392.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject_prm392.Admin.AdminUpdateProductActivity;
import com.example.miniproject_prm392.Fragments.HomeFragment;
import com.example.miniproject_prm392.Models.App;
import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentNotification extends AppCompatActivity {

    TextView txtNotification, tv_back_home;
    ImageView img_fail, img_success;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_payment_notification);
        tv_back_home = findViewById(R.id.tv_back_home);
        img_fail = findViewById(R.id.img_fail);
        img_success = findViewById(R.id.img_success);
        txtNotification = findViewById(R.id.textViewNotify);
        Intent intent = getIntent();
        txtNotification.setText(intent.getStringExtra("result"));

        if(intent.getStringExtra("result").equalsIgnoreCase("Payment success")){
            img_success.setVisibility(View.VISIBLE);
            img_fail.setVisibility(View.INVISIBLE);

        }else{
            img_success.setVisibility(View.INVISIBLE);
            img_fail.setVisibility(View.VISIBLE);
        }

        tv_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.document("/AddToCart/ptQ4XKp3YqZyMM7mAKRKdExORkp1").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PaymentNotification.this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PaymentNotification.this, "Error while deleting product", Toast.LENGTH_SHORT).show();
                    }
                });
                /*firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User")
                        .document().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PaymentNotification.this, "Removed from Cart", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PaymentNotification.this, "Error removing from cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
                Intent intent1 = new Intent(PaymentNotification.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}