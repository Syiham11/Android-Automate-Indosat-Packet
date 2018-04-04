package com.ndahapp.deceive.kerehure.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.ndahapp.deceive.kerehure.R;

/**
 * Created by Deceive on 3/25/2018.
 */

public class MyDatePicker extends DialogFragment {
    static dialogViewCallback viewCallback;
    View v;
    Button saveDate;

    public MyDatePicker setCallback(dialogViewCallback callback) {
        viewCallback = callback;
        return new MyDatePicker();
    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
////        if(getShowsDialog()){
////            return super.onCreateView(inflater, container, savedInstanceState);
////        }
//        v = getActivity().getLayoutInflater().inflate(R.layout.date_picker_config,null);
//        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
//        datePicker.setMinDate(System.currentTimeMillis() - 86400000 );
//        datePicker.setMaxDate(System.currentTimeMillis());
//        saveDate = (Button) v.findViewById(R.id.btn_save_tgl);
//        viewCallback.process(datePicker,saveDate);
//        return v;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
////        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
////        v = getActivity().getLayoutInflater().inflate(R.layout.date_picker_config,null);
////        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
////        datePicker.setMinDate(System.currentTimeMillis() - 86400000 );
////        datePicker.setMaxDate(System.currentTimeMillis());
////        saveDate = (Button) v.findViewById(R.id.btn_save_tgl);
////        viewCallback.process(datePicker,saveDate);
////        super.onCreate(savedInstanceState);
//    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        if(isInLayout()){
//
//        }
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        v = getActivity().getLayoutInflater().inflate(R.layout.date_picker_config, null);
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
        datePicker.setMinDate(System.currentTimeMillis() - 86400000);
        datePicker.setMaxDate(System.currentTimeMillis());
        saveDate = (Button) v.findViewById(R.id.btn_save_tgl);
        viewCallback.process(datePicker, saveDate);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        AlertDialog alertDialog = dialog.create();
        alertDialog.setView(v);
        return alertDialog;
    }

    @Override
    public View getView() {
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
        datePicker.setMinDate(System.currentTimeMillis() - 86400000);
        datePicker.setMaxDate(System.currentTimeMillis());
        saveDate = (Button) v.findViewById(R.id.btn_save_tgl);
        viewCallback.process(datePicker, saveDate);
        return getActivity().getLayoutInflater().inflate(R.layout.date_picker_config, null);
    }

    public Button getButtonSave() {
        return saveDate;
    }

    public interface dialogViewCallback {
        void process(DatePicker datePicker, Button button);
    }

}
