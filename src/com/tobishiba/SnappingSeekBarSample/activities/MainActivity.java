package com.tobishiba.SnappingSeekBarSample.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.Gson;
import com.tobishiba.SnappingSeekBarSample.R;
import com.tobishiba.SnappingSeekBarSample.inappbilling.GoogleIabHelper;
import com.tobishiba.SnappingSeekBarSample.inappbilling.GoogleReceipt;
import com.tobishiba.SnappingSeekBarSample.views.SnappingSeekBar;

/**
 * User: tobiasbuchholz
 * Date: 28.07.14 | Time: 14:18
 */
public class MainActivity extends Activity implements SnappingSeekBar.OnItemSelectionListener {
    private GoogleIabHelper mIabHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInAppBillingHelper();
        initActionBar();
        initSeekBars();
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

    private void initSeekBars() {
        final SnappingSeekBar seekBarWithoutTexts = (SnappingSeekBar) findViewById(R.id.activity_main_seek_bar_without_texts);
        final SnappingSeekBar seekBarWithNumbers = (SnappingSeekBar) findViewById(R.id.activity_main_seek_bar_with_numbers);
        final SnappingSeekBar seekBarWithStrings = (SnappingSeekBar) findViewById(R.id.activity_main_seek_bar_with_strings);
        final SnappingSeekBar seekBarWithDifferentColors = (SnappingSeekBar) findViewById(R.id.activity_main_seek_bar_with_different_colors);
        final SnappingSeekBar seekBarWithDifferentDrawables = (SnappingSeekBar) findViewById(R.id.activity_main_seek_bar_with_different_drawables);
        final SnappingSeekBar seekBarWithBigIndicators = (SnappingSeekBar) findViewById(R.id.activity_main_seek_bar_with_bigg_indicators);

        seekBarWithoutTexts.setProgressToIndex(1);
        seekBarWithNumbers.setProgressToIndex(1);
        seekBarWithStrings.setProgressToIndex(2);
        seekBarWithDifferentColors.setProgressToIndex(3);
        seekBarWithDifferentDrawables.setProgressToIndex(3);
        seekBarWithBigIndicators.setProgressToIndex(3);

        seekBarWithoutTexts.setOnItemSelectionListener(this);
        seekBarWithNumbers.setOnItemSelectionListener(this);
        seekBarWithStrings.setOnItemSelectionListener(this);
        seekBarWithDifferentColors.setOnItemSelectionListener(this);
        seekBarWithDifferentDrawables.setOnItemSelectionListener(this);
        seekBarWithBigIndicators.setOnItemSelectionListener(this);
    }

    @Override
    public void onItemSelected(final int itemIndex, final String itemString) {
        Toast.makeText(this, getString(R.string.toast_item_selected, itemIndex, itemString), Toast.LENGTH_LONG).show();
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
