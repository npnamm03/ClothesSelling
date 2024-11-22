package com.example.miniproject_prm392.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_prm392.Admin.CategoryAdminAdapter;
import com.example.miniproject_prm392.Models.App;
import com.example.miniproject_prm392.Models.CategoryModel;
import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoryActivity extends AppCompatActivity {
    RecyclerView catRecycleView;
    FirebaseFirestore db;
    List<CategoryModel> categoryModelList;
    CategoryAdminAdapter categoryAdapter;
    androidx.appcompat.widget.SearchView searchView;
    ProgressDialog progressDialog;
    FloatingActionButton btnAdd;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        toolbar = findViewById(R.id.admin_cate_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        catRecycleView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        btnAdd = findViewById(R.id.add_cat_admin1);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Welcome To Admin Version");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        catRecycleView.setLayoutManager(new LinearLayoutManager(AdminCategoryActivity.this, RecyclerView.VERTICAL, false));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddCateActivity.class);
                startActivity(intent);
            }
        });

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            categoryModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModel.setId(document.getId());
                                categoryModelList.add(categoryModel);

                            }
                            categoryAdapter = new CategoryAdminAdapter(AdminCategoryActivity.this, categoryModelList);
                            catRecycleView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                            categoryAdapter.setOnItemClickListener(new CategoryAdminAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(CategoryModel categoryModel) {
                                    App.cate = categoryModel;
                                    startActivity(new Intent(AdminCategoryActivity.this, AdminUpdateCateActivity.class));
                                }
                            });
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(AdminCategoryActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FloatingActionButton refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                db.collection("Category")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    categoryModelList = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                        categoryModel.setId(document.getId());
                                        categoryModelList.add(categoryModel);

                                    }
                                    categoryAdapter = new CategoryAdminAdapter(AdminCategoryActivity.this, categoryModelList);
                                    catRecycleView.setAdapter(categoryAdapter);
                                    categoryAdapter.notifyDataSetChanged();
                                    categoryAdapter.setOnItemClickListener(new CategoryAdminAdapter.OnItemClickListener() {
                                        @Override
                                        public void onClick(CategoryModel categoryModel) {
                                            App.cate = categoryModel;
                                            startActivity(new Intent(AdminCategoryActivity.this, AdminUpdateCateActivity.class));
                                        }
                                    });
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(AdminCategoryActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoryAdapter.filter(newText);
                return true;
            }
        });
    }

}