package com.renxl.realmall.feature.sign_in;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.utils.Log;
import com.renxl.realmall.utils.ManifestUtils;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class SignUpActivity extends Activity {

    private static final String MOB_SMS_KEY = "mob_sms_appKey";
    private static final String MOB_SMS_SECRET = "mob_sms_appSecret";

    @BindView(R.id.toolbar_sign_up_1)
    ToolBar toolbarSignUp1;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    HashMap<String, String> countryRules;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.btn_regist)
    Button btnRegist;

    private EventHandler eh;
    private String code, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initToolbar();
        initSMSSDK();
    }

    private void initToolbar() {
        toolbarSignUp1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarSignUp1.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {

            }

            @Override
            public void onRightClick() {
                sendSms();
            }
        });
    }

    private void initSMSSDK() {

        SMSSDK.initSDK(this, ManifestUtils.getMetaDataValue(this, MOB_SMS_KEY), ManifestUtils.getMetaDataValue(this, MOB_SMS_SECRET));

        String[] country = SMSSDK.getCountry("42");
        if (country != null) {
            tvCountry.setText(country[0]);
            String c = "+" + country[1];
            tvCode.setText(c);
        }

        countryRules = new HashMap<>();
        eh = new EventHandler() {
            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == SMSSDK.RESULT_COMPLETE) { // 回调完成
                            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { // 获取验证码成功
                                onVerificationCodeGot((boolean) data);
                            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) { // 返回支持发送验证码的国家列表
                                onCountryListGot((ArrayList<HashMap<String, String>>) data);
                            }
                        } else { // 发生异常
                            try {
                                ((Throwable) data).printStackTrace();
                                Throwable resId = (Throwable) data;
                                JSONObject object = new JSONObject(resId.getMessage());
                                String des = object.optString("detail");
                                if (!TextUtils.isEmpty(des)) Toast.show(des);
                            } catch (Exception var5) {
                                SMSLog.getInstance().w(var5);
                            }
                        }
                    }
                });
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    private void sendSms() {
        code = tvCode.getText().toString().trim().replaceAll("\\s*", "");
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (!checkNumber(code, phone, password)) return;

        // 获取国家列表
        SMSSDK.getSupportedCountries();
        // 发送验证码
        SMSSDK.getVerificationCode(code, phone);
    }

    private boolean checkNumber(String code, String phone, String password) {

        if (TextUtils.isEmpty(phone)) {
            Toast.show("请输入手机号码");
            return false;
        }

        if ("+86".equals(code)) {
            if (phone.length() != 11) {
                Toast.show("手机号码长度不对");
                return false;
            }
        }

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

        if (password.length() < 6) {
            Toast.show("密码长度不能少于 6 位");
            return false;
        }
        return true;
    }

    private void onVerificationCodeGot(boolean data) {
        Log.i("获取验证码返回结果：" + (data ? "智能验证" : "普通短信验证"));
        Intent intent = new Intent(this, SignUp2Activity.class);
        intent.putExtra(SignUp2Activity.PHONE, phone);
        intent.putExtra(SignUp2Activity.PASSWORD, password);
        intent.putExtra(SignUp2Activity.COUNTRY_CODE, code);
        startActivity(intent);
        finish();
    }

    private void onCountryListGot(ArrayList<HashMap<String, String>> countries) {
//        if (countries == null) return;
//        for (HashMap<String, String> country : countries) {
//            String code = country.get("zone");
//            String rule = country.get("rule");
//
//            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) continue;
//
//            Log.i("Country " + "zone:" + code + "  rule:" + rule);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eh != null) SMSSDK.registerEventHandler(eh);

    }
}
