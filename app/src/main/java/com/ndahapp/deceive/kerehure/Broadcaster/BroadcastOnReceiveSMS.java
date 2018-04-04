package com.ndahapp.deceive.kerehure.Broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.ndahapp.deceive.kerehure.Builder.AlarmFactory;
import com.ndahapp.deceive.kerehure.Services.ScheduleService;
import com.ndahapp.deceive.kerehure.SmsListener;

/**
 * Created by Deceive on 2/22/2018.
 */

public class BroadcastOnReceiveSMS extends BroadcastReceiver {
    private static SmsListener smsListener;
    Boolean isSuccess = false;
    Context context;

    public static void bindListener(SmsListener listener) {
        smsListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        this.context = context;
        Object[] pdus = (Object[]) data.get("pdus");
        SmsManager smsManager = SmsManager.getDefault();
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            if (sender.equals("363")) {
                String newMessage = smsMessage.getMessageBody();
                String confirmMessage = newMessage.substring(0, 6);
                Log.d("newmessage", "onReceive: " + confirmMessage);
                if (confirmMessage.equals("Apakah")) {
                    Log.d("newmessage", "onReceive: true");
                    AlarmFactory alarmFactory = new AlarmFactory(context);
                    alarmFactory.Perpanjang();
                    smsManager.sendTextMessage("363", null, "Ok", null, null);
                }
            }
        }
    }

    public void reCall() {
        final CountDownTimer counter = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isSuccess) {
                    this.cancel();
                }
            }

            @Override
            public void onFinish() {
                if (!isSuccess) {
                    triggerAlarm();
                    reCall();
                }
                if (isSuccess) {
                    this.cancel();
                }
            }
        }.start();
    }

    public void triggerAlarm() {
        Intent i = new Intent(context, ScheduleService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
