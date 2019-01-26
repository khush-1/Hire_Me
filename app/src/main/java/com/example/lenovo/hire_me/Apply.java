package com.example.lenovo.hire_me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Apply extends AppCompatActivity {
    Button b1,b2,b3;
    Spinner spinner;
    String branch;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        b1=(Button)findViewById(R.id.details);
        b2=(Button)findViewById(R.id.about);
        b3=(Button)findViewById(R.id.apply);
        spinner=(Spinner)findViewById(R.id.companyId);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Apply.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.companyNames));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view, int position, long id){
                spinner.setSelection(position);

                branch = parent.getItemAtPosition(position).toString();
                databaseReference = FirebaseDatabase.getInstance().getReference("companyNames").child("branch");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                {

                }

            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Apply.this,DetailInfo.class);
                intent.putExtra("positionsOfCompany",databaseReference);
                startActivity(intent);
            }
        });


    }



}
