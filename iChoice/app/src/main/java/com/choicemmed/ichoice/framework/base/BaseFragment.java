package com.choicemmed.ichoice.framework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gaofang on 2019/1/14.
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;
    private View mView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if(contentViewID()!=0){
//            if (mView !=null){
//                ViewGroup parent = (ViewGroup) mView.getParent();
//                if (parent != null) {
//                    parent.removeView(mView);
//                }
//                return mView;
//            }
            mView = inflater.inflate(contentViewID(),container,false);
            return mView;
        }else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder= ButterKnife.bind(this,view);
        initialize();
    }

    /**
     * Activity 关联布局文件
     *
     * @return
     */
    protected abstract int contentViewID();

    /**
     * 对象初始化，方法调用
     */
    protected abstract void initialize();

    /**
     * View点击事件
     */
//    public abstract void widgetClick(View v);
//    @Override
//    public void onClick(View v) {
//        if (fastClick())
//            widgetClick(v);
//    }

    /**
     * 防止快速点击
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 页面跳转,跳转后关闭界面
     *
     * @param clz
     */
    public void startActivityFinish(Class<?> clz) {
        startActivity(clz, null);
        getActivity().finish();
    }


    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();//视图销毁时必须解绑
    }
}
