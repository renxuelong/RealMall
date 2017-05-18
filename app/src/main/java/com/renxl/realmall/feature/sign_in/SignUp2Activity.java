package com.renxl.realmall.feature.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseTokenBean;
import com.renxl.realmall.http.HTTPCallback;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.utils.CountTimerView;
import com.renxl.realmall.utils.DESUtil;
import com.renxl.realmall.utils.Log;
import com.renxl.realmall.utils.ManifestUtils;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class SignUp2Activity extends AppCompatActivity {
    private static final String MOB_SMS_KEY = "mob_sms_appKey";
    private static final String MOB_SMS_SECRET = "mob_sms_appSecret";

    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String COUNTRY_CODE = "countryCode";

    @BindView(R.id.toolbar_sign_up_2)
    ToolBar toolbarSignUp2;
    @BindView(R.id.tv_sign_tip)
    TextView tvSignTip;
    @BindView(R.id.et_sign_code)
    EditText etSignCode;
    @BindView(R.id.btn_sign_resend)
    Button btnSignResend;

    private CountTimerView mCountTimerView;
    private String countryCode;
    private String phoneNumber;
    private String password;
    private EventHandler eh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        ButterKnife.bind(this);

        initSMS();
        initData();
        initToolbar();
        initCountTimer();
    }

    private void initSMS() {
        SMSSDK.initSDK(this, ManifestUtils.getMetaDataValue(this, MOB_SMS_KEY), ManifestUtils.getMetaDataValue(this, MOB_SMS_SECRET));
        eh = new EventHandler() {
            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { // 校验验证码
                                doReg();
                            }
                        } else {
                            Throwable resId = (Throwable) data;
                            resId.printStackTrace();
                            try {
                                JSONObject obj = new JSONObject(resId.getMessage());
                                String detail = obj.optString("detail");
                                if (!TextUtils.isEmpty(detail)) Toast.show(detail);
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

    private void initData() {
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra(PHONE);
        password = intent.getStringExtra(PASSWORD);
        countryCode = intent.getStringExtra(COUNTRY_CODE);

        String text = getString(R.string.smssdk_send_mobile_detail) + countryCode + " " + formatPhone();

        Log.i("SignUp2 -- " + phoneNumber + "  " + password + "  " + countryCode + text);

        tvSignTip.setText(Html.fromHtml(text));
    }

    private void initToolbar() {
        toolbarSignUp2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarSignUp2.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {

            }

            @Override
            public void onRightClick() {
                checkCode();
            }
        });
    }

    private void initCountTimer() {
        mCountTimerView = new CountTimerView(btnSignResend);
        mCountTimerView.start();
    }

    private void doReg() {
        RequestParams params = new RequestParams();
        params.put("phone", phoneNumber);
        params.put("password", DESUtil.encode(Constants.DES_KEY, password));
        RealMallClient.getInstance().post(Constants.USER_SIGN_UP, params, new HTTPCallback<BaseTokenBean<User>>() {
            @Override
            public void ok(BaseTokenBean<User> response) {
                if (response.getStatus() != 1) {
                    fail(response.getMessage());
                    return;
                }
                Toast.show("注册成功");
                finish();
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show(errorMessage);
            }
        });
    }

    private String formatPhone() {
        StringBuilder sb = new StringBuilder(phoneNumber);
        sb.reverse();
        for (int i = 4, len = sb.length(); i < len; i += 5) {
            sb.insert(i, " ");
        }
        sb.reverse();

        return sb.toString();
    }

    private void checkCode() {
        String code = etSignCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.show("请输入验证码");
        } else {
            SMSSDK.submitVerificationCode(countryCode, phoneNumber, code);
        }
    }

    @OnClick(R.id.btn_sign_resend)
    public void onViewClicked() {
        mCountTimerView = new CountTimerView(btnSignResend);
        mCountTimerView.start();
        SMSSDK.getVerificationCode(countryCode, phoneNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eh != null) SMSSDK.unregisterEventHandler(eh);
    }
}
