package com.hulahoop.mentalhealth.undepress;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.hulahoop.mentalhealth.undepress.loaders.ChatSendTaskLoader;
import com.hulahoop.mentalhealth.undepress.loaders.ChatTaskLoader;
import com.hulahoop.mentalhealth.undepress.models.ChatBubble;
import com.hulahoop.mentalhealth.undepress.models.Expert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private Expert expert;
    private SharedPreferences mPreferences;
    private ArrayList<ChatBubble> chatLog;
    private ImageButton sendButton;
    private EditText inputMessage;
    private ListView chatListView;
    private ChatsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mPreferences = getSharedPreferences("authorization", MODE_PRIVATE);
        chatLog = new ArrayList<>();

        expert = (Expert) getIntent().getSerializableExtra("current_expert");

        if (chatLog.size() == 0) {
            getSupportLoaderManager().initLoader(1, null, this);
        }

        adapter = new ChatsAdapter(this, R.layout.chat_bubble_left, chatLog);

        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMessage.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Type a message", Toast.LENGTH_SHORT).show();
                } else {
                    chatLog.add(new ChatBubble(inputMessage.getText().toString(), true));
                    sendChat();
                    adapter.notifyDataSetChanged();
                }

            }
        });

        inputMessage = findViewById(R.id.type_message);
        chatListView = findViewById(R.id.chat_listview);

        setTitle(expert.getName());
    }

    public void sendChat() {
        Bundle args = new Bundle();
        args.putString("message", inputMessage.getText().toString());
        args.putString("timestamp", new Timestamp(System.currentTimeMillis()).toString());
        getSupportLoaderManager().initLoader(0, args, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 0:
                return new ChatSendTaskLoader(this, mPreferences.getString("access_token", "defaultaccesstoken"), expert.getId(), args.getString("message"), args.getString("timestamp"));
            case 1:
                return new ChatTaskLoader(this, mPreferences.getString("access_token", "defaultaccesstoken"), expert.getId());
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case 0:
                break;
            case 1:
                if(chatLog.size() > 0) {
                    chatLog.clear();
                }
                try {
                    JSONArray chatJsonArray = new JSONArray(data);
                    JSONObject chatJsonObject;
                    for(int i = 0; i < chatJsonArray.length(); i++) {
                        chatJsonObject = chatJsonArray.getJSONObject(i);
                        boolean signedUserMessage = chatJsonObject.getInt("sender_id") != expert.getId();
                        String message = chatJsonObject.getString("message");
                        ChatBubble temp = new ChatBubble(message, signedUserMessage);
                        chatLog.add(temp);
                    }

                    adapter = new ChatsAdapter(this, R.layout.chat_bubble_left, chatLog);
                    chatListView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
