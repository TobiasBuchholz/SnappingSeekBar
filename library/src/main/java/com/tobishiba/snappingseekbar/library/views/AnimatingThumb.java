package com.tobishiba.snappingseekbar.library.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.tobishiba.snappingseekbar.R;
import com.tobishiba.snappingseekbar.library.utils.UiUtils;

/**
 * User: tobiasbuchholz @ PressMatrix GmbH
 * Date: 20.11.14 | Time: 23:51
 */
public class AnimatingThumb extends View {
    private static final long       ANIMATION_DURATION = 300;
    private final Context           mContext;
    private int                     mWidth;
    private int                     mHeight;
    private OnSizeChangedListener   mOnSizeChangedListener;

    public AnimatingThumb(final Context context) {
        this(context, null);
    }

    public AnimatingThumb(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        final int size = (int) (12 * getResources().getDisplayMetrics().density);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        setBackgroundResource(R.drawable.circle_background);
        setLayoutParams(params);
    }

    public void setColor(final int color) {
        UiUtils.setColor(getBackground(), color);
    }

    public void show() {
        animate().scaleX(1)
                 .scaleY(1)
                 .setStartDelay(0)
                 .setDuration(ANIMATION_DURATION)
                 .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.accelerate_decelerate))
                 .setListener(null);
    }

    public void showWithDelay(final int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }, delay);
    }

    public boolean isVisible() {
        return getScaleX() == 1;
    }

    public void hide() {
        animate().scaleX(0)
                 .scaleY(0)
                 .setStartDelay(0)
                 .setDuration(200)
                 .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.accelerate_decelerate))
                 .setListener(null);
    }

    public void animateToX(final int x) {
        animate().translationX(x - mWidth/2)
                 .setStartDelay(0)
                 .setDuration(200)
                 .setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.interpolator.linear))
                 .setListener(null);
    }

    @Override
    protected void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mWidth = width;
        mHeight = height;
        invokeOnSizeChangedListener(width, height, oldWidth, oldHeight);
    }

    private void invokeOnSizeChangedListener(final int width, final int height, final int oldWidth, final int oldHeight) {
        if(mOnSizeChangedListener != null) {
            mOnSizeChangedListener.onSizeChanged(width, height, oldWidth, oldHeight);
        }
    }

    @Override
    public void setTranslationX(final float translationX) {
        super.setTranslationX(translationX - mWidth/2);
    }


    public void setOnSizeChangedListener(final OnSizeChangedListener listener) {
        mOnSizeChangedListener = listener;
    }

    public interface OnSizeChangedListener {
        public void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight);
    }
}
