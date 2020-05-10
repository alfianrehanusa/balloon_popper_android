package com.alfianwibowo.ballonpopper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.core.content.ContextCompat;

import com.alfianwibowo.ballonpopper.Utils.PixelHelper;

import java.util.Objects;

public class Balloon extends androidx.appcompat.widget.AppCompatImageView implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator mAnimator;
    private BalloonListener mListener;
    private boolean mPopped;

    public Balloon(Context context) {
        super(context);
    }

    public Balloon(Context context, int color, int rawHeight) {
        super(context);

        mListener = (BalloonListener) context;

        /*this.setImageResource(R.drawable.balloon);
        this.setColorFilter(color);*/

        Drawable ballon = ContextCompat.getDrawable(context, R.drawable.balloon);
//        Objects.requireNonNull(ballon).setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        Objects.requireNonNull(ballon).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        Drawable[] layers = new Drawable[2];
        layers[0] = ballon;
        layers[1] = ContextCompat.getDrawable(context, R.drawable.efekballon);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        this.setImageDrawable(layerDrawable);

        int rawWidth = rawHeight / 2;

        int dpHeight = PixelHelper.pixelsToDp(rawHeight, context);
        int dpWidth = PixelHelper.pixelsToDp(rawWidth, context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpWidth, dpHeight);
        setLayoutParams(params);
    }

    public void releaseBalloon(int screenHeight, int duration) {
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(duration);
        mAnimator.setFloatValues(screenHeight, 0f);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setTarget(this);
        mAnimator.addListener(this);
        mAnimator.addUpdateListener(this);
        mAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (!mPopped) {
            mListener.popBalloon(this, false);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        setY((float) valueAnimator.getAnimatedValue());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mPopped && event.getAction() == MotionEvent.ACTION_DOWN) {
            mListener.popBalloon(this, true);
            mPopped = true;
            mAnimator.cancel();
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            performClick();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setPopped(boolean popped) {
        mPopped = popped;
        if (popped) {
            mAnimator.cancel();
        }

    }

    public interface BalloonListener {
        void popBalloon(Balloon balloon, boolean userTouch);
    }

}
