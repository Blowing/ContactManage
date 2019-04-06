package com.blowing.contact.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.blowing.contact.R;
import com.blowing.contact.impl.LoginInterface;
import com.blowing.contact.model.Constant;
import com.blowing.contact.util.ToastUtil;

/**
 * Created by wujie
 * on 2019/4/3/003.
 * 注册界面
 */
public class RegistFragment extends Fragment implements View.OnClickListener{


    private EditText accountEt;
    private EditText pwdEt;
    private Button registerBtn;
    private TextView loginTv;


    private LoginInterface loginInterface;

    public void setLoginInterface(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        accountEt = view.findViewById(R.id.register_et_account);
        pwdEt = view.findViewById(R.id.register_et_password);
        registerBtn = view.findViewById(R.id.register_btn_register);
        loginTv = view.findViewById(R.id.register_tv_to_login);

        registerBtn.setOnClickListener(this);
        loginTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_register:
                register();
                break;
            case R.id.register_tv_to_login:
                loginInterface.toLogin();
                break;

        }
    }

    private void register() {

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

        SharedPreferences sp  = getActivity().getSharedPreferences(Constant.SP_LOGIN_PASSWORD, Context.MODE_PRIVATE);

        if (sp.contains(account)) {
            ToastUtil.showToast(getActivity(), "该账号已经被注册，请勿重新注册");
        } else {
            ToastUtil.showToast(getActivity(), "注册成功请登录");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(account, password);
            editor.apply();

            loginInterface.toLogin();
        }

    }
}
