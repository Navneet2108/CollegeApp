package com.example.collegeapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.model.Courses;
import com.example.collegeapp.model.Guidelines;
import com.example.e_collegeapp.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GuidelinesActivity extends AppCompatActivity implements View.OnClickListener{
      EditText etxtguideline;
      Button btnview,btnsave;

      Guidelines guidelines;
    boolean updateMode;
      FirebaseUser firebaseUser;
      FirebaseAuth auth;
      FirebaseFirestore db;

      ProgressDialog progressDialog;

    void initViews(){
        etxtguideline =findViewById(R.id.editTextguidelines);
        btnview = findViewById(R.id.btnView);
        btnsave=findViewById(R.id.btnsave);

        guidelines=new Guidelines();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);


        btnview.setOnClickListener(this);
        btnsave.setOnClickListener(this);

        Intent rcv = getIntent();

        updateMode = rcv.hasExtra("keyGuideln");
        if (updateMode) {
            getSupportActionBar().setTitle("E-College");
            guidelines = (Guidelines) rcv.getSerializableExtra("keyGuideln");
            etxtguideline.setText(guidelines.guideln);

            btnsave.setText("Update Course");
            btnview.setVisibility(View.INVISIBLE);


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);
        initViews();
    }
    void saveguidelines(){
        if (updateMode) {
            db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid()).collection("AdmissionGuidelines").document(guidelines.docid).set(guidelines)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(GuidelinesActivity.this, "Updation Finished", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(GuidelinesActivity.this, CoursesActivity.class);
                                startActivity(intent);
                                //finish();
                            } else {
                                Toast.makeText(GuidelinesActivity.this, "Updation Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else {
            progressDialog.show();
            db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid()).collection("AdmissionGuidelines").add(guidelines)
                    .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isComplete()) {
                                Toast.makeText(GuidelinesActivity.this, "Details Saved Successfully", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                clearFields();
                            }
                        }
                    });
        }
    }
    void clearFields(){
        etxtguideline.setText("");


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnsave:
                guidelines.guideln = etxtguideline.getText().toString();
                saveguidelines();
                break;
            case R.id.btnView:
                Intent intent=new Intent(GuidelinesActivity.this,AllGuidelinesActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
