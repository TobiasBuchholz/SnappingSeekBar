package com.tobishiba.snappingseekbar.library.utils;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * User: tobiasbuchholz
 * Date: 28.07.14 | Time: 14:18
 */
public class UiUtils {

    public static void setColor(final Drawable drawable, final int color) {
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
    }

    public static void setLeftMargin(final View view, final int leftMargin) {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.leftMargin = leftMargin;
        view.setLayoutParams(params);
    }

    public static void waitForLayoutPrepared(final View view, final LayoutPreparedListener listener) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if(viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    invokeLayoutListener();
                    removeGlobalOnLayoutListenerIfNeeded();
                }

                private void invokeLayoutListener() {
                    if(listener != null) {
                        listener.onLayoutPrepared(view);
                    }
                }

                private void removeGlobalOnLayoutListenerIfNeeded() {
                    final ViewTreeObserver laterViewTreeObserver = view.getViewTreeObserver();
                    if(laterViewTreeObserver != null && laterViewTreeObserver.isAlive()) {
                        laterViewTreeObserver.removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    public interface LayoutPreparedListener {
        public void onLayoutPrepared(final View preparedView);
    }

}
