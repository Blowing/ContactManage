package com.blowing.contact.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.blowing.contact.R;
import com.blowing.contact.model.CallRecord;
import com.blowing.contact.util.TimeUtil;

import java.util.ArrayList;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallHolder> {

    private ArrayList<CallRecord> mList;

    private Context mContext;

    public void setList(ArrayList<CallRecord> mList) {
        this.mList = mList;
    }

    public CallAdapter(Context context, ArrayList<CallRecord> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CallHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_call,viewGroup, false);
        return new CallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallHolder callHolder, int i) {
        CallRecord callRecord = mList.get(i);
        callHolder.personTv.setText(TextUtils.isEmpty(callRecord.name) ?
                callRecord.matched_number : callRecord.name);

        callHolder.dateTv.setText(TimeUtil.formatTime(callRecord.date));
        callHolder.locationTv.setText(callRecord.location);
        callHolder.durationTv.setText(TimeUtil.formatDuration(callRecord.duration));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CallHolder extends RecyclerView.ViewHolder {

        private TextView personTv;
        private TextView dateTv;
        private TextView locationTv;
        private TextView durationTv;

        private CallHolder(@NonNull View itemView) {
            super(itemView);

            personTv = itemView.findViewById(R.id.call_tv_person);
            dateTv = itemView.findViewById(R.id.call_tv_date);
            locationTv = itemView.findViewById(R.id.call_tv_location);
            durationTv = itemView.findViewById(R.id.call_tv_duration);
        }
    }
}
