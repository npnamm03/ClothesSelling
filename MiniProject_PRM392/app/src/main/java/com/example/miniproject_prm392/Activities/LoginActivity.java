package com.example.miniproject_prm392.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject_prm392.Admin.ActivityAdminMain;
import com.example.miniproject_prm392.Admin.AdminCategoryActivity;
import com.example.miniproject_prm392.Admin.AdminProductActivity;
import com.example.miniproject_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    private FirebaseAuth auth;
    Button btnMap;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnMap = findViewById(R.id.mapBtn);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(i);
            }
        });
    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    public void signIn(View view) {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length() < 6) {
            Toast.makeText(this, "Password must > 6!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(userEmail.equalsIgnoreCase("admin@prm.com")  && userPassword.equalsIgnoreCase("admin123")) {
                            Toast.makeText(LoginActivity.this, "Successfully admin login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, AdminProductActivity.class));
                        } else if(task.isSuccessful() && userEmail != "admin@prm.com" && userPassword != "admin123") {
                            Toast.makeText(LoginActivity.this, "Successfully login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Login fail" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });}
}
