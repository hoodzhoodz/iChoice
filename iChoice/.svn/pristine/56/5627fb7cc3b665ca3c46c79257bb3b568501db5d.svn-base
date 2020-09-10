package com.choicemmed.ichoice.healthcheck.fragment.wristpulse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.graphics.Color.TRANSPARENT;


public class UpLoadDialogFragment extends DialogFragment {
    private String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    private ImageView iv1;
    private RelativeLayout rl;
    private ImageView iv_success;
    private ImageView iv_fail;
    private TextView tv_state;
    private TextView tv_state_tip;
    private boolean isAnimation;

    public boolean isAnimation() {
        return isAnimation;
    }

    public void setAnimation(boolean animation) {
        isAnimation = animation;
    }

    public UpLoadDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_upload_data, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
        mUnbinder = ButterKnife.bind(this, view);
        iv1 = view.findViewById(R.id.iv1);
        rl = view.findViewById(R.id.rl);
        iv_success = view.findViewById(R.id.iv_success);
        iv_fail = view.findViewById(R.id.iv_fail);
        tv_state = view.findViewById(R.id.tv_state);
        tv_state_tip = view.findViewById(R.id.tv_state_tip);

        RotateAnimation rotateAnimation = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);//设置动画持续周期
        rotateAnimation.setRepeatCount(-1);//设置重复次数
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        //让旋转动画一直转，不停顿的重点
        rotateAnimation.setInterpolator(new LinearInterpolator());
//        iv1.setAnimation(rotateAnimation);
        iv1.startAnimation(rotateAnimation);


//        gifView = (GifView) view.findViewById(R.id.gif);
//        gifView.setVisibility(View.VISIBLE);
//        gifView.play();


//        gifView.pause();
//        gifView.setGifResource(R.mipmap.gif5);
//        gifView.getGifResource();
//        gifView.setMovieTime(time);
//        gifView.getMovie();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void linkState(final boolean linkState) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isAnimation = true;
                AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_and_scale_out);
                rl.startAnimation(animationSet);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        LogUtils.d(TAG, "onAnimationEnd");
                        rl.setVisibility(View.GONE);
                        AnimationSet animationSet1 = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_and_scale_in);
                        if (!linkState) {
                            iv_fail.setVisibility(View.VISIBLE);
                            iv_fail.startAnimation(animationSet1);
                        } else {
                            iv_success.setVisibility(View.VISIBLE);
                            iv_success.startAnimation(animationSet1);
                        }
                        animationSet1.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (!linkState) {
                                    tv_state.setText(R.string.w628_state_sync_fail);
                                } else {
                                    tv_state.setText(R.string.w628_state_sync_success);
                                }
                                tv_state_tip.setText("");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent();
                                        intent.setAction("resetState");
                                        getActivity().sendBroadcast(intent);
                                        dismiss();

                                    }
                                }, 2000);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
