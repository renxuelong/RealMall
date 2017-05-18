package com.renxl.realmall.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.utils.Log;

/**
 * Created by renxl
 * On 2017/3/2 9:16.
 */

public class ToolBar extends Toolbar {

    private EditText titleEt;
    private TextView titleTv;
    private Button rightBtn;
    private ToolbarListener toolbarListener;

    public ToolBar(Context context) {
        this(context, null);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(10, 10);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
        String titleText = ta.getString(R.styleable.ToolBar_title_text);
        if (!TextUtils.isEmpty(titleText)) {
            setTitle(titleText);
        }

        boolean isSearchShow = ta.getBoolean(R.styleable.ToolBar_title_search, false);
        setSearch(isSearchShow);

        String rightBtnText = ta.getString(R.styleable.ToolBar_right_text);
        if (!TextUtils.isEmpty(rightBtnText)) {
            setRightBtnText(rightBtnText);
        }

        Drawable rightBtnImg = ta.getDrawable(R.styleable.ToolBar_right_img);
        if (rightBtnImg != null) {
            setRightBtnImg(rightBtnImg);
        }

        ta.recycle();
    }

    public void setToolbarListener(ToolbarListener toolbarListener) {
        this.toolbarListener = toolbarListener;
    }

    public void setRightBtnText(String title) {
        rightBtn.setText(title);
        rightBtn.setVisibility(VISIBLE);
        rightBtn.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setRightBtnImg(Drawable resource) {
        rightBtn.setText("");
        rightBtn.setBackgroundDrawable(resource);
        rightBtn.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        titleEt.setVisibility(GONE);
        titleTv.setVisibility(VISIBLE);
        titleTv.setText(title);
        Log.i(title);
    }

    public void setSearch(boolean isShowSearch) {
        if (isShowSearch) {
            titleTv.setVisibility(GONE);
            titleEt.setVisibility(VISIBLE);
            Log.i("titleSearch");
        } else {
            titleEt.setVisibility(GONE);
        }

    }

    private void initView() {
        @SuppressLint("InflateParams") RelativeLayout view = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.toolbar_main, null);
        titleEt = (EditText) view.findViewById(R.id.et_main_toolbar_search);
        titleTv = (TextView) view.findViewById(R.id.tv_main_toolbar_title);
        rightBtn = (Button) view.findViewById(R.id.btn_main_toolbar_right);

        titleEt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbarListener != null) {
                    toolbarListener.onSearchClick();
                }
            }
        });

        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbarListener != null) {
                    toolbarListener.onRightClick();
                }
            }
        });

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        addView(view, 0, lp);
    }

    public interface ToolbarListener {
        void onSearchClick();

        void onRightClick();
    }

}
