package com.choicemmed.common;


import android.app.Activity;

import android.util.DisplayMetrics;

import android.util.Log;

import android.view.View;

import android.view.ViewGroup;

import android.widget.TextView;

 

public class ViewScale {

	public static void scaleContents(Activity context,View paramView1) {

		DisplayMetrics localDisplayMetrics = new DisplayMetrics();

		context.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);

		// 获得屏幕的宽高

		int i = localDisplayMetrics.widthPixels;

		int j = localDisplayMetrics.heightPixels;

		float f = Math.min(i / 720f, j / 1280f);

		scaleViewAndChildren(paramView1,1f);

		Log.i("notcloud", "Scaling Factor=" + f);

	}

 

	public static void scaleViewAndChildren(View paramView, float paramFloat) {

		ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();

		if ((localLayoutParams.width != -1) && (localLayoutParams.width != -2)) {

			localLayoutParams.width = ((int) (paramFloat * localLayoutParams.width));

		}

		if ((localLayoutParams.height != -1)

				&& (localLayoutParams.height != -2)) {

			localLayoutParams.height = ((int) (paramFloat * localLayoutParams.height));

		}

		if ((localLayoutParams instanceof ViewGroup.MarginLayoutParams)) {

			ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) localLayoutParams;

			localMarginLayoutParams.leftMargin = ((int) (paramFloat * localMarginLayoutParams.leftMargin));

			localMarginLayoutParams.rightMargin = ((int) (paramFloat * localMarginLayoutParams.rightMargin));

			localMarginLayoutParams.topMargin = ((int) (paramFloat * localMarginLayoutParams.topMargin));

			localMarginLayoutParams.bottomMargin = ((int) (paramFloat * localMarginLayoutParams.bottomMargin));

		}

		paramView.setLayoutParams(localLayoutParams);

		paramView.setPadding((int) (paramFloat * paramView.getPaddingLeft()),

				(int) (paramFloat * paramView.getPaddingTop()),

				(int) (paramFloat * paramView.getPaddingRight()),

				(int) (paramFloat * paramView.getPaddingBottom()));

		if ((paramView instanceof TextView)) {

			TextView localTextView = (TextView) paramView;

			Log.d("Calculator",

					"Scaling text size from " + localTextView.getTextSize()

							+ " to " + paramFloat * localTextView.getTextSize());

			localTextView.setTextSize(paramFloat * localTextView.getTextSize());

		}

		ViewGroup localViewGroup = null;

		if ((paramView instanceof ViewGroup)) {

			localViewGroup = (ViewGroup) paramView;

		}

		if (localViewGroup != null) {

			System.out.println("子元素的数量" + localViewGroup.getChildCount());

			for (int i = 0;; i++) {

				if (i >= localViewGroup.getChildCount()) {

					break;

				}

				scaleViewAndChildren(localViewGroup.getChildAt(i), paramFloat);

			}

		}

	}

}
