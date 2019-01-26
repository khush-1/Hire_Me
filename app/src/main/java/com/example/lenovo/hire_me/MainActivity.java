package com.example.lenovo.hire_me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference firebaseDatabase;
    ProgressDialog progressDialog;

    TextView about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
         about=findViewById(R.id.about);

         about.setText("Motilal Nehru National Institute Of Technology, Allahabad was formerly Motilal\n" +
                 "Nehru Regional Engineering College, Allahabad . It is an institute with total\n" +
                 "commitment to quality and excellence in academic pursuits, is among one of the\n" +
                 "leading institutes in INDIA and was established in year 1961 as a joint enterprise of\n" +
                 "Govt. of India and Govt. of U.P. in accordance with the scheme of establishment of\n" +
                 "REC. However with effect from June 26th of 2002 the college became deemed\n" +
                 "university and is now known as Motilal Nehru National Institute of technology. The\n" +
                 "foundation stone of the college was laid by the first Prime Minister of India, Pt.\n" +
                 "Jawahar Lal Nehru on the 3rd of may, 1961 on a site spreading over 222 acres on\n" +
                 "the banks of the river Ganga. The main building of college was inaugurated by\n" +
                 "another illustrious son of India, Prime Minister Sri Lal Bahadur Shastri on 18th of\n" +
                 "April, 1965. The students are extensively exposed to cross-cultural environment as\n" +
                 "candidates from various other countries such as Sri Lanka, Nepal, Bangladesh,\n" +
                 "Bhutan, Mauritius, Malaysia, Iran, Yemen, Iraq, Palestine and Thailand also join\n" +
                 "MNNIT for various undergraduate and post-graduate programs. MNNIT is fully\n" +
                 "residential institution with eight hostels for boys and two for girls.");

        currentUser=mAuth.getCurrentUser();
        if(currentUser==null){

            sendUserToLoginActivity();

        }
        else
        {
            
            checkUserexistence();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void checkUserexistence() {
        final String currentUserId=currentUser.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(currentUserId)){
                    sendUsertoRegisterActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendUsertoRegisterActivity() {
        Intent intent=new Intent(MainActivity.this,Registration.class);
        startActivity(intent);
        finish();
    }

    private void sendUserToLoginActivity() {
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Dashboard) {
            startActivity(new Intent(MainActivity.this,Dashboard.class));
        } else if (id == R.id.Home) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.companies) {

            startActivity(new Intent(MainActivity.this,Visiting_Companies.class));

        } else if (id == R.id.apply) {
            startActivity(new Intent(MainActivity.this,Apply.class));

        } else if (id == R.id.Comment) {
           sendUserToCommentActivity();


        }
        else if(id == R.id.archives)
        {
            startActivity(new Intent(MainActivity.this,WebViewActivity.class));
        }
        else if (id == R.id.logout) {


            progressDialog.setMessage("please wait while logging you out");
            progressDialog.show();
            mAuth.signOut();
            finish();
            sendUserToLoginActivity();


        }
        else if (id == R.id.faq) {
           sendUserToFaqActivity();

        }

        else if (id == R.id.stats) {
            startActivity(new Intent(MainActivity.this,Statistics.class));

        }

        else if (id == R.id.Ques) {
            startActivity(new Intent(MainActivity.this,QuesAns.class));

        }


        return true;
    }

    private void sendUserToFaqActivity() {
        Intent i=new Intent(MainActivity.this,Faq.class);
        startActivity(i);


    }

    private void sendUserToCommentActivity() {
        Intent i=new Intent(MainActivity.this,CommentSection.class);
        startActivity(i);

    }

}
