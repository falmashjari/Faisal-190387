package com.example.faisal_190387;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseMainActivity extends AppCompatActivity {
    ListView lstFirebaseUser;
    Button btnAddNewUser;
    ArrayList<String> firstName;
    ArrayList<String> lastName;
    ArrayList<String> email;
    ArrayList<String> phones;
    ArrayList<userData> userDataArr;
    ArrayList<Integer> img;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_main);
        lstFirebaseUser = findViewById(R.id.lstFirebase);
        btnAddNewUser = findViewById(R.id.btnAddRecordInFirebase);

        pDialog = new ProgressDialog(FirebaseMainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userData userData = null;
                firstName = new ArrayList<>();
                lastName = new ArrayList<>();
                email = new ArrayList<>();
                phones = new ArrayList<>();
                userDataArr=new ArrayList<>();
                img=new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userData = snapshot.getValue(userData.class);
                    userDataArr.add(userData);
                    Log.e("Store Dec",snapshot.toString());
                }

                for (int i = 0; i < userDataArr.size(); i++) {
                    userData st = userDataArr.get(i);
                    firstName.add(st.getFirstName());
                    lastName.add(st.getLastName());
                    email.add(st.getEmail());
                    phones.add(st.getPhone());
                    img.add(R.drawable.ic_user);
                }
                lstFirebaseUser.setAdapter(new CostumAdapter(getApplicationContext(),firstName,email,lastName,phones,img));
                pDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });



        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUserInFirebaseActivity.class);
                startActivity(intent);
            }
        });
    }
}