package com.example.faisal_190387;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SqliteMainActivity extends AppCompatActivity {
    ListView lstSqlite;
    Button btnAddNewUser;
    ArrayList<String> firstName;
    ArrayList<String> lastName;
    ArrayList<String> phone;
    ArrayList<String> email;
    ArrayList<userData> userDataArr;
    ArrayList<Integer> img;
    DBAdapter obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_main);
        lstSqlite = findViewById(R.id.lstSqlite);
        btnAddNewUser = findViewById(R.id.btnAddNewUserInSqlite);
        firstName = new ArrayList<>();
        lastName = new ArrayList<>();
        phone = new ArrayList<>();
        email = new ArrayList<>();
        userDataArr=new ArrayList<>();
        img=new ArrayList<>();

        obj = DBAdapter.getDBAdapter(getApplicationContext());
        if (obj.checkDatabase() == false)
            obj.createDatabase(getApplicationContext());
        obj.openDatabase();
        userDataArr=obj.getUserData();

        for(int i=0;i<userDataArr.size();i++){
            firstName.add(userDataArr.get(i).getFirstName());
            lastName.add(userDataArr.get(i).getLastName());
            email.add(userDataArr.get(i).getEmail());
            phone.add(userDataArr.get(i).getPhone());
            img.add(R.drawable.ic_user);
        }

        lstSqlite.setAdapter(new CostumAdapter(getApplicationContext(),firstName,email,lastName,phone,img));

        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddUserSqliteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}