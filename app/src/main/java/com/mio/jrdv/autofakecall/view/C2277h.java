package com.mio.jrdv.autofakecall.view;

import android.content.Context;
import android.util.DisplayMetrics;

public class C2277h {
    private static float f7902a = 0.0f;
    private static int f7903b = 0;
    private static int f7904c = 0;

    public static int m12974a(Context context) {
        if (f7903b == 0) {
            C2277h.m12977d(context);
        }
        return f7903b;
    }

    public static int m12975b(Context context) {
        if (f7904c == 0) {
            C2277h.m12977d(context);
        }
        return f7904c;
    }

    public static float m12976c(Context context) {
        if (f7902a == 0.0f) {
            C2277h.m12977d(context);
        }
        return f7902a;
    }

    private static void m12977d(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        f7902a = displayMetrics.density;
        f7903b = displayMetrics.widthPixels;
        f7904c = displayMetrics.heightPixels;
    }
}
