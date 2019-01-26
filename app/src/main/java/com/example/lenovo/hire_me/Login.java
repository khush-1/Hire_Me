package com.example.lenovo.hire_me;

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

import com.example.lenovo.hire_me.Admin.AdminLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button loginbtn;
    EditText email,password;
    TextView t1;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    TextView forgotPassword;
    TextView admin_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t1=(TextView)findViewById(R.id.textView4);
        loginbtn =(Button)findViewById(R.id.regbtn) ;
        email=(EditText)findViewById(R.id.regemail) ;
        password=(EditText)findViewById(R.id.regpassword);
        mAuth=FirebaseAuth.getInstance();
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        admin_login=(TextView)findViewById(R.id.admin_login_id);



        progressDialog=new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,Register.class);
                startActivity(i);
                finish();
            }
        });
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, AdminLogin.class);
                startActivity(i);
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Password error", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Login.this,PasswordActivity.class);
                startActivity(i);

            }
        });


    }

    private void signIn() {
        String eMail=email.getText().toString();
        String passWord=password.getText().toString();
        if(TextUtils.isEmpty(eMail)){
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passWord)){
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(eMail,passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser().isEmailVerified()  ){
                                Intent i=new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Toast.makeText(Login.this,"please verify your email ", Toast.LENGTH_SHORT).show();

                            }

                        }
                        else{
                            Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}
