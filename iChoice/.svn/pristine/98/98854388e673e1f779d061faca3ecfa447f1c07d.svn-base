package com.choicemmed.ichoice.initalization.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.choicemmed.common.DateUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.framework.utils.PreferenceUtil;
import com.choicemmed.ichoice.framework.utils.Utils;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.initalization.config.ApiConfig;
import com.choicemmed.ichoice.profile.presenter.impl.PersonalInfoPresenterImpl;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by gaofang on 2019/1/23.
 * 个人信息设置 - 生日、体重、身高
 */

public class PersonalInfoActivity extends BaseActivty implements IBaseView {
    @BindView(R.id.img_personal_info)
    SimpleDraweeView imgHead;
    @BindView(R.id.img_personal_info_failure)
    ImageView imgFailure;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    ArrayList<Integer> hightIntegers = new ArrayList<>();
    ArrayList<Integer> weightIntegers = new ArrayList<>();
    ArrayList<ArrayList<String>> hightDecimals = new ArrayList<>();
    ArrayList<ArrayList<String>> weightDecimals = new ArrayList<>();
    private OptionsPickerView HightOptions, WeightOptions;
    private PersonalInfoPresenterImpl personalInfoPresenter;
    @Override
    protected int contentViewID() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initialize() {
        setLeftBtnFinish();
        setTopTitle(getResources().getString(R.string.new_user), true);
        if ("".equals(IchoiceApplication.getAppData().user.getHeadImgUrl())) {
            imgFailure.setVisibility(View.VISIBLE);
            imgHead.setVisibility(View.GONE);
        }else {
            Utils.setRoundImage(this, imgHead, IchoiceApplication.getAppData().user.getHeadImgUrl());
        }
        personalInfoPresenter = new PersonalInfoPresenterImpl(this,this);
        initData();
        initOptionPicker();
    }

    private void initData() {
        ArrayList<String> decimal = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            decimal.add("."+i);
        }
        for (int i = 50; i <= 299; i++) {
            hightIntegers.add(i);
            hightDecimals.add(i - 50, decimal);
        }
        for (int i = 5; i <= 199; i++) {
            weightIntegers.add(i);
            weightDecimals.add(i - 5, decimal);
        }
    }

    private void initOptionPicker() {
        HightOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = hightIntegers.get(options1)
                        + hightDecimals.get(options1).get(options2);
                Log.d("onOptionsSelect",tx);
                tvHeight.setText(tx + " cm");
                PreferenceUtil.getInstance().putPreferences(ApiConfig.HEIGHT,tx);
                IchoiceApplication.getAppData().user.refresh();


            }
        }).setTitleText("")
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.done))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setSelectOptions(170)

                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {

                    }
                }).build();
        HightOptions.setPicker(hightIntegers,hightDecimals);

        WeightOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = weightIntegers.get(options1)
                        + weightDecimals.get(options1).get(options2);
                Log.d("onOptionsSelect",tx);
                tvWeight.setText(tx + " kg");
                PreferenceUtil.getInstance().putPreferences(ApiConfig.WEIGHT,tx);
                IchoiceApplication.getAppData().user.refresh();
            }
        }).setTitleText("")
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.done))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setSelectOptions(60)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {

                    }
                }).build();
        WeightOptions.setPicker(weightIntegers,weightDecimals);
    }

    @OnClick({R.id.tv_birthday, R.id.tv_height, R.id.tv_weight, R.id.btn_finish})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_birthday:
                DatePicker picker = new DatePicker(this);
                picker.setTopLineVisible(false);
                picker.setTopHeight(50);
                picker.setTextColor(getResources().getColor(R.color.color_04d9b4));
                picker.setTextSize(21);
                picker.setLabel(getString(R.string.y), getString(R.string.m), getString(R.string.d));
                picker.setLabelTextColor(getResources().getColor(R.color.color_04d9b4));
                picker.setCancelText(getString(R.string.cancel));
                picker.setTitleText("");
                picker.setTitleTextColor(getResources().getColor(R.color.color_414141));
                picker.setTitleTextSize(18);
                picker.setSubmitText(getString(R.string.done));
                picker.setSubmitTextSize(16);
                picker.setRange(DateUtils.getYear(),1900);

                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        try {
                            int Year = Integer.parseInt(year);
                            int Month = Integer.parseInt(month);
                            int Day = Integer.parseInt(day);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Year,Month-1,Day);
                            if (calendar.after(Calendar.getInstance())){
                                MethodsUtils.showErrorTip(PersonalInfoActivity.this,getString(R.string.tip_birthday));
                                return;
                            }
                            tvBirthday.setText(year + "-" + month + "-" + day);
                            PreferenceUtil.getInstance().putPreferences(ApiConfig.BIRTHDAY,year + "-" + month + "-" + day);
                            IchoiceApplication.getAppData().user.refresh();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
                picker.show();
                break;
            case R.id.tv_height:
                HightOptions.show();
                break;
            case R.id.tv_weight:
                WeightOptions.show();
                break;
            case R.id.btn_finish:
                if (!PermissionsUtils.isNetworkConnected(this)) {
                    MethodsUtils.showErrorTip(this,getString(R.string.no_signal));
                    return;
                }

                if (!TextUtils.isEmpty(tvBirthday.getText()) && !TextUtils.isEmpty(tvHeight.getText()) && !TextUtils.isEmpty(tvWeight.getText())){
                    personalInfoPresenter.sendPersonalInfoRequest(IchoiceApplication.getAppData().user.getToken(), IchoiceApplication.getAppData().user.getGender(),
                            IchoiceApplication.getAppData().user.getBirthday(), IchoiceApplication.getAppData().user.getHeight(),
                            IchoiceApplication.getAppData().user.getWeight(), IchoiceApplication.getAppData().user.getFirstName(), IchoiceApplication.getAppData().user.getFamilyName());

                }else {
                    MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
                }
                break;
        }
    }

    @Override
    public void onSuccess() {
        startActivity(LoginActivity.class);
        ActivityUtils.removeAll();
        finish();
    }

    @Override
    public void onError(String msg) {
        MethodsUtils.showErrorTip(this,msg);
    }
}
