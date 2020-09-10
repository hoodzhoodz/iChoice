package com.choicemmed.ichoice.healthcheck.activity.ecg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.choicemmed.common.LogUtils;
import com.choicemmed.common.StringUtils;
import com.choicemmed.common.ThreadManager;
import com.choicemmed.common.XPermissionUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.DialogUtil;
import com.choicemmed.ichoice.healthcheck.view.EcgScaleViewSplit;
import com.choicemmed.ichoice.healthcheck.view.EcgViewSplit;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import pro.choicemmed.datalib.UserProfileInfo;
import uk.co.senab.photoview.PhotoView;

import static com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgMeasureFragment.getAge;
import static com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgMeasureFragment.uncompressA12bEcgData;
import static com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgMeasureFragment.uncompressP10bEcgData;


public class EcgFullChartActivity extends BaseActivty {
    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.home_vEcgBarView1)
    EcgViewSplit home_vEcgBarView1;
    @BindView(R.id.home_vEcgBarView2)
    EcgViewSplit home_vEcgBarView2;
    @BindView(R.id.home_vEcgBarView3)
    EcgViewSplit home_vEcgBarView3;
    @BindView(R.id.home_vEcgScaleView1)
    EcgScaleViewSplit home_vEcgScaleView1;
    @BindView(R.id.home_vEcgScaleView2)
    EcgScaleViewSplit home_vEcgScaleView2;
    @BindView(R.id.home_vEcgScaleView3)
    EcgScaleViewSplit home_vEcgScaleView3;
    private int[] ecgData;
    @BindView(R.id.scroll)
    LinearLayout layout;
    @BindView(R.id.ll_parent)
    LinearLayout ll_parent;
    @BindView(R.id.scrollview)
    ScrollView scrollView;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_height)
    TextView tv_height;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_avg_hr1)
    TextView tv_avg_hr1;
    @BindView(R.id.imageView)
    PhotoView imageView;
    private UserProfileInfo userProfileInfo;
    private int during;
    ProgressDialog dialog;
    @Override
    protected int contentViewID() {
        return R.layout.activity_ecg_full_chart;
    }

    @Override
    protected void initialize() {
//        dialog = new ProgressDialog(this);
//        dialog.setTitle(null);
//        dialog.setIndeterminate(true);
//        dialog.setMessage(getResources().getString(R.string.loading));
//        dialog.setCancelable(false);
//        dialog.show();

        setTopTitle(getString(R.string.analysis_results), true);
        setLeftBtnFinish();

        home_vEcgScaleView1.setText("0-10s");
        home_vEcgScaleView2.setText("10-20s");
        home_vEcgScaleView3.setText("20-30s");
        if (!"".equals(IchoiceApplication.ecgData.getEcgData())) {
            String [] data = IchoiceApplication.ecgData.getEcgData().split(",");
            ecgData = new int[data.length];
            for (int i = 0; i < data.length; i++) {
                ecgData[i]= Integer.parseInt(data[i]);
            }
        }
        if ("A12".equals(IchoiceApplication.type)) {
            during = 30;
            if (ecgData.length >= 7500) {
                int[] one = new int[2500];
                int[] two = new int[2500];
                int[] three = new int[2500];
                System.arraycopy(ecgData, 0, one, 0, 2500);
                System.arraycopy(ecgData, 2500, two, 0, 2500);
                System.arraycopy(ecgData, 5000, three, 0, 2500);
                home_vEcgBarView1.redrawEcg(one);
                home_vEcgBarView2.redrawEcg(two);
                home_vEcgBarView3.redrawEcg(three);
            }
        } else if ("P10".equals(IchoiceApplication.type)) {
            if (ecgData.length >= 7500) {
                int[] ecgData1 = new int[2500];
                int[] ecgData2 = new int[2500];
                int[] ecgData3 = new int[ecgData.length - 5000];
                for (int i = 0; i < ecgData.length; i++) {
                    if (i < 2500) {
                        ecgData1[i] = ecgData[i];
                    } else if (i < 5000) {
                        ecgData2[i - 2500] = ecgData[i];
                    } else {
                        ecgData3[i - 5000] = ecgData[i];
                    }
                }
                home_vEcgBarView1.redrawEcg(ecgData1);
                home_vEcgBarView2.redrawEcg(ecgData2);
                home_vEcgBarView3.redrawEcg(ecgData3);
            }

        }

        initData();
        layout.post(new Runnable() {
            @Override
            public void run() {
                ceshi();
//                try {
//                    PdfDocument document = new PdfDocument();
//                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(layout.getWidth(), layout.getHeight(), 1).create();
//                    PdfDocument.Page page = document.startPage(pageInfo);
//                    layout.draw(page.getCanvas());
//                    document.finishPage(page);
//                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "ecg.pdf");
//                    FileOutputStream outputStream = new FileOutputStream(file);
//                    try {
//                        document.writeTo(outputStream);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    document.close();
//                    scrollView.setVisibility(View.GONE);
//                    pdfView.setVisibility(View.VISIBLE);
//                    pdfView.fromFile(file)
//                            .load();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    dialog.dismiss();
//                }
            }
        });

    }

    private void ceshi() {
        Bitmap bitmap = loadBitmapFromView1(this, layout);
                            layout.setVisibility(View.GONE);

        layout.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);






    }


    /**
     * 这里是给体检报告打印用的方法
     * @param context
     * @param v
     * @return
     */
    private   Bitmap loadBitmapFromView1(Context context, View v) {
        LogUtils.d(TAG,"v.getMeasuredHeight()  "+v.getMeasuredHeight());
        if (v.getMeasuredHeight() > 0) {
            Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);

//            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            v.layout(0, 0, v.getWidth(), v.getHeight());
            v.draw(c);
            return b;

        }
        // if the view was not displayed before the size of it will be zero. its possible to measure it like this
        if (v.getMeasuredHeight() <= 0) {
            v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }

        return null;
    }
    private void initData() {
        String language = Locale.getDefault().getLanguage();
        userProfileInfo = IchoiceApplication.getInstance().getDaoSession().getUserProfileInfoDao().queryBuilder().unique();
        IchoiceApplication.getAppData().userProfileInfo = userProfileInfo;
        if (userProfileInfo != null) {
            if (!StringUtils.isEmpty(userProfileInfo.getFirstName()) && !StringUtils.isEmpty(userProfileInfo.getFamilyName())) {
                if (language.contains("zh")) {
                    tv_name.setText(userProfileInfo.getFamilyName() + "" + userProfileInfo.getFirstName());
                } else {
                    tv_name.setText(userProfileInfo.getFirstName() + "·" + userProfileInfo.getFamilyName());
                }

            }
            int age = getAge(IchoiceApplication.getAppData().userProfileInfo.getBirthday());
            if (userProfileInfo.getGender().equals("2")) {
                tv_sex.setText(getResources().getString(R.string.male));
            } else {
                tv_sex.setText(getResources().getString(R.string.female));
            }
            tv_time.setText(IchoiceApplication.ecgData.getMeasureTime());
            tv_height.setText(userProfileInfo.getHeight());
            tv_weight.setText(userProfileInfo.getWeight());
            tv_duration.setText(during + "");
            tv_avg_hr1.setText(IchoiceApplication.thisResult.getObservation().getHr() + "");
            tv_age.setText(getAge(IchoiceApplication.getAppData().userProfileInfo.getBirthday()) + "");
        }
    }

    /**
     * View转换成Bitmap
     *
     * @param targetView     targetView

     */
    private   void getBitmapFromView(@NotNull Activity activity, View targetView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //准备一个bitmap对象，用来将copy出来的区域绘制到此对象中
            final Bitmap bitmap = Bitmap.createBitmap(targetView.getWidth(), targetView.getHeight(), Bitmap.Config.ARGB_8888);
            //获取layout的left-top顶点位置
            final int[] location = new int[2];
            targetView.getLocationInWindow(location);
            //请求转换
            PixelCopy.request(activity.getWindow(),
                    new Rect(location[0], location[1],
                            location[0] + targetView.getWidth(), location[1] + targetView.getHeight()),
                    bitmap, new PixelCopy.OnPixelCopyFinishedListener() {
                        @Override
                        public void onPixelCopyFinished(int copyResult) {
                            //如果成功
                            if (copyResult == PixelCopy.SUCCESS) {
                                //方法回调

                                layout.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    }, new Handler(Looper.getMainLooper()));
        } else {

            //开启DrawingCache
            targetView.setDrawingCacheEnabled(true);
            //构建开启DrawingCache
            targetView.buildDrawingCache();
            //获取Bitmap
            Bitmap drawingCache = targetView.getDrawingCache();
            //方法回调
            layout.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(drawingCache);
            //销毁DrawingCache
            targetView.destroyDrawingCache();
        }
    }


}
