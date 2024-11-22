package com.example.miniproject_prm392.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminAddCateActivity extends AppCompatActivity {
    EditText uploadImage;
    Button saveButton;
    EditText uploadType, uploadTypeName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_cate);
        uploadImage = findViewById(R.id.uploadImage);
        uploadTypeName = findViewById(R.id.uploadTypeName);
        uploadType = findViewById(R.id.uploadType);
        saveButton = findViewById(R.id.saveButton);

        toolbar = findViewById(R.id.admin_add_cate_toolbar);
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
                Map<String, Object> user = new HashMap<>();
                user.put("img_url", Objects.requireNonNull(uploadImage.getText()).toString());
                user.put("name", Objects.requireNonNull(uploadTypeName.getText()).toString());
                user.put("type", Objects.requireNonNull(uploadType.getText()).toString());

                db.collection("Category").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AdminAddCateActivity.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminAddCateActivity.this, "There was an error while adding category", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}