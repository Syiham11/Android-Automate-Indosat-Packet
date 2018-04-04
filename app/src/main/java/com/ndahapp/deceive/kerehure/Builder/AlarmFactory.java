package com.ndahapp.deceive.kerehure.Builder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.ndahapp.deceive.kerehure.Broadcaster.BroadcastOnAlarmIsSet;
import com.ndahapp.deceive.kerehure.Interface.IAlarmEvents;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class AlarmFactory {
    public static AlarmFactory instance;
    AlarmManager am;
    Context context;
    IAlarmEvents callback;

    public AlarmFactory(Context context) {
        this.context = context;
    }

    public AlarmFactory(Context context, AlarmManager am) {
        this.am = am;
        this.context = context;
    }

    public void setCallBack(IAlarmEvents callback) {
        this.callback = callback;
    }

    public boolean getStatusAlarm() {
        boolean alarmUp = (PendingIntent.getBroadcast(context, BroadcastOnAlarmIsSet.REQUEST_CODE,
                new Intent(context, BroadcastOnAlarmIsSet.class),
                PendingIntent.FLAG_NO_CREATE) != null);
        return alarmUp;
    }

    public void StartAlarm(long targetAlarm) {
        Intent intent = new Intent(context, BroadcastOnAlarmIsSet.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, BroadcastOnAlarmIsSet.REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.setExact(AlarmManager.RTC_WAKEUP, (targetAlarm + 82800000), pendingIntent);
        Log.d("set", "StartServices: " + targetAlarm);
        Toast.makeText(context, "Sukses setup", Toast.LENGTH_SHORT).show();
        callback.OnAlarmSet();
    }

    public void StopServices() {
        Intent intent = new Intent(context, BroadcastOnAlarmIsSet.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, BroadcastOnAlarmIsSet.REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        callback.OnAlarmStop();
    }

    public void Perpanjang() {
        Intent intent = new Intent(context, BroadcastOnAlarmIsSet.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, BroadcastOnAlarmIsSet.REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        SharedPreferences sharedPreferences = context.getSharedPreferences("transaction", MODE_PRIVATE);
        long targetAlarm = System.currentTimeMillis();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("tglpembelian", targetAlarm);
        editor.commit();
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, targetAlarm, pendingIntent);
        Log.d("set", "StartServices: " + targetAlarm);
        Toast.makeText(context, "Sukses setup", Toast.LENGTH_SHORT).show();
    }
}
