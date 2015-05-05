package com.example.agcostfu.wya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.agcostfu.server.AddToChatClient;
import com.example.agcostfu.server.GetGroupChatClient;
import com.example.agcostfu.server.GetGroupLocationClient;
import com.example.agcostfu.server.UpdatingClient;


public class ChatBubbleActivity extends ActionBarActivity {
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private Handler updateHandler;
    Intent intent;
    private boolean side = false;
    private String number;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_chat);
        //updateHandler = new Handler();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        number = tm.getLine1Number();

        buttonSend = (Button) findViewById(R.id.buttonSend);

        listView = (ListView) findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.chatText);
        chatText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });
        startPeriodicUpdate();

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private void startPeriodicUpdate() {
        updateHandler.post(update);
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            String chat = new GetGroupChatClient(number).getInfoFromRequest();
            String next = "";
            for (int i = 0; i < chat.length(); i++) {
                if (chat.charAt(i) != '\n') {
                    next = next + chat.charAt(i);
                } else {
                    chatArrayAdapter.add(new ChatMessage(side, chat));
                }
            }

            //chatArrayAdapter.add(new ChatMessage(side, chat));
            //if(inGroup)
         /*   }else
                setUpMap();*/

            updateHandler.postDelayed(update, 1000);
        }
    };

    private boolean sendChatMessage() {
        String text = chatText.getText().toString();
        chatArrayAdapter.add(new ChatMessage(side, text));


        new AddToChatClient(number, number, text);
        chatText.setText("");
        side = !side;
        return true;
    }

}