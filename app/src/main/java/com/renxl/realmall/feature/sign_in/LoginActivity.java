package com.renxl.realmall.feature.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.base.BaseTokenBean;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.utils.DESUtil;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renxl
 * On 2017/5/16 14:26.
 */

public class LoginActivity extends BaseActivity {

    public static String JUMP_INTENT = "jump_intent";

    Button tvLogin;
    @BindView(R.id.toolbar_sign_in)
    ToolBar toolbarSignIn;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_psw)
    EditText etLoginPsw;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_sign_up)
    TextView tvLoginSignUp;
    @BindView(R.id.tv_login_reset_pwd)
    TextView tvLoginResetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.btn_login, R.id.tv_login_sign_up, R.id.tv_login_reset_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.tv_login_sign_up:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.tv_login_reset_pwd:
                break;
        }
    }

    private void doLogin() {
        String phone = etLoginPhone.getText().toString().trim();
        String pwd = tvLoginResetPwd.getText().toString();
        if (!checkNumber(phone, pwd)) return;

        RequestParams params = new RequestParams();
        params.put("phone", "+86" + phone);
        params.put("password", DESUtil.encode(Constants.DES_KEY, pwd));
        RealMallClient.getInstance().post(Constants.USER_SIGN_IN, params, new HTTPCallback<BaseTokenBean<User>>() {
            @Override
            public void ok(BaseTokenBean<User> response) {
                if (response.getStatus() != 1) {
                    fail(response.getMessage());
                    return;
                }
                loginSuccess(response);
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show(errorMessage);
            }
        });
    }

    private void loginSuccess(BaseTokenBean<User> response) {
        UserLocalData.putUser(LoginActivity.this, response.getT());
        UserLocalData.putToken(LoginActivity.this, response.getToken());
        Intent jumpIntent = getIntent().getParcelableExtra(JUMP_INTENT);
        if (jumpIntent != null) startActivity(jumpIntent);
        finish();
    }

    private boolean checkNumber(String phone, String password) {
        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(phone);

        if (!m.matches()) {
            Toast.show("您输入的手机号码格式不正确");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.show("密码不能为空");
            return false;
        }
        return true;
    }
}
