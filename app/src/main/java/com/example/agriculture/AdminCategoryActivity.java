package com.example.agriculture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    ImageView imageView_crop, imageView_machine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        imageView_crop = findViewById(R.id.crop_pro);
        imageView_machine = findViewById(R.id.machine_pro);

        imageView_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","crop");
                startActivity(intent);
            }
        });

        imageView_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","machine");
                startActivity(intent);
            }
        });



    }
}
