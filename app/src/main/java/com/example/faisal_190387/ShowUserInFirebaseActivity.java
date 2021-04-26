package com.example.faisal_190387;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowUserInFirebaseActivity extends AppCompatActivity {
    TextView txtFirstName, txtLastName, txtemail, txtphone;
    Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_in_firebase);
        txtemail = findViewById(R.id.txtShowUserEmail);
        txtFirstName = findViewById(R.id.txtShowUserFirstName);
        txtLastName = findViewById(R.id.txtShowUserLastName);
        txtphone = findViewById(R.id.txtShowUserPhone);
        btnDelete = findViewById(R.id.btnUserDelete);
        btnUpdate = findViewById(R.id.btnUserUpdate);

        Intent intent = getIntent();
        txtphone.setText(intent.getStringExtra("Phone"));
        txtLastName.setText(intent.getStringExtra("LastName"));
        txtFirstName.setText(intent.getStringExtra("FirstName"));
        txtemail.setText(intent.getStringExtra("Email"));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("users").orderByChild("Email").equalTo(txtemail.getText().toString());

                AlertDialog.Builder alert = new AlertDialog.Builder(ShowUserInFirebaseActivity.this);
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }
                                finish();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("TAG", "onCancelled", databaseError.toException());
                            }
                        });
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserInFirebaseActivity.userValue=1;
                Intent intent1=new Intent(getApplicationContext(),AddUserInFirebaseActivity.class);
                intent1.putExtra("FirstName",txtFirstName.getText().toString());
                intent1.putExtra("LastName",txtLastName.getText().toString());
                intent1.putExtra("Email",txtemail.getText().toString());
                intent1.putExtra("Phone",txtphone.getText().toString());
                startActivity(intent1);
            }
        });
    }
}