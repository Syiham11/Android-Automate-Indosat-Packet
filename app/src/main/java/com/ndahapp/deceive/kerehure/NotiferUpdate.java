package com.ndahapp.deceive.kerehure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NotiferUpdate extends AsyncTask<String, String, String> {
    public int currentVersion = 0;
    Context context;
    URL myApi;

    {
        try {
            myApi = new URL("https://pastebin.com/raw/7fnGAQjT");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public NotiferUpdate(Context context) {
        this.context = context;
    }

    public String notifMe() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) myApi.openConnection();
        InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.readLine();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            return notifMe();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (Integer.parseInt(s) > currentVersion) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("New Update");
            builder.setMessage("Ada update terbaru");
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://deceive3w.github.io"));
                    context.startActivity(i);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
