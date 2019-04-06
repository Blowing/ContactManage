package com.blowing.contact.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.blowing.contact.R;
import com.blowing.contact.model.SMSmessage;
import com.blowing.contact.util.TimeUtil;

import java.util.ArrayList;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private ArrayList<SMSmessage> mList;
    private Context mContext;

    public MessageAdapter(Context context,ArrayList<SMSmessage> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    public void setList(ArrayList<SMSmessage> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, viewGroup,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder messageHolder, int i) {
        SMSmessage smessage = mList.get(i);
        messageHolder.personTv.setText(smessage.person);
        messageHolder.dateTv.setText(TimeUtil.formatTime(smessage.date));
        messageHolder.contentTv.setText(smessage.body);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {

        private TextView personTv;
        private TextView dateTv;
        private TextView contentTv;

        private MessageHolder(@NonNull View itemView) {
            super(itemView);
            personTv = itemView.findViewById(R.id.message_tv_person);
            dateTv = itemView.findViewById(R.id.message_tv_date);
            contentTv = itemView.findViewById(R.id.message_tv_content);
        }
    }
}
