package com.renxl.realmall.feature.orders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.renxl.realmall.R;
import com.renxl.realmall.base.BaseAdapter;
import com.renxl.realmall.base.BaseViewHolder;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/20 10:09.
 */

class AddressAdapter extends BaseAdapter<Address> {

    private UpDateListener mUpDateListener;

    AddressAdapter(List<Address> datas, Context context,UpDateListener listener) {
        super(datas, context, R.layout.item_address);
        mUpDateListener = listener;
    }

    @Override
    public void covert(BaseViewHolder holder, final Address item) {
        setText(holder.getTextView(R.id.tv_address_name), item.getConsignee());
        setText(holder.getTextView(R.id.tv_address_phone), item.getPhone());
        setText(holder.getTextView(R.id.tv_address_item_address), item.getAddr());

        final RadioButton radioButton = holder.getView(R.id.radio_address_default);
        if (item.isIsDefault()) {
            radioButton.setChecked(true);
            radioButton.setClickable(false);
            holder.getTextView(R.id.tv_status_default).setText("默认地址");
        } else {
            radioButton.setChecked(false);
            radioButton.setClickable(true);
            holder.getTextView(R.id.tv_status_default).setText("设为默认");
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked || mUpDateListener != null) {
                        item.setIsDefault(true);
                        mUpDateListener.setDefault(item);
                    }
                }
            });
        }

        holder.getTextView(R.id.tv_address_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAddress(item);
            }
        });
        holder.getTextView(R.id.tv_address_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlert(item);
            }
        });
    }

    private void showDeleteAlert(final Address item) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle("删除地址")
                .setMessage("确认删除该条地址信息吗？ 该操作不可恢复")
                .setNegativeButton("取消", null)
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestDelete(item);
                    }
                })
                .create();
        alertDialog.show();
    }

    private void requestDelete(Address item) {
        if (item != null && mUpDateListener != null)
            mUpDateListener.delete(item);
    }

    private void editAddress(Address item) {
        Intent intent = new Intent(mContext, AddressEditActivity.class);
        intent.putExtra(AddressEditActivity.ADDRESS, item);
        Activity activity = (Activity) mContext;
        activity.startActivityForResult(intent, AddressListActivity.REQUEST_CODE);
    }

    interface UpDateListener {
        void setDefault(Address item);

        void delete(Address item);
    }
}
