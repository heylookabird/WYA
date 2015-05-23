package com.example.agcostfu.wya;

/*
Activity to handle creating a group and sending a request to the server


 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.agcostfu.server.CreateGroupClient;

public class CreateNewGroup extends ActionBarActivity{
    private EditText username, groupname;
    private Button finish;
    private String un, gn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.action_create_group);

        username = (EditText) findViewById(R.id.username);
        groupname = (EditText) findViewById(R.id.groupname);
        finish = (Button) findViewById(R.id.buttoncreate);
        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        groupname.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateGroupClient(username.getText().toString(), LonelyMapActivity.getThisNumber(), groupname.getText().toString());
                Intent newMap = new Intent(CreateNewGroup.this, GroupMapActivity.class);
                CreateNewGroup.this.startActivity(newMap);

            }
        });




    }

    private void setUserInfo() {

         }
    }
