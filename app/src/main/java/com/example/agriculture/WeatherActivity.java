package com.example.agriculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.androdocs.httprequest.HttpRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.paperdb.Paper;

public class WeatherActivity extends AppCompatActivity {
    String CITY = "New Panvel";
    String API = "8118ed6ee68db2debfaaa5a44c832918";
    Button logout_btn;
    BottomNavigationView MainNav;

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);



                addressTxt = findViewById(R.id.address);
                updated_atTxt = findViewById(R.id.updated_at);
                statusTxt = findViewById(R.id.status);
                tempTxt = findViewById(R.id.temp);
                temp_minTxt = findViewById(R.id.temp_min);
                temp_maxTxt = findViewById(R.id.temp_max);
                sunriseTxt = findViewById(R.id.sunrise);
                sunsetTxt = findViewById(R.id.sunset);
                windTxt = findViewById(R.id.wind);
                pressureTxt = findViewById(R.id.pressure);
                humidityTxt = findViewById(R.id.humidity);

                new weatherTask().execute();
        MainNav=findViewById(R.id.main_nav);
        MainNav.setSelectedItemId(R.id.nav_home);



        MainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_weather:
                        return true;

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_offer:
                        startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                    default:
                        return false;
                }

            }


        });






    }



            class weatherTask extends AsyncTask<String, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    /* Showing the ProgressBar, Making the main design GONE */
                    findViewById(R.id.loader).setVisibility(View.VISIBLE);
                    findViewById(R.id.mainContainer).setVisibility(View.GONE);
                    findViewById(R.id.errorText).setVisibility(View.GONE);
                }

                protected String doInBackground(String... args) {
                    String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
                    return response;
                }

                @Override
                protected void onPostExecute(String result) {


                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        JSONObject main = jsonObj.getJSONObject("main");
                        JSONObject sys = jsonObj.getJSONObject("sys");
                        JSONObject wind = jsonObj.getJSONObject("wind");
                        JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                        Long updatedAt = jsonObj.getLong("dt");
                        String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                        String temp = main.getString("temp") + "°C";
                        String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                        String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                        String pressure = main.getString("pressure");
                        String humidity = main.getString("humidity");

                        Long sunrise = sys.getLong("sunrise");
                        Long sunset = sys.getLong("sunset");
                        String windSpeed = wind.getString("speed");
                        String weatherDescription = weather.getString("description");

                        String address = jsonObj.getString("name") + ", " + sys.getString("country");


                        /* Populating extracted data into our views */
                        addressTxt.setText(address);
                        updated_atTxt.setText(updatedAtText);
                        statusTxt.setText(weatherDescription.toUpperCase());
                        tempTxt.setText(temp);
                        temp_minTxt.setText(tempMin);
                        temp_maxTxt.setText(tempMax);
                        sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                        sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                        windTxt.setText(windSpeed);
                        pressureTxt.setText(pressure);
                        humidityTxt.setText(humidity);

                        /* Views populated, Hiding the loader, Showing the main design */
                        findViewById(R.id.loader).setVisibility(View.GONE);
                        findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


                    } catch (JSONException e) {
                        findViewById(R.id.loader).setVisibility(View.GONE);
                        findViewById(R.id.errorText).setVisibility(View.VISIBLE);
                    }

                }
            }
        }
