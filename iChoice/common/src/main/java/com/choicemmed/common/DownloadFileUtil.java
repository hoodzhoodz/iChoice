package com.choicemmed.common;

import java.io.File;
import java.net.URLDecoder;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * @项目名称: htfytj
 * @类描述: 下载pdf、xps、图片的工具类
 * @创建人：李林海
 * @创建时间：2016/1/8 14:31
 * @备注：
 */
@SuppressLint("DefaultLocale")
public class DownloadFileUtil {
	static String FilePath;
	static BroadcastReceiver receiver;
	private static DownloadManager downManager;
	private static final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" }, { ".apk", "application/vnd.android.package-archive" }, { ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" }, { ".c", "text/plain" },
			{ ".class", "application/octet-stream" }, { ".conf", "text/plain" }, { ".cpp", "text/plain" },
			{ ".doc", "application/msword" }, { ".docx", "application/msword" }, { ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" }, { ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" }, { ".h", "text/plain" },
			{ ".htm", "text/html" }, { ".html", "text/html" }, { ".jar", "application/java-archive" }, { ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" }, { ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" }, { ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" }, { ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
			{ ".png", "image/png" }, { ".pps", "application/vnd.ms-powerpoint" }, { ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx", "application/vnd.ms-powerpoint" }, { ".prop", "text/plain" }, { ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" }, { ".sh", "text/plain" },
			{ ".tar", "application/x-tar" }, { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" },
			// {".xml", "text/xml"},
			{ ".xml", "text/plain" }, { ".xls", "application/vnd.ms-excel" }, { ".xlsx", "application/vnd.ms-excel" },
			{ ".z", "application/x-compress" }, { ".zip", "application/zip" }, { "", "*/*" },{".xps","application/xps"} };

	/**
	 * @param context
	 *            上下文场景
	 * @param url
	 *            下载文件的地址
	 * @param path
	 *            SD卡保存的路径 如："/MyDownload",自动在SD下创建该目录。
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void DownloadFile(Context context, String url, String path) {
		Log.i("DownloadFileUtil", "url--->" + url);
		// 注册广播监听下载完成
		receiver = new DownloadCompleteReceiver();
		IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		context.registerReceiver(receiver, intentFilter);
		String file = FileUtils.getRootFilePath(context, path);
		// 截取文件名
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		fileName = URLDecoder.decode(fileName);
		downManager = (DownloadManager) context.getSystemService(Activity.DOWNLOAD_SERVICE);
		DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
		down.setShowRunningNotification(true);
		// 在通知栏显示
		down.setVisibleInDownloadsUi(true);
		// 输出目录
		down.setDestinationInExternalPublicDir(path + "/", fileName);
		// 文件路径
		FilePath = file + "/" + fileName;
		// 加入下载队列执行
		downManager.enqueue(down);
	}

	public static void unregisterReceiver(Context context) {
		context.unregisterReceiver(receiver);
	};

	/**
	 * 监听下载完成
	 * 
	 * @author Administrator
	 * 
	 */
	@SuppressLint("NewApi")
	public static class DownloadCompleteReceiver extends BroadcastReceiver {
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
				// 获取文件路径
				// Toast.LENGTH_SHORT).show();
				// 获取文件路径
				File files = new File(FilePath);
				Intent in;
				if (files.exists()) {
					String mimeType = getMIMEType(files);
					in = new Intent("android.intent.action.VIEW");
					in.addCategory("android.intent.category.DEFAULT");
					in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Uri uri = Uri.fromFile(files);
					in.setDataAndType(uri, mimeType);
					context.startActivity(in);
				} else {
					long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
					// 查询
					Cursor c = downManager.query(new Query().setFilterById(downloadId));
					if (c.moveToFirst()) {
						int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
						if (DownloadManager.STATUS_FAILED == c.getInt(columnIndex)) {
							Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
						}
					}
					if (c != null) {
						c.close();
					}
				}

			}
		}
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	@SuppressLint("DefaultLocale")
	public static String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}
}
