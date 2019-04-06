package com.blowing.contact.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blowing.contact.R;
import com.blowing.contact.activity.LoginActivity;
import com.blowing.contact.model.Constant;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class SetFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set,container, false);
        view.findViewById(R.id.set_btn_log_out).setOnClickListener(this);
        view.findViewById(R.id.set_btn_ret_psw).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_btn_log_out:
                SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_LAST_ACCOUNT, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.set_btn_ret_psw:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("forget", "forget");
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
