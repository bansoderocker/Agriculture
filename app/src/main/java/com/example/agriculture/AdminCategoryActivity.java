package com.example.agriculture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



public class AdminCategoryActivity extends AppCompatActivity {

    ImageView cropImageView,machineImageView,seedImageView,fertilizerImageView;
    String Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        cropImageView = findViewById(R.id.crop_pro);
        machineImageView = findViewById(R.id.machine_pro);
        seedImageView = findViewById(R.id.seed_pro);
        fertilizerImageView = findViewById(R.id.fertilizer_pro);

        cropImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","crop");
                startActivity(intent);

            }
        });
        machineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","machine");

                startActivity(intent);

            }
        });
        seedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","seed");
                startActivity(intent);
            }
        });
        fertilizerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","fertilizer");
                startActivity(intent);
            }
        });



    }
}
