package com.example.collegeapp.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.model.CollegeInfo;
import com.example.collegeapp.model.Util;
import com.example.e_collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class InfoActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    CollegeInfo collegeInfo;
    EditText etxtinfo;
    EditText etxtaddress,etxtwebsite,etxtphone;
    TextView txtnewdeadline,txttransferdeadline;
    EditText newStudent,transerStudent;
    Button btnsave,btnback;
    DatePickerDialog datePickerDialog;


    void initviews(){
        txtnewdeadline=findViewById(R.id.NewDeadline);
        txttransferdeadline=findViewById(R.id.transferDeadline);
        etxtinfo = findViewById(R.id.editTextinfo);
        etxtaddress = findViewById(R.id.editTextAddress);
        etxtwebsite=findViewById(R.id.editTextWebsite);
        etxtphone=findViewById(R.id.editTextphone);
        newStudent = findViewById(R.id.newStudent);
        transerStudent=findViewById(R.id.transferStudent);
        btnsave = findViewById(R.id.buttonSave);
        btnback = findViewById(R.id.buttonback);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        collegeInfo = new CollegeInfo();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        txtnewdeadline.setOnClickListener(clickListener);
        txttransferdeadline.setOnClickListener(clickListener);

        btnsave.setOnClickListener(clickListener);
        btnback.setOnClickListener(clickListener);

    }
    void saveDetails(){
        progressDialog.show();
        db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid()).collection("CollegeDetails").add(collegeInfo)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isComplete()){
                            Toast.makeText(InfoActivity.this, "Details Saved Successfully", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch (id){
                case R.id.NewDeadline:
                    showdatepickerDialog();
                    break;
                case R.id.transferDeadline:
                    showdatepickerDialog();
                    break;
                case R.id.buttonSave:
                    collegeInfo.info=etxtinfo.getText().toString();
                    collegeInfo.newDeadline=txtnewdeadline.getText().toString();
                    collegeInfo.transferdeadline=txttransferdeadline.getText().toString();
                    collegeInfo.newstufee=newStudent.getText().toString();
                    collegeInfo.transferstufee=transerStudent.getText().toString();
                    collegeInfo.phone=etxtphone.getText().toString();
                    collegeInfo.address=etxtaddress.getText().toString();
                    collegeInfo.website=etxtwebsite.getText().toString();
                    if(Util.isInternetConnected(InfoActivity.this)) {
                        saveDetails();
                    }else{
                        Toast.makeText(InfoActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.buttonback:
                    Intent intent=new Intent(InfoActivity.this,AllCollegeActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };
    void showdatepickerDialog(){
        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(this, onDateSetListener, yy, mm, dd);
        datePickerDialog.show();
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = year+"/"+(month+1)+"/"+dayOfMonth;
            int id=view.getId();
            if (id==R.id.NewDeadline){
                txtnewdeadline.setText(date);
            }else{
                txttransferdeadline.setText(date);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("E-College");
        setContentView(R.layout.activity_info);
        initviews();

    }


}
