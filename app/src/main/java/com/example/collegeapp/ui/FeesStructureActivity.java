package com.example.collegeapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e_collegeapp.R;

public class FeesStructureActivity extends AppCompatActivity {
    TextView txtpaymnt;
     void initViews(){
         txtpaymnt= findViewById(R.id.textViewpayment);

         txtpaymnt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent= new Intent(FeesStructureActivity.this,GuidelinesActivity.class);
                 startActivity(intent);

             }
         });
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_structure);
        initViews();
    }
}
