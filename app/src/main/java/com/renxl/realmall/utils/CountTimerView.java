package com.renxl.realmall.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by renxl
 * On 2017/5/18 9:58.
 */

public class CountTimerView extends CountDownTimer {

    private static final long DEFAULT_TIME = 61 * 1000;
    private static final long DEFAULT_INTERVAL = 1000;

    private TextView tvTime;
    private int strId;

    public CountTimerView(TextView tv) {
        this(DEFAULT_TIME, DEFAULT_INTERVAL, tv, cn.smssdk.gui.R.string.smssdk_resend_identify_code);
    }

    public CountTimerView(TextView tv, int strId) {
        this(DEFAULT_TIME, DEFAULT_INTERVAL, tv, strId);
    }

    /**
     * @param millisInFuture    The number of millis in the future from the call 需要倒计时的时长
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks. 倒计时的间隔
     */
    public CountTimerView(long millisInFuture, long countDownInterval, TextView tv, int strId) {
        super(millisInFuture, countDownInterval);
        tvTime = tv;
        this.strId = strId;
        tvTime.setEnabled(false);
    }

    /**
     * 倒计时过程显示
     *
     * @param millisUntilFinished 剩余的时间
     */
    @Override
    public void onTick(long millisUntilFinished) {
        String str = millisUntilFinished / 1000 + "秒后可重新发送";
        tvTime.setText(str);
    }

    /**
     * 倒计时结束时触发
     */
    @Override
    public void onFinish() {
        tvTime.setText(strId);
        tvTime.setEnabled(true);
    }
}
