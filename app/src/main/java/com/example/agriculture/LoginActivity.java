package com.example.agriculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculture.Model.Users;
import com.example.agriculture.Prevanlent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn,register_btn;
   private EditText editText_phone, editText_password;
    private ProgressDialog loadingBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");
    CheckBox chkBox_remember;
    TextView Admin_txtView,NotAdmin_txtAdmin;
    private String ParentDBname ;
    Users UserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



       editText_phone=findViewById(R.id.login_phone_input);
       editText_password=findViewById(R.id.login_password_input);
       login_btn =findViewById(R.id.login_button);
       chkBox_remember=findViewById(R.id.checkBox_remember);
        Paper.init(this);
        Admin_txtView=findViewById(R.id.textView_admin);
        NotAdmin_txtAdmin=findViewById(R.id.textView_notAdmin);
        ParentDBname="Users";


        loadingBar = new ProgressDialog(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        Admin_txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setText("Admin Login");
                Admin_txtView.setVisibility(View.INVISIBLE);
                NotAdmin_txtAdmin.setVisibility(View.VISIBLE);
                ParentDBname="Admins";
            }
        });
        NotAdmin_txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setText("Login");
                Admin_txtView.setVisibility(View.VISIBLE);
                NotAdmin_txtAdmin.setVisibility(View.INVISIBLE);
                ParentDBname="Users";
            }
        });




    }

    private void LoginUser() {
       String input_phone = editText_phone.getText().toString().trim();
       String input_password = editText_password.getText().toString().trim();

        if (TextUtils.isEmpty(input_phone)) {
            Toast.makeText(this, "Please write your phone number!! ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(input_password)) {
            Toast.makeText(this, "Please your enter password!!", Toast.LENGTH_SHORT).show();
        }else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, While we are checking credential!!");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessAccount(input_phone,input_password);

        }


    }

    private void AllowAccessAccount(final String input_phone, final String input_password) {

        if(chkBox_remember.isChecked()){

            Paper.book().write(Prevalent.UserPhoneKey,input_phone);
            Paper.book().write(Prevalent.UserPasswordKey,input_password);
        }

        myRef = database.getInstance().getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ParentDBname).child(input_phone).exists()){


                    UserData = dataSnapshot.child(ParentDBname).child(input_phone).getValue(Users.class);

                    if(UserData.getPhone().equals(input_phone)){
                        if(UserData.getPassword().equals(input_password)){

                            if(input_phone.equals("8286")) {

                                Toast.makeText(LoginActivity.this, "Welcome Admin, log in successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                startActivity(new Intent(LoginActivity.this, AdminCategoryActivity.class));

                                finish();
                            } else if(ParentDBname.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "Welcome "+UserData.getName() +", Log in successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                finish();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();



                            }


                        }else {
                            Toast.makeText(LoginActivity.this," Password is wrong... ",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            finish();
                            startActivity(new Intent(LoginActivity.this,LoginActivity.class));


                        }
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"Account with this "+input_phone+" number do not exist!",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this," ",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
