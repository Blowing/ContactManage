package com.blowing.contact.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blowing.contact.R;
import com.blowing.contact.activity.MessageShowCharActivity;
import com.blowing.contact.activity.ShowListActivity;
import com.blowing.contact.model.Constant;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class MessageFragment extends Fragment implements View.OnClickListener{

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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_show_card:
                intent.setClass(getActivity(), MessageShowCharActivity.class);
                break;
            case R.id.btn_show_list:
                intent.setClass(getActivity(), ShowListActivity.class);
                intent.putExtra("action", Constant.MESSAGE);
                break;
        }
        startActivity(intent);
    }
}
