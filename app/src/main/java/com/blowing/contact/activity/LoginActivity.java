package com.blowing.contact.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.blowing.contact.R;
import com.blowing.contact.fragment.ForgetFragment;
import com.blowing.contact.fragment.LoginFragment;
import com.blowing.contact.fragment.RegistFragment;
import com.blowing.contact.impl.LoginInterface;
import com.blowing.contact.model.Constant;

public class LoginActivity extends AppCompatActivity implements LoginInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().hasExtra("forget")) {
            toForgetPwd();
        } else {
            SharedPreferences sp = getSharedPreferences(Constant.SP_LAST_ACCOUNT,MODE_PRIVATE);
            if (sp.contains(Constant.SP_LAST_ACCOUNT)) {
                toMain();
            }
            toLogin();
        }

    }

    @Override
    public void toRegister() {
        RegistFragment fragment = new RegistFragment();
        fragment.setLoginInterface(this);
        switchFragment(fragment);
    }

    @Override
    public void toForgetPwd() {
        ForgetFragment fragment = new ForgetFragment();
        fragment.setLoginInterface(this);
        switchFragment(fragment);
    }

    @Override
    public void toLogin() {
        // 加载登录页
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setLoginInterface(this);
        switchFragment(loginFragment);
    }

    @Override
    public void toMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 切换界面
     * @param fragment 指定的界面
     */
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_content, fragment);
        fragmentTransaction.commit();
    }
}
