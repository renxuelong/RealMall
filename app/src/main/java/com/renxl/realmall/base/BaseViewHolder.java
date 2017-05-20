package com.renxl.realmall.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.renxl.realmall.widget.AddSubView;

/**
 * Created by renxl
 * On 2017/4/7 8:37.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public BaseViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);
        views = new SparseArray<>();
        if (onItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getLayoutPosition());
                }
            });
        }
    }

    public TextView getTextView(int id) {
        return getView(id);
    }

    public EditText getEditText(int id) {
        return getView(id);
    }

    public ImageView getImageView(int id) {
        return getView(id);
    }

    public Button getButton(int id) {
        return getView(id);
    }

    public SimpleDraweeView getSimpleDraweeView(int id) {
        return getView(id);
    }

    public CheckBox getCheckBox(int id) {
        return getView(id);
    }

    public AddSubView getAddSubView(int id) {
        return getView(id);
    }

    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
