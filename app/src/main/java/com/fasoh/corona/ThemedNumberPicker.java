package com.fasoh.corona;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import androidx.appcompat.view.ContextThemeWrapper;

public class ThemedNumberPicker extends NumberPicker {

    public ThemedNumberPicker(Context context) {
        this(context, null);
    }

    public ThemedNumberPicker(Context context, AttributeSet attrs) {
        // wrap the current context in the style we defined before
        super(new ContextThemeWrapper(context, R.style.PickerStyle), attrs);
    }
}