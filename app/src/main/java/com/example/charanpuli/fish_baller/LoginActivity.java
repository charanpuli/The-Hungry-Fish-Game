package com.example.charanpuli.fish_baller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charanpuli.fish_baller.Prevalent.prevalent;
import com.example.charanpuli.fish_baller.model.Share;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPassword;
    private Button loginBtn;
    private TextView newUserLink;
    private com.rey.material.widget.CheckBox rememberMe;
    private ProgressDialog LoadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = (EditText) findViewById(R.id.loginemailtext);
        loginPassword = (EditText) findViewById(R.id.loginpasswordtext);
        loginBtn = (Button) findViewById(R.id.loginbtn);

        LoadingBar = new ProgressDialog(this);
        rememberMe = (com.rey.material.widget.CheckBox) findViewById(R.id.remember_me_chk);
        Paper.init(this);
        mAuth=FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAccess();
            }
        });
        if(Paper.book().read(prevalent.userName)!=null && Paper.book().read(prevalent.Password)!=null) {
            loginEmail.setText(Paper.book().read(prevalent.userName).toString());
            loginPassword.setText(Paper.book().read(prevalent.Password).toString());
        }

    }


    private void checkAccess() {
        String email=loginEmail.getText().toString();
        String password=loginPassword.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "enter your email no.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "enter your password....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.setTitle("Login Account");
            LoadingBar.setMessage("Please wait,while we are checking for credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();
            AllowAccesstoAccount(email,password);
        }

    }

    private void AllowAccesstoAccount(final String email, final String password) {
       mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   if(rememberMe.isChecked()){
                       Paper.book().write(prevalent.userName,loginEmail.getText().toString());
                       Paper.book().write(prevalent.Password,loginPassword.getText().toString());
                   }

                    Intent intent=new Intent(LoginActivity.this,LoginLeaderActivity.class);
                    startActivity(intent);
                   Toast.makeText(LoginActivity.this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                   LoadingBar.dismiss();
               }
               else{
                   Toast.makeText(LoginActivity.this, "Login Unsuccessful,Please try again...or Register...", Toast.LENGTH_LONG).show();
                   LoadingBar.dismiss();
               }
           }
       });
    }

}

