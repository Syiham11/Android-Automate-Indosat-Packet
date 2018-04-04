package com.ndahapp.deceive.kerehure.Broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ndahapp.deceive.kerehure.Services.ScheduleService;

/**
 * Created by Deceive on 2/22/2018.
 */

public class BroadcastOnAlarmIsSet extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ScheduleService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
