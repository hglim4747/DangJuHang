package ku.im.dangjuhang;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by user on 2016-06-22.
 */
public class SplashActivity extends Activity{
    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final ImageView img, back;
        img = (ImageView)findViewById(R.id.splash_img);
        back = (ImageView)findViewById(R.id.splash_back);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                finish();
            }
        };

        ReStartAnimation(img, back);


        handler.sendEmptyMessageDelayed(0, 5000);


    } //end onCreate Method

    void ReStartAnimation(final ImageView img, final ImageView back ){
        final AnimatorSet setRightOutAnimatorSet;
        final AnimatorSet setLeftInAnimatorSet;
        setRightOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.flight_right_out);
        setLeftInAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.flight_left_in);

        setRightOutAnimatorSet.setTarget(back);
        setLeftInAnimatorSet.setTarget(img);
        setRightOutAnimatorSet.start();
        setLeftInAnimatorSet.start();
        setLeftInAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                final AnimatorSet setRightOutAnimatorSet;
                final AnimatorSet setLeftInAnimatorSet;
                setRightOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                        R.animator.flight_right_out);
                setLeftInAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                        R.animator.flight_left_in);
                if(flag) {
                    flag = false;
                    setRightOutAnimatorSet.setTarget(img);
                    setLeftInAnimatorSet.setTarget(back);
                }
                else{
                    flag = true;
                    setRightOutAnimatorSet.setTarget(back);
                    setLeftInAnimatorSet.setTarget(img);
                }
                setRightOutAnimatorSet.start();
                setLeftInAnimatorSet.start();
                setLeftInAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ReStartAnimation(img,back);
                    }
                });
            }
        });
    }

}
