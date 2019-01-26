package com.example.lenovo.hire_me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Registration extends AppCompatActivity {
    private EditText regName, regEmail, regPhone, regDob;
    private Spinner regBranch;
    private ImageView imageview;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    Button registerBtn;
    ProgressDialog progressDialog;
    String branch;
    Button btnchoose, btnUpload;
    private Uri filepath;
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage storage;
    StorageReference storageReference;
    private EditText resu,tra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        regName = (EditText)findViewById(R.id.username);
        registerBtn = (Button) findViewById(R.id.register);
        regEmail = (EditText) findViewById(R.id.regemail);
        regPhone = (EditText) findViewById(R.id.phone);
        regDob = (EditText) findViewById(R.id.dob);
        regBranch = (Spinner) findViewById(R.id.spinner);
        btnchoose = (Button) findViewById(R.id.btnsel);
        btnUpload = (Button) findViewById(R.id.btnPro);
        imageview = (ImageView) findViewById(R.id.imgView);
        resu=(EditText)findViewById(R.id.resume);
        tra=(EditText)findViewById(R.id.transcript);
        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regBranch.setAdapter(adapter);
        regBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                regBranch.setSelection(position);

                branch = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    private void uploadImage() {
        if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading..");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Registration.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Registration.this, "Failed to Upload" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded" + (int) progress + "%");


                }
            });
        }
    }



    private void RegisterUser() {

       // final ProgressDialog progressDialog = new ProgressDialog(this);

        String name=regName.getText().toString().trim();
        final String email=regEmail.getText().toString().trim();
        String phone=regPhone.getText().toString().trim();
        String dob=regDob.getText().toString().trim();
        String r=resu.getText().toString().trim();
        String t=tra.getText().toString().trim();
        if(name.equals("")||email.equals("")||phone.equals("")||dob.equals("")||r.equals("")||t.equals("")){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

        else{
            /*progressDialog.setTitle("registering..");
            progressDialog.show();
*/
            User user=new User(name,dob,
                    email,phone,branch,r,t);
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


            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null)
                {
                    filepath = data.getData();
                    try{
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                        imageview.setImageBitmap(bitmap);


                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else {

                    //Toast.makeText(ImageUpload.this, "Error", Toast.LENGTH_SHORT).show();
                    if(requestCode != PICK_IMAGE_REQUEST)
                    {
                        Toast.makeText(Registration.this, "Error1", Toast.LENGTH_SHORT).show();

                    }
                    if(resultCode != RESULT_OK)
                    {
                        Toast.makeText(Registration.this, "Error2", Toast.LENGTH_SHORT).show();
                    }
                    if(data == null)
                    {
                        Toast.makeText(Registration.this, "Error3", Toast.LENGTH_SHORT).show();
                    }
                    if(data.getData() == null)
                    {
                        Toast.makeText(Registration.this, "Error4", Toast.LENGTH_SHORT).show();
                    }

                }
            }


}
