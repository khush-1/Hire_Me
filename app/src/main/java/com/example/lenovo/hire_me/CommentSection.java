package com.example.lenovo.hire_me;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentSection extends AppCompatActivity {
    Button addbtn;
    EditText comment;
    FirebaseDatabase database;
    String saveCurrentDate,saveCurrentTime,randomName,currentUserId;
    private DatabaseReference databaseReference,postRef;
    FirebaseAuth mAuth;
    ProgressDialog progressBar;
    RecyclerView postList;

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);
        addbtn=(Button)findViewById(R.id.addcommentbtn);
        comment=(EditText)findViewById(R.id.commentbox);
        database=FirebaseDatabase.getInstance();
        postRef=database.getReference().child("Posts");
        mAuth = FirebaseAuth.getInstance();
        postList=(RecyclerView)findViewById(R.id.recyclerview);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);

        progressBar=new ProgressDialog(this);
        currentUserId=mAuth.getCurrentUser().getUid();
        databaseReference=database.getReference().child("Users");
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcoomenttopost();
            }
        });
        
        displayallusersposts();
    }

    private void displayallusersposts() {
    }

    private void addcoomenttopost() {
    }
}
