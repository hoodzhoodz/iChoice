package com.choicemmed.ichoice.framework.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/10 10:46
 * 修改人：114100
 * 修改时间：2019/4/10 10:46
 * 修改备注：
 */
public class RoundAnimatorImageView extends AppCompatImageView {
    private ObjectAnimator objectAnimator;
    public static final int STATE_PLAYING =1;//正在播放
    public static final int STATE_PAUSE =2;//暂停
     public static final int STATE_STOP =3;//停止
     public int state;
     public RoundAnimatorImageView(Context context) {
         super(context); init();
     }
     public RoundAnimatorImageView(Context context, AttributeSet attrs) {
         super(context, attrs); init();
     }
     public RoundAnimatorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
         super(context, attrs, defStyleAttr); init();
     }
     private void init(){
         state = STATE_STOP;
         objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);//添加旋转动画，旋转中心默认为控件中点
         objectAnimator.setDuration(3000);//设置动画时间
         objectAnimator.setInterpolator(new LinearInterpolator());//动画时间线性渐变
         objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
         objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
     }
     public void play(){
         objectAnimator.start();//动画开始
//         if(state == STATE_STOP){
//             objectAnimator.start();//动画开始
//             state = STATE_PLAYING;
//         }else if(state == STATE_PAUSE){
//             objectAnimator.resume();//动画重新开始
//             state = STATE_PLAYING;
//         }else if(state == STATE_PLAYING){
//             objectAnimator.pause();//动画暂停
//             state = STATE_PAUSE;
//         }
     }
     public void stop(){
         objectAnimator.end();//动画结束
//         state = STATE_STOP;
     }

}
