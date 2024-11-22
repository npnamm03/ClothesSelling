package com.example.miniproject_prm392.Admin;

import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_prm392.Models.App;
import com.example.miniproject_prm392.Models.CategoryModel;
import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdminUpdateCateActivity extends AppCompatActivity {

    Button updateButton, deleteButton;
    EditText uploadTypeEt, uploadTypeNameEt, uploadImageEt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_update_cate);

        uploadImageEt = findViewById(R.id.editImage);
        uploadTypeNameEt = findViewById(R.id.editTypeName);
        uploadTypeEt = findViewById(R.id.editType);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        uploadImageEt.setText(App.cate.getImg_url());
        uploadTypeNameEt.setText(App.cate.getName());
        uploadTypeEt.setText(App.cate.getType());

        updateData();
        deleteData();
    }

    public void deleteData(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Category").document(App.cate.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminUpdateCateActivity.this, "Category Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminUpdateCateActivity.this, "Error while deleting category", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void updateData(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> cate = new HashMap<>();
                cate.put("img_url", Objects.requireNonNull(uploadImageEt.getText()).toString());
                cate.put("name", Objects.requireNonNull(uploadTypeNameEt.getText()).toString());
                cate.put("type", Objects.requireNonNull(uploadTypeEt.getText()).toString());

                db.collection("Category").document(App.cate.getId()).set(cate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminUpdateCateActivity.this, "Category Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminUpdateCateActivity.this, "Error while update category", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}