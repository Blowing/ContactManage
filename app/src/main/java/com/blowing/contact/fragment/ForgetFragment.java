package com.blowing.contact.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.blowing.contact.R;
import com.blowing.contact.impl.LoginInterface;
import com.blowing.contact.model.Constant;
import com.blowing.contact.util.ToastUtil;

/**
 * Created by wujie
 * on 2019/4/6/006.
 */
public class ForgetFragment extends Fragment implements View.OnClickListener {

    private EditText accountEt;
    private EditText pwdEt;
    private Button okButton;
    private LoginInterface loginInterface;

    public void setLoginInterface(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_pwd, container, false);
        accountEt = view.findViewById(R.id.forget_et_account);
        pwdEt = view.findViewById(R.id.forget_et_password);
        view.findViewById(R.id.forget_btn_ok).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_btn_ok:
                restPwd();
                break;
        }
    }

    private void restPwd() {
        String account = accountEt.getEditableText().toString();
        String password = pwdEt.getEditableText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showToast(getActivity(), "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(getActivity(), "密码不能为空");
            return;
        }
        SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_LOGIN_PASSWORD, Context.MODE_PRIVATE);
        if (sp.contains(account)) {
            loginInterface.toLogin();
        } else {
            ToastUtil.showToast(getActivity(), "账号未注册");
        }
    }

}

