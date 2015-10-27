package com.uae.tra_smart_services_cutter.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.TextView;

import com.uae.tra_smart_services_cutter.R;

/**
 * Created by Mikazme on 29/09/2015.
 */
public abstract class LayoutDirectionUtils {

    public static void setDrawableStart(final Context _context, final TextView _textView,
                                        @DrawableRes final int _drawableRes) {
        if (_context.getResources().getConfiguration().locale.getLanguage().equals("ar")) {
            _textView.setCompoundDrawablesWithIntrinsicBounds(null, null, ImageUtils.getFilteredDrawableByTheme(_context, _drawableRes, R.attr.authorizationDrawableColors), null);
        } else {
            _textView.setCompoundDrawablesWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(_context, _drawableRes, R.attr.authorizationDrawableColors), null, null, null);
        }
    }

}
