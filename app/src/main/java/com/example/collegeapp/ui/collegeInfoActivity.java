package com.example.collegeapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.model.CollegeInfo;
import com.example.e_collegeapp.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class collegeInfoActivity extends AppCompatActivity {
    EditText etxtinfo;
    Button btnsubmit;

    CollegeInfo collegeInfo;

    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    void initViews(){
        etxtinfo = findViewById(R.id.editTextinfo);
        btnsubmit= findViewById(R.id.buttonsubmit);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        collegeInfo = new CollegeInfo();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collegeInfo.info = etxtinfo.getText().toString();
                submitInformation();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_info);
        initViews();
    }
    void submitInformation(){
        progressDialog.show();
        Toast.makeText(getApplicationContext(),"Submit information",Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
}
