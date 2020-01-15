package com.example.agriculture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText editText_name, editText_phone, editText_password;
    Button CreateAccount_btn;
    private ProgressDialog loadingBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        CreateAccount_btn = findViewById(R.id.register_btn);
        editText_name = findViewById(R.id.register_name_input);
        editText_phone = findViewById(R.id.register_phone_no_input);
        editText_password = findViewById(R.id.register_password_input);

        loadingBar = new ProgressDialog(this);

        CreateAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });


    }

    private void CreateAccount() {
        String input_name, input_phone, input_password;
        input_name = editText_name.getText().toString().trim();
        input_phone = editText_phone.getText().toString().trim();
        input_password = editText_password.getText().toString().trim();
        if (TextUtils.isEmpty(input_name)) {
            Toast.makeText(this, "Please enter your good name!!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(input_phone)) {
            Toast.makeText(this, "Please enter your phone number!! ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(input_password)) {
            Toast.makeText(this, "Please enter password!!", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, While we are checking credential!!");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

           Validated_input(input_name, input_phone, input_password);


        }


    }

    private void Validated_input(final String input_name, final String input_phone, final String input_password) {
try {

    myRef = database.getInstance().getReference();




}catch (Exception e){ Toast.makeText(this," Firebase error : "+e,Toast.LENGTH_LONG).show();}

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(input_phone).exists())){

                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("phone",input_phone);
                    UserDataMap.put("password",input_password);
                    UserDataMap.put("name",input_name);

                    myRef.child("Users").child(input_phone).updateChildren(UserDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){Toast.makeText(RegisterActivity.this,"Congratulation , Account create successfully",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                finish();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                            }
                            else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this,"Network Error!! Try After sometime..",Toast.LENGTH_SHORT).show();}

                        }
                    });


                }else { Toast.makeText(RegisterActivity.this,"This "+input_phone+" Already Exist!!",Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
 Toast.makeText(RegisterActivity.this,"Trying Again with Another Phone Number!!",Toast.LENGTH_SHORT).show();
 startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}