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
import com.renxl.realmall.feature.orders.AddressListActivity;
import com.renxl.realmall.feature.orders.OrderListActivity;
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

    @BindView(R.id.img_mine_avator)
    CircleImageView imgMineAvator;
    @BindView(R.id.tv_mine_username)
    TextView tvMineUsername;
    @BindView(R.id.btn_sign_out)
    Button btnSignOut;
    @BindView(R.id.tv_mine_orders)
    TextView mTvMineOrders;
    @BindView(R.id.tv_mine_favorite)
    TextView mTvMineFavorite;
    @BindView(R.id.tv_mine_address)
    TextView mTvMineAddress;

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
            if (!TextUtils.isEmpty(user.getLogo_url()))
                Picasso.with(getContext()).load(user.getLogo_url()).into(imgMineAvator);
            else
                imgMineAvator.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_user));
            btnSignOut.setVisibility(View.VISIBLE);
        } else {
            tvMineUsername.setText(getString(R.string.click_login));
            imgMineAvator.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.avator));
            btnSignOut.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_mine_avator, R.id.tv_mine_username, R.id.btn_sign_out, R.id.tv_mine_orders, R.id.tv_mine_favorite, R.id.tv_mine_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_mine_avator:
            case R.id.tv_mine_username:
                toLoginOrJump(null);
                break;
            case R.id.btn_sign_out:
                toSignOut();
                break;
            case R.id.tv_mine_orders:
                toLoginOrJump(OrderListActivity.class);
                break;
            case R.id.tv_mine_favorite:
                toLoginOrJump(FavoriteActivity.class);
                break;
            case R.id.tv_mine_address:
                toLoginOrJump(AddressListActivity.class);
                break;
        }
    }

    private void toSignOut() {
        UserLocalData.clearUser(getContext());
        showUser();
    }

    private void toLoginOrJump(Class clazz) {
        if (isLogin()) {
            if (clazz != null) startActivity(new Intent(getActivity(), clazz));
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            if (clazz != null)
                intent.putExtra(LoginActivity.JUMP_INTENT, new Intent(getActivity(), clazz));
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private boolean isLogin() {
        return UserLocalData.getUser(getContext()) != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showUser();
    }
}