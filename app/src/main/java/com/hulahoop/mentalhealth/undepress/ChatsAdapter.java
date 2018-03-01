package com.hulahoop.mentalhealth.undepress;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hulahoop.mentalhealth.undepress.models.ChatBubble;

import java.util.List;

public class ChatsAdapter extends ArrayAdapter<ChatBubble> {
    private Activity activity;
    private List<ChatBubble> chats;

    public ChatsAdapter(@NonNull Activity activity, @LayoutRes int resource, List<ChatBubble>
            chats) {
        super(activity, resource, chats);
        this.activity = activity;
        this.chats = chats;
    }

    public void setChats(List<ChatBubble> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity
                .LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0;
        ChatBubble chatBubble = getItem(position);

        if (chatBubble != null) {
            if (!chatBubble.isSignedInUserMessage()) {
                layoutResource = R.layout.chat_bubble_left;
            } else {
                layoutResource = R.layout.chat_bubble_right;
            }
        }

        if (inflater != null) {
            convertView = inflater.inflate(layoutResource, parent, false);
        }
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);

        if (chatBubble != null) {
            holder.message.setText(chatBubble.getMessageContent());
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView message;

        public ViewHolder(View view) {
            message = view.findViewById(R.id.bubbleChat_message);
        }
    }
}
