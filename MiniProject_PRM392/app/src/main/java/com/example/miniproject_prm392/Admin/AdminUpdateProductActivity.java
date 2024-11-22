package com.example.miniproject_prm392.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miniproject_prm392.Models.App;
import com.example.miniproject_prm392.Models.CategoryModel;
import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdminUpdateProductActivity extends AppCompatActivity {

    Button updateButton, deleteButton;
    EditText uploadNameEt, uploadDesEt, uploadImageEt, uploadPriceEt, uploadRatingEt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_update_product);

        uploadImageEt = findViewById(R.id.editImage);
        uploadNameEt = findViewById(R.id.editName);
        uploadDesEt = findViewById(R.id.editDes);
        uploadPriceEt = findViewById(R.id.editPrice);
        uploadRatingEt = findViewById(R.id.editRating);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        uploadImageEt.setText(App.newProductsModel.getImg_url());
        uploadNameEt.setText(App.newProductsModel.getName());
        uploadDesEt.setText(App.newProductsModel.getDescription());
        uploadPriceEt.setText(String.valueOf(App.newProductsModel.getPrice()));
        uploadRatingEt.setText(App.newProductsModel.getRating());

        updateData();
        deleteData();
    }

    public void deleteData(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("AllProducts").document(App.newProductsModel.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminUpdateProductActivity.this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminUpdateProductActivity.this, "Error while deleting product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void updateData(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> product = new HashMap<>();
                product.put("img_url", Objects.requireNonNull(uploadImageEt.getText()).toString());
                product.put("name", Objects.requireNonNull(uploadNameEt.getText()).toString());
                product.put("description", Objects.requireNonNull(uploadDesEt.getText()).toString());
                int price = Integer.parseInt(Objects.requireNonNull(uploadPriceEt.getText()).toString());
                product.put("price", price);
                product.put("rating", Objects.requireNonNull(uploadRatingEt.getText()).toString());

                db.collection("AllProducts").document(App.newProductsModel.getId()).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminUpdateProductActivity.this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminUpdateProductActivity.this, "Error while update product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}