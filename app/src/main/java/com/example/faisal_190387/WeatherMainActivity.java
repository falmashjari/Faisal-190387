package com.example.faisal_190387;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherMainActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    String lat, lon;
    TextView txtTemp, txtTempMax, txtTempMin, txtDesc;
    ImageView img;
    Spinner spinnerCity;
    Button btnSearch;
    ArrayList<String> cityNameArr;
    String cityName = "";
    String temp,maxtemp,mintemp,dec,path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);

        txtDesc = findViewById(R.id.txtDec);
        txtTemp = findViewById(R.id.txtTemp);
        txtTempMax = findViewById(R.id.txtTempMax);
        txtTempMin = findViewById(R.id.txtTempMin);
        img = findViewById(R.id.imgWeather);
        btnSearch = findViewById(R.id.btnSearchWeather);
        spinnerCity = findViewById(R.id.spinnerCityName);

        cityNameArr = new ArrayList<>();
        cityNameArr.add("--Select City--");
        cityNameArr.add("Riyadh");
        cityNameArr.add("Jeddah");
        cityNameArr.add("Dammam");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, cityNameArr);
        spinnerCity.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName = spinnerCity.getSelectedItem().toString();
                pDialog = new ProgressDialog(WeatherMainActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setIndeterminate(true);
                pDialog.setCancelable(false);
                new Details().execute();
                new Details().execute();
            }
        });
    }

    public class Details extends AsyncTask<Object, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Object... objects) {
            AndroidNetworking.get("https://api.openweathermap.org/data/2.5/weather?q="+spinnerCity.getSelectedItem().toString()+"&APPID=7e943c97096a9784391a981c4d878b22&mode=json&units=metric")
                    .setPriority(Priority.HIGH)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        JSONObject co = response.getJSONObject("coord");
                        lat = co.getString("lat");
                        lon = co.getString("lon");
                        JSONArray obj = response.getJSONArray("weather");
                        JSONObject ob = obj.getJSONObject(0);
                        Log.d("Result", ob.getString("icon"));
                        dec=ob.getString("description");
                        path= "http://openweathermap.org/img/w/" + ob.getString("icon") + ".png";
                        JSONObject we = response.getJSONObject("main");
                        temp=we.getString("temp");
                        mintemp=we.getString("temp_min");
                        maxtemp=we.getString("temp_max");
                   //     new Details1().execute();

                    } catch (Exception e) {
                        Log.e("Error", e + "");
                    }
                    txtDesc.setText(dec);
                    txtTemp.setText(temp);
                    txtTempMax.setText(maxtemp);
                    txtTempMin.setText(mintemp);
                    Picasso.get().load(path).into(img);
                }

                @Override
                public void onError(ANError anError) {
                    pDialog.dismiss();
                    Log.e("Error", anError + "");
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            txtDesc.setText(dec);
            txtTemp.setText(temp);
            txtTempMax.setText(maxtemp);
            txtTempMin.setText(mintemp);
            Picasso.get().load(path).into(img);
            super.onPostExecute(strings);
        }
    }

    public class Details1 extends AsyncTask<Object, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Object... objects) {
            AndroidNetworking.get("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=" + lat + "&lon=" + lon + "")
                    .setPriority(Priority.HIGH)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.d("Values : ", response.getString("value"));
                    } catch (Exception e) {
                        Log.e("Error", e + "");
                    }
                }

                @Override
                public void onError(ANError anError) {
                    pDialog.dismiss();
                    Log.e("Error", anError + "");
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
        }
    }
}