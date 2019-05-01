package com.example.collegeapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.model.College;
import com.example.collegeapp.model.Courses;
import com.example.collegeapp.model.User;
import com.example.e_collegeapp.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCoursesActivity extends AppCompatActivity implements View.OnClickListener {
      EditText etxtcourse,etxtcoursefee;
      Button btnSubmit,btnView;

      Courses courses;
      User user;

      FirebaseUser firebaseUser;
      FirebaseAuth auth;
      FirebaseFirestore db;

boolean updateMode;
    void initViews(){
      etxtcourse = findViewById(R.id.editTextCoures);
        etxtcoursefee = findViewById(R.id.editTextCoursefees);
       btnSubmit=findViewById(R.id.buttonsubmit);
       btnView=findViewById(R.id.buttonview);

       courses = new Courses();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        user=new User();

        btnSubmit.setOnClickListener(this);

        btnView.setOnClickListener(this);

        Intent rcv = getIntent();

        updateMode = rcv.hasExtra("keyCourse");
        if (updateMode) {
            getSupportActionBar().setTitle("Update Course");
            courses = (Courses) rcv.getSerializableExtra("keyCourse");
            etxtcourse.setText(courses.Name);
            etxtcoursefee.setText(courses.coursefees);
            btnSubmit.setText("Update Course");
            btnView.setVisibility(View.INVISIBLE);

        }


    }
    void saveCoursesInCloud() {
        if (updateMode) {
            db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid()).collection("Courses").document(courses.doc_Id).set(courses)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(AddCoursesActivity.this, "Updation Finished", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddCoursesActivity.this, CoursesActivity.class);
                                startActivity(intent);
                                //finish();
                            } else {
                                Toast.makeText(AddCoursesActivity.this, "Updation Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else {
            db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid())
                    .collection("Courses").add(courses)
                    .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isComplete()) {
                                Toast.makeText(AddCoursesActivity.this, "Courses Saved in DB", Toast.LENGTH_LONG).show();
                                clearFields();
                            }
                        }
                    });

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        initViews();
    }


    void clearFields(){
        etxtcourse.setText("");
        etxtcoursefee.setText("");

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id == R.id.buttonsubmit){
            courses.Name = etxtcourse.getText().toString();
            courses.coursefees=etxtcoursefee.getText().toString();
            saveCoursesInCloud();

        }else {
            Intent intent=new Intent(AddCoursesActivity.this,CoursesActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
