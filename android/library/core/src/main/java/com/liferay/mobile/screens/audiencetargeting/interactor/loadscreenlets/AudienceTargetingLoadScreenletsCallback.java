package com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingLoadScreenletsCallback
	extends InteractorAsyncTaskCallback<Object> {

	public AudienceTargetingLoadScreenletsCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public Object transform(Object obj) throws Exception {
		return obj;
	}

	@Override
	protected BasicEvent createEvent(final int targetScreenletId, final Object result) {
		return new AudienceTargetingScreenletsLoadedEvent(targetScreenletId, (JSONArray) result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new AudienceTargetingScreenletsLoadedEvent(targetScreenletId, e);
	}

}
