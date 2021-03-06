package com.choicemmed.ichoice.initalization.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.PermissionUtil;
import com.choicemmed.common.ToastUtils;
import com.choicemmed.common.XPermissionUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.fragment.HealthCheckFragment;
import com.choicemmed.ichoice.healthreport.fragment.HealthReportFragment;
import com.choicemmed.ichoice.profile.fragment.ProfileFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;

/**
 * Created by gaofang on 2019/1/15.
 * 主界面
 */

public class MainActivity extends BaseActivty implements BottomNavigationBar.OnTabSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "MainActivity";
    @BindView(R.id.fragment_result)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_bar)
    BottomNavigationBar bottomBar;

    private HealthCheckFragment healthCheckFragment;
    private HealthReportFragment healthReportFragment;
    private ProfileFragment profileFragment;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_CALENDAR};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int contentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtil.PERMISSION_MULTIPLE:
                PermissionUtil.onRequestMorePermissionsResult(this, PERMISSIONS, new PermissionUtil.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        LogUtils.d(TAG, "已有权限");
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        LogUtils.d(TAG, "应用拒绝了此权限，部分功能使用将会受限");
//                        ToastUtils.showShort(MainActivity.this, "");
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        LogUtils.d(TAG, "应用拒绝了此权限，以后不再询问");
//                        ToastUtils.showShort(MainActivity.this, "应用拒绝了此权限，以后不再询问");
                        showToAppSettingDialog();
                    }
                });
        }
    }

    /**
     * 显示前往应用设置Dialog
     */
    private void showToAppSettingDialog() {
        new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("需要权限")
                .setMessage("需要允许相关权限才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PermissionUtil.toAppSetting(MainActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    /**
     * api 23以上需要权限申请
     */
    private void requestPermissions() {
        PermissionUtil.checkAndRequestMorePermissions(this, PERMISSIONS, PermissionUtil.PERMISSION_MULTIPLE,
                new PermissionUtil.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        LogUtils.d(TAG, "权限已被授予");
                    }
                });
    }
    @Override
    protected void initialize() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }

        setTopTitle(getResources().getString(R.string.app_name), true);
        setLeftBtn(false, 0, null);
        bottomBar.setTabSelectedListener(this);
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomBar.addItem(new BottomNavigationItem(ContextCompat.getDrawable(this, R.mipmap.ic_checked_health_check),
                getString(R.string.health_check)).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_health_check)))
                .addItem(new BottomNavigationItem(ContextCompat.getDrawable(this, R.mipmap.ic_checked_health_report),
                        getString(R.string.health_report)).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_health_report)))
                .addItem(new BottomNavigationItem(ContextCompat.getDrawable(this, R.mipmap.ic_checked_profile),
                        getString(R.string.profile)).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_profile)))
                .setFirstSelectedPosition(0)
                .initialise();
        setDefaultFragment();
//        File dbFile = getDatabasePath("ichoice-db");
//        copyDb2Sd("ichoice-db",dbFile);
    }

    /**
     * 从数据库拷贝文档到sd卡，仅用于测试用
     *
     * @param dbName
     * @param dbFile
     * @return
     */
    private boolean copyDb2Sd(String dbName, File dbFile) {
        //--- 从数据库拷贝至sd卡
        if (!dbFile.exists()) {
            return true;
        } else {
            FileInputStream fis = null;
            File outputFile = null;
            FileOutputStream fos = null;
            //sd卡文件
            File outputFileDir = new File(Environment.getExternalStorageDirectory() + File.separator + "ichoice_db");
            if (!outputFileDir.exists()) {
                outputFileDir.mkdirs();
            } else {
                outputFile = new File(outputFileDir, dbName);
                if (!outputFile.exists()) {
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        LogUtils.d(TAG, "新建数据库目标文件失败");
                        e.printStackTrace();
                        return true;
                    }
                }
            }
            try {
                fis = new FileInputStream(dbFile);
                fos = new FileOutputStream(outputFile);

                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }



    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                setTopTitle(getResources().getString(R.string.app_name), true);
                if (healthCheckFragment == null) {
                    healthCheckFragment = (HealthCheckFragment) HealthCheckFragment.getInstance();
                }
                transaction.replace(R.id.fragment_result, healthCheckFragment);
                break;
            case 1:
                setTopTitle(getResources().getString(R.string.app_name), true);
                if (healthReportFragment == null){
                    healthReportFragment = (HealthReportFragment) HealthReportFragment.getInstance();
                }
                transaction.replace(R.id.fragment_result, healthReportFragment);
                break;
            case 2:
                setTopTitle(getResources().getString(R.string.profile), true);
                if (profileFragment == null){
                    profileFragment = (ProfileFragment) ProfileFragment.getInstance();
                }
                transaction.replace(R.id.fragment_result, profileFragment);
                break;
                default:
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {


    }

    @Override
    public void onTabReselected(int position) {
    }
    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        healthCheckFragment = (HealthCheckFragment) HealthCheckFragment.getInstance();
        transaction.replace(R.id.fragment_result, healthCheckFragment);
        transaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
