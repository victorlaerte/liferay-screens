package com.liferay.mobile.screens.consumerreports;

import android.content.Context;
import android.content.SharedPreferences;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

import java.util.Date;

/**
 * @author Javier Gamarra
 */
public class ConsumerTrackingActions {

	public static final String EVENT_INSTALLATION = "installation";
	public static final String EVENT_SESSION = "session";
	public static final String EVENT_SESSION_ON_SCREEN = "sessionOnScreen";
	public static final String INSTALLED = "installed";

	public static final String FORM_VIEW = "formView";
	public static final String FORM_SUBMIT = "formSubmit";
	//FIXME user interaction? button click?
	// public static final String FORM_INTERACTION = "formInteraction";
	// public static final String BUTTON_CLICK = "buttonClick";

	//FIXME elementId not null

	public static void postInstallation(Context context) {

		LiferayScreensContext.init(context);

		SharedPreferences preferences = context.getSharedPreferences(EVENT_INSTALLATION, Context.MODE_PRIVATE);
		if (!preferences.contains(INSTALLED)) {

			post(context, EVENT_INSTALLATION);

			preferences.edit().putBoolean(INSTALLED, true).apply();
		}
	}

	public static void postSession(Context context) {
		post(context, EVENT_SESSION);
	}

	public static void postSessionOnScreen(Context context, long time) {
		post(context, EVENT_SESSION_ON_SCREEN, "", 0, "-", time);
	}

	public static void ddlView(Context context, long id) {

		//FIXME recordId ? recordSetId

		ddl(context, FORM_VIEW, id);
	}

	public static void ddlSubmit(Context context, long id) {
		ddl(context, FORM_SUBMIT, id);
	}

	public static void ddl(Context context, String formAction, long recordId) {
		post(context, formAction, "com.liferay.portlet.dynamicdatalists.model.DDLRecordSet", recordId, "-", 1);
	}

	public static void post(Context context, final String eventType) {
		post(context, eventType, "", 0, "-", 1);
	}

	public static void post(Context context, final String eventType, final String className,
							final long classPK, final String elementId, final long dataValue) {

		LiferayScreensContext.init(context);

		final long consumerId = LiferayServerContext.getConsumerId();
		final long companyId = LiferayServerContext.getCompanyId();
		final long groupId = LiferayServerContext.getGroupId();

		final Session session = getSession(context);

		//FIXME userId?
		//final long userId = SessionContext.getLoggedUser() == null ? 0 : SessionContext.getLoggedUser().getId();

		Executor.getExecutor().submit(new Runnable() {
			@Override
			public void run() {

				ConsumerdataeventService consumerdataeventService = new ConsumerdataeventService(session);

				try {
					JSONObject jsonObject = consumerdataeventService.addConsumerDataEvent(companyId, groupId,
						consumerId, eventType, className, classPK, elementId, new Date().getTime(), dataValue);
					LiferayLogger.e(jsonObject.toString());
				}
				catch (Exception e) {
					LiferayLogger.e("Error sending consumer");
				}

			}
		});
	}

	private static Session getSession(Context context) {
		if (SessionContext.hasSession()) {
			return SessionContext.createSessionFromCurrentSession();
		}
		else {
			String anonymousUserName = context.getString(R.string.liferay_anonymous_username);
			String anonymousPassword = context.getString(R.string.liferay_anonymous_password);
			BasicAuthentication authentication = new BasicAuthentication(anonymousUserName, anonymousPassword);
			return new SessionImpl(LiferayServerContext.getServer(), authentication);
		}
	}


}
