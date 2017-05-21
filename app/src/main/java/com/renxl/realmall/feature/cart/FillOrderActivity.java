package com.renxl.realmall.feature.cart;

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

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseActivity;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.wares_detail.WareDetailActivity;
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

    private static final String TAG_AL = "支付宝";
    private static final String TAG_WX = "微信支付";
    private static final String TAG_BD = "百度钱包";

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
        for (Map.Entry<String, RadioButton> entry : tags.entrySet()) {
            boolean isCheck = entry.getValue().isChecked();
            if (entry.getKey().equals(tag))
                entry.getValue().setChecked(!isCheck);
            else
                entry.getValue().setChecked(false);
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

    @OnClick(R.id.btn_fill_submit)
    public void onSubmitClicked(View view) {
        Toast.show("提交订单");
    }

    @OnClick({R.id.layout_ali, R.id.layout_wx, R.id.layout_bd})
    public void onViewClicked(View view) {
        resetRadioButton((String) view.getTag());
    }
}
