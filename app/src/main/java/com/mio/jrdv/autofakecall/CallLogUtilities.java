package com.mio.jrdv.autofakecall;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.provider.CallLog.Calls;
import android.support.v4.app.ActivityCompat;

public class CallLogUtilities {

    public static void addCallToLog(ContentResolver contentResolver, String number, long duration, int type, long time) {

        ContentValues values = new ContentValues();

        values.put(Calls.NUMBER, number);

        values.put(Calls.DATE, time);

        values.put(Calls.DURATION, duration);

        values.put(Calls.TYPE, type);

        values.put(Calls.NEW, 1);

        values.put(Calls.CACHED_NAME, "");

        values.put(Calls.CACHED_NUMBER_TYPE, 0);

        values.put(Calls.CACHED_NUMBER_LABEL, "");
/*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

  */
        contentResolver.insert(Calls.CONTENT_URI, values);

    }

}
