package com.blowing.contact.activity;

import android.app.ProgressDialog;
import android.os.*;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.blowing.contact.R;
import com.blowing.contact.adapter.CallAdapter;
import com.blowing.contact.adapter.MessageAdapter;
import com.blowing.contact.adapter.NetworkAdapter;
import com.blowing.contact.manager.CallManager;
import com.blowing.contact.manager.NetworkManager;
import com.blowing.contact.manager.SMSManager;
import com.blowing.contact.model.AppInfo;
import com.blowing.contact.model.CallRecord;
import com.blowing.contact.model.Constant;
import com.blowing.contact.model.SMSmessage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    private String action;
    private RecyclerView mRecyclerView;
    private TextView titleTv;

    private WeakHandler mHandler;

    private MessageAdapter messageAdapter;
    private CallAdapter callAdapter;
    private NetworkAdapter networkAdapter;

    private ProgressDialog loadDialog;


    private static class WeakHandler extends Handler {

        private final WeakReference<ShowListActivity> weakReference;

        private WeakHandler(ShowListActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ShowListActivity activity = weakReference.get();
            if (activity != null) {
                activity.showData();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        mHandler = new WeakHandler(this);
        action = getIntent().getStringExtra("action");
        initView();
        loadDialog = ProgressDialog.show(ShowListActivity.this, "", "加载中，请稍后", true,false);
        loadDialog.show();
        LoadData();
    }


    private void initView() {
        titleTv = findViewById(R.id.header_tv_title);
        titleTv.setText(action);
        findViewById(R.id.header_ly_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.show_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        setAdapter();


    }

    private void setAdapter() {
        switch (action) {
            case Constant.CALL:
                callAdapter = new CallAdapter(getApplicationContext(), new ArrayList<CallRecord>());
                mRecyclerView.setAdapter(callAdapter);
                break;
            case Constant.MESSAGE:
                messageAdapter = new MessageAdapter(getApplicationContext(), new ArrayList<SMSmessage>());
                mRecyclerView.setAdapter(messageAdapter);
                break;
            case Constant.NETWORK:
                networkAdapter = new NetworkAdapter(getApplicationContext(), new ArrayList<AppInfo>());
                mRecyclerView.setAdapter(networkAdapter);
                break;
        }
    }

    private void LoadData() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                switch (action) {
                    case Constant.CALL:
                        callAdapter.setList(CallManager.getCallRecods(ShowListActivity.this));
                        mHandler.sendEmptyMessage(0x01);
                        break;
                    case Constant.MESSAGE:
                        messageAdapter.setList(SMSManager.getSMS(ShowListActivity.this));
                        mHandler.sendEmptyMessage(0x01);
                        break;
                    case Constant.NETWORK:
                        try {
                            networkAdapter.setList(new NetworkManager(ShowListActivity.this).getNetwork());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(0x01);
                        break;
                }
            }
        }).start();



    }

    private void showData() {
        loadDialog.dismiss();
        switch (action) {
            case Constant.CALL:
                callAdapter.notifyDataSetChanged();
                break;
            case Constant.MESSAGE:
                messageAdapter.notifyDataSetChanged();
                break;
            case Constant.NETWORK:
                networkAdapter.notifyDataSetChanged();
                break;
        }
    }

}
