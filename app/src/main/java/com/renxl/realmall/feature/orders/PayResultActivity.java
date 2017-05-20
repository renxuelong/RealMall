package com.renxl.realmall.feature.orders;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.feature.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renxl
 * On 2017/5/20 17:02.
 */

public class PayResultActivity extends BaseActivity {
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    @BindView(R.id.img_pay_result_icon)
    ImageView mImgPayResultIcon;
    @BindView(R.id.tv_pay_result_message)
    TextView mTvPayResultMessage;
    @BindView(R.id.btn_pay_result_back)
    Button mBtnPayResultBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        boolean success = intent.getBooleanExtra(SUCCESS, false);
        String message = intent.getStringExtra(MESSAGE);
        if (success)
            mImgPayResultIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_success_128));
        else
            mImgPayResultIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_cancel_128));

        mTvPayResultMessage.setText(message);
    }

    @OnClick(R.id.btn_pay_result_back)
    public void onViewClicked() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }
}
