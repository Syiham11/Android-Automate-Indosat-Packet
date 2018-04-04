package com.ndahapp.deceive.kerehure;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    ImageView imageView;
    TextView defri;
    TextView endah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        imageView = (ImageView) findViewById(R.id.mylove);
        defri = (TextView) findViewById(R.id.defri);
        endah = (TextView) findViewById(R.id.endah);
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealImage();
            }
        }, 1000);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defri.setVisibility(View.VISIBLE);
                endah.setVisibility(View.VISIBLE);
            }
        });
        defri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/deceive3w");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");
                startActivity(likeIng);
            }
        });
//        endah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri uri = Uri.parse("http://instagram.com/_u/esaaaaf");
//                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
//
//                likeIng.setPackage("com.instagram.android");
//                startActivity(likeIng);
//            }
//        });
    }

    void revealImage() {
        int cx = (imageView.getLeft() + imageView.getRight() / 2);
        int cy = (imageView.getTop() + imageView.getBottom() / 2);
        int finalRadius = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0, finalRadius);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        imageView.setVisibility(View.VISIBLE);
        animator.start();
    }

    void initAnim() {

    }
}
