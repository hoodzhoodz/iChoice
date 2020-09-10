package com.choicemmed.ichoice.healthcheck.activity.wristpulse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.http.Urls;
import com.choicemmed.ichoice.healthcheck.db.W314B4Operation;
import com.choicemmed.ichoice.healthcheck.db.W628Operation;
import com.github.barteksc.pdfviewer.PDFView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

import butterknife.BindView;
import pro.choicemmed.datalib.W314B4Data;
import pro.choicemmed.datalib.W628Data;

/**
*@anthor by jiangnan
*@Date on 2020/1/21.
*/
public class ReportWpoActivity extends BaseActivty  {
    public static final String TAG = "ReportWpoActivity";
    @BindView(R.id.pdfView)
    PDFView mPDFView;
    @BindView(R.id.text_report)
    TextView text_report;
    private static String target;
    private static int REQUEST_CODE_CONTACT = 101;
    private W314B4Data w314B4Data;
    private W628Data w628Data;
    private String type , uuid;
    OkGo okGo = OkGo.getInstance();

    @Override
    protected int contentViewID() {
        return R.layout.activity_report_wpo;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.report), true);
        setLeftBtnFinish();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        uuid = bundle.getString("UUID");
        type = bundle.getString("TYPE");
        initData();

    }

    private void initData() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (ReportWpoActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    ReportWpoActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    downloadReport();
                }
            }
        } else {
            downloadReport();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CONTACT) {
            downloadReport();
        }
    }

    private void downloadReport() {
        switch (type){
            case "W314":
                W314B4Operation w314B4Operation = new W314B4Operation(this);
                w314B4Data = w314B4Operation.queryByUserUuid(IchoiceApplication.getAppData().userProfileInfo.getUserId(), uuid);
                if(w314B4Data.getUpLoadFlag().equals("false")){
                    noReportTip(R.string.no_upload);
                    return;
                }
                W314downloadReport(w314B4Data);
                break;
            case "W628":
                W628Operation w628Operation = new W628Operation(this);
                w628Data = w628Operation.queryByUserUuid(IchoiceApplication.getAppData().userProfileInfo.getUserId(), uuid);
                LogUtils.d(TAG, w628Data.toString());
                if (w628Data.getUpLoadFlag().equals("false")){
                    noReportTip(R.string.no_upload);
                    return;
                }
                W628downloadReport(w628Data);
                break;
        }


    }

    private void W314downloadReport(W314B4Data record) {
        String accessTokenKey = record.getAccountKey();
        LogUtils.d(TAG, "accessTokenKey---->" + accessTokenKey);
        String linkId = record.getUuid();
        LogUtils.d(TAG, "linkId---->" + linkId);
        final File file = new File(Environment.getExternalStorageDirectory() + File.separator + "SleepReport");
        if (!file.exists()) {
            file.mkdirs();
        }
        target = Environment.getExternalStorageDirectory() + File.separator + "sleepReport" + File.separator  ;
        okGo.<File>get(Urls.SleepReport)
                .params("accessTokenKey", record.getAccountKey())
                .params("linkId", record.getUuid())
                .params("is628", 0)
                .execute(new FileCallback(target,"sleepReport.pdf" ) {
                    @Override
                    public void onSuccess(Response<File> response) {

                        LogUtils.d(TAG, "报告下载成功..."+ response.body());
                        try {
                            if(response.body().isFile()){
                                mPDFView.fromFile(response.body())
                                        .defaultPage(0)//默认展示第一页
                                        .load();
                            }else {
                                noReportTip(R.string.no_report);
                            }
                        } catch (Exception e) {
                            noReportTip(R.string.no_report);
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        Log.d(TAG, "onError: "+ response.body());
                        noReportTip(R.string.no_report);
                    }
                });
    }

    private void W628downloadReport(W628Data record) {
        String accessTokenKey = record.getAccountKey();
        LogUtils.d(TAG, "accessTokenKey---->" + accessTokenKey);
        String linkId = record.getUuid();
        LogUtils.d(TAG, "linkId---->" + linkId);
        final File file = new File(Environment.getExternalStorageDirectory() + File.separator + "SleepReport");
        if (!file.exists()) {
            file.mkdirs();
        }
        target = Environment.getExternalStorageDirectory() + File.separator + "sleepReport" + File.separator  ;
        okGo.<File>get(Urls.SleepReport)
                .params("accessTokenKey", record.getAccountKey())
                .params("linkId", record.getUuid())
                .params("is628", 1)
                .execute(new FileCallback(target,"sleepReport.pdf" ) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        LogUtils.d(TAG, "报告下载成功..."+ response.body());
                        try {
                            if(response.body().isFile()){
                                mPDFView.fromFile(response.body())
                                        .defaultPage(0)//默认展示第一页
                                        .load();
                            }else {
                                noReportTip(R.string.no_report);
                            }

                        } catch (Exception e) {
                            noReportTip(R.string.no_report);
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        Log.d(TAG, "onError: "+ response.body());
                        noReportTip(R.string.no_report);
                    }
                });
    }

    private void noReportTip(int resid) {
        text_report.setVisibility(View.VISIBLE);
        text_report.setText(resid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okGo.cancelAll();
    }
}
