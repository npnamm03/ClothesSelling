package com.example.miniproject_prm392.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminAddProductActivity extends AppCompatActivity {
    EditText uploadImage;
    Button saveButton;
    EditText uploadName, uploadDes, uploadPrice, uploadRating;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);
        uploadImage = findViewById(R.id.uploadImage);
        uploadName = findViewById(R.id.uploadName);
        uploadDes = findViewById(R.id.uploadDes);
        uploadPrice = findViewById(R.id.uploadPrice);
        uploadRating = findViewById(R.id.uploadRating);
        saveButton = findViewById(R.id.saveButton);
        toolbar = findViewById(R.id.admin_add_pro_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveData();
    }

    public void saveData(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> product = new HashMap<>();
                product.put("img_url", Objects.requireNonNull(uploadImage.getText()).toString());
                product.put("name", Objects.requireNonNull(uploadName.getText()).toString());
                product.put("description", Objects.requireNonNull(uploadDes.getText()).toString());
                int price = Integer.parseInt(Objects.requireNonNull(uploadPrice.getText()).toString());
                product.put("price", price);
                product.put("rating", Objects.requireNonNull(uploadRating.getText()).toString());

                db.collection("AllProducts").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AdminAddProductActivity.this, "Products Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminAddProductActivity.this, "There was an error while adding product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}