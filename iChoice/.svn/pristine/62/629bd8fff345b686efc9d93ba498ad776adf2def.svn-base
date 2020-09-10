package com.choicemmed.ichoice.framework.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;

import com.choicemmed.ichoice.framework.widget.AddGlideImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.text.DecimalFormat;


/**
 * 项目名称：iChoice
 * 类描述：参数配置
 * 创建人：114100
 * 创建时间：2019/4/2 13:20
 * 修改人：114100
 * 修改时间：2019/4/2 13:20
 * 修改备注：
 */
public class Utils {
    public static void initImagePicker(int maxImgCount) {

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new AddGlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(800);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);                         //保存文件的高度。单位像素
    }
    /**
     * 计算图片大小
     * @param size
     * @return
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    /**
     * 压缩图片
     * @param context
     * @param oldFile
     * @param fileName
     * @param size
     * @return
     */
    public static File getCompressionImage(Context context, File oldFile, String fileName, int size) {
        File newFile = new CompressHelper.Builder(context)
                .setMaxWidth(960)  // 默认最大宽度为720
                .setMaxHeight(720) // 默认最大高度为960
                .setQuality(size)    // 默认压缩质量为80
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setFileName(fileName) // 设置你的文件名
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(oldFile);
        return newFile;
    }
    /**
     *加载本地圆角图片
     * @param image
     * @param imageUrl
     */
    public static void setlocalRoundImage(Context context,SimpleDraweeView image, Uri imageUrl){
        /**初始化圆角圆形参数对象**/
        RoundingParams rp = new RoundingParams();
        /**设置图像是否为圆形**/
        rp.setRoundAsCircle(true);
        /**设置圆角半径**/
        //rp.setCornersRadius(20);
        /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
        //rp.setCornersRadii(20,25,30,35);
        /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
        //rp.setCornersRadii(new float[]{20,25,30,35,40,45,50,55});
        /**设置边框颜色及其宽度**/
        rp.setBorder(Color.WHITE, 2);
        /**设置叠加颜色**/
        rp.setOverlayColor(Color.GRAY);
        /**设置圆形圆角模式**/
        //rp.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        /**设置圆形圆角模式**/
        rp.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);

        /**获取GenericDraweeHierarchy对象**/
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                /**设置圆形圆角参数**/
                .setRoundingParams(rp)
                /**设置圆角半径**/
                // .setRoundingParams(RoundingParams.fromCornersRadius(20))
                /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(20,25,30,35))
                /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(new float[]{20,25,30,35,40,45,50,55}))
                /**设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形**/
                .setRoundingParams(RoundingParams.asCircle())
                /**设置淡入淡出动画持续时间(单位：毫秒ms)**/
                .setFadeDuration(1000)
                .build();
        image.setHierarchy(hierarchy);
        /**构建Controller**/
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(imageUrl)
                /**设置需要下载的图片地址**/
                /**设置点击重试是否开启**/
                .setTapToRetryEnabled(true)
                .build();
        /**设置Controller**/
        image.setController(controller);
    }
    /**
     *加载圆角图片
     * @param image
     * @param imageUrl
     */
    public static void setRoundImage(Context context,SimpleDraweeView image, String imageUrl){
        /**初始化圆角圆形参数对象**/
        RoundingParams rp = new RoundingParams();
        /**设置图像是否为圆形**/
        rp.setRoundAsCircle(true);
        /**设置圆角半径**/
        //rp.setCornersRadius(20);
        /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
        //rp.setCornersRadii(20,25,30,35);
        /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
        //rp.setCornersRadii(new float[]{20,25,30,35,40,45,50,55});
        /**设置边框颜色及其宽度**/
        rp.setBorder(Color.WHITE, 2);
        /**设置叠加颜色**/
        rp.setOverlayColor(Color.GRAY);
        /**设置圆形圆角模式**/
        //rp.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        /**设置圆形圆角模式**/
        rp.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);

        /**获取GenericDraweeHierarchy对象**/
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                /**设置圆形圆角参数**/
                .setRoundingParams(rp)
                /**设置圆角半径**/
                // .setRoundingParams(RoundingParams.fromCornersRadius(20))
                /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(20,25,30,35))
                /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(new float[]{20,25,30,35,40,45,50,55}))
                /**设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形**/
                .setRoundingParams(RoundingParams.asCircle())
                /**设置淡入淡出动画持续时间(单位：毫秒ms)**/
                .setFadeDuration(1000)
                .build();
        image.setHierarchy(hierarchy);
        /**构建Controller**/
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(imageUrl)
                /**设置需要下载的图片地址**/
                /**设置点击重试是否开启**/
                .setTapToRetryEnabled(true)
                .build();
        /**设置Controller**/
        image.setController(controller);
    }
    /**
     * 获取App版本 取manifest文件versionName字段，前面加“V”返回，异常返回空字符串
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int version = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
