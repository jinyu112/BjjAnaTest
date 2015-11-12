package example.org.bjjanatest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;


public class MyEditText extends EditText implements View.OnFocusChangeListener {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaLTStd-LightCond.otf");
        //setTypeface(tf);
        setOnFocusChangeListener(this);
        this.setTextColor(Color.BLACK);
    }

    @Override
    public void onFocusChange(View v,boolean hasFocus) {
        if (hasFocus) {
//            this.setTextColor(Color.parseColor("#ffffff"));
            this.setText("");
        }
        else {
//            this.setTextColor(Color.parseColor("#3298da"));
        }
    }


}