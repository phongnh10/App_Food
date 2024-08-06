package com.example.du_an_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import dao.UserDAO;
import data.base.DbHelper;

public class SigninActivity extends AppCompatActivity {

    EditText edtUser,edtPass,edtRePass,edtFName,edtPhone,edtCCCD;
    Button btnSignin;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ImageView btnBack = findViewById(R.id.btnBack);

        // back to starActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, StarActivity.class);
                startActivity(intent);
            }
        });
        // code register
        edtUser = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        edtRePass = findViewById(R.id.edtRePassword);
        edtPhone = findViewById(R.id.edtPhone);
        edtFName = findViewById(R.id.edtFullName);
        edtCCCD = findViewById(R.id.edtCCCD);
        btnSignin = findViewById(R.id.btnSignin);
        UserDAO userDAO = new UserDAO(SigninActivity.this);


    }
}