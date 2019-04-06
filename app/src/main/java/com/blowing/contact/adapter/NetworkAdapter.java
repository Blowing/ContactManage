package com.blowing.contact.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.blowing.contact.R;
import com.blowing.contact.model.AppInfo;
import com.blowing.contact.util.DataUtil;

import java.util.ArrayList;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.NetworkHolder> {

    private ArrayList<AppInfo> mList;
    private Context mContext;

    public NetworkAdapter(Context context, ArrayList<AppInfo> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    public void setList(ArrayList<AppInfo> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public NetworkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_network, viewGroup, false);
        return new NetworkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkHolder networkHolder, int i) {
        AppInfo appInfo = mList.get(i);
        networkHolder.iconIv.setImageDrawable(appInfo.icon);
        networkHolder.appNameTv.setText(appInfo.name);
        networkHolder.dataTv.setText(DataUtil.getPrintSize(appInfo.data ));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NetworkHolder extends RecyclerView.ViewHolder {

        private ImageView iconIv;
        private TextView appNameTv;
        private TextView dataTv;

        private NetworkHolder(@NonNull View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.network_iv_icon);
            appNameTv = itemView.findViewById(R.id.network_tv_app_name);
            dataTv = itemView.findViewById(R.id.network_tv_data);
        }
    }
}
