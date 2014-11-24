package com.tobishiba.snappingseekbar.library.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.tobishiba.snappingseekbar.library.utils.UiUtils;

/**
 * User: tobiasbuchholz @ PressMatrix GmbH
 * Date: 19.11.14 | Time: 10:13
 */
public class TextBubble extends TextView {
    public static final int         ANIMATION_DURATION  = 300;
    private final Context           mContext;
    private int                     mSize;
    private int                     mWidth;
    private int                     mHeight;
    private Path                    mBackgroundPath;
    private Paint                   mBackgroundPaint;
    private float                   mDensity;
    private OnSizeChangedListener   mOnSizeChangedListener;

    public TextBubble(final Context context, final int size) {
        this(context, null, size);
    }

    public TextBubble(final Context context, final AttributeSet attrs, final int size) {
        super(context, attrs);
        mContext = context;
        mSize = size;
        initDensity();
        initLayoutParams();
        initBackgroundPaint();
        setScaleX(0);
        setScaleY(0);
        setGravity(Gravity.CENTER);
    }

    private void initDensity() {
        mDensity = getResources().getDisplayMetrics().density;
    }

    private void initLayoutParams() {
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mSize, mSize * 7/5);
        setLayoutParams(params);
    }

    private void initBackgroundPaint() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.WHITE);
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void show() {
        animate().scaleX(1)
                 .scaleY(1)
                 .translationY(0)
                 .setStartDelay(0)
                 .setDuration(ANIMATION_DURATION)
                 .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.accelerate_decelerate))
                 .setListener(null);
    }

    public boolean isVisible() {
        return getScaleX() == 1;
    }

    public void hide() {
        animate().scaleX(0)
                .scaleY(0)
                .setStartDelay(0)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.accelerate_decelerate))
                .setListener(null);
    }

    public void animateToXAndHideWithDelay(final int x, final int hideDelay) {
        animate().translationX(x - mWidth / 2)
                 .setDuration(200)
                 .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.linear))
                 .setListener(new AnimatorListenerAdapter() {
                     @Override
                     public void onAnimationEnd(final Animator animation) {
                         hideWithDelay(hideDelay);
                     }
                 });
    }

    public void animateToX(final int x) {
        animate().translationX(x - mWidth / 2)
                .setStartDelay(0)
                .setDuration(200)
                .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.linear))
                .setListener(null);
    }

    private void hideWithDelay(final int delay) {
        animate().scaleX(0)
                .scaleY(0)
                .translationY(mHeight/2)
                .setStartDelay(delay)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.accelerate_decelerate))
                .setListener(null);
    }

    @Override
    public void setBackgroundColor(final int color) {
        mBackgroundPaint.setColor(color);
    }

    @Override
    public void setTranslationX(final float translationX) {
        super.setTranslationX(translationX - mWidth / 2);
    }

    @Override
    protected void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mWidth = width;
        mHeight = height;
        initBackgroundPath();
        invokeOnSizeChangedListener(width, height, oldWidth, oldHeight);
    }

    private void initBackgroundPath() {
        if(mBackgroundPath == null) {
            mBackgroundPath = new Path();
            mBackgroundPath.moveTo(mWidth / 2, mHeight);
            mBackgroundPath.quadTo(0, mHeight/2 + 20/3 * mDensity, 0, mHeight/2);
            mBackgroundPath.cubicTo(0, 0, mWidth, 0, mWidth, mHeight/2);
            mBackgroundPath.quadTo(mWidth, mHeight/2 + 20/3 * mDensity, mWidth/2, mHeight);
            mBackgroundPath.close();
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        canvas.drawPath(mBackgroundPath, mBackgroundPaint);
        super.onDraw(canvas);
    }

    private void invokeOnSizeChangedListener(final int width, final int height, final int oldWidth, final int oldHeight) {
        if(mOnSizeChangedListener != null) {
            mOnSizeChangedListener.onSizeChanged(width, height, oldWidth, oldHeight);
        }
    }

    public void setOnSizeChangedListener(final OnSizeChangedListener onSizeChangedListener) {
        mOnSizeChangedListener = onSizeChangedListener;
    }

    public interface OnSizeChangedListener {
        public void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight);
    }
}
