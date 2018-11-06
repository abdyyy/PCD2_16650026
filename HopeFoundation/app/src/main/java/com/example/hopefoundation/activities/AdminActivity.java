package com.example.hopefoundation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.hopefoundation.R;
import com.example.hopefoundation.adapters.StudentListAdapter;
import com.example.hopefoundation.services.FetchStudentsService;
import com.example.hopefoundation.views.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    RecyclerView rvStudentList;
    ProgressBar pbLoading;
    StudentListAdapter rvAdapter;
    BroadcastReceiver mBroadcastReceiver;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> userNames = new ArrayList<>();
    ArrayList<String> addresses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Users");
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        FetchStudentsService.startService(this);
        rvStudentList = (RecyclerView) findViewById(R.id.rv_centre_data);
        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rvStudentList.addItemDecoration(itemDecoration);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (pbLoading != null) {
                    pbLoading.setVisibility(View.INVISIBLE);
                    rvStudentList.setVisibility(View.VISIBLE);
                    try {
                        JSONArray jsonArray = new JSONArray(getSharedPreferences("MyPrefs",
                                Context.MODE_PRIVATE).getString("all_data", null));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String name = obj.getString("fname") + " " +obj.getString("lname");
                            names.add(name);
                            addresses.add(obj.getString("address"));
                            userNames.add(obj.getString("username"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvAdapter = new StudentListAdapter(names , userNames, AdminActivity.this, addresses);
                    rvStudentList.setAdapter(rvAdapter);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver,
                new IntentFilter(FetchStudentsService.PLANS));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
