package com.axb.plugin.nodoubleclick.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.axb.plugin.nodoubleclick.lib.NoDoubleClickUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","button1被点击了："+System.currentTimeMillis());
            }
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            Log.e("TAG","button2被点击了："+System.currentTimeMillis());
        });

        findViewById(R.id.button3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button3:
                Log.e("TAG","button3被点击了："+System.currentTimeMillis());
                break;
            default:
                break;
        }
    }
}