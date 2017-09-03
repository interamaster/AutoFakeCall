package com.mio.jrdv.autofakecall.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class C2279c {
    public static String m12981a(Context context) {
        return C2279c.m13005t(context).getString("callVoicePath", "");
    }

    public static void m12982a(Context context, String str) {
        C2279c.m13005t(context).edit().remove(str).apply();
    }

    public static void m12983a(Context context, String str, int i) {
        C2279c.m13005t(context).edit().putInt(str, i).apply();
    }

    public static void m12984a(Context context, String str, long j) {
        C2279c.m13005t(context).edit().putLong(str, j).apply();
    }

    public static void m12985a(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            C2279c.m12982a(context, str);
        } else {
            C2279c.m13005t(context).edit().putString(str, str2).apply();
        }
    }

    public static void m12986a(Context context, String str, boolean z) {
        C2279c.m13005t(context).edit().putBoolean(str, z).apply();
    }

    public static String m12987b(Context context) {
        return C2279c.m13005t(context).getString("callContactName", "");
    }

    public static String m12988c(Context context) {
        return C2279c.m13005t(context).getString("callContactNumber", "");
    }

    public static String m12989d(Context context) {
        return C2279c.m13005t(context).getString("callContactPhotoPath", "");
    }

    public static String m12990e(Context context) {
        return C2279c.m13005t(context).getString("ringtoneUri", null);
    }

    public static boolean m12991f(Context context) {
        return C2279c.m13005t(context).getBoolean("enableVibration", true);
    }

    public static int m12992g(Context context) {
        return C2279c.m13005t(context).getInt("selectedPhoneId", 2);
    }

    public static String m12993h(Context context) {
        return C2279c.m13005t(context).getString("selectedPhoneName", "Android 2.3");
    }

    public static int m12994i(Context context) {
        return C2279c.m13005t(context).getInt("talkTime", 0);
    }

    public static String m12995j(Context context) {
        return C2279c.m13005t(context).getString("defaultSmsPackage", "");
    }

    public static String m12996k(Context context) {
        return C2279c.m13005t(context).getString("smsContactNumber", "");
    }

    public static int m12997l(Context context) {
        return C2279c.m13005t(context).getInt("smsType", 1);
    }

    public static String m12998m(Context context) {
        return C2279c.m13005t(context).getString("smsMessageContent", "");
    }

    public static long m12999n(Context context) {
        return C2279c.m13005t(context).getLong("smsTime", System.currentTimeMillis());
    }

    public static String m13000o(Context context) {
        return C2279c.m13005t(context).getString("mmsFilePATH", "");
    }

    public static String m13001p(Context context) {
        return C2279c.m13005t(context).getString("theme", "light");
    }

    public static boolean m13002q(Context context) {
        return C2279c.m13005t(context).getBoolean("callLog", false);
    }

    public static boolean m13003r(Context context) {
        return C2279c.m13005t(context).getBoolean("isRated", false);
    }

    public static int m13004s(Context context) {
        return C2279c.m13005t(context).getInt("interstitialCounter", 0);
    }

    private static SharedPreferences m13005t(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
