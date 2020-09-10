package com.choicemmed.common;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 */
public class ToastUtils
{

	private ViewGroup.LayoutParams layoutParams;

	private ToastUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 *  @param context
	 * @param message
     */
	public static void showShort(Context context, String message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 显示自定义视图的Toast
	 *
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void showCustom(Context context, CharSequence message, int duration)
	{
		Toast toast=new Toast(context);
		View view= LayoutInflater.from(context).inflate(R.layout.custom_toast_layout,null);
		TextView tvMsg= (TextView) view.findViewById(R.id.message);
		tvMsg.setText(message);
		toast.setView(view);
		toast.setDuration(duration);
		toast.show();
	}

	public static void showCustom(Context context, CharSequence message)
	{
		Toast toast=new Toast(context);
		View view= LayoutInflater.from(context).inflate(R.layout.custom_toast_layout,null);
		TextView tvMsg= (TextView) view.findViewById(R.id.message);
		tvMsg.setText(message);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void showCircle(Context context, CharSequence message)
	{
		Toast toast=new Toast(context);
		View view= LayoutInflater.from(context).inflate(R.layout.circle_toast_layout,null);
		TextView tvMsg= (TextView) view.findViewById(R.id.message);
		tvMsg.setText(message);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
	public static void showCustom1(Context context, CharSequence message)
	{
		Toast toast=new Toast(context);
		View view= LayoutInflater.from(context).inflate(R.layout.custom_toast_layout,null);
		TextView tvMsg= (TextView) view.findViewById(R.id.message);
		ViewGroup.LayoutParams params=tvMsg.getLayoutParams();
		params.width= ViewGroup.LayoutParams.WRAP_CONTENT;
		tvMsg.setLayoutParams(params);
		tvMsg.setText(message);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void showCircleCustom(Context context, CharSequence message)
	{
		Toast toast=new Toast(context);
		View view= LayoutInflater.from(context).inflate(R.layout.circle_custom_toast_layout,null);
		TextView tvMsg= (TextView) view.findViewById(R.id.message);
		tvMsg.setText(message);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
}