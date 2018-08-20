package com.iaijian.robot.androidmddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mUserNameTil, mPasswordTil;
    private EditText mUserNameEt, mPasswordEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mUserNameTil = findViewById(R.id.til_user_name);
        mPasswordTil = findViewById(R.id.til_password);
        mUserNameEt = findViewById(R.id.et_user_name);
        mPasswordEt = findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
        mUserNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mUserNameTil.getError()!=null){
                    mUserNameTil.setError(null);
                }
            }
        });
        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mPasswordTil.getError()!=null){
                    mPasswordTil.setError(null);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (TextUtils.isEmpty(mUserNameEt.getText().toString()) ||
                    !isAccountValid(mUserNameEt.getText().toString())) {
                mUserNameTil.setError("无效的手机号码");
                return;
            }
            if (TextUtils.isEmpty(mPasswordEt.getText().toString()) ||
                    !isPasswordValid(mPasswordEt.getText().toString())) {
                mPasswordTil.setError("格式有误");
                return;
            }

            MainActivity.startInstance(this);
            finish();
        }
    }



    private boolean isAccountValid(String name) {
        //TODO: Replace this with your own logic
        Pattern pattern = Pattern.compile("^(13[0-9]|14[5|7]|15\\d|17[6|7]|18[\\d])\\d{8}$");
        return pattern.matcher(name).matches();
    }

    private boolean isPasswordValid(String password) { //TODO: Replace this with your own logic
        return password.length() > 6;
    }
}
