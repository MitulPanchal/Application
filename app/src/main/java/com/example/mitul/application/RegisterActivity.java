package com.example.mitul.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    public DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText name = findViewById(R.id.editName);
        final EditText email = findViewById(R.id.editEmail);
        final EditText password = findViewById(R.id.editPassword);
        final EditText confirmPassowrd = findViewById(R.id.editConfirmPassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Button buttonRegister = findViewById(R.id.buttonRegister);
        final TextView textViewSingIn = findViewById(R.id.textViewSignIn);

        databaseHelper = new DatabaseHelper(RegisterActivity.this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _name = name.getText().toString();
                String _email = email.getText().toString();
                String _password = password.getText().toString();
                String _confirmPassword = confirmPassowrd.getText().toString();


                if (TextUtils.isEmpty(_name)){
                    Toast.makeText(RegisterActivity.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(_email)){
                    Toast.makeText(RegisterActivity.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(_password)){
                    Toast.makeText(RegisterActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(_confirmPassword)){
                    Toast.makeText(RegisterActivity.this,"Please Confirm Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!_password.equals(_confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Password don't Match!", Toast.LENGTH_LONG).show();
                }
                else{
                    UserInfo userInfo = new UserInfo();
                    userInfo.set_name(_name);
                    userInfo.set_email(_email);
                    userInfo.set_password(_password);

                    databaseHelper.insertContact(userInfo);
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        textViewSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
