package com.choicemmed.ichoice.framework.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.choicemmed.ichoice.R;

/**
 * @author Created by Jiang nan on 2019/11/27 10:36.
 * @description
 **/

public class DialogUtil {

    private Context mContext;
    private Activity mActivity;
    private OnItemClickListener mOnItemClickListener;

    public DialogUtil(Activity activity, Context context){
        mActivity = activity;
        mContext = context;

    }

    /** 定义一个接口，当用户点击按钮时，可在主页面进行逻辑操作 */
    public interface OnItemClickListener{
        /** 取消 */
        void onItemCancelClick();
        /** 确定 */
        void onItemConfirmClick();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }



    /** 提示框,自定义标题，内容，按钮 */
    public void dialog(String content, String cancelString, String confirmString){
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_center_tip, null);
        TextView titleView = view.findViewById(R.id.tv_title);
        TextView contentVIew = view.findViewById(R.id.tv_content);
        TextView cancelButtonView = view.findViewById(R.id.btn_negative);
        TextView confirmButtonView = view.findViewById(R.id.btn_positive);
//        titleView.setText(title);
        contentVIew.setText(content);
        cancelButtonView.setText(cancelString);
        confirmButtonView.setText(confirmString);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        /** 设置宽为屏幕的0.8大小 */
        lp.width = (int) (mActivity.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        dialog.getWindow().setAttributes(lp);
        /** 自定义布局应该在这里添加，要在dialog.show()的后面 */
        dialog.getWindow().setContentView(view);
        cancelButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemCancelClick();
                dialog.dismiss();
            }

        });

        confirmButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemConfirmClick();
                dialog.dismiss();
            }
        });

    }
}
