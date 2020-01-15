package com.example.agriculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.agriculture.Prevanlent.Prevalent;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    Button logout_btn;
    BottomNavigationView MainNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    logout_btn=findViewById(R.id.button);
    MainNav=findViewById(R.id.main_nav);
MainNav.setSelectedItemId(R.id.nav_home);



    MainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){

                case R.id.nav_home:
                    return true;

                case R.id.nav_offer:
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                overridePendingTransition(0,0);
                return true;

                case R.id.nav_weather:
                    startActivity(new Intent(getApplicationContext(),WeatherActivity.class));
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

    logout_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Paper.book().destroy();
            finish();

           startActivity(new Intent(HomeActivity.this,LoginActivity.class));



        }
    });



}

}

