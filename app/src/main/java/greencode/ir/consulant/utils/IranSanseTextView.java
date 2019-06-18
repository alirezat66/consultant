package greencode.ir.consulant.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import greencode.ir.consulant.R;
import okhttp3.internal.Util;

public class IranSanseTextView extends android.support.v7.widget.AppCompatTextView {
    public IranSanseTextView(Context context) {
        super(context);

        this.setTypeface( Utility.getRegularTypeFace(context));
    }

    public IranSanseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface( Utility.getRegularTypeFace(context));

    }

    public IranSanseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface( Utility.getRegularTypeFace(context));

    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}
