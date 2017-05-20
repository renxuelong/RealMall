package com.renxl.realmall.feature.orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pingplusplus.android.Pingpp;
import com.renxl.realmall.R;
import com.renxl.realmall.application.Constants;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.cart.CartBean;
import com.renxl.realmall.feature.cart.CartProvider;
import com.renxl.realmall.feature.sign_in.LoginActivity;
import com.renxl.realmall.feature.sign_in.User;
import com.renxl.realmall.feature.sign_in.UserLocalData;
import com.renxl.realmall.feature.wares_detail.WareDetailActivity;
import com.renxl.realmall.http.RealMallClient;
import com.renxl.realmall.http.RequestParams;
import com.renxl.realmall.http.TokenCallback;
import com.renxl.realmall.utils.JsonUtils;
import com.renxl.realmall.utils.Toast;
import com.renxl.realmall.widget.ToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renxl
 * On 2017/5/17 17:46.
 */

public class FillOrderActivity extends BaseActivity {

    private static final int REQUEST_CODE = 10001;

    private static final String TAG_AL = "alipay";
    private static final String TAG_WX = "wx";
    private static final String TAG_BD = "bfb";

    @BindView(R.id.tv_fill_order_phone)
    TextView tvFillOrderPhone;
    @BindView(R.id.tv_fill_order_address)
    TextView tvFillOrderAddress;
    @BindView(R.id.img_fill_order_choose_address)
    ImageView imgFillOrderChooseAddress;
    @BindView(R.id.recycler_fill_order_wares)
    RecyclerView recyclerFillOrderWares;
    @BindView(R.id.radioBtn_ali)
    RadioButton radioBtnAli;
    @BindView(R.id.layout_ali)
    LinearLayout layoutAli;
    @BindView(R.id.radioBtn_wx)
    RadioButton radioBtnWx;
    @BindView(R.id.layout_wx)
    LinearLayout layoutWx;
    @BindView(R.id.radioBtn_bd)
    RadioButton radioBtnBd;
    @BindView(R.id.layout_bd)
    LinearLayout layoutBd;
    @BindView(R.id.toolbar_fill_order)
    ToolBar toolbarFillOrder;
    @BindView(R.id.tv_fill_order_price)
    TextView tvFillOrderPrice;
    @BindView(R.id.btn_fill_submit)
    Button btnFillSubmit;

    private Map<String, RadioButton> tags;
    private CartProvider mCartProvider;
    private WareAdapter adapter;
    private String payChannel = TAG_AL;
    private long addressId;
    private boolean mJsonCarts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_order);
        ButterKnife.bind(this);

        initToolBar();
        initRadioBtn();
        initWareList();
    }

    private void initToolBar() {
        toolbarFillOrder.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshWaresList();
    }

    private void refreshWaresList() {
        List<CartBean> wares = mCartProvider.getCheckedCarBeanList();
        if (wares == null || wares.size() <= 0) return;
        adapter.clear();
        adapter.setData(wares);

        String price = "应付款：¥ " + mCartProvider.totalPrice();
        tvFillOrderPrice.setText(price);
    }

    private void initWareList() {
        mCartProvider = CartProvider.getInstance();
        List<CartBean> wares = new ArrayList<>();
        adapter = new WareAdapter(wares, this);
        recyclerFillOrderWares.setAdapter(adapter);
        recyclerFillOrderWares.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(FillOrderActivity.this, WareDetailActivity.class);
                intent.putExtra(WareDetailActivity.EXTRA_WARES, adapter.getDatas().get(position));
                startActivity(intent);
            }
        });
    }

    private void initRadioBtn() {
        tags = new HashMap<>();
        tags.put(TAG_AL, radioBtnAli);
        tags.put(TAG_WX, radioBtnWx);
        tags.put(TAG_BD, radioBtnBd);
    }

    private void resetRadioButton(String tag) {
        payChannel = tag;
        for (Map.Entry<String, RadioButton> entry : tags.entrySet()) {
            if (entry.getKey().equals(tag))
                entry.getValue().setChecked(true);
            else
                entry.getValue().setChecked(false);
        }
    }

    @OnClick(R.id.layout_fill_order_address)
    public void onAddressLayoutClick(View view) {
        startActivityForResult(new Intent(this, AddressListActivity.class), REQUEST_CODE);
    }

    @OnClick({R.id.layout_ali, R.id.layout_wx, R.id.layout_bd})
    public void onViewClicked(View view) {
        resetRadioButton((String) view.getTag());
    }

    @OnClick(R.id.btn_fill_submit)
    public void onSubmitClicked(View view) {
        submitOrder();
    }

    private void submitOrder() {
        User user = UserLocalData.getUser(this);
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        final RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", user.getId() + "");
        requestParams.put("item_json", getJsonCarts());
        requestParams.put("amount", (int) mCartProvider.totalPrice());
        requestParams.put("addr_id", addressId);
        requestParams.put("pay_channel", payChannel);
        RealMallClient.getInstance().post(Constants.ORDER_CREATE, RealMallClient.getTokenParams(requestParams),
                new TokenCallback<Order<OrderCharge>>(this) {
                    @Override
                    public void ok(Order<OrderCharge> response) {
                        if (response == null || response.getStatus() != 1) {
                            fail("");
                            return;
                        }
                        mCartProvider.clearCheckedCart();
                        requestPay(response);
                    }

                    @Override
                    public void fail(String errorMessage) {
                        super.fail(errorMessage);
                        Toast.show("订单创建失败");
                    }
                });
    }

    private void requestPay(Order<OrderCharge> response) {
        Pingpp.createPayment(this, JsonUtils.toString(response.getData().getCharge()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            onSelectAddressResult(data);
        } else if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            onPayResult(data);
        }

    }

    private void onPayResult(Intent data) {
        String result = data.getExtras().getString("pay_result");
           /* 处理返回值
            * "success" - 支付成功
            * "fail"    - 支付失败
            * "cancel"  - 取消支付
            * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
            * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
            */
        String tag = "支付失败";
        boolean success = false;
        if (!TextUtils.isEmpty(result)) {
            switch (result) {
                case "success":
                    success = true;
                    tag = "支付成功";
                    break;
                case "fail":
                    tag = "支付失败";
                    break;
                case "cancel":
                    tag = "取消支付";
                    break;
                case "invalid":
                    tag = "支付插件未安装";
                    break;
                case "unknown":
                    tag = "APP 进程异常被杀死";
                    break;
            }
        }
        Intent intent = new Intent(this, PayResultActivity.class);
        intent.putExtra(PayResultActivity.SUCCESS, success);
        intent.putExtra(PayResultActivity.MESSAGE, tag);
        startActivity(intent);
        finish();
    }

    private void onSelectAddressResult(Intent data) {
        if (data != null) {
            Address address = data.getParcelableExtra(AddressEditActivity.ADDRESS);
            if (address != null) {
                tvFillOrderPhone.setText(address.getConsignee() + "  " + address.getPhone());
                tvFillOrderAddress.setText(address.getAddr());
                addressId = address.getId();
            }
        }
    }

    public String getJsonCarts() {
        List<OrderWare> lists = new ArrayList<>();
        for (CartBean cartBean : mCartProvider.getCheckedCarBeanList()) {
            OrderWare ware = new OrderWare();
            ware.setWare_id(cartBean.getId());
            ware.setMount(cartBean.getPrice());
            lists.add(ware);
        }
        return JsonUtils.toString(lists);
    }

    private class OrderWare {
        private long ware_id;
        private double mount;

        public long getWare_id() {
            return ware_id;
        }

        public void setWare_id(long ware_id) {
            this.ware_id = ware_id;
        }

        public double getMount() {
            return mount;
        }

        public void setMount(double mount) {
            this.mount = mount;
        }
    }

    private class WareAdapter extends BaseAdapter<CartBean> {

        WareAdapter(List<CartBean> datas, Context context) {
            super(datas, context, R.layout.item_fill_order_wares);
        }

        @Override
        public void covert(BaseViewHolder holder, CartBean item) {
            if (!TextUtils.isEmpty(item.getImgUrl()))
                holder.getSimpleDraweeView(R.id.sdv_item_fill_order).setImageURI(item.getImgUrl());
        }
    }
}
