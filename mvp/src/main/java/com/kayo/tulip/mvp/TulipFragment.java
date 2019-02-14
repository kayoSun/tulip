package com.kayo.tulip.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * KayoSun
 * 2019-02-14
 * 20:11
 * ----------
 */
public abstract class TulipFragment<P extends TulipPresenter> extends Fragment implements TulipView, TulipTip {

    protected View rootView;
    protected Toast mToast;
    private P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = bindView();
        if (view == null) {
            view = inflater.inflate(inflateView(), container, false);
        }
        rootView = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onCreateInit();
    }

    private void onCreateInit() {
        initView();
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();
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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

}
