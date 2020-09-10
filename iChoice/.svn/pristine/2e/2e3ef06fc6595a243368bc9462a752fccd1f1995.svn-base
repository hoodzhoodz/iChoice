package com.choicemmed.common;

import android.content.Intent;
import android.net.Uri;

import java.io.File;
/**
 * @项目名称: htfytj
 * @类描述: Intent 辅助类
 * @创建人：李林海
 * @创建时间：2016/1/8 17:03
 * @备注：
 */
public class MyIntent {
	// android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	public static Intent getXpsFileIntent(String param){
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/xps");
		return intent;
	}
	// Android获取一个用于打开所有文件的intent
	public static Intent getAllFileIntent(String param) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}
}
