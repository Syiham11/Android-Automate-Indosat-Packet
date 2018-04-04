package com.ndahapp.deceive.kerehure;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ndahapp.deceive.kerehure.Broadcaster.BroadcastOnAlarmIsSet;
import com.ndahapp.deceive.kerehure.Builder.AlarmFactory;
import com.ndahapp.deceive.kerehure.Interface.IAlarmEvents;
import com.ndahapp.deceive.kerehure.Utils.PermissionChecker;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    final static private long ONE_SECOND = 1000;
    final static private long TWENTY_SECONDS = ONE_SECOND * 20;
    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;
    TimePicker timePicker;
    PendingIntent alarmIntent;
    FloatingActionButton fab;
    TextView txt_tgl_registrasi;
    TextView txt_status_paket;
    TextView txt_config;
    Button btn_start;
    Long targetAlarm;
    NotiferUpdate notiferUpdate;
    AlarmFactory alarmFactory;

    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        InitCoreApp();
        InitUIComponent();
//        updateData();
        setup();
//        UpdateUI();
    }

    public void InitCoreApp() {


        PermissionChecker.checkMe(this);
        Intent alarm = new Intent(this, BroadcastOnAlarmIsSet.class);
        pi = PendingIntent.getBroadcast(this, 0, alarm, 0);
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmFactory = new AlarmFactory(this, am);
        alarmFactory.setCallBack(new IAlarmEvents() {
            @Override
            public void OnAlarmSet() {
                UpdateData();
                UpdateUI();
            }

            @Override
            public void OnAlarmStop() {
                UpdateData();
                UpdateUI();
            }

            @Override
            public void OnALarmDestroy() {

            }
        });
        Intent intent = new Intent(this, BroadcastOnAlarmIsSet.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        notiferUpdate = new NotiferUpdate(this);
//        notiferUpdate.execute();
    }

    //TODO:Add design pattern soon
    public void InitUIComponent() {
        txt_tgl_registrasi = (TextView) findViewById(R.id.txt_regis_ulang_value);
        btn_start = (Button) findViewById(R.id.btn_start);
        txt_status_paket = (TextView) findViewById(R.id.txt_status_paket_value);
        txt_config = (TextView) findViewById(R.id.txt_config);
        txt_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ConfigActivity.class), 2);
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmFactory.getStatusAlarm()) {
                    alarmFactory.StopServices();
                } else {
                    if (haveConfig()) {
                        alarmFactory.StartAlarm(targetAlarm);
                    } else {
                        startActivityForResult(new Intent(getApplicationContext(), ConfigActivity.class), 2);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //todo
        getMenuInflater().inflate(R.menu.mtoolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.aboutMe){
//            startActivity(new Intent(this,AboutActivity.class));
//        }
        return super.onOptionsItemSelected(item);
    }

    void setup() {
        Intent alarm = new Intent(this, BroadcastOnAlarmIsSet.class);
        pi = PendingIntent.getBroadcast(this, 0, alarm, 0);
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void UpdateData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("transaction", MODE_PRIVATE);
        if (sharedPreferences.getLong("tglpembelian", 0) > 0) {
            txt_tgl_registrasi.setText(milisToString(sharedPreferences.getLong("tglpembelian", 0) + 82800000));
            targetAlarm = sharedPreferences.getLong("tglpembelian", 0);
        }

    }

    public Boolean haveConfig() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("transaction", MODE_PRIVATE);
        return sharedPreferences.getLong("tglpembelian", 0) > 0;
    }

    public String milisToString(long milis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(milis);
        String calculateAmPM;
        int rawAmPm = calendar.get(Calendar.AM_PM);
        if (rawAmPm == Calendar.AM) {
            calculateAmPM = "AM";
        } else {
            calculateAmPM = "PM";
        }
        Log.d("asd", "milisToString: " + calendar.getTime());
        String timeInString = calendar.getTime().getDate() + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR) + "  " + calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes() + " " + calculateAmPM;
        return timeInString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UpdateData();
    }

    public void UpdateUI() {
        if (alarmFactory.getStatusAlarm()) {
            txt_status_paket.setText("Aktif");
            btn_start.setText("STOP");
        } else {
            btn_start.setText("START");
            txt_status_paket.setText("Tidak Aktif");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (notiferUpdate.getStatus() == AsyncTask.Status.FINISHED) {
            notiferUpdate = new NotiferUpdate(this);
//            notiferUpdate.execute();
        }
        UpdateData();
        UpdateUI();
    }
}
