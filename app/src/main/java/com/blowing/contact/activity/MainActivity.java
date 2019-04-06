package com.blowing.contact.activity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.blowing.contact.R;
import com.blowing.contact.adapter.FragmentAdapter;
import com.blowing.contact.fragment.CallFragment;
import com.blowing.contact.fragment.MessageFragment;
import com.blowing.contact.fragment.NetworkFragment;
import com.blowing.contact.fragment.SetFragment;
import com.blowing.contact.manager.CallManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView title;

    private FragmentAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        checkPermission();
//        if (hasPermissionToReadNetworkStats()) {
//            try {
//                new NetworkManager().getNetwork(this);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                CallManager.getCallRecods(MainActivity.this);
            }
        }).start();
    }

    private void initView() {

        tabLayout = findViewById(R.id.bottom_tab);
        viewPager = findViewById(R.id.vp_fragment);
        title = findViewById(R.id.tv_title);

        tabLayout.setupWithViewPager(viewPager, false);
        initAdapter();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void initAdapter() {
        MessageFragment messageFragment = new MessageFragment();
        CallFragment callFragment = new CallFragment();
        NetworkFragment networkFragment = new NetworkFragment();
        SetFragment setFragment = new SetFragment();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(messageFragment);
        fragments.add(callFragment);
        fragments.add(networkFragment);
        fragments.add(setFragment);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("短信");
        titles.add("通话");
        titles.add("流量");
        titles.add("设置");
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(mAdapter);
    }


    private void checkPermission() {

        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_CALL_LOG, Permission.READ_SMS, Permission.READ_CONTACTS)
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (data.size() > 0) {
                            finish();
                        }
                    }
                }).start();

    }

    private boolean hasPermissionToReadNetworkStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }

        requestReadNetworkStats();
        return false;
    }

    // 打开“有权查看使用情况的应用”页面
    private void requestReadNetworkStats() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }
}
