package com.wcl.addressbook;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
    private Context mContext;

    public AddressAdapter(Context context, int layoutResId, @Nullable List<AddressBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        int layoutPosition = helper.getLayoutPosition();

    }
}
