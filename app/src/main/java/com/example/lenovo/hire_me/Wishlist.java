package com.example.lenovo.hire_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Wishlist extends AppCompatActivity {

    EditText dream_company;
    Button add_company;

    DatabaseReference database_wish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        dream_company=findViewById(R.id.dream);
        add_company=findViewById(R.id.add_company);
        database_wish=FirebaseDatabase.getInstance().getReference("Dream_comp");

        add_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comp=dream_company.getText().toString();
                 if(TextUtils.isEmpty(comp))
                     Toast.makeText(Wishlist.this, "Enter Company name", Toast.LENGTH_SHORT).show();
                 else
                 {
                      String id=database_wish.push().getKey();
                      Wishlist_Company Dcomp=new Wishlist_Company(comp);
                      database_wish.child(id).setValue(Dcomp);
                     Toast.makeText(Wishlist.this, "Company added to Wishlist", Toast.LENGTH_SHORT).show();
                     dream_company.setText("");
                 }
            }
        });

    }
}
