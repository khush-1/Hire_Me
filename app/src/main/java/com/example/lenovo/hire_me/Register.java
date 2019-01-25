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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    Button registerbtn;
    EditText regEmail,regPassword,regpass2;
    TextView t1;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerbtn=(Button)findViewById(R.id.regbtn);
        regEmail=(EditText)findViewById(R.id.regemail);
        regPassword=(EditText)findViewById(R.id.regpassword);
        regpass2=(EditText)findViewById(R.id.regpassword2);
        t1=(TextView)findViewById(R.id.reglogin);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Register.this,Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void createAccount() {
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();
        String password2 = regpass2.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password) ){
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!password.equals(password2)){
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
            return;
        }
        else{

            progressDialog.setMessage("Registering User...");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();

                        progressDialog.dismiss();
                        progressDialog.setTitle("sending verification email..");
                        progressDialog.show();
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this, "verification email sent ", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(Register.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
