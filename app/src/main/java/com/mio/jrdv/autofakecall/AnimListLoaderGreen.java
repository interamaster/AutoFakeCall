package com.mio.jrdv.autofakecall;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by esq00931 on 04/09/2017.
 *
 * copiado de aqui : https://stackoverflow.com/questions/8721458/using-android-animation-list
 *
 *

 I think the most elegant and versatile option is to extend from the ImageView class:

 public class Loader extends ImageView {

 public Loader(Context context, AttributeSet attrs, int defStyle) {
 super(context, attrs, defStyle);
 init();
 }

 public Loader(Context context, AttributeSet attrs) {
 super(context, attrs);
 init();
 }

 public Loader(Context context) {
 super(context);
 init();
 }

 private void init() {
 setBackgroundResource(R.drawable.loader);
 final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
 post(new Runnable(){
 public void run(){
 frameAnimation.start();
 }
 });
 }
 }
 The loader.xml located in the drawable folder:

 <---?xml version="1.0" encoding="utf-8"?>
 <animation-list xmlns:android="http://schemas.android.com/apk/res/android" >
 <item android:drawable="@drawable/loader_1" android:duration="50" />
 <item android:drawable="@drawable/loader_2" android:duration="50" />
 <item android:drawable="@drawable/loader_3" android:duration="50" />
 <item android:drawable="@drawable/loader_4" android:duration="50" />
 .....
 </animation-list>
 Now include in your views something as simple as this:

 <com.yourpackage.Loader
 android:layout_width="wrap_content"
 android:layout_height="wrap_content" />
 shareimprove this answer
 */

public class AnimListLoaderGreen extends ImageView {

    public AnimListLoaderGreen(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AnimListLoaderGreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimListLoaderGreen(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.s5_arrow_blue);
        final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable(){
            public void run(){
                frameAnimation.start();
            }
        });
    }
}