package com.example.lenovo.hire_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Dashboard extends AppCompatActivity {

    private TextView profileName,profileEmail,profileDob,profileContact,profileBranch,profileResume,profileTranscript;
    private Button profileUpdate;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        profileName = (TextView)findViewById(R.id.tvName);
        profileEmail = (TextView)findViewById(R.id.tvEmail);
        profileBranch = (TextView)findViewById(R.id.tvBranch);
        profileDob = (TextView)findViewById(R.id.tvDOB);
        profileContact = (TextView)findViewById(R.id.tvContact);
        profileUpdate = (Button)findViewById(R.id.btnupdate);
        profileResume=(TextView)findViewById(R.id.res);
        profileTranscript=(TextView)findViewById(R.id.trans);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText(userProfile.getName());
                profileEmail.setText(userProfile.getEmail());
                profileBranch.setText(userProfile.getBranch());
                profileDob.setText(userProfile.getDob());
                profileContact.setText(userProfile.getPhone());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Dashboard.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
