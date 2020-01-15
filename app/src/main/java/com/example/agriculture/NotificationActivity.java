package com.example.agriculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;

public class NotificationActivity extends AppCompatActivity {
    Button logout_btn;
    BottomNavigationView MainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        logout_btn=findViewById(R.id.button);

        MainNav=findViewById(R.id.main_nav);
        MainNav.setSelectedItemId(R.id.nav_home);



        MainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_weather:
                        startActivity(new Intent(getApplicationContext(),WeatherActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_offer:
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

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                finish();

                startActivity(new Intent(NotificationActivity.this,LoginActivity.class));



            }
        });



    }

}


