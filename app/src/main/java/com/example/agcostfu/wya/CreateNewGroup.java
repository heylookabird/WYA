package com.example.agcostfu.wya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agcostfu.server.CreateGroupClient;
import com.example.agcostfu.wya.LonelyMapActivity;


public class CreateNewGroup extends ActionBarActivity{
    private EditText username, groupname;
    private Button finish;
    private String un, gn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.action_create_group);

        username = (EditText) findViewById(R.id.editText);
        groupname = (EditText) findViewById(R.id.editText2);
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

                //MainMapsActivity.setInGroup(true);
            }
        });


       /* buttonCreate = (Button) findViewById(R.id.buttoncreate);


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setUserInfo();
            }
        });*/

    }

    private void setUserInfo() {

         }
    }
