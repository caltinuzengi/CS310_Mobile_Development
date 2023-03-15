package com.example.trail_01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem economicsTabItem, sportsTabItem, politicsTabItem;
    FragmentAdapter pagesAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF424242"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("CS310 News");
        ActionBar currentBar = getSupportActionBar();
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.icons8_google_news);

        // FRAGMENT ADAPTING PART START
        economicsTabItem = findViewById(R.id.economicsTabItem);
        sportsTabItem = findViewById(R.id.sportsTabItem);
        politicsTabItem = findViewById(R.id.politicsTabItem);
        tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.fragmentContainer);

        pagesAdapter = new FragmentAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagesAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int a = tab.getPosition();
                if(a == 0 || a == 1 || a == 2){
                    pagesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // FINISH







    }
}