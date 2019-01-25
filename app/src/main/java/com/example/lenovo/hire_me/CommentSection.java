package com.example.lenovo.hire_me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CommentSection extends AppCompatActivity {
    Button addbtn;
    EditText comment;
    FirebaseDatabase database;
    String saveCurrentDate,saveCurrentTime,randomName,currentUserId;
    private DatabaseReference databaseReference,postRef;
    FirebaseAuth mAuth;
    ProgressDialog progressBar;
    RecyclerView postList;
    FirebaseRecyclerAdapter<Posts,PostViewHolder> firebaseRecyclerAdapter;

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);
        addbtn=(Button)findViewById(R.id.addcommentbtn);
        comment=(EditText)findViewById(R.id.commentbox);
        database=FirebaseDatabase.getInstance();
        postRef=database.getReference().child("Posts");
        mAuth=FirebaseAuth.getInstance();
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
        Query query=postRef.limitToLast(100);
        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(query,Posts.class).build();

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Posts, PostViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Posts model) {

                        holder.setName(model.getName());
                        holder.setDate(model.getDate());
                        holder.setTime(model.getTime());
                        holder.setPost(model.getPost());

                    }

                    @NonNull
                    @Override
                    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.activity_post_layout,parent,false
                        );
                        return new PostViewHolder(view);

                    }
                };
        postList.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        if(firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        if(firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();
        super.onPostResume();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public PostViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String name) {
            TextView user=(TextView)mView.findViewById(R.id.user_name);
            user.setText(name);
        }
        public void setDate(String date){
            TextView dAte=(TextView)mView.findViewById(R.id.date);
            dAte.setText(date);
        }
        public void setPost(String post){
            TextView com=(TextView)mView.findViewById(R.id.newpost);
            com.setText(post);
        }
        public void setTime(String time){
            TextView mTime=(TextView)mView.findViewById(R.id.times);
            mTime.setText(time);
        }


    }

    private void addcoomenttopost() {
        final String com=comment.getText().toString();
        if(com.equals("")){
            Toast.makeText(this, "please add comment", Toast.LENGTH_SHORT).show();
        }
        else{
            progressBar.setTitle("loading");
            progressBar.setMessage("please wait,while we are updating your post");
            progressBar.show();
            progressBar.setCanceledOnTouchOutside(false);

            Calendar calfordate= Calendar.getInstance();
            final SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            saveCurrentDate = currentDate.format(calfordate.getTime());

            Calendar calfortime= Calendar.getInstance();
            final SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            saveCurrentTime = currentTime.format(calfordate.getTime());

            randomName=saveCurrentDate + saveCurrentTime;
            databaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){

                        String fullName=dataSnapshot.child("name").getValue().toString();
                        HashMap postsmap=new HashMap();
                        postsmap.put("uid",currentUserId);
                        postsmap.put("date",saveCurrentDate);
                        postsmap.put("time",saveCurrentTime);
                        postsmap.put("post",com);
                        postsmap.put("name",fullName);
                        postRef.child(currentUserId + randomName).updateChildren(postsmap)
                                .addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            sendUsertoMainActivity();
                                            Toast.makeText(CommentSection.this, "added to data", Toast.LENGTH_SHORT).show();
                                            progressBar.dismiss();
                                        }
                                        else
                                        {
                                            Toast.makeText(CommentSection.this, "sorry", Toast.LENGTH_SHORT).show();
                                            progressBar.dismiss();
                                        }
                                    }
                                });


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void sendUsertoMainActivity() {
        Intent i=new Intent(CommentSection.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
