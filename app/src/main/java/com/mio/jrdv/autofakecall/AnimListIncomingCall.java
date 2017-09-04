package com.mio.jrdv.autofakecall;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by esq00931 on 04/09/2017.
 */

public class AnimListIncomingCall extends ImageView {

    public AnimListIncomingCall(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AnimListIncomingCall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimListIncomingCall(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.note5_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable(){
            public void run(){
                frameAnimation.start();
            }
        });
    }
}
