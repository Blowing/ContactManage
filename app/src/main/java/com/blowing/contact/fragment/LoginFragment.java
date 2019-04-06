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
 * 登录界面
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText accountEt;
    private EditText pwdEt;
    private Button loginBtn;
    private TextView registerTv;
    private TextView forgetPwdTv;

    private LoginInterface loginInterface;

    public void setLoginInterface(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        accountEt = view.findViewById(R.id.login_et_account);
        pwdEt = view.findViewById(R.id.login_et_password);
        loginBtn = view.findViewById(R.id.login_btn_login);
        registerTv = view.findViewById(R.id.login_tv_register);
        forgetPwdTv = view.findViewById(R.id.login_tv_forget_pwd);

        loginBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
        forgetPwdTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                login();
                break;
            case R.id.login_tv_forget_pwd:
                loginInterface.toForgetPwd();
                break;
            case R.id.login_tv_register:
                loginInterface.toRegister();
                break;
        }
    }

    /**
     * 检查并且登录
     */
    private void login() {
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

        if (password.equals(sp.getString(account, ""))) {
            SharedPreferences sp1 = getActivity().getSharedPreferences(Constant.SP_LAST_ACCOUNT, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putString(Constant.SP_LAST_ACCOUNT, account);
            editor.apply();
            loginInterface.toMain();
        } else {
            if (sp.contains(account)) {
                ToastUtil.showToast(getActivity(), "账号或密码错误");
            } else {
                ToastUtil.showToast(getActivity(), "账号未注册");
            }
        }

    }


}
