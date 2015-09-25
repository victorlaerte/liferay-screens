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

package com.liferay.mobile.screens.viewsets.defaultviews.webcontentdisplay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.audiencetargeting.ATTrackingActions;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayScreenlet;
import com.liferay.mobile.screens.webcontentdisplay.view.WebContentDisplayViewModel;

import java.lang.ref.WeakReference;

/**
 * @author Silvio Santos
 */
public class WebContentDisplayView extends FrameLayout
	implements WebContentDisplayViewModel {

	public WebContentDisplayView(Context context) {
		super(context);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public WebContentDisplayView(Context context, AttributeSet attributes) {

		super(context, attributes);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public WebContentDisplayView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	@Override
	public void showStartOperation(String actionName) {
		_progressBar.setVisibility(View.VISIBLE);
		_webView.setVisibility(View.GONE);
	}

	@Override
	public void showFinishOperation(String html) {
		_progressBar.setVisibility(View.GONE);
		_webView.setVisibility(View.VISIBLE);

		LiferayLogger.i("article loaded: " + html);

		String styledHtml = STYLES + "<div class=\"MobileCSS\">" + html + "</div>";

		//TODO check encoding
		_webView.loadDataWithBaseURL(
			LiferayServerContext.getServer(), styledHtml, "text/html", "utf-8",
			null);
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		_progressBar.setVisibility(View.GONE);
		_webView.setVisibility(View.VISIBLE);

		LiferayCrouton.error(getContext(), getContext().getString(R.string.loading_article_error), e);
		LiferayLogger.e(getContext().getString(R.string.loading_article_error), e);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_webView = (WebView) findViewById(R.id.liferay_webview);
		_webView.getSettings().setSupportMultipleWindows(true);
		_webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

//FIXME
				int end = url.indexOf("//");

				int end1 = url.indexOf("/", end + 2);

				String shortedUrl = url.substring(0, end1);
				ATTrackingActions.postATContent(getContext(), ATTrackingActions.CLICK,
					((WebContentDisplayScreenlet) getParent()).getAtResult(), shortedUrl);

				view.loadUrl(url);
				return false;
			}
		});
		_webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onCreateWindow(WebView webView, boolean dialog, boolean userGesture, Message resultMsg) {
				final Context context = webView.getContext();

				final WebView.HitTestResult result = webView.getHitTestResult();

				ATTrackingActions.postATContent(getContext(), ATTrackingActions.CLICK, ((WebContentDisplayScreenlet) getParent()).getAtResult(),
					result.getExtra());

				if (isALinkInsideAnImage(result)) {
					retrieveOriginalLinkAndRedirect();
				}
				else {
					String url = result.getExtra();
					launchLinkInNewActivity(context, url);
				}
				return false;
			}
		});
		_webView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(final String url, final String userAgent, final String contentDisposition, final String mimeType, final long contentLength) {
				launchLinkInNewActivity(getContext(), url);
			}
		});
		_progressBar = (ProgressBar) findViewById(R.id.liferay_webview_progress);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		WebContentDisplayScreenlet screenlet = (WebContentDisplayScreenlet) getParent();
		if (screenlet.isJavascriptEnabled()) {
			_webView.getSettings().setJavaScriptEnabled(true);
		}
	}

	private boolean isALinkInsideAnImage(final WebView.HitTestResult result) {
		return result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE;
	}

	private void retrieveOriginalLinkAndRedirect() {
		Handler handler = new ContextHandler(new WeakReference<>(getContext()));
		Message message = handler.obtainMessage();
		_webView.requestFocusNodeHref(message);
	}

	private static void launchLinkInNewActivity(final Context context, final String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	private static final String STYLES =
		"<style>" +
			".MobileCSS {padding: 4%; width: 92%;} " +
			".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, " +
			".MobileCSS h2, .MobileCSS h3{ " +
			"font-size: 110%; font-weight: 200;" +
			"font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;} " +
			".MobileCSS img { width: 100% !important; } " +
			".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }" +
			"</style>";
	private WebView _webView;
	private ProgressBar _progressBar;

	private static class ContextHandler extends Handler {

		public ContextHandler(WeakReference<Context> contextWeakReference) {
			_contextWeakReference = contextWeakReference;
		}

		@Override
		public void handleMessage(Message msg) {
			String url = (String) msg.getData().get("url");
			Context context = _contextWeakReference.get();
			if (context != null) {
				launchLinkInNewActivity(context, url);
			}
		}
		private WeakReference<Context> _contextWeakReference;
	}


}