package com.example.faisal_190387;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddUserSqliteActivity extends AppCompatActivity {
    TextView txtHeading;
    EditText txtFirstName, txtLastName, txtEmail, txtPhone;
    Button btnSubmit;
    DBAdapter obj;
    public static int userValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_sqlite);
        txtHeading = findViewById(R.id.txtSqlHeading);
        txtEmail = findViewById(R.id.txtSqlEmail);
        txtFirstName = findViewById(R.id.txtSqlFirstName);
        txtLastName = findViewById(R.id.txtSqlLastName);
        txtPhone = findViewById(R.id.txtSqlPhoneNumber);
        btnSubmit = findViewById(R.id.btnSqlAddUserSubmit);

        if (userValue == 1) {
            Intent intent = getIntent();
            txtEmail.setText(intent.getStringExtra("Email"));
            txtFirstName.setText(intent.getStringExtra("FirstName"));
            txtLastName.setText(intent.getStringExtra("LastName"));
            txtPhone.setText(intent.getStringExtra("Phone"));
            txtEmail.setEnabled(false);
        } else {
            txtEmail.setEnabled(true);
        }

        obj = DBAdapter.getDBAdapter(getApplicationContext());
        if (obj.checkDatabase() == false)
            obj.createDatabase(getApplicationContext());
        obj.openDatabase();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEmail.getText().toString().trim().length() > 0 && txtLastName.getText().toString().trim().length() > 0 && txtFirstName.getText().toString().trim().length() > 0 && txtPhone.getText().toString().trim().length() > 0) {
                    if (userValue == 1) {
                        obj.UpdateUser(txtFirstName.getText().toString().trim(), txtLastName.getText().toString().trim(), txtPhone.getText().toString().trim(), txtEmail.getText().toString().trim());
                        Toast.makeText(getApplicationContext(), "User Detail's Update Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        obj.insertDB(txtFirstName.getText().toString().trim(), txtLastName.getText().toString().trim(), txtPhone.getText().toString().trim(), txtEmail.getText().toString().trim());
                        Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_LONG).show();
                    }
                    setTextData();
                }
            }
        });
    }

    public void setTextData() {
        txtPhone.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        txtEmail.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SqliteMainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}