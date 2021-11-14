package com.example.project3;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        CustomFragmentAdapter mCustomFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.viewPagerId);
        mViewPager.setAdapter(mCustomFragmentAdapter);



        /*if tablayout is outside of viewpager tag in xml file
        TabLayout mTabLayout = findViewById(R.id.tabLayoutId);
        mTabLayout.setupWithViewPager(mViewPager);*/


    }



}