package com.choicemmed.common;

import android.graphics.Color;

/**
 * Created by Luis on 2015/6/15.
 */
public class ColorUtils {
    public static final String Color_White="#FFFFFF";
    public static final String Color_Yellow="#FFF000";
    public static final String Color_Orange="#FF8000";
    public static final String Color_Red="#FF2000";


    /**
     * 正常：37.5以下 绿色
     * 低热：37.5～38.0℃；黄色
     * 中等热：38.1～39.0℃；橙色
     * 高热：39.1～40.0℃及以上 红色
     *
     * @param temp
     * @return
     */
    public static int getColor(float temp){
        int color;
        if(temp<37.5){
            color=Color.parseColor(Color_White);
        }else if(temp<38.0){
            color=Color.parseColor(Color_Yellow);
        }else if(temp<39.0){
            color=Color.parseColor(Color_Orange);
        }else{
            color=Color.parseColor(Color_Red);
        }
        return color;
    }

}
