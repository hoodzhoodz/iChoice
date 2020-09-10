package com.choicemmed.ichoice.initalization.activity;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.initalization.config.ConstantConfig;
import com.choicemmed.ichoice.initalization.adapter.GuideViewPagerAdapter;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gaofang on 2019/1/21.
 * 引导界面
 */

public class GuideActivity extends BaseActivty {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;

    @BindView(R.id.ll)
    LinearLayout llDot;

    private GuideViewPagerAdapter guideAdapter;
    private List<View> views = new ArrayList<>();
    // 引导页图片资源
    private static final int[] pics = {R.layout.layout_guide1, R.layout.layout_guide2, R.layout.layout_guide3};

    // 底部小点图片
    private ImageView[] dotViews;

    @Override
    protected int contentViewID() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initialize() {
        setTopTitle("",false);
        StatusBarUtils.setLightMode(GuideActivity.this);
        initBtn();
        // 初始化adapter
        guideAdapter = new GuideViewPagerAdapter(views);
        vpGuide.setAdapter(guideAdapter);
        vpGuide.setOnPageChangeListener(new PageChangeListener());

        initDots();

    }


    private void initDots() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(45, 40);
        mParams.setMargins(6, 0, 6, 0);//设置小圆点左右之间的间隔
        dotViews = new ImageView[pics.length];
        //判断小圆点的数量，从0开始，0表示第一个
        for (int i = 0; i < pics.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.selector_dot);
            if (i == 0) {
                imageView.setSelected(true);//默认启动时，选中第一个小圆点
            } else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            llDot.addView(imageView);//添加到布局里面显示
        }

    }


    private void initBtn() {
        // 初始化引导页视图列表
        int btnIndex = pics.length - 1;
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            views.add(view);
            if (i == btnIndex) {
                view.findViewById(R.id.btn_enter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharePreferenceUtil.put(GuideActivity.this, ConstantConfig.FIRST_OPEN, true);
                        startActivity(LoginActivity.class);
                        finish();
                    }
                });
            }


        }
    }


    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        for (int i = 0; i < dotViews.length; i++) {
            if (position == i) {
                dotViews[i].setSelected(true);
            } else {
                dotViews[i].setSelected(false);
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页

    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }

    }
}
