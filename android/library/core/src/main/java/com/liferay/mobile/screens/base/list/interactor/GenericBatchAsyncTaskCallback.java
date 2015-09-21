package com.liferay.mobile.screens.base.list.interactor;

import com.liferay.mobile.android.callback.typed.GenericBatchCallback;
import com.liferay.mobile.android.callback.typed.GenericCallback;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * @author Javier Gamarra
 */
public abstract class GenericBatchAsyncTaskCallback<T> extends GenericCallback<T> {

	public ArrayList<T> inBackground(ArrayList<T> results) throws Exception {
		return results;
	}

	@Override
	public T inBackground(JSONArray jsonArray) throws Exception {
		return inBackground(transform(jsonArray));
	}
}
