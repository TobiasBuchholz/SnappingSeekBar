package com.tobishiba.snappingseekbar.library.views;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * User: tobiasbuchholz
 * Date: 28.07.14 | Time: 14:18
 */
public class ProgressBarAnimation extends Animation {
    private ProgressBar mProgressBar;
    private float       mFrom;
    private float       mTo;

    public ProgressBarAnimation(final ProgressBar progressBar, final float from, final float to) {
        super();
        this.mProgressBar = progressBar;
        this.mFrom = from;
        this.mTo = to;
    }

    @Override
    protected void applyTransformation(final float interpolatedTime, final Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = mFrom + (mTo - mFrom) * interpolatedTime;
        mProgressBar.setProgress((int) value);
    }

}