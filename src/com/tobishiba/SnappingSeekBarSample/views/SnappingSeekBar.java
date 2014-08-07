package com.tobishiba.SnappingSeekBarSample.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.tobishiba.SnappingSeekBarSample.R;
import com.tobishiba.SnappingSeekBarSample.utils.UiUtils;

/**
 * User: tobiasbuchholz
 * Date: 28.07.14 | Time: 14:18
 */
public class SnappingSeekBar extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {
    private Context                 mContext;
    private SeekBar                 mSeekBar;
    private int                     mItemsAmount;
    private int                     mProgress;
    private String[]                mItems              = new String[0];
    private int                     mProgressDrawableId;
    private int                     mThumbDrawableId;
    private int                     mIndicatorDrawableId;
    private float                   mIndicatorSize;
    private int                     mSeekBarColor;
    private int                     mIndicatorColor;
    private float                   mTextIndicatorTopMargin;
    private int                     mThumbnailColor;
    private int                     mTextIndicatorColor;
    private int                     mTextStyleId;
    private float                   mTextSize;
    private Drawable                mProgressDrawable;
    private Drawable                mThumbDrawable;
    private OnItemSelectionListener mOnItemSelectionListener;

    public SnappingSeekBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        handleAttributeSet(attrs);
        initViews();
    }

    private void handleAttributeSet(final AttributeSet attrs) {
        final TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.SnappingSeekBar, 0, 0);
        try {
            initDrawables(typedArray);
            initItems(typedArray);
            initIndicatorAttributes(typedArray);
            initTextAttributes(typedArray);
            initColors(typedArray);
        } finally {
            typedArray.recycle();
        }
    }

    private void initDrawables(final TypedArray typedArray) {
        mProgressDrawableId = typedArray.getResourceId(R.styleable.SnappingSeekBar_progressDrawable, R.drawable.apptheme_scrubber_progress_horizontal_holo_light);
        mThumbDrawableId = typedArray.getResourceId(R.styleable.SnappingSeekBar_thumb, R.drawable.apptheme_scrubber_control_selector_holo_light);
        mIndicatorDrawableId = typedArray.getResourceId(R.styleable.SnappingSeekBar_indicatorDrawable, R.drawable.circle_background);
    }

    private void initItems(final TypedArray typedArray) {
        final int valueArrayId = typedArray.getResourceId(R.styleable.SnappingSeekBar_itemsArrayId, 0);
        if(valueArrayId > 0) {
            setItems(mContext.getResources().getStringArray(valueArrayId));
        } else {
            setItemsAmount(typedArray.getInteger(R.styleable.SnappingSeekBar_itemsAmount, 10));
        }
    }

    public void setItems(final String[] items) {
        mItems = items;
        mItemsAmount = mItems.length;
    }

    public void setItemsAmount(final int itemsAmount) {
        mItemsAmount = itemsAmount;
    }

    private void initIndicatorAttributes(final TypedArray typedArray) {
        mIndicatorSize = typedArray.getDimension(R.styleable.SnappingSeekBar_indicatorSize, 11.3f * getDensity());
    }

    private float getDensity() {
        return mContext.getResources().getDisplayMetrics().density;
    }

    private void initTextAttributes(final TypedArray typedArray) {
        final float density = getDensity();
        mTextIndicatorTopMargin = typedArray.getDimension(R.styleable.SnappingSeekBar_textIndicatorTopMargin, 35 * density);
        mTextStyleId = typedArray.getResourceId(R.styleable.SnappingSeekBar_textStyle, 0);
        mTextSize = typedArray.getDimension(R.styleable.SnappingSeekBar_textSize, 12 * density);
    }

    private void initColors(final TypedArray typedArray) {
        mSeekBarColor = typedArray.getColor(R.styleable.SnappingSeekBar_progressColor, Color.WHITE);
        mIndicatorColor = typedArray.getColor(R.styleable.SnappingSeekBar_indicatorColor, Color.WHITE);
        mThumbnailColor = typedArray.getColor(R.styleable.SnappingSeekBar_thumbnailColor, Color.WHITE);
        mTextIndicatorColor = typedArray.getColor(R.styleable.SnappingSeekBar_textIndicatorColor, Color.WHITE);
    }

    private void initViews() {
        initSeekBar();
        initIndicators();
    }

    private void initSeekBar() {
        final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSeekBar = new SeekBar(mContext);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setLayoutParams(params);
        setDrawablesToSeekBar();
        addView(mSeekBar, params);
    }

    private void setDrawablesToSeekBar() {
        final Resources resources = getResources();
        mProgressDrawable = resources.getDrawable(mProgressDrawableId);
        mThumbDrawable = resources.getDrawable(mThumbDrawableId);
        UiUtils.setColor(mProgressDrawable, mSeekBarColor);
        UiUtils.setColor(mThumbDrawable, mThumbnailColor);
        mSeekBar.setProgressDrawable(mProgressDrawable);
        mSeekBar.setThumb(mThumbDrawable);
        final int thumbnailWidth = mThumbDrawable.getIntrinsicWidth();
        mSeekBar.setPadding(thumbnailWidth / 2, 0, thumbnailWidth / 2, 0);
    }

    private void initIndicators() {
        UiUtils.waitForLayoutPrepared(mSeekBar, new UiUtils.LayoutPreparedListener() {
            @Override
            public void onLayoutPrepared(final View preparedView) {
                final int seekBarWidth = preparedView.getWidth();
                initIndicators(seekBarWidth);
            }
        });
    }

    private void initIndicators(final int seekBarWidth) {
        for(int i = 0; i < mItemsAmount; i++) {
            addCircleIndicator(seekBarWidth, i);
            addTextIndicatorIfNeeded(seekBarWidth, i);
        }
    }

    private void addCircleIndicator(final int seekBarWidth, final int index) {
        final int thumbnailWidth = mThumbDrawable.getIntrinsicWidth();
        final int sectionFactor = 100 / (mItemsAmount - 1);
        final float seekBarWidthWithoutThumbOffset = seekBarWidth - thumbnailWidth;
        final LayoutParams indicatorParams = new LayoutParams((int) mIndicatorSize, (int) mIndicatorSize);
        final View indicator = new View(mContext);
        indicator.setBackgroundResource(mIndicatorDrawableId);
        UiUtils.setColor(indicator.getBackground(), mIndicatorColor);
        indicatorParams.leftMargin = (int) (seekBarWidthWithoutThumbOffset / 100 * index * sectionFactor + thumbnailWidth / 2 - mIndicatorSize / 2);
        indicatorParams.topMargin = mThumbDrawable.getIntrinsicHeight() / 2 - (int) (mIndicatorSize / 2);
        addView(indicator, indicatorParams);
    }

    private void addTextIndicatorIfNeeded(final int completeSeekBarWidth, final int index) {
        if(mItems.length == mItemsAmount) {
            addTextIndicator(completeSeekBarWidth, index);
        }
    }

    private void addTextIndicator(final int completeSeekBarWidth, final int index) {
        final int thumbnailWidth = mThumbDrawable.getIntrinsicWidth();
        final int sectionFactor = 100 / (mItemsAmount - 1);
        final float seekBarWidthWithoutThumbOffset = completeSeekBarWidth - thumbnailWidth;
        final LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textIndicator = new TextView(mContext);
        final int numberLeftMargin = (int) (seekBarWidthWithoutThumbOffset / 100 * index * sectionFactor + thumbnailWidth / 2);
        textIndicator.setText(mItems[index]);
        textIndicator.setTextSize(mTextSize / getDensity());
        textIndicator.setTextColor(mTextIndicatorColor);
        textIndicator.setTextAppearance(mContext, mTextStyleId);
        textParams.topMargin = (int) mTextIndicatorTopMargin;
        addView(textIndicator, textParams);
        UiUtils.waitForLayoutPrepared(textIndicator, createTextIndicatorLayoutPreparedListener(numberLeftMargin));
    }

    private UiUtils.LayoutPreparedListener createTextIndicatorLayoutPreparedListener(final int numberLeftMargin) {
        return new UiUtils.LayoutPreparedListener() {
            @Override
            public void onLayoutPrepared(final View preparedView) {
                final int layoutWidth = getWidth() - getPaddingRight();
                final int viewWidth = preparedView.getWidth();
                final int leftMargin = numberLeftMargin - viewWidth / 2;
                final int paddingLeft = getPaddingLeft();
                final int finalMargin = leftMargin < paddingLeft ? paddingLeft : leftMargin + viewWidth > layoutWidth ? layoutWidth - viewWidth : leftMargin;
                UiUtils.setLeftMargin(preparedView, finalMargin);
            }
        };
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
        mProgress = progress;
    }

    private void handleSnapToClosestValue() {
        final float sectionLength = 100 / (mItemsAmount - 1);
        final int selectedSection = (int) ((mProgress / sectionLength) + 0.5);
        final int valueToSnap = (int) (selectedSection * sectionLength);
        animateProgressBar(valueToSnap);
        invokeItemSelected(selectedSection);
    }

    private void animateProgressBar(final int toProgress) {
        final ProgressBarAnimation anim = new ProgressBarAnimation(mSeekBar, mSeekBar.getProgress(), toProgress);
        anim.setDuration(200);
        startAnimation(anim);
    }

    private void invokeItemSelected(final int selectedSection) {
        if(mOnItemSelectionListener != null) {
            mOnItemSelectionListener.onItemSelected(selectedSection, getItemString(selectedSection));
        }
    }

    private String getItemString(final int index) {
        if(mItems.length > index) {
            return mItems[index];
        }
        return "";
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {
        // do nothing
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
        handleSnapToClosestValue();
    }

    public Drawable getProgressDrawable() {
        return mProgressDrawable;
    }

    public Drawable getThumb() {
        return mThumbDrawable;
    }

    public int getProgress() {
        return mSeekBar.getProgress();
    }

    public void setProgress(final int progress) {
        mProgress = progress;
        handleSnapToClosestValue();
    }

    public void setProgressToIndex(final int index) {
        mProgress = getProgressForIndex(index);
        mSeekBar.setProgress(mProgress);
    }

    private int getProgressForIndex(final int index) {
        final float sectionLength = 100 / (mItemsAmount - 1);
        return (int) (index * sectionLength);
    }

    public void setProgressToIndexWithAnimation(final int index) {
        mProgress = getProgressForIndex(index);
        animateProgressBar(mProgress);
    }

    public int getSelectedItemIndex() {
        final float sectionLength = 100 / (mItemsAmount - 1);
        return (int) ((mProgress / sectionLength) + 0.5);
    }

    public void setOnItemSelectionListener(final OnItemSelectionListener listener) {
        mOnItemSelectionListener = listener;
    }

    public interface OnItemSelectionListener {
        public void onItemSelected(final int itemIndex, final String itemString);
    }
}
