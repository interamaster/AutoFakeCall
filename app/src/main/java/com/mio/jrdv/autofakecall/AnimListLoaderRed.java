package com.mio.jrdv.autofakecall;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by esq00931 on 04/09/2017.
 */

public class AnimListLoaderRed extends ImageView {

    public AnimListLoaderRed(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AnimListLoaderRed(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimListLoaderRed(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.s5_arrow_red);
        final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable(){
            public void run(){
                frameAnimation.start();
            }
        });
    }
}