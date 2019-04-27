package com.example.collegeapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.model.Guidelines;
import com.example.e_collegeapp.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class GuidelinesActivity extends AppCompatActivity {
      EditText etxtguideline;
      Button btnview;

      Guidelines guidelines;

      FirebaseUser firebaseUser;
      FirebaseAuth auth;
      FirebaseFirestore db;

    void initViews(){
        etxtguideline =findViewById(R.id.editTextguidelines);
        btnview = findViewById(R.id.buttonView);

        guidelines=new Guidelines();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              guidelines.guideln = etxtguideline.getText().toString();
              viewguidelines();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);
        initViews();
    }
    void viewguidelines(){
        Toast.makeText(getApplicationContext(),"View guidlines",Toast.LENGTH_LONG).show();
    }
}
