package com.example.lenovo.hire_me;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatBox extends AppCompatActivity {
private static int SIGN_IN_REQUEST_CODE=1;
RelativeLayout chatbox;
FloatingActionButton fab;
private FirebaseListAdapter<ChatMessage> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        chatbox=(RelativeLayout)findViewById(R.id.chatbox);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input=(EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });
        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        }
        else
        {
            Snackbar.make(chatbox,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
        }
        displayChatMessage();
        }

    private void displayChatMessage() {
        ListView listofmessage=(ListView)findViewById(R.id.list_of_message);
//        adapter=new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,android.R.layout.simple_list_item_1,FirebaseDatabase.getInstance().getReference())
//        {
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//                TextView messagetext,messageuser,messagetime;
//                messagetext=(TextView)findViewById(R.id.message_text);
//                messageuser=(TextView)findViewById(R.id.message_user);
//                messagetime=(TextView)findViewById(R.id.message_time);
//                messagetext.setText(model.getMessagetext());
//                messageuser.setText(model.getMessageuser());
//                messagetime.setText(android.text.format.DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessagetime()));
//            }
//        };

        Query query = FirebaseDatabase.getInstance().getReference().child("chats");
//The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLayout(R.layout.list_item)
                .build();
        //Finally you pass them to the constructor here:
        adapter = new FirebaseListAdapter<ChatMessage>(options){
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);

                // Set their text
                messageText.setText(model.getMessagetext());
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessagetime()));
                messageUser.setText(model.getMessageuser());
            }
        };
        listofmessage.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                Snackbar.make(chatbox,"Successfully Signed in, Welcome",Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            }
            else
            {
                Snackbar.make(chatbox,"We couldn't sign you in!",Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_signout)
        {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar.make(chatbox,"Signed Out!",Snackbar.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }
        return true;
    }
}
