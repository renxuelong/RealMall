package com.renxl.realmall.feature.orders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;
import com.renxl.realmall.feature.wares_detail.WareDetailActivity;
import com.renxl.realmall.utils.Log;
import com.renxl.realmall.utils.Toast;
import com.squareup.picasso.Picasso;
import com.w4lle.library.NineGridAdapter;
import com.w4lle.library.NineGridlayout;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/21 15:13.
 */

class OrderAdapter extends BaseAdapter<CompleteOrder> {

    OrderAdapter(List<CompleteOrder> datas, Context context) {
        super(datas, context, R.layout.item_order);
    }

    @Override
    public void covert(BaseViewHolder holder, CompleteOrder item) {
        String number = "订单号：" + item.getOrderNum();
        holder.getTextView(R.id.tv_order_number).setText(number);

        TextView tvStatus = holder.getTextView(R.id.tv_order_status);
        switch (item.getStatus()) {
            case 0:
                tvStatus.setText("待支付");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
                break;
            case -2:
                tvStatus.setText("支付失败");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                break;
            case 1:
                tvStatus.setText("支付成功");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.springgreen));
                break;
        }

        NineAdapter adapter = new NineAdapter(mContext, item.getItems());
        NineGridlayout nineGridlayout = holder.getView(R.id.grid_order_wares);
        int imgWidth = mContext.getResources().getDisplayMetrics().widthPixels / 3 - 5;

        nineGridlayout.setAdapter(adapter);

        String price = "应付金额：¥ " + item.getAmount();
        holder.getTextView(R.id.tv_order_price).setText(price);

        holder.getTextView(R.id.tv_order_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.show("评论晒单");
            }
        });
        holder.getTextView(R.id.tv_order_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.show("再次购买");
            }
        });
    }

    private class NineAdapter extends NineGridAdapter {

        NineAdapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public int getCount() {
            return (list == null) ? 0 : list.size();
        }

        @Override
        public String getUrl(int position) {

            Log.i(getItem(position).toString());

            return getItem(position) == null ? null : ((CompleteOrder.OrderWare) getItem(position)).getWares().getImgUrl();
        }

        @Override
        public Object getItem(int position) {
            return (list == null) ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int i, View view) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setBackgroundColor(Color.parseColor("#f5f5f5"));
            Picasso.with(context).load(getUrl(i)).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(iv);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("URL   " + getUrl(i));
                    Intent intent = new Intent(mContext, WareDetailActivity.class);
                    intent.putExtra(WareDetailActivity.EXTRA_WARES, ((CompleteOrder.OrderWare) getItem(i)).getWares());
                    mContext.startActivity(intent);
                }
            });

            return iv;
        }
    }
}
