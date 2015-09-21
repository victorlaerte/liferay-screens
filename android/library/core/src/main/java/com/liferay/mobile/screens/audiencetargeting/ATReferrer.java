package com.liferay.mobile.screens.audiencetargeting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class ATReferrer {

	public static final String USER_SEGMENT = "com.liferay.content.targeting.model.UserSegment";
	public static final String CAMPAIGN = "com.liferay.content.targeting.model.Campaign";
	public static final String CONSUMER = "com.liferay.consumer.manager.model.Consumer";
	public static final String PLACEHOLDER = "com.liferay.consumer.manager.extension.screens.model.Placeholder";

	public ATReferrer(String className, long classPK) {
		_className = className;
		_classPKs.add(classPK);
	}

	public ATReferrer(String className, List<Long> classPKs) {
		_className = className;
		_classPKs = classPKs;
	}

	public String getFormattedClassPKs() {
		String delimiter = "";
		StringBuilder builder = new StringBuilder();
		for (long classPK : _classPKs) {
			builder.append(delimiter).append(classPK);
			delimiter = ",";
		}
		return builder.toString();
	}

	public List<Long> getClassPKs() {
		return _classPKs;
	}

	public String getClassName() {
		return _className;
	}

	private final String _className;
	private List<Long> _classPKs = new ArrayList<>();


}
