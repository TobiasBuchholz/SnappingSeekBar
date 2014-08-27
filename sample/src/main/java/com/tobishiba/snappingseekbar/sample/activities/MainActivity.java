package com.tobishiba.snappingseekbar.sample.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import com.tobishiba.snappingseekbar.R;
import com.tobishiba.snappingseekbar.sample.inappbilling.GoogleIabHelper;
import com.tobishiba.snappingseekbar.sample.inappbilling.GoogleReceipt;
import com.tobishiba.snappingseekbar.library.utils.UiUtils;
import com.tobishiba.snappingseekbar.library.views.SnappingSeekBar;

/**
 * User: tobiasbuchholz
 * Date: 28.07.14 | Time: 14:18
 */
public class MainActivity extends Activity implements SnappingSeekBar.OnItemSelectionListener {
    private GoogleIabHelper mIabHelper;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInAppBillingHelper();
        initActionBar();
        initSeekBarsFromLayout();
        initSeekBarProgrammatically();
    }

    private void initInAppBillingHelper() {
        mIabHelper = new GoogleIabHelper(this);
        mIabHelper.setUpServiceConnection();
    }

    private void initActionBar() {
        final ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grey_darker)));
        }
    }

    private void initSeekBarsFromLayout() {
        initSeekBarFromLayout(R.id.activity_main_seek_bar_without_texts, 1);
        initSeekBarFromLayout(R.id.activity_main_seek_bar_with_numbers, 1);
        initSeekBarFromLayout(R.id.activity_main_seek_bar_with_strings, 2);
        initSeekBarFromLayout(R.id.activity_main_seek_bar_with_different_colors, 4);
        initSeekBarFromLayout(R.id.activity_main_seek_bar_with_different_drawables, 3);
        initSeekBarFromLayout(R.id.activity_main_seek_bar_with_big_indicators, 2);
    }

    private void initSeekBarFromLayout(final int resId, final int progressIndex) {
        final SnappingSeekBar snappingSeekBar = (SnappingSeekBar) findViewById(resId);
        snappingSeekBar.setProgressToIndex(progressIndex);
        snappingSeekBar.setOnItemSelectionListener(this);
    }

    private void initSeekBarProgrammatically() {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main_layout);
        final SnappingSeekBar snappingSeekBar = createSnappingSeekBarProgrammatically();
        final int margin = (int) (20 * getResources().getDisplayMetrics().density);
        params.setMargins(margin, margin, margin, margin);
        layout.addView(snappingSeekBar, params);
        UiUtils.waitForLayoutPrepared(snappingSeekBar, new UiUtils.LayoutPreparedListener() {
            @Override
            public void onLayoutPrepared(final View preparedView) {
                snappingSeekBar.setProgressToIndex(1);
            }
        });
    }

    private SnappingSeekBar createSnappingSeekBarProgrammatically() {
        final Resources resources = getResources();
        final SnappingSeekBar snappingSeekBar = new SnappingSeekBar(this);
        snappingSeekBar.setProgressDrawable(R.drawable.apptheme_scrubber_progress_horizontal_holo_light);
        snappingSeekBar.setThumbDrawable(R.drawable.apptheme_scrubber_control_selector_holo_light);
        snappingSeekBar.setItems(new String[]{getString(R.string.programmatically), getString(R.string.created), getString(R.string.seekBar)});
        snappingSeekBar.setProgressColor(resources.getColor(R.color.green_darker));
        snappingSeekBar.setThumbnailColor(resources.getColor(R.color.yellow_light));
        snappingSeekBar.setTextIndicatorColor(resources.getColor(R.color.red_darker));
        snappingSeekBar.setIndicatorColor(resources.getColor(R.color.green_light));
        snappingSeekBar.setTextSize(14);
        snappingSeekBar.setIndicatorSize(14);
        snappingSeekBar.setOnItemSelectionListener(this);
        return snappingSeekBar;
    }

    @Override
    public void onItemSelected(final int itemIndex, final String itemString) {
        Toast.makeText(this, getString(R.string.toast_item_selected, itemIndex, itemString), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        handleGooglePurchaseIntent(requestCode, resultCode, intent);

    }

    public void handleGooglePurchaseIntent(final int requestCode, final int resultCode, final Intent intent) {
        if (isValidPurchaseIntent(requestCode, resultCode, intent)) {
            consumeItemAsync(intent);
        }
    }

    private void consumeItemAsync(final Intent intent) {
        final String receiptJson = mIabHelper.getPurchaseReceipt(intent);
        final GoogleReceipt googleReceipt = new Gson().fromJson(receiptJson, GoogleReceipt.class);
        mIabHelper.consumeItemAsync(googleReceipt.mPurchaseToken);
    }

    private boolean isValidPurchaseIntent(final int requestCode, final int resultCode, final Intent intent) {
        return resultCode == Activity.RESULT_OK && mIabHelper.isValidPurchaseIntent(requestCode, intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.clear();
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(item.getItemId() == R.id.action_beer) {
            mIabHelper.purchaseItem(this, "beer_donation");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIabHelper.unbindServiceConnection();
    }
}
