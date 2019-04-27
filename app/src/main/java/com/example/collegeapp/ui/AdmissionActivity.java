package com.example.collegeapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_collegeapp.R;

public class AdmissionActivity extends AppCompatActivity implements View.OnClickListener {
   TextView guidelines,feeStructure;
   Button btnForm;
   public  void initViews(){
       guidelines = findViewById(R.id.textViewDeadline);
       feeStructure=findViewById(R.id.textViewFees);
       btnForm = findViewById(R.id.buttonForms);

       guidelines.setOnClickListener(this);
       feeStructure.setOnClickListener(this);
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);
        initViews();
    }

    @Override
    public void onClick(View v) {
       int id=v.getId();
       switch (id){
           case R.id.textViewDeadline:
               Intent intent= new Intent(AdmissionActivity.this,GuidelinesActivity.class);
               startActivity(intent);
               break;
           case R.id.textViewFees:
               Intent intent1= new Intent(AdmissionActivity.this, FeesStructureActivity.class);
               startActivity(intent1);
               break;


       }

    }
}
