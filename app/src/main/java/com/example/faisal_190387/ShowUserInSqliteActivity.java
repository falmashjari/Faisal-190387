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
import com.google.firebase.database.ValueEventListener;

public class ShowUserInSqliteActivity extends AppCompatActivity {
    TextView txtFirstName, txtLastName, txtemail, txtphone;
    Button btnUpdate, btnDelete;
    DBAdapter obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_in_sqlite);

        txtemail = findViewById(R.id.txtSqlShowUserEmail);
        txtFirstName = findViewById(R.id.txtSqlShowUserFirstName);
        txtLastName = findViewById(R.id.txtSqlShowUserLastName);
        txtphone = findViewById(R.id.txtSqlShowUserPhone);
        btnDelete = findViewById(R.id.btnSqlUserDelete);
        btnUpdate = findViewById(R.id.btnSqlUserUpdate);

        obj = DBAdapter.getDBAdapter(getApplicationContext());
        if (obj.checkDatabase() == false)
            obj.createDatabase(getApplicationContext());
        obj.openDatabase();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ShowUserInSqliteActivity.this);
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        obj.DeleteUser(txtemail.getText().toString().trim());
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
                AddUserSqliteActivity.userValue = 1;
                Intent intent = new Intent(getApplicationContext(), AddUserSqliteActivity.class);
                intent.putExtra("FirstName", txtFirstName.getText().toString().trim());
                intent.putExtra("LastName", txtLastName.getText().toString().trim());
                intent.putExtra("Phone", txtphone.getText().toString().trim());
                intent.putExtra("Email", txtemail.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });
    }
}