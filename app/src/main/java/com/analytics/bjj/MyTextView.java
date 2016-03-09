package com.analytics.bjj;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
//                "fonts/HelveticaLTStd-Roman.otf");
        this.setTextColor(Color.parseColor("#262626"));
        //this.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
//        setTypeface(tf);
    }

}