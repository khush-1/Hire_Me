package com.example.lenovo.hire_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class DetailInfo extends AppCompatActivity {

    RecyclerView postList;
    FirebaseRecyclerAdapter<Posts,CommentSection.PostViewHolder> firebaseRecyclerAdapter;
    TextView tvposition,tvcpi,tvctc,tvallowed,tvpreference,tvdeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        tvposition=(TextView) findViewById(R.id.tvposition);
        tvcpi = (TextView) findViewById(R.id.tvcpi);
        tvctc = (TextView)findViewById(R.id.tvctc);
        tvallowed = (TextView)findViewById(R.id.tvallowed);
        tvpreference = (TextView)findViewById(R.id.tvpreference);
        tvdeadline = (TextView)findViewById(R.id.tvdeadline);


    }
}
