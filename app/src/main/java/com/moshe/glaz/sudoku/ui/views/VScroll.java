package com.moshe.glaz.sudoku.ui.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class VScroll extends ScrollView {

    public VScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VScroll(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
