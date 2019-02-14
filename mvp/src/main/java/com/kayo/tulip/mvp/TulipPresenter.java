package com.kayo.tulip.mvp;

/**
 * KayoSun
 * 2019-02-14
 * 16:49
 * ----------
 */
public abstract class TulipPresenter<V extends TulipView> {

    protected V view;
    protected TulipTip mTip;
    protected TulipProgress mProgress;

    protected boolean isAttached;

    private ViewAttachListener viewAttachListener;

    public TulipPresenter() {
    }

    public TulipPresenter(ViewAttachListener viewAttachListener) {
        this.viewAttachListener = viewAttachListener;
    }

    /**
     * 绑定view
     *
     * @param view
     * @return
     */
    public TulipPresenter attachView(V view) {
        this.view = view;
        isAttached = true;
        if (view instanceof TulipTip){
            mTip = (TulipTip) view;
        }
        if (view instanceof TulipProgress){
            mProgress = (TulipProgress) view;
        }
        if (viewAttachListener != null) {
            viewAttachListener.onAttached();
        }
        return this;
    }

    /**
     * 取消绑定view
     *
     * @return
     */
    public TulipPresenter detachView() {
        isAttached = false;
        view = null;
        if (viewAttachListener != null) {
            viewAttachListener.onDetached();
        }
        return this;
    }

    public void onDestroy() {
        this.detachView();
    }


    public interface ViewAttachListener {
        void onAttached();

        void onDetached();
    }
}
