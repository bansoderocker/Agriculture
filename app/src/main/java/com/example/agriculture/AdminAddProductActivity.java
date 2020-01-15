package com.example.agriculture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class AdminAddProductActivity extends AppCompatActivity {

    String CategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        CategoryName=getIntent().getExtras().get("category").toString();

        Toast.makeText(AdminAddProductActivity.this," "+CategoryName,Toast.LENGTH_SHORT).show();



    }
}
