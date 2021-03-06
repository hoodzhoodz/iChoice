package com.choicemmed.ichoice.initalization.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.choicemmed.common.LogUtils;
import com.choicemmed.common.NetUtils;
import com.choicemmed.common.ToastUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.initalization.config.ApiConfig;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.profile.presenter.impl.AvatarPresenterImpl;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lxj.xpopup.XPopup;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;


import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaofang on 2019/1/22.
 * 个人信息设置 - 头像
 */

public class AvatarActivity extends BaseActivty implements com.choicemmed.ichoice.framework.view.IBaseView {
    private static final int RESULT_CODE_STARTCAMERA = 0x01;
    @BindView(R.id.img_avatar)
    SimpleDraweeView imgAvatar;
    @BindView(R.id.btn_continue)
    Button btnContinue;

    private int maxImgCount = 1;               //允许选择图片最大数
    private ArrayList<ImageItem> images = null;

    private File newFile;
    private String filePath;

    private AvatarPresenterImpl avatarPresenter;

    @Override
    protected int contentViewID() {
        return R.layout.activity_add_avatar;
    }

    @Override
    protected void initialize() {
        com.choicemmed.ichoice.framework.utils.Utils.initImagePicker(maxImgCount);
        avatarPresenter = new AvatarPresenterImpl(this,this);
        setTopTitle(getResources().getString(R.string.new_user),true);

        setLeftBtn(true, R.mipmap.ic_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFinishTip();
            }
        });


    }

    private void ShowFinishTip() {
        com.choicemmed.ichoice.framework.widget.MyCenterPopupView centerPopupView = new com.choicemmed.ichoice.framework.widget.MyCenterPopupView(mContext);
        XPopup.get(mContext).asCustom(centerPopupView).show();
        centerPopupView.setDoublePopup("", getResources().getString(R.string.avatar_popup_tip)
                , getResources().getString(R.string.no)
                , getResources().getString(R.string.yes), new com.choicemmed.ichoice.framework.widget.MyCenterPopupView.PositiveClickListener() {
                    @Override
                    public void onPositiveClick() {

                    }
                }, new com.choicemmed.ichoice.framework.widget.MyCenterPopupView.NegativeClickListener() {
                    @Override
                    public void onNegativeClick() {
                        finish();
                    }
                });
    }


    @OnClick({R.id.img_avatar, R.id.btn_continue})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_avatar:
                if (NetUtils.isConnected(this)) {

                    XPopup.get(mContext).asCustom(new com.choicemmed.ichoice.framework.widget.ChooseAvatarPopupView(mContext, new com.choicemmed.ichoice.framework.widget.ChooseAvatarPopupView.ChooseCallback() {
                        @Override
                        public void onCamera() {
                            autoCameraPermission();

                        }

                        @Override
                        public void onAlbum() {
                            Intent intent1 = new Intent(AvatarActivity.this, ImageGridActivity.class);
                            startActivityForResult(intent1, ApiConfig.REQUEST_CODE_SELECT);

                        }
                    })).show();
                } else {
                    ToastUtils.showCustom(this, getString(R.string.not_net));
                }

                break;
            case R.id.btn_continue:
                com.choicemmed.ichoice.framework.utils.ActivityUtils.addActivity(this);
                startActivity(UserNameActivity.class);
                break;
        }
    }

    private void autoCameraPermission() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)){
            onCamera();
        }else {
            //提示用户开户权限   拍照和读写sd卡权限
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, perms, RESULT_CODE_STARTCAMERA);
        }
    }

    private void onCamera() {
        Intent intent = new Intent(AvatarActivity.this, ImageGridActivity.class);
        /**是否是直接打开相机*/
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
        startActivityForResult(intent, ApiConfig.REQUEST_CODE_SELECT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case RESULT_CODE_STARTCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    onCamera();
                }else {
                    ToastUtils.showCustom(this,getString(R.string.permission_fail));
                }
                break;
                default:

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            int compressSize=50;
            /**添加图片返回*/
            if (data != null && requestCode == ApiConfig.REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    filePath=images.get(0).path;
                    File file =new File(filePath);
                    String size= com.choicemmed.ichoice.framework.utils.Utils.getReadableFileSize(file.length());
                    if (size.substring(size.length()-2,size.length()).contains("MB")){
                        newFile= com.choicemmed.ichoice.framework.utils.Utils.getCompressionImage(this,file,"head_compression.jpg", compressSize);
                    }else if (size.substring(size.length()-2,size.length()).contains("KB")){
                        String leng=size.substring(0,size.indexOf(" "));
                        double newSize=Double.parseDouble(leng);
                        if (newSize>50.0){
                            LogUtils.i("gxz","图片大小"+ com.choicemmed.ichoice.framework.utils.Utils.getReadableFileSize(file.length()));
                            newFile= com.choicemmed.ichoice.framework.utils.Utils.getCompressionImage(this,file,"head_compression.jpg",80);
                        }
                    }
                    avatarPresenter.sendAvatarRequest(IchoiceApplication.getAppData().user.getToken(), newFile);
                }
            }
        }
    }

    @Override
    public void onSuccess() {
        com.choicemmed.ichoice.framework.utils.Utils.setRoundImage(this, imgAvatar, IchoiceApplication.getAppData().user.getHeadImgUrl());
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(this,msg);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ShowFinishTip();
        }
        return true;
    }
}
