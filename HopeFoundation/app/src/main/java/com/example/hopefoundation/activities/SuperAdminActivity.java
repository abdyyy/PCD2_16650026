package com.example.hopefoundation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.hopefoundation.R;
import com.example.hopefoundation.adapters.StudentListAdapterv2;
import com.example.hopefoundation.services.FetchCenterDetails;
import com.example.hopefoundation.services.FetchCentreStudentsService;
import com.example.hopefoundation.views.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SuperAdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SuperAdminActivity";
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String LOCATION = "location";
    private static final String POSITION = "position";

    RecyclerView rvStudentList;
    StudentListAdapterv2 rvAdapter;
    Spinner mSpinner;
    ProgressBar pbLoading;
    BroadcastReceiver mBroadcastReceiver1, mBroadcastReceiver2;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> namesRL = new ArrayList<>();


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        getSupportActionBar().setTitle("Admin");

        pbLoading = (ProgressBar) findViewById(R.id.pb_loadingSA);
        FetchCenterDetails.startService(this);
        mSpinner = (Spinner) findViewById(R.id.spinner01);
        mSpinner.setOnItemSelectedListener(this);


        rvStudentList = (RecyclerView) findViewById(R.id.rv_centre_datas);
        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rvStudentList.addItemDecoration(itemDecoration);


        mBroadcastReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (pbLoading != null && intent.getAction().equals(FetchCenterDetails.CENTRES)) {
                    pbLoading.setVisibility(View.INVISIBLE);
                    mSpinner.setVisibility(View.VISIBLE);
                    try {
                        JSONArray jsonArray = new JSONArray(getSharedPreferences("MyPrefs",
                                Context.MODE_PRIVATE).getString("all_centers", null));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String name = obj.getString("name") + " - " + obj.getString("location");
                            names.add(name);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(LOCATION, obj.getString("name"));
                            editor.commit();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setInflate();
                } else if (pbLoading != null && intent.getAction().equals("centres_students")) {
                    pbLoading.setVisibility(View.INVISIBLE);
                    rvStudentList.setVisibility(View.VISIBLE);
                    try {
                        JSONArray jsonArray = new JSONArray(getSharedPreferences("MyPrefs",
                                Context.MODE_PRIVATE).getString("all_centers_students", null));
                        Log.d("pothigai", "got length as: " + jsonArray.length());
                        namesRL.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
//                            String name = obj.getString("fname") + " " + obj.getString("lname");
                            String name = obj.getString("fname") + " " + obj.getString("lname");
                            namesRL.add(name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvAdapter = new StudentListAdapterv2(namesRL);
                    rvStudentList.setAdapter(rvAdapter);
                }
            }
        };

    }


    private void setInflate() {
        Log.d(TAG, "setInflate()");

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(aa);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(FetchCenterDetails.CENTRES);
        filter.addAction(FetchCentreStudentsService.CENTRES_STU);
        registerReceiver(mBroadcastReceiver1, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected" + mSpinner.getSelectedItem().toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(POSITION, mSpinner.getSelectedItem().toString());
        editor.commit();
        FetchCentreStudentsService.startService(this);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

