package com.ndahapp.deceive.kerehure;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ndahapp.deceive.kerehure.Adapter.ConfigAdapterList;
import com.ndahapp.deceive.kerehure.Adapter.ConfigEntity;
import com.ndahapp.deceive.kerehure.Broadcaster.BroadcastOnAlarmIsSet;
import com.ndahapp.deceive.kerehure.Dialogs.MyDatePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class ConfigActivity extends AppCompatActivity implements MyDatePicker.dialogViewCallback {
    public Long temp_tgl_pembelian;
    public Long temp_jam_pembelian;
    AlarmManager am;
    Calendar calendar;
    Button button;
    SharedPreferences sharedPreferences;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        calendar = Calendar.getInstance();
        ListView listView = (ListView) findViewById(R.id.list_config);
        ArrayList<ConfigEntity> list = new ArrayList<>();
        sharedPreferences = this.getSharedPreferences("transaction", Context.MODE_PRIVATE);
        button = (Button) findViewById(R.id.btn_save_config);
        tb = (Toolbar) findViewById(R.id.tb1);
        tb.setTitleTextColor(Color.BLACK);
        setSupportActionBar(tb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putLong("tglpembelian", calendar.getTimeInMillis());
                edit.commit();
                Intent intent = new Intent();
                intent.putExtra("update", true);
                setResult(2, intent);
                finish();
            }
        });
        list.add(new ConfigEntity("Tanggal Pembelian", "kosong"));
        list.add(new ConfigEntity("Jam Pembelian", "kosong"));
        listView.setAdapter(new ConfigAdapterList(this, list));
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
                if (position == 0) {
                    setDate(view);
                    return;
                }
                if (position == 1) {
                    Log.d("asd", "asd" + view);
                    setClock(view);
                    return;
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("asdasd", "onItemClick: " + position);
                return false;
            }
        });
    }

    public void setClock(final View view) {
        TimePickerDialog mTimePicker;

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                Log.d("ALARM", "onClick: PAKET SEBELUM NYA " + calendar.getTimeInMillis());
                Log.d("ALARM", "onClick: PAKET SETELAH NYA " + (calendar.getTimeInMillis() + 82800000));
                Intent intent = new Intent(getApplicationContext(), BroadcastOnAlarmIsSet.class);
                TextView contentView = (TextView) view.findViewById(R.id.contentTextConfigRow);
                contentView.setText("Pukul" + selectedHour + "." + selectedMinute);
                Log.d("FIXED", "onTimeSet: " + calendar.getTimeInMillis());

//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),AlarmReceiver.REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//                am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),82800000,pendingIntent);
                Toast.makeText(getApplicationContext(), "Sukses setup", Toast.LENGTH_SHORT).show();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void setDate(final View view) {
        openDate();
//        android.app.FragmentManager fm = getFragmentManager();
//        MyDatePicker myDatePicker = new MyDatePicker();
//        final DialogFragment newDialogFragment = myDatePicker;
//        myDatePicker.setCallback(new MyDatePicker.dialogViewCallback(){
//
//            @Override
//            public void process(final DatePicker datePicker, Button button) {
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TextView textView = (TextView)view.findViewById(R.id.contentTextConfigRow);
//                        final DatePicker dp = datePicker;
//                        textView.setText(dp.getDayOfMonth()+"/"+dp.getMonth()+"/"+dp.getYear());
//                        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
//
//                        newDialogFragment.dismiss();
//                    }
//                });
//
//            }
//        });
//        newDialogFragment.show(getFragmentManager(),null);
//        SublimeDatePicker datePicker = new SublimeDatePicker(this);
//        datePicker.setMinDate(System.currentTimeMillis() - 86400000);
//        datePicker.setMaxDate(System.currentTimeMillis());
    }

    public void openDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dateView = getLayoutInflater().inflate(R.layout.date_picker_config, null);
        Button setDateBtn = (Button) dateView.findViewById(R.id.btn_save_tgl);
        final DatePicker datePicker = (DatePicker) dateView.findViewById(R.id.date_picker);
        datePicker.setMinDate(System.currentTimeMillis() - 86400000);
        datePicker.setMaxDate(System.currentTimeMillis());
        Button saveDate = (Button) dateView.findViewById(R.id.btn_save_tgl);
        builder.setView(dateView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.contentTextConfigRow);
                final DatePicker dp = datePicker;
                textView.setText(dp.getDayOfMonth() + "/" + dp.getMonth() + "/" + dp.getYear());
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                dialog.dismiss();
            }
        });

    }

    public void submitDate(DatePicker datePicker, View v) {
        TextView contentView = (TextView) v.findViewById(R.id.contentTextConfigRow);
        contentView.setText(datePicker.getDayOfMonth());
    }

    @Override
    public void process(final DatePicker datePicker, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
