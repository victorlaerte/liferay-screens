package com.liferay.mobile.screens.bankofwesteros.audience;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.liferay.mobile.screens.audiencetargeting.AudienceListener;
import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingManager;

public class AudienceTargetingHelper {

	public static final String IF_OLD_DONT_ALLOW_MESSAGES = "show_messages_button";
	public static final String IF_DEVELOPER_SHOW_BUTTON = "show_demo_resources_button";
	public static final String IF_MARKETING_SHOW_DIALOG_TO_NEW_FORM = "show_form_dialog";
	public static final String IF_SALES_CHANGE_THEME = "change_app_theme";

	public static void changeThemeOfIssuesListIfSales(final AudienceListener listener) {
		AudienceTargetingManager manager = new AudienceTargetingManager(2);
		manager.getCustomContent(IF_SALES_CHANGE_THEME, listener);
	}

	public static void checkIfOldToShowMessages(final View messageButton) {
		AudienceTargetingManager manager = new AudienceTargetingManager(messageButton.getId());
		manager.getCustomContent(IF_OLD_DONT_ALLOW_MESSAGES, new AudienceListener() {
			@Override
			public void onSuccess(String result) {
				if (result != null && "true".equalsIgnoreCase(result)) {
					boolean showOldMessages = Boolean.valueOf(result);
					messageButton.setVisibility(showOldMessages ? View.VISIBLE : View.GONE);
				}
			}
		});
	}

	public static void checkIfDeveloperCanShowResources(final View demoResources) {
		AudienceTargetingManager manager = new AudienceTargetingManager(demoResources.getId());
		manager.getCustomContent(IF_DEVELOPER_SHOW_BUTTON, new AudienceListener() {
			@Override
			public void onSuccess(String result) {
				if (result != null && "true".equalsIgnoreCase(result)) {
					boolean showDeveloperButton = Boolean.valueOf(result);
					demoResources.setVisibility(showDeveloperButton ? View.VISIBLE : View.GONE);
				}
			}
		});
	}

	public static void checkIfMarketingAndShowNewForm(final Activity activity) {
		AudienceTargetingManager manager = new AudienceTargetingManager(1);
		manager.getCustomContent(IF_MARKETING_SHOW_DIALOG_TO_NEW_FORM, new AudienceListener() {
			@Override
			public void onSuccess(String result) {
				if (result != null && "true".equalsIgnoreCase(result)) {
					showDialog(activity);
				}
			}
		});
	}

	private static void showDialog(final Activity activity) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("New marketing poll");
		builder.setMessage("There is a new poll available, do you want to check it?");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.startActivity(new Intent(activity, NewPollActivity.class));
			}
		});
		builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
