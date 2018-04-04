package com.ndahapp.deceive.kerehure.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionChecker {
    public static void checkMe(Activity context) {
        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE))
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.CALL_PHONE)) {

            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.INTERNET},
                        190);
            }
        }
        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_SMS)) {
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.READ_SMS},
                        191);
            }
        }
        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECEIVE_SMS))
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        192);
            }
        }
        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS))
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.SEND_SMS},
                        193);
            }
        }
        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET))
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.INTERNET},
                        193);
            }
        }
    }
}
