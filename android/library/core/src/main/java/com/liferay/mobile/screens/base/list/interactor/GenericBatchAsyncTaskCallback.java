package com.liferay.mobile.screens.base.list.interactor;

import com.liferay.mobile.android.callback.typed.GenericBatchCallback;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * @author Javier Gamarra
 */
public abstract class GenericBatchAsyncTaskCallback<T>
	extends GenericBatchCallback<T> {

	public ArrayList<T> inBackground(ArrayList<T> results) throws Exception {
		return results;
	}

	@Override
	public T inBackground(JSONArray jsonArray) throws Exception {
		results = new ArrayList<>();
		results.add(transform(jsonArray));
		results = inBackground(results);

		return null;
	}

	public abstract void onSuccess(ArrayList<T> results);

	@Override
	public void onSuccess(T result) {
	}

	protected ArrayList<T> results;

}