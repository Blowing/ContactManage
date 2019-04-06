package com.blowing.contact.fragment;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blowing.contact.R;
import com.blowing.contact.activity.NetworkShowCartActivity;
import com.blowing.contact.activity.ShowListActivity;
import com.blowing.contact.model.Constant;
import com.blowing.contact.util.ToastUtil;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class NetworkFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.btn_show_list).setOnClickListener(this);
        view.findViewById(R.id.btn_show_card).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (hasPermissionToReadNetworkStats()) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.btn_show_card:
                    intent.setClass(getActivity(), NetworkShowCartActivity.class);
                    break;
                case R.id.btn_show_list:
                    intent.setClass(getActivity(), ShowListActivity.class);
                    intent.putExtra("action", Constant.NETWORK);
                    break;
            }
            startActivity(intent);
        } else {
            ToastUtil.showToast(getActivity(), "手机版本太低或者没有权限");
        }

    }

    private boolean hasPermissionToReadNetworkStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        final AppOpsManager appOps = (AppOpsManager) getActivity().getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getActivity().getPackageName());
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
