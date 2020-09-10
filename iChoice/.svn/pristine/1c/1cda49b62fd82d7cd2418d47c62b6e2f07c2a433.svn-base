package com.choicemmed.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * Created by Luis on 2015/5/30.
 */
public class BitmapUtil {

    /**
     * 加载指定resId的Bitmap
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap loadBitmap(Context context ,int resId){
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), resId);
        return bitmap;
    };

    /**
     *按指定比例缩放Bitmap
     *
     * @param bitmap
     * @param zoomX
     * @param zoomY
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap,float zoomX,float zoomY) {
        Matrix matrix = new Matrix();
        matrix.postScale(zoomX, zoomY); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }


    /**
     * 二进制转换为bitmap
     * @param temp
     * @return
     */
    public static Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

    /**
     * bitmap转换为二进制
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
