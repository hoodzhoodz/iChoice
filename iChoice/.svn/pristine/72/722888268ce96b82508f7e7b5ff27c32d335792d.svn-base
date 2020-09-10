package com.choicemmed.ichoice.framework.presenter;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/1 11:25
 * 修改人：114100
 * 修改时间：2019/4/1 11:25
 * 修改备注：
 */
public class BasePresenterImpl<T> implements com.choicemmed.ichoice.framework.presenter.IBasePresenter {
    public T mView;

    protected void attachView(T mView) {
        this.mView = mView;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
