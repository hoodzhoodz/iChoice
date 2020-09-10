package com.choicemmed.ichoice.framework.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * Created by gaofang on 2019/1/18.
 * 选择头像弹窗
 */

public class ChooseAvatarPopupView extends BottomPopupView  {
    ChooseCallback mCallback;
    public ChooseAvatarPopupView(@NonNull Context context,ChooseCallback callback) {
        super(context);
        this.mCallback = callback;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_choose_avatar;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        TextView tvCancel = findViewById(R.id.tv_cancel);
        TextView tvCamera = findViewById(R.id.tv_camera);
        TextView tvAlbum = findViewById(R.id.tv_album);
        tvCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onCamera();
                dismiss();
            }
        });
        tvAlbum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onAlbum();
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public interface ChooseCallback{
        /**
         * 点击拍照事件
         */
        void onCamera();

        /**
         * 点击相册事件
         */
        void onAlbum();

    }

}
