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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxRatingBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.BooleanField;
import com.liferay.mobile.screens.ddl.model.NumberField;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldCheckboxView extends LinearLayout
    implements DDLFieldViewModel<BooleanField>, CompoundButton.OnCheckedChangeListener {

    protected BooleanField field;
    protected SwitchCompat switchCompat;
    protected View parentView;
    protected Observable<BooleanField> onChangedValueObservable = Observable.empty();

    public DDLFieldCheckboxView(Context context) {
        super(context);
    }

    public DDLFieldCheckboxView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDLFieldCheckboxView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public BooleanField getField() {
        return field;
    }

    @Override
    public void setField(BooleanField field) {
        this.field = field;

        if (this.field.isShowLabel()) {
            switchCompat.setHint("");
            switchCompat.setText(this.field.getLabel());
        } else {
            switchCompat.setHint(this.field.getLabel());
            switchCompat.setText("");
        }

        refresh();
    }

    @Override
    public void refresh() {
        switchCompat.setChecked(field.getCurrentValue());
    }

    @Override
    public void onPostValidation(boolean valid) {
        //This field is always valid because it has always a value
    }

    @Override
    public View getParentView() {
        return parentView;
    }

    @Override
    public void setParentView(View view) {
        parentView = view;
    }

    @Override
    public Observable<BooleanField> getOnChangedValueObservable() {
        return onChangedValueObservable;
    }

    @Override
    public void setUpdateMode(boolean enabled) {
        switchCompat.setEnabled(enabled);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        field.setCurrentValue(isChecked);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setSaveEnabled(false);

        switchCompat = findViewById(R.id.liferay_ddl_switch);

        switchCompat.setOnCheckedChangeListener(this);

        onChangedValueObservable = RxCompoundButton.checkedChanges(switchCompat)
            .distinctUntilChanged()
            .map(new Func1<Boolean, BooleanField>() {
                @Override
                public BooleanField call(Boolean aBoolean) {
                    return field;
                }
            });
    }
}