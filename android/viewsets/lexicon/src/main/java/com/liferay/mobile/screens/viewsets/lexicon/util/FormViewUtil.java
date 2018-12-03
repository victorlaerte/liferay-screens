/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.lexicon.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.viewsets.lexicon.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Victor Oliveira
 */
public class FormViewUtil {

    public static int convertDpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static void setupBackground(Context context, boolean valid, View view) {
        Drawable drawable;

        if (valid) {
            drawable = context.getResources().getDrawable(R.drawable.lexicon_edit_text_selector);
        } else {
            drawable = context.getResources().getDrawable(R.drawable.lexicon_edit_text_error_drawable);
        }

        view.setBackground(drawable);
    }

    public static void setupErrorView(boolean valid, View errorView) {
        if (errorView != null) {
            errorView.setVisibility(valid ? GONE : VISIBLE);
        }
    }

    public static void setupErrorView(boolean valid, View errorView, String errorMessage) {
        if (errorView != null) {
            TextView textView = errorView.findViewById(R.id.message_error_view);
            textView.setText(errorMessage);
            errorView.setVisibility(valid ? GONE : VISIBLE);
        }
    }
}
