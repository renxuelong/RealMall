package com.renxl.realmall.feature.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.base.BaseBean;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.sign_in.LoginActivity;
import com.renxl.realmall.feature.sign_in.User;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.http.TokenCallback;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by renxl
 * On 2017/5/20 9:50.
 */

public class AddressListActivity extends BaseActivity {

    public static final int REQUEST_CODE = 10001;

    @BindView(R.id.toolbar_address_list)
    ToolBar mToolbarAddressList;
    @BindView(R.id.recycler_address_list)
    RecyclerView mRecyclerAddressList;

    private AddressAdapter mAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);

        initToolbar();
        requestList();
    }

    private void requestList() {
        User user = UserLocalData.getUser(this);
        if (user == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE);
            return;
        }

        RequestParams params = new RequestParams();
        params.put("user_id", user.getId());
        RealMallClient.getInstance().get(Constants.ADDRESS_LIST, RealMallClient.getTokenParams(params), new TokenCallback<List<Address>>(this) {
            @Override
            public void ok(List<Address> response) {
                if (response == null) return;
                showList(response);
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show(errorMessage);
            }
        });
    }

    private void showList(List<Address> response) {
        Collections.sort(response);
        if (mAddressAdapter == null) {
            mAddressAdapter = new AddressAdapter(response, this, new AddressUpdateListener());
            mAddressAdapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Address address = mAddressAdapter.getDatas().get(position);
                    Intent intent = new Intent();
                    intent.putExtra(AddressEditActivity.ADDRESS, address);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            mRecyclerAddressList.setAdapter(mAddressAdapter);
            mRecyclerAddressList.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mAddressAdapter.clear();
            mAddressAdapter.setData(response);
            mRecyclerAddressList.setAdapter(mAddressAdapter);
        }
    }

    private void initToolbar() {
        mToolbarAddressList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbarAddressList.setToolbarListener(new ToolBar.ToolbarListener() {
            @Override
            public void onSearchClick() {

            }

            @Override
            public void onRightClick() {
                startActivityForResult(new Intent(AddressListActivity.this, AddressEditActivity.class), REQUEST_CODE);
            }
        });
    }

    private void changeDefault(Address item) {
        RequestParams params = new RequestParams();
        params.put("id", item.getId());
        params.put("consignee", item.getConsignee());
        params.put("phone", item.getPhone());
        params.put("addr", item.getAddr());
        params.put("zip_code", item.getZipCode());
        params.put("is_default", item.isIsDefault());

        RealMallClient.getInstance().post(Constants.ADDRESS_UPDATE, RealMallClient.getTokenParams(params), new TokenCallback<BaseBean>(this) {
            @Override
            public void ok(BaseBean response) {
                if (response.getStatus() == 1)
                    requestList();
                else
                    fail(response.getMessage());
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show(errorMessage);
                requestList();
            }
        });
    }

    private void deleteAddress(Address item) {
        RequestParams params = new RequestParams();
        params.put("id", item.getId());
        RealMallClient.getInstance().post(Constants.ADDRESS_DELETE, RealMallClient.getTokenParams(params), new TokenCallback<BaseBean>(this) {
            @Override
            public void ok(BaseBean response) {
                if (response.getStatus() == 1)
                    requestList();
                else
                    fail(response.getMessage());
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show(errorMessage);
                requestList();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestList();
    }

    private class AddressUpdateListener implements AddressAdapter.UpDateListener {

        @Override
        public void setDefault(Address item) {
            changeDefault(item);
        }

        @Override
        public void delete(Address item) {
            deleteAddress(item);
        }
    }
}
