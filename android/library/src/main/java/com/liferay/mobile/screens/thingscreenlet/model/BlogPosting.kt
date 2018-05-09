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

package com.liferay.mobile.screens.thingscreenlet.model

import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Row
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.mobile.sdk.apio.extensions.asDate
import com.liferay.mobile.sdk.apio.model.Relation
import com.liferay.mobile.sdk.apio.model.Thing
import com.liferay.mobile.sdk.apio.model.get
import java.util.Date

data class BlogPosting(
	val headline: String?,
	val alternativeHeadline: String?,
	val articleBody: String?,
	val creator: Relation?,
	val createDate: Date?) {

	companion object {
		val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
			mutableMapOf(
				Detail to R.layout.blog_posting_detail_default,
				Row to R.layout.blog_posting_row_default
			)

		val converter: (Thing) -> Any = {
			BlogPosting(
				it["headline"] as? String,
				it["alternativeHeadline"] as? String,
				it["articleBody"] as? String,
				it["creator"] as? Relation,
				(it["dateCreated"] as? String)?.asDate())
		}
	}
}
