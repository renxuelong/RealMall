package com.renxl.realmall.feature.orders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lljjcoder.citypickerview.widget.CityPickerView;
import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.base.BaseBean;
import com.renxl.realmall.feature.sign_in.LoginActivity;
import com.renxl.realmall.feature.sign_in.User;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.renxl.realmall.http.BaseClient;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.http.TokenCallback;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renxl
 * On 2017/5/20 10:00.
 */

public class AddressEditActivity extends BaseActivity {

    public static final String ADDRESS = "address";

    @BindView(R.id.toolbar_address_list)
    ToolBar mToolbarAddressList;
    @BindView(R.id.et_edit_address_recipient)
    EditText mEtEditAddressRecipient;
    @BindView(R.id.et_address_edit_phone)
    EditText mEtAddressEditPhone;
    @BindView(R.id.tv_edit_address_address)
    TextView mTvEditAddressAddress;
    @BindView(R.id.et_edit_address_detail)
    EditText mEtEditAddressDetail;

    private String zip_code;
    private boolean isEdit, isDefault;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);

        initToolbar();
        initAddress();
    }

    private void initAddress() {
        Intent intent = getIntent();
        Address address = intent.getParcelableExtra(ADDRESS);
        if (address == null) return;
        mEtEditAddressRecipient.setText(address.getConsignee());
        mEtAddressEditPhone.setText(address.getPhone());
        id = address.getId();
        isEdit = true;
        isDefault = address.isIsDefault();
        mToolbarAddressList.setTitleText("修改地址");
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
                saveAddress();
            }
        });
    }

    private void saveAddress() {
        String recipient = mEtEditAddressRecipient.getText().toString().trim();
        String phone = mEtAddressEditPhone.getText().toString().trim();
        String address = mTvEditAddressAddress.getText().toString() + " " + mEtEditAddressDetail.getText().toString();

        if (!checkMessage(recipient, phone, address)) return;

        User user = UserLocalData.getUser(this);
        if (user == null) {
            Toast.show("请先登录");
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        RequestParams params = new RequestParams();
        if (isEdit) {
            params.put("id", id);
            params.put("is_default", isDefault);
        } else
            params.put("user_id", user.getId());
        params.put("consignee", recipient);
        params.put("phone", phone);
        params.put("addr", address);
        params.put("zip_code", zip_code);

        String url = isEdit ? Constants.ADDRESS_UPDATE : Constants.ADDRESS_ADD;

        RealMallClient.getInstance().post(url, BaseClient.getTokenParams(params), new TokenCallback<BaseBean>(this) {
            @Override
            public void ok(BaseBean response) {
                if (response == null) {
                    fail("");
                    return;
                }

                if (response.getStatus() == 1) {
                    Toast.show("提交成功");
                    setResult(RESULT_OK);
                    finish();
                } else fail(response.getMessage());
            }

            @Override
            public void fail(String errorMessage) {
                super.fail(errorMessage);
                Toast.show("提交失败" + errorMessage);
            }
        });
    }

    private boolean checkMessage(String recipient, String phone, String address) {
        if (TextUtils.isEmpty(recipient)) {
            Toast.show("请输入联系人名称");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.show("请输入联系电话");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.show("请输入地址信息");
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_edit_address_address)
    public void onViewClicked() {
        showPickerView();
    }

    private void showPickerView() {
        CityPickerView cityPickerView = new CityPickerView(this);
        cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县
                String district = citySelected[2];
                //邮编
                zip_code = citySelected[3];
                mTvEditAddressAddress.setText(province + city + district);
            }
        });
        cityPickerView.show();
    }
}
