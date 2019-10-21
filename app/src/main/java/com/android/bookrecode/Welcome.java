package com.android.bookrecode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Welcome  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Welcome.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        },100);//3000毫秒后执行，即3秒跳转
    }



}
