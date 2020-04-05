package com.dev_m_elbanna.watsonbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev_m_elbanna.watsonbot.network.pojo.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created By Mohamed El Banna On 4/5/2020
 **/
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mMessagesList;
    Context context;

    public MessageAdapter(List<Message> mMessagesList) {
        this.mMessagesList = mMessagesList;
    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout2, parent, false);
        return new MessageViewHolder(view);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView displayName;
        public TextView displayTime;
//        public CircleImageView profileImage;
//        public ImageView messageImage;


        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_text_layout);
            displayName = (TextView) itemView.findViewById(R.id.name_text_layout);
            displayTime = (TextView) itemView.findViewById(R.id.time_text_layout);
//            profileImage = (CircleImageView)itemView.findViewById(R.id.message_profile_layout);
            // messageImage = (ImageView)itemView.findViewById(R.id.message_image_layout);

            context = itemView.getContext();


        }

    }

    public void addItems(List<Message> messages) {
        mMessagesList.clear();
        mMessagesList.addAll(messages);
        notifyDataSetChanged();
    }

    public void addSingleItem(Message messages) {
        mMessagesList.add(messages);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {


        Message mMessageBean = mMessagesList.get(position);
        holder.displayName.setText(mMessageBean.getUserName());
        holder.messageText.setText(mMessageBean.getMessage());
        String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
        holder.displayTime.setText("Time: " + currentTime);

    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }
}
