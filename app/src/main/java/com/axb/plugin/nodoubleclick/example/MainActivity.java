package com.axb.plugin.nodoubleclick.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.axb.plugin.nodoubleclick.lib.NoDoubleClickUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置两次点击事件之间的时间差（判断双击），不设置默认是500ms
        NoDoubleClickUtil.init(1000);

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


        List<String> listdata = new ArrayList<String>();
        listdata.add("上海");
        listdata.add("北京");
        listdata.add("天津");
        listdata.add("江苏");
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listdata);//listdata和str均可
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> Log.e("TAG","listView被点击了："+System.currentTimeMillis()));
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