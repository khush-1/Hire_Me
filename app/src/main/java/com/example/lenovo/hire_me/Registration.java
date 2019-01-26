package com.example.lenovo.hire_me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private EditText regName,regEmail,regPhone,regDob;
    private Spinner regBranch;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    Button registerBtn;
    ProgressDialog progressDialog;
    String branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth=FirebaseAuth.getInstance();

        regName=(EditText)findViewById(R.id.name);
        registerBtn=(Button)findViewById(R.id.register);
        regEmail=(EditText)findViewById(R.id.email);
        regPhone=(EditText)findViewById(R.id.phone);
        regDob=(EditText)findViewById(R.id.dob);
        regBranch=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.branch,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regBranch.setAdapter(adapter);
        regBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                regBranch.setSelection(position);

                branch=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }

    private void RegisterUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("registering..");
        progressDialog.show();
        String name=regName.getText().toString().trim();
        final String email=regEmail.getText().toString().trim();
        String phone=regPhone.getText().toString().trim();
        String dob=regDob.getText().toString().trim();

        UserProfile user=new UserProfile(name,
                email,branch,dob,phone);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent i=new Intent(Registration.this,MainActivity.class);

                startActivity(i);
                finish();

                progressDialog.dismiss();
                Toast.makeText(Registration.this, "done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registration.this, "sorry", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
