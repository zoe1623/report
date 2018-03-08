package com.zoe.report;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zoe.report.view.PostureReportsWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    Map<Integer, String> map = new HashMap<>();
    List<Integer> list = new ArrayList<>();
    private PostureReportsWidget mPostureReportsWidget;
    private View day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPostureReportsWidget = findViewById(R.id.posture_reports_widget);
        day = findViewById(R.id.day);
        day.setOnClickListener(this);
        findViewById(R.id.week).setOnClickListener(this);
        findViewById(R.id.month).setOnClickListener(this);
        findViewById(R.id.year).setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        day.performClick();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.day:
                refreshDay();
                break;
            case R.id.week:
                updateWeek();
                break;
            case R.id.month:
                updateMonth();
                break;
            case R.id.year:
                updateYear();
                break;
        }
    }
    Random random = new Random(100);
    String [] weeks = {
            "日","一","二","三","四","五","六"
    };
    private void updateWeek(){
        list.clear();
        for(int i = 0; i<7; i++){
            list.add(random.nextInt());
        }
        map.clear();
        for(int i = 0; i<list.size(); i++){
            map.put(i, "周"+weeks[i]);
        }
        mPostureReportsWidget.setBlankLeave(30);//留白5dp
        mPostureReportsWidget.setData(list, map);
    }

    private void updateMonth(){
        list.clear();
        for(int i = 0; i<31; i++){
            list.add(random.nextInt());
        }
        map.clear();
        for(int i = 0; i<list.size(); i++){
            if(i % 7 == 0){
                map.put(i, i+"日");
            }
        }
        mPostureReportsWidget.setBlankLeave(5);//留白5dp
        mPostureReportsWidget.setData(list, map);
    }

    private void refreshDay() {
        list.clear();
        for(int i = 0; i<25; i++){
            list.add(random.nextInt());
        }
        map.clear();
        for(int i = 0; i<list.size(); i++){
            if(i % 6 == 0){
                map.put(i, i + ":00");
            }
        }
        mPostureReportsWidget.setBlankLeave(5);//留白5dp
        mPostureReportsWidget.setData(list, map);
    }

    private void updateYear(){
        list.clear();
        for(int i = 0; i<13; i++){
            list.add(random.nextInt());
        }
        map.clear();
        for(int i = 0; i<list.size(); i++){
            if(i % 3 == 0){
                map.put(i, i + "月");
            }
        }
        mPostureReportsWidget.setBlankLeave(20);//留白5dp
        mPostureReportsWidget.setData(list, map);
    }
}
