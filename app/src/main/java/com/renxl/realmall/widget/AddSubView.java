package com.renxl.realmall.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renxl.realmall.R;

/**
 * Created by renxl
 * On 2017/5/3 9:02.
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private static final int DEFAULT_NUMBER = 1;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 5;

    private int mValue;
    private int minValue;
    private int maxValue;
    private Drawable btnAddBg, btnSubBg, tvValueBg;

    private TextView tvValue;
    private Button btnAdd, btnSub;

    private OnAddSubClickListener onAddSubClickListener;

    public AddSubView(Context context) {
        this(context, null);
    }

    public AddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

        if (attrs == null) return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AddSubView);

        mValue = ta.getInteger(R.styleable.AddSubView_value, DEFAULT_NUMBER);
        setValue(mValue);
        minValue = ta.getInteger(R.styleable.AddSubView_min_value, MIN_NUMBER);
        setMinValue(minValue);
        maxValue = ta.getInteger(R.styleable.AddSubView_max_value, MAX_NUMBER);
        if (maxValue != 0) setMaxValue(maxValue);

        btnAddBg = ta.getDrawable(R.styleable.AddSubView_btn_add_background);
        btnSubBg = ta.getDrawable(R.styleable.AddSubView_btn_sub_background);
        tvValueBg = ta.getDrawable(R.styleable.AddSubView_value_background);
        if (btnAddBg != null) setBtnAddBg(btnAddBg);
        if (btnSub != null) setBtnSubBg(btnSubBg);
        if (tvValueBg != null) setTvValueBg(tvValueBg);

        ta.recycle();
    }

    private void add() {
        if (mValue < maxValue) {
            mValue++;
        }
        String value = mValue + "";
        tvValue.setText(value);
    }

    private void sub() {
        if (mValue > minValue) {
            mValue--;
        }
        String value = mValue + "";
        tvValue.setText(value);
    }

    public void setOnAddSubClickListener(OnAddSubClickListener listener) {
        this.onAddSubClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add();
                if (onAddSubClickListener != null) onAddSubClickListener.onAddClick(v, mValue);
                break;
            case R.id.btn_sub:
                sub();
                if (onAddSubClickListener != null) onAddSubClickListener.onSubClick(v, mValue);
                break;
        }
    }

    private void initView() {
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.add_sub_widget, this, true);
        tvValue = (TextView) view.findViewById(R.id.tv_value);
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        btnSub = (Button) view.findViewById(R.id.btn_sub);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
        String value = mValue + "";
        tvValue.setText(value);
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setBtnAddBg(Drawable btnAddBg) {
        this.btnAddBg = btnAddBg;
        btnAdd.setBackgroundDrawable(btnAddBg);
    }

    public void setBtnSubBg(Drawable btnSubBg) {
        this.btnSubBg = btnSubBg;
        btnSub.setBackgroundDrawable(btnSubBg);
    }

    public void setTvValueBg(Drawable tvValueBg) {
        this.tvValueBg = tvValueBg;
        tvValue.setBackgroundDrawable(tvValueBg);
    }

    public interface OnAddSubClickListener {
        void onAddClick(View view, int value);

        void onSubClick(View view, int value);
    }
}
