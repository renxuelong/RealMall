package com.renxl.realmall.feature.mine;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseFragment;
import com.renxl.realmall.feature.sign_in.LoginActivity;
import com.renxl.realmall.feature.sign_in.User;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by renxl
 * On 2017/2/28 17:35.
 */

public class MineFragment extends BaseFragment {

    public static final int REQUEST_CODE = 0;
    private static final String TAG_LOGIN = "sign_in";

    @BindView(R.id.img_mine_avator)
    CircleImageView imgMineAvator;
    @BindView(R.id.tv_mine_username)
    TextView tvMineUsername;
    @BindView(R.id.btn_sign_out)
    Button btnSignOut;

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void init() {
        showUser();
    }

    public void showUser() {
        User user = UserLocalData.getUser(getContext());
        if (user != null) {
            tvMineUsername.setText(user.getUsername());
            tvMineUsername.setTag("");
            if (!TextUtils.isEmpty(user.getLogo_url()))
                Picasso.with(getContext()).load(user.getLogo_url()).into(imgMineAvator);
            else
                imgMineAvator.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_user));
            btnSignOut.setVisibility(View.VISIBLE);
        } else {
            tvMineUsername.setText(getString(R.string.click_login));
            tvMineUsername.setTag(TAG_LOGIN);
            imgMineAvator.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.avator));
            btnSignOut.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_mine_avator, R.id.tv_mine_username, R.id.btn_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_mine_avator:
            case R.id.tv_mine_username:
                toLogin();
                break;
            case R.id.btn_sign_out:
                toSignOut();
                break;
        }
    }

    private void toSignOut() {
        UserLocalData.clearUser(getContext());
        showUser();
    }

    private void toLogin() {
        String tag = (String) tvMineUsername.getTag();
        if (TAG_LOGIN.equals(tag)) {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showUser();
    }

}