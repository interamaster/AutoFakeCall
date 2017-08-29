package com.mio.jrdv.autofakecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class LaunchAppViaDialReceiver extends BroadcastReceiver {

    SharedPreferences appSettings;

    @Override
    public void onReceive(Context context, Intent intent) {

        appSettings = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);

        String numberToDial = appSettings.getString("numberToDial", "111");

        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        Log.d("INFO","receiber LaunchAppViaDialReceiver nuenro marcado: "+phoneNumber);

        if (phoneNumber.equals(numberToDial)) {

            setResultData(null);

            Intent appIntent = new Intent(context, ScheduleCallActivity.class);

            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(appIntent);

        }

    }

}
