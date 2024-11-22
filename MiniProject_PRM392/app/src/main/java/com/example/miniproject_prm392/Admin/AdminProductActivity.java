package com.example.miniproject_prm392.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_prm392.Models.App;
import com.example.miniproject_prm392.Models.CategoryModel;
import com.example.miniproject_prm392.Models.NewProductsModel;
import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminProductActivity extends AppCompatActivity {
    RecyclerView proRecycleView;
    FirebaseFirestore db;
    List<NewProductsModel> productModelList;
    ProductAdminAdapter productAdapter;
    androidx.appcompat.widget.SearchView searchView;
    ProgressDialog progressDialog;
    FloatingActionButton btnAdd;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);
        toolbar = findViewById(R.id.admin_pro_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        proRecycleView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchPro);
        btnAdd = findViewById(R.id.add_pro_admin);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Welcome To Admin Version");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        proRecycleView.setLayoutManager(new LinearLayoutManager(AdminProductActivity.this, RecyclerView.VERTICAL, false));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProductActivity.this, AdminAddProductActivity.class);
                startActivity(intent);
            }
        });

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            productModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModel.setId(document.getId());
                                productModelList.add(newProductsModel);

                            }
                            productAdapter = new ProductAdminAdapter(AdminProductActivity.this, productModelList);
                            proRecycleView.setAdapter(productAdapter);
                            productAdapter.notifyDataSetChanged();
                            productAdapter.setOnItemClickListener(new ProductAdminAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(NewProductsModel productsModel) {
                                    App.newProductsModel = productsModel;
                                    startActivity(new Intent(AdminProductActivity.this, AdminUpdateProductActivity.class));
                                }
                            });
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(AdminProductActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FloatingActionButton refresh = findViewById(R.id.refreshProduct);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                db.collection("AllProducts")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    productModelList = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                        newProductsModel.setId(document.getId());
                                        productModelList.add(newProductsModel);

                                    }
                                    productAdapter = new ProductAdminAdapter(AdminProductActivity.this, productModelList);
                                    proRecycleView.setAdapter(productAdapter);
                                    productAdapter.notifyDataSetChanged();
                                    productAdapter.setOnItemClickListener(new ProductAdminAdapter.OnItemClickListener() {
                                        @Override
                                        public void onClick(NewProductsModel productsModel) {
                                            App.newProductsModel = productsModel;
                                            startActivity(new Intent(AdminProductActivity.this, AdminUpdateProductActivity.class));
                                        }
                                    });
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(AdminProductActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
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
                productAdapter.filter(newText);
                return true;
            }
        });
    }

}
