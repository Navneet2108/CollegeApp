package com.example.collegeapp.ui;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.model.Deadline;
import com.example.e_collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeadlineActivity extends AppCompatActivity implements View.OnClickListener {
     @BindView(R.id.buttonview)
     Button btnview;

     @BindView(R.id.NewDeadline)
     TextView txtNewdeadline;
    @BindView(R.id.transferDeadline)
    TextView txtTransferdeadline;
    Deadline deadline;

     DatePickerDialog datePickerDialog;
     FirebaseFirestore db;
     FirebaseAuth auth;
     FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadline);
        getSupportActionBar().setTitle("E-College");
        ButterKnife.bind(this);
        txtNewdeadline.setOnClickListener(this);
        txtTransferdeadline.setOnClickListener(this);
        deadline=new Deadline();
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        user=auth.getCurrentUser();
        btnview.setOnClickListener(this);
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = year+"/"+(month+1)+"/"+dayOfMonth;
            int id=view.getId();
            if(id==R.id.NewDeadline) {
                txtNewdeadline.setText(date);
            }else {
                txtTransferdeadline.setText(date);
            }
        }
    };
   void saveDate(){
    user= auth.getCurrentUser();
        db.collection("Colleges").document(user.getUid()).collection("collegeInfo").document(user.getUid()).collection("deadline").add(deadline)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DeadlineActivity.this, " Details saved Successful", Toast.LENGTH_LONG).show();
                        //progressDialog.dismiss();
                        //Intent intent = new Intent(RegisterActivity.this, SuccessActivity.class);
                        Intent intent = new Intent(DeadlineActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
}

    void showdatepickerDialog(){
        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(this, onDateSetListener, yy, mm, dd);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.NewDeadline:
                showdatepickerDialog();
                break;
            case R.id.transferDeadline:
                showdatepickerDialog();
                break;
            case R.id.deadlineview:
                deadline.newDeadline = txtNewdeadline.getText().toString();
                deadline.transferdeadline = txtTransferdeadline.getText().toString();
                saveDate();
                break;

            }
        }

    }
  /* void ViewShowDeadline(){
       NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           NotificationChannel notificationChannel = new NotificationChannel("myId","myName",NotificationManager.IMPORTANCE_HIGH);
           notificationManager.createNotificationChannel(notificationChannel);
       }
       NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"myId");
       builder.setSmallIcon(R.drawable.ic_email);
       builder.setContentTitle("This is Title");
       builder.setContentText("This is Text");
       Intent intent = new Intent(DeadlineActivity.this,InfoActivity .class);
       PendingIntent pendingIntent = PendingIntent.getActivity(this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
       builder.setContentIntent(pendingIntent);
       builder.setStyle(new NotificationCompat.BigTextStyle());
       builder.addAction(android.R.drawable.ic_menu_add, "Add",pendingIntent);
       builder.addAction(android.R.drawable.ic_menu_delete, "Delete",pendingIntent);
       Notification notification = builder.build();
       notificationManager.notify(101, notification);

   }*/

