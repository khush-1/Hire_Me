package com.example.lenovo.hire_me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailInfo extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView postList;
    FirebaseRecyclerAdapter<Posts,CommentSection.PostViewHolder> firebaseRecyclerAdapter;
    TextView tvposition1,tvcpi1,tvctc1,tvallowed1,tvpreference1,tvdeadline1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        tvposition1=(TextView) findViewById(R.id.tvposition);
        tvcpi1 = (TextView) findViewById(R.id.tvcpi);
        tvctc1 = (TextView)findViewById(R.id.tvctc);
        tvallowed1 = (TextView)findViewById(R.id.tvallowed);
        tvpreference1 = (TextView)findViewById(R.id.tvpreference);
        tvdeadline1 = (TextView)findViewById(R.id.tvdeadline);


        Intent intent = getIntent();
        String companyName = intent.getStringExtra("nameOfCompany");

        databaseReference = FirebaseDatabase.getInstance().getReference("companyNames").child(companyName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String position = dataSnapshot.child("position").getValue().toString();
                    String ctc = dataSnapshot.child("ctc").getValue().toString();
                    String cpi = dataSnapshot.child("cpi").getValue().toString();
                    String allowed = dataSnapshot.child("branchesallowed").getValue().toString();
                    String preference = dataSnapshot.child("preference").getValue().toString();
                    String deadline = dataSnapshot.child("deadline").getValue().toString();

                    tvposition1.setText(position);
                    tvctc1.setText(ctc);
                    tvcpi1.setText(cpi);
                    tvallowed1.setText(allowed);
                    tvpreference1.setText(preference);
                    tvdeadline1.setText(deadline);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


         tvposition.setText("");
        tvcpi.setText("");
        tvctc.setText("");
        tvallowed.setText("");
        tvpreference.setText("");
        tvdeadline.setText("");
    }
}
