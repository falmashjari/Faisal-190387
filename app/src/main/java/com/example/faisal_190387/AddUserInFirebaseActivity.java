package com.example.faisal_190387;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddUserInFirebaseActivity extends AppCompatActivity {
    TextView txtHeading;
    EditText txtFirstName, txtLastName, txtPhoneNumber, txtEmail;
    Button btnSubmit;
    public static int userValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_in_firebase);
        txtHeading = findViewById(R.id.txtHeading);
        txtEmail = findViewById(R.id.txtEmail);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnSubmit = findViewById(R.id.btnFirebaseAddUserSubmit);

        if (userValue == 1) {
            Intent intent = getIntent();
            txtEmail.setText(intent.getStringExtra("Email"));
            txtFirstName.setText(intent.getStringExtra("FirstName"));
            txtLastName.setText(intent.getStringExtra("LastName"));
            txtPhoneNumber.setText(intent.getStringExtra("Phone"));
            txtEmail.setEnabled(false);
            txtHeading.setText("Update User Info");
        }else{
            txtEmail.setEnabled(true);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEmail.getText().toString().trim().length() > 0 && txtFirstName.getText().toString().trim().length() > 0 && txtLastName.getText().toString().trim().length() > 0 && txtPhoneNumber.getText().toString().trim().length() > 0) {
                    if (userValue == 1) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("users").orderByChild("Email").equalTo(txtEmail.getText().toString());
                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("TAG", "onCancelled", databaseError.toException());
                            }
                        });
                        insertData();
                        Toast.makeText(getApplicationContext(), "User data update successfully", Toast.LENGTH_LONG).show();
                    } else {
                        insertData();
                        Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all detail's", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setTextdata() {
        txtPhoneNumber.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        txtEmail.setText("");
    }
    public void insertData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("FirstName", txtFirstName.getText().toString().trim());
        hashMap.put("LastName", txtLastName.getText().toString().trim());
        hashMap.put("Phone", txtPhoneNumber.getText().toString().trim());
        hashMap.put("Email", txtEmail.getText().toString().trim());
        myRef.push().setValue(hashMap);
        setTextdata();
    }
}