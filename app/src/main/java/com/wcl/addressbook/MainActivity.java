package com.wcl.addressbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private EditText mEditName;
    private EditText mEditPhone;
    private RecyclerView mRecyclerView;
    private AddressAdapter mAdapter;
    private EditText mDialogEditName;
    private EditText mDialogEditComnapy;
    private EditText mDialogEditPhone;
    private EditText mDialogEditEmail;
    private MaterialDialog mInsertDialog;
    private MaterialDialog mModifiDialog;
    private MaterialDialog mDeleteDialog;
    private List<AddressBean> mAllAddressBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycleview);
        mEditName = findViewById(R.id.edit_name);
        mEditPhone = findViewById(R.id.edit_phone);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnSearchClear = findViewById(R.id.btn_search_clear);
        btnSearch.setOnClickListener(this);
        btnSearchClear.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAllAddressBeans = DBManager.getAllRccord();
        mAdapter = new AddressAdapter(this, R.layout.item, mAllAddressBeans);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                //查询
                String name = mEditName.getText().toString().trim();
                String phone = mEditPhone.getText().toString().trim();
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone)) {
                    ToastUtil.showMessage("查询条件不能为空");
                } else if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
                    ToastUtil.showMessage("抱歉,不支持双重条件查询!");
                } else if (!TextUtils.isEmpty(name)) {
                    mAllAddressBeans = DBManager.queryByName(name);
                    mAdapter.setNewData(mAllAddressBeans);
                    mAdapter.notifyDataSetChanged();
                } else if (!TextUtils.isEmpty(phone)) {
                    mAllAddressBeans = DBManager.queryByPhoneNum(phone);
                    mAdapter.setNewData(mAllAddressBeans);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_insert:
                //新建
                showInsertDialog();
                break;
            case R.id.btn_search_clear:
                //清除查询
                mEditName.setText("");
                mEditPhone.setText("");
                mAllAddressBeans = DBManager.getAllRccord();
                mAdapter.setNewData(mAllAddressBeans);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void showInsertDialog() {
        mInsertDialog = new MaterialDialog.Builder(this)
                .title("人脸角度限制")
                .customView(R.layout.address_layout, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.no)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                        String name = mDialogEditName.getText().toString().trim();
                        String comnapy = mDialogEditComnapy.getText().toString().trim();
                        String phone = mDialogEditPhone.getText().toString().trim();
                        String email = mDialogEditEmail.getText().toString().trim();
                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(comnapy) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
                            ToastUtil.showMessage("输入框全部都不能为空!");
                            return;
                        }
                        AddressBean addressBean = DBManager.inserStorage2DB(name, comnapy, phone, email);
                        mAllAddressBeans.add(addressBean);
                        mAdapter.setNewData(mAllAddressBeans);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        mDialogEditName = (EditText) mInsertDialog.findViewById(R.id.edit_name_dialog);
        mDialogEditComnapy = (EditText) mInsertDialog.findViewById(R.id.edit_comnapy_dialog);
        mDialogEditPhone = (EditText) mInsertDialog.findViewById(R.id.edit_phone_dialog);
        mDialogEditEmail = (EditText) mInsertDialog.findViewById(R.id.edit_email_dialog);
        mDialogEditName.setText("");
        mDialogEditComnapy.setText("");
        mDialogEditPhone.setText("");
        mDialogEditEmail.setText("");
        mInsertDialog.show();
    }

    private void showModificationDialog(final AddressBean bean) {
        mModifiDialog = new MaterialDialog.Builder(this)
                .title("人脸角度限制")
                .customView(R.layout.address_layout, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.no)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                        String name = mDialogEditName.getText().toString().trim();
                        String comnapy = mDialogEditComnapy.getText().toString().trim();
                        String phone = mDialogEditPhone.getText().toString().trim();
                        String email = mDialogEditEmail.getText().toString().trim();
                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(comnapy) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
                            ToastUtil.showMessage("输入框全部都不能为空!");
                            return;
                        }
                        bean.setName(name);
                        bean.setCompany(comnapy);
                        bean.setPhone_num(phone);
                        bean.setEmail(email);
                        DBManager.inserStorage2DB(bean);
                        mAdapter.setNewData(mAllAddressBeans);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        mDialogEditName = (EditText) mModifiDialog.findViewById(R.id.edit_name_dialog);
        mDialogEditComnapy = (EditText) mModifiDialog.findViewById(R.id.edit_comnapy_dialog);
        mDialogEditPhone = (EditText) mModifiDialog.findViewById(R.id.edit_phone_dialog);
        mDialogEditEmail = (EditText) mModifiDialog.findViewById(R.id.edit_email_dialog);
        mDialogEditName.setText(bean.getName());
        mDialogEditComnapy.setText(bean.getCompany());
        mDialogEditPhone.setText(bean.getPhone_num());
        mDialogEditEmail.setText(bean.getEmail());
        mModifiDialog.show();
    }

    private void showDeleteDialog(final AddressBean bean) {
        mDeleteDialog = new MaterialDialog.Builder(this)
                .title("删除警告")
                .content("  点击确认按钮,将会删除 " + bean.getName() + " 的数据,请确认是否删除?")
                .positiveText(R.string.ok)
                .negativeText(R.string.no)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                        ToastUtil.showMessage("已删除用户: " + bean.getName());
                        DBManager.delete(bean);
                        mAllAddressBeans.remove(bean);
                        mAdapter.setNewData(mAllAddressBeans);
                        mAdapter.notifyDataSetChanged();
                        mDeleteDialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mDeleteDialog.dismiss();
                    }
                })
                .build();
        mDeleteDialog.show();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.btn_modification:
                if (mAllAddressBeans != null && position < mAllAddressBeans.size()) {
                    showModificationDialog(mAllAddressBeans.get(position));
                }
                break;
            case R.id.btn_delete:
                if (mAllAddressBeans != null && position < mAllAddressBeans.size()) {
                    showDeleteDialog(mAllAddressBeans.get(position));
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDeleteDialog != null && mDeleteDialog.isShowing()) {
            mDeleteDialog.dismiss();
        }
        if (mInsertDialog != null && mInsertDialog.isShowing()) {
            mInsertDialog.dismiss();
        }
        if (mModifiDialog != null && mModifiDialog.isShowing()) {
            mModifiDialog.dismiss();
        }
    }
}
