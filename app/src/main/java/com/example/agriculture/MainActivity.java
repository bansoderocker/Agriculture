package com.example.agriculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.agriculture.Model.Users;
import com.example.agriculture.Prevanlent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button main_reg_btn,main_login_btn;
    private ProgressDialog loadingBar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        main_reg_btn=findViewById(R.id.main_signup_btn);
        main_login_btn=findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        main_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        main_reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));

            }
        });

        String UserPhoneKey, UserPasswordKey;
        UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey!="" && UserPasswordKey!= ""){

         if(!TextUtils.isEmpty(UserPasswordKey) && !TextUtils.isEmpty(UserPhoneKey)){

             AllowAccess(UserPasswordKey,UserPhoneKey);

             loadingBar.setTitle("Already Login Account");
             loadingBar.setMessage("Please wait, While we are checking credential!!");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();


         }
        }
    }

    private void AllowAccess(final String input_password, final String input_phone) {


        myRef = database.getInstance().getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(input_phone).exists()){
                    Users UserData = dataSnapshot.child("Users").child(input_phone).getValue(Users.class);
                    if(UserData.getPhone().equals(input_phone)){
                        if(UserData.getPassword().equals(input_password)){

                            Toast.makeText(MainActivity.this," log in successfully... ",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            finish();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }else {
                            Toast.makeText(MainActivity.this," Password is wrong... ",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                         //   finish();
                         //   startActivity(new Intent(MainActivity.this,LoginActivity.class));


                        }
                    }

                }else {
                    Toast.makeText(MainActivity.this,"Account with this "+input_phone+" number do not exist!",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this," ",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
