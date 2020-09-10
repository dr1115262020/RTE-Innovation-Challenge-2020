package com.xiaoyang.poweroperation.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xiaoyang.poweroperation.R;
import com.xiaoyang.poweroperation.data.entity.Message;
import com.xiaoyang.poweroperation.di.component.DaggerMessageComponent;
import com.xiaoyang.poweroperation.mine.adapter.MessageAdapter;
import com.xiaoyang.poweroperation.mine.contract.MessageContract;
import com.xiaoyang.poweroperation.mine.presenter.MessagePresenter;
import com.xylib.base.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Package:
 * ClassName:      MessageActivity
 * Author:         xiaoyangyan
 * CreateDate:     2020/8/12 12:31
 * Description:   消息中心
 */
public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.rlv_content)
    RecyclerView rlvContent;
    @BindView(R.id.refreshLayoutMine)
    SmartRefreshLayout refreshLayoutMine;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.tv_toolbar_title_tv)
    TextView tvToolbarTitleTv;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    private List<Message> mDataList;
    private MessageAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvToolbarTitleTv.setText("消息中心");
        initMessageRecycleView();
        getMessageList();
    }

    private void initMessageRecycleView() {
        mDataList = new ArrayList<>();
        mAdapter = new MessageAdapter(R.layout.item_message_view, mDataList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {


        });
        rlvContent.setAdapter(mAdapter);
        rlvContent.setLayoutManager(new LinearLayoutManager(Utils.getApp()));
    }

    private void getMessageList() {
        BmobQuery<Message> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> object, BmobException e) {
                if (e == null) {
                    Log.v("yxy", "查询成功" + object.size());
                    mDataList = object;
                    mAdapter.replaceData(object);
                } else {
                    Log.e("BMOB", e.toString());

                }
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}