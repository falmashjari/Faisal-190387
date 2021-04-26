package com.example.faisal_190387;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnFirebase, btnweather, btnsqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFirebase = findViewById(R.id.btnfirebase);
        btnweather = findViewById(R.id.btnweather);
        btnsqlite = findViewById(R.id.btnSqlite);

        btnFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirebaseMainActivity.class);
                startActivity(intent);
            }
        });

        btnsqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SqliteMainActivity.class);
                startActivity(intent);
            }
        });

        btnweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),WeatherMainActivity.class);
                startActivity(intent);
            }
        });
    }
}