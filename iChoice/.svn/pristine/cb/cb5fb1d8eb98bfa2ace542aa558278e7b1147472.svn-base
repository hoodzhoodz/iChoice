package com.choicemmed.ichoice.profile.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.choicemmed.common.DateUtils;
import com.choicemmed.common.StringUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.healthcheck.db.UserOperation;
import com.choicemmed.ichoice.profile.presenter.impl.PersonalInfoPresenterImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import pro.choicemmed.datalib.UserProfileInfo;

/**
 * 项目名称：iChoice
 * 类描述：信息界面
 * 创建人：114100
 * 创建时间：2019/4/3 18:55
 * 修改人：114100
 * 修改时间：2019/4/3 18:55
 * 修改备注：
 */
public class InformationActivity extends BaseActivty implements IBaseView {
    @BindView(R.id.tv_FamilyName)
    TextView tvFamilyname;
    @BindView(R.id.tv_FirstName)
    TextView tvFirstName;
    @BindView(R.id.tv_information_gender)
    TextView tvGender;
    @BindView(R.id.tv_information_birthday)
    TextView tvBirthDay;
    @BindView(R.id.tv_information_height)
    TextView tvHeight;
    @BindView(R.id.tv_information_weight)
    TextView tvWeight;
    @BindView(R.id.tv_user_email)
    TextView tvEmail;

    private UserProfileInfo info=null;
    private PersonalInfoPresenterImpl personalInfoPresenter;
    String sFirstName = "";
    String sFamilyName = "";
    String sGender = "";
    String sBirthday = "";
    String sWeight = "";
    String sHeight = "";
    ArrayList<Integer> hightIntegers = new ArrayList<>();
    ArrayList<Integer> weightIntegers = new ArrayList<>();
    ArrayList<ArrayList<String>> hightDecimals = new ArrayList<>();
    ArrayList<ArrayList<String>> weightDecimals = new ArrayList<>();
    private OptionsPickerView HightOptions, WeightOptions;
    @Override
    protected int contentViewID() {
        return R.layout.activity_information;
    }

    @Override
    protected void initialize() {
        personalInfoPresenter = new PersonalInfoPresenterImpl(this,this);
        setTopTitle(getResources().getString(R.string.information), true);
        setLeftBtnFinish();
        info = IchoiceApplication.getInstance().getDaoSession().getUserProfileInfoDao().queryBuilder().unique();
        Log.d("initializeinfo",info.toString());
        if (info !=null) {
            if (!StringUtils.isEmpty(info.getFamilyName())) {
                tvFamilyname.setText(info.getFamilyName());
            }
            if (!StringUtils.isEmpty(info.getFirstName())) {
                tvFirstName.setText(info.getFirstName());
            }
            if (info.getGender() != null && !info.getGender().equals("")) {
                if ("2".equals(info.getGender())) {
                    tvGender.setText(getString(R.string.male));
                } else if ("3".equals(info.getGender())) {
                    tvGender.setText(getString(R.string.female));
                }
            }
            if (!info.getBirthday().equals("")) {
                tvBirthDay.setText(info.getBirthday());
            }
            if (!info.getHeight().equals("") && info.getHeight() != null) {
                tvHeight.setText(formatFloat(info.getHeight(), 175.0f) + "cm");
            }
            if (!info.getWeight().equals("") && info.getWeight() != null) {
                tvWeight.setText(formatFloat(info.getWeight(), 70.0f) + "kg");
            }

            if (!info.getEmail().equals("") && info.getEmail() != null) {
                tvEmail.setText(info.getEmail());
            } else {
                if (!StringUtils.isEmpty(info.getPhone())) {
                    tvEmail.setText(info.getPhone());
                }
            }

        }
        initData();
        initOptionPicker();
    }

    private String formatFloat(String sValue,float defaultValue) {
        float fValue = defaultValue;
        try {
            fValue = Float.parseFloat(sValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        sValue = String.format("%.1f",fValue);
        return sValue;
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

            }
        }).setTitleText("").setCancelText("")
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
            }
        }).setTitleText("")
                .setSubmitText(getString(R.string.done))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setSelectOptions(60)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {


                    }
                }).setCancelText("").build();
        WeightOptions.setPicker(weightIntegers,weightDecimals);
    }



    @OnClick({R.id.tv_information_gender,R.id.tv_information_birthday,R.id.tv_information_height,R.id.tv_information_weight,R.id.btn_information_ok})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_information_gender:
                initGender();
                break;
            case R.id.tv_information_birthday:
                initDatePicker();
                break;
            case R.id.tv_information_height:
                HightOptions.show();

                break;
            case R.id.tv_information_weight:
                WeightOptions.show();

                break;
            case R.id.btn_information_ok:
                saveInformation();
                break;
                default:
        }
    }

    private void saveInformation() {
        if (!PermissionsUtils.isNetworkConnected(this)) {
            MethodsUtils.showErrorTip(this,getString(R.string.no_signal));
            return;
        }
        if (!TextUtils.isEmpty(tvFamilyname.getText()) && !TextUtils.isEmpty(tvFirstName.getText()) &&
                !TextUtils.isEmpty(tvGender.getText()) && !TextUtils.isEmpty(tvBirthDay.getText()) &&
                !TextUtils.isEmpty(tvHeight.getText()) &&!TextUtils.isEmpty(tvWeight.getText())){

            if (getString(R.string.male).equals(tvGender.getText().toString().trim())) {
                sGender="2";
            } else if (getString(R.string.female).equals(tvGender.getText().toString().trim())) {
                sGender="3";
            }else {
                sGender="1";
            }

            sFamilyName = tvFamilyname.getText().toString();
            sFirstName = tvFirstName.getText().toString();
            sBirthday = tvBirthDay.getText().toString();
            sHeight = tvHeight.getText().toString();
            sWeight = tvWeight.getText().toString();
            sHeight = sHeight.substring(0,sHeight.length()-2);
            sWeight = sWeight.substring(0,sWeight.length()-2);
            Log.d("info",sHeight+"|"+ sWeight);
            personalInfoPresenter.sendPersonalInfoRequest(IchoiceApplication.getAppData().user.getToken(), sGender, sBirthday, sHeight,
                        sWeight, sFirstName, sFamilyName);
            info.setFamilyName(sFamilyName);
            info.setFirstName(sFirstName);
            info.setGender(sGender);
            info.setBirthday(sBirthday);
            info.setHeight(sHeight);
            info.setWeight(sWeight);
            UserOperation userOperation = new UserOperation(this);
            userOperation.upDateUser(info);
            IchoiceApplication.getAppData().userProfileInfo = info;
        }else {
            MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
        }
    }

    private void initGender() {
        List<String> genders=new ArrayList<>();
        genders.add(getString(R.string.male));
        genders.add(getString(R.string.female));
        SinglePicker singlePicker = new SinglePicker(this,genders);
        singlePicker.setTopLineVisible(false);
        singlePicker.setTopHeight(50);
        singlePicker.setTextSize(21);
        singlePicker.setTextColor(getResources().getColor(R.color.color_04d9b4));
        singlePicker.setItemWidth(150);
        singlePicker.setTitleTextSize(18);
        singlePicker.setTitleTextColor(getResources().getColor(R.color.color_414141));
        singlePicker.setCancelText("");
        singlePicker.setCancelText(getString(R.string.cancel));
        singlePicker.setSubmitText(getString(R.string.done));
        singlePicker.setSubmitTextSize(16);
        singlePicker.setItems(genders);
        singlePicker.setSelectedItem(150);
        singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener() {
            @Override
            public void onItemPicked(int i, Object object) {
                tvGender.setText(object+"");
            }
        });
        singlePicker.show();
    }

    private void initDatePicker() {
        DatePicker picker = new DatePicker(this);
        picker.setTopLineVisible(false);
        picker.setTopHeight(50);
        picker.setTextColor(getResources().getColor(R.color.color_04d9b4));
        picker.setTextSize(21);
        picker.setLabel(getString(R.string.y), getString(R.string.m), getString(R.string.d));
        picker.setLabelTextColor(getResources().getColor(R.color.color_04d9b4));
        picker.setCancelText("");
        picker.setTitleTextColor(getResources().getColor(R.color.color_414141));
        picker.setTitleTextSize(18);
        picker.setCancelText(getString(R.string.cancel));
        picker.setSubmitText(getString(R.string.done));
        picker.setSubmitTextSize(16);
        picker.setRange(DateUtils.getYear(),1900);//年份范围
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                try {
                    int Year = Integer.parseInt(year);
                    int Month = Integer.parseInt(month);
                    int Day = Integer.parseInt(day);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Year,Month-1,Day);
                    Log.d("calendar",year + "-" + month + "-" + day);
                    Log.d("calendar",Year + "-" + Month + "-" + Day);
                    Log.d("calendar",calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)-1) + "-" + calendar.get(Calendar.DATE));
                    if (calendar.after(Calendar.getInstance())){
                        MethodsUtils.showErrorTip(InformationActivity.this,getString(R.string.tip_birthday));
                        return;
                    }
                    tvBirthDay.setText(year + "-" + month + "-" + day);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        picker.show();
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onError(String msg) {
        MethodsUtils.showErrorTip(this,msg);
    }
}
