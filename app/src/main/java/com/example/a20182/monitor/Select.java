package com.example.a20182.monitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class Select extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private SelectAdapter mAdapter;
    private List<Application> mApps;
    public static int curPosition;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        mListView = findViewById(R.id.lv_select);
        mListView.setOnItemClickListener(this);
        mApps = MainActivity.AppList;
        mAdapter = new SelectAdapter(this, mApps);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        curPosition = position;
        startActivity(new Intent(Select.this, Settings.class));
    }
}
