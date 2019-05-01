package com.example.collegeapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.collegeapp.model.College;

import com.example.collegeapp.model.User;
import com.example.collegeapp.model.Util;
import com.example.e_collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText eTxtName, eTxtEmail, eTxtPassword;//,etxtcity,etxtstate;
    TextView txtLogin, txtRegister;
    Button btnRegister;
    College colleges;
    User user;
boolean updateMode;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;

    void initViews() {
        eTxtName = findViewById(R.id.editTextName);
        eTxtEmail = findViewById(R.id.editTextEmail);
        eTxtPassword = findViewById(R.id.editTextPassword);
        //etxtcity=findViewById(R.id.editcity);
        //etxtstate=findViewById(R.id.editstate);
        txtRegister=findViewById(R.id.textViewRegister);
        btnRegister = findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener((View.OnClickListener) this);
        txtLogin = findViewById(R.id.textViewLogin);
        txtLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        user=new User();

        colleges = new College();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser=auth.getCurrentUser();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.buttonRegister){

            user.Name = eTxtName.getText().toString();
            //colleges.city=etxtcity.getText().toString();
            //colleges.state=etxtstate.getText().toString();
            user.Email=eTxtEmail.getText().toString();
            user.Password=eTxtPassword.getText().toString();
            if(Util.isInternetConnected(this)) {
                registerUser();
            }else {
                Toast.makeText(this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
            }
        } else {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    void registerUser() {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(user.Email, user.Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                         /*Toast.makeText(RegistrationActivity.this,user.Name+"Registered Sucessfully",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();*/
                            saveUserInCloudDB();
                        }
                    }
                });
    }

    void saveUserInCloudDB() {


        firebaseUser = auth.getCurrentUser();
        db.collection("User").document(firebaseUser.getUid()).set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegistrationActivity.this, user.Name + " Registered Successful", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        //Intent intent = new Intent(RegisterActivity.this, SuccessActivity.class);
                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }
    }

