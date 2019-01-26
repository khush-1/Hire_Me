package com.example.lenovo.hire_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Apply extends AppCompatActivity {
    Button b1,b2,b3;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        b1=(Button)findViewById(R.id.details);
        b2=(Button)findViewById(R.id.about);
        b3=(Button)findViewById(R.id.apply);
        spinner=(Spinner)findViewById(R.id.companyId);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Apply.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.branch));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

    }
}
