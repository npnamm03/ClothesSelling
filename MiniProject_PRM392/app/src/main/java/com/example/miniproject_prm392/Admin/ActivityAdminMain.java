package com.example.miniproject_prm392.Admin;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject_prm392.R;

public class ActivityAdminMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.btn_categories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAdminMain.this, AdminCategoryActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_products).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAdminMain.this, AdminProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
