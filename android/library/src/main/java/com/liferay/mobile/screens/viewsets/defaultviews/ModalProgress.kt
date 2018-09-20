/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.mobile.screens.viewsets.defaultviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull

/**
 * @author Victor Oliveira
 */
class ModalProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val progressLabel by bindNonNull<TextView>(R.id.progress_text_view)

    init {
        inflate(context, R.layout.progress_default, this)
    }

    fun show(message: String?) {
        if (message.isNullOrEmpty()) {
            progressLabel.visibility = View.GONE
        } else {
            progressLabel.text = message
            progressLabel.visibility = View.VISIBLE
        }

        this.visibility = View.VISIBLE
    }

    fun hide() {
        this.visibility = View.GONE
    }
}
