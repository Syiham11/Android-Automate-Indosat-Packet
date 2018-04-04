package com.ndahapp.deceive.kerehure.Services;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Deceive on 2/22/2018.
 */

public class ScheduleService extends IntentService {

    public ScheduleService() {
        super("ScheduleService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Call();
        Toast.makeText(this, "SERVICE IS RUNNING", Toast.LENGTH_SHORT).show();
    }

    void Call() {
        Uri numberUri = Uri.parse("tel:*123*111*1*1%23");
        Intent callIntent = new Intent(Intent.ACTION_CALL, numberUri);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

}

