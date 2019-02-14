package com.kayo.tulip.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.kayo.tulip.mvp.progress.ProgressView;

/**
 * KayoSun
 * 2019-02-14
 * 16:34
 * ----------
 */
public abstract class TulipActivity<P extends TulipPresenter>
        extends AppCompatActivity implements TulipView, TulipTip, TulipProgress {
    public String TAG = "TulipActivity";
    protected Toast mToast;
    private ProgressView mProgress;
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeView();
        super.onCreate(savedInstanceState);
        onCreateInit();
    }

    private void onCreateInit() {
        View view = bindView();
        if (view == null) {
            setContentView(inflateView());
        } else {
            setContentView(view);
        }
        afterView();
        initView();
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();

    }

    /**
     * setContentView() 之后
     */
    @CallSuper
    protected void afterView() {
        mProgress = new ProgressView(this);
        mProgress.bindView(this);
        mProgress.hidden();
    }

    /**
     * setContentView() 之前
     */
    protected void beforeView() {
    }

    /**
     * 绑定 view布局
     *
     * @return
     */
    @LayoutRes
    protected int inflateView() {
        return 0;
    }

    /**
     * 绑定 view对象
     *
     * @return
     */
    protected View bindView() {
        return null;
    }

    /**
     * 初始化视图
     * 此时 presenter 还未初始化
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    protected abstract P initPresenter();

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showProgress() {
        mProgress.show();
    }

    @Override
    public void hideProgress() {
        mProgress.hidden();
    }
}
