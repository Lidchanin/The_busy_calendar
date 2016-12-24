package com.example.destr.busy_calendar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.adapters.GridCellAdapter;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.socialsJob.FacebookLoginActivity;
import com.example.destr.busy_calendar.socialsJob.TokenJob;
import com.example.destr.busy_calendar.socialsJob.VkLoginActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView currentMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        final Intent event = new Intent(MainActivity.this, EventActivity.class);
        Button vk_loginButton = (Button) findViewById(R.id.main_btn_vk);
        Button facebook_loginButton = (Button) findViewById(R.id.main_btn_facebook);
        ImageView facebook = (ImageView) findViewById(R.id.facebookimage);
        ImageView vkimage = (ImageView) findViewById(R.id.vkimage);
        vk_loginButton.setVisibility(View.VISIBLE);
        facebook_loginButton.setVisibility(View.VISIBLE);
        vk_loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VkLoginActivity.class));
            }
        });
        facebook_loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FacebookLoginActivity.class));
            }
        });

        ImageButton addButton = (ImageButton) findViewById(R.id.newevent);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(event);
            }
        });

        new TokenJob(getApplicationContext(), facebook_loginButton, vk_loginButton, facebook, vkimage);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final int[] month = {calendar.get(Calendar.MONTH) + 1};
        final int[] year = {calendar.get(Calendar.YEAR)};
        ImageView prevMonth = (ImageView) findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.setPrevMonth(getApplicationContext(), calendarView, currentMonth);
            }
        });
        ImageView nextMonth = (ImageView) findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.setNextMonth(getApplicationContext(), calendarView, currentMonth);
            }
        });
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        calendarView = (GridView) findViewById(R.id.calendar);
        adapter = new GridCellAdapter(getApplicationContext(), month[0], year[0]) ;
        currentMonth.setText(adapter.getMonthAsString(month[0]) + Constants.OtherConstants.SPACE + year[0]);
        calendarView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
