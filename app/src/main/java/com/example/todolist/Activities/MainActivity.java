package com.example.todolist.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.DataBase.AppDataBase;
import com.example.todolist.Fragments.High_Fragment;
import com.example.todolist.Fragments.Low_Fragment;
import com.example.todolist.Fragments.Medium_Fragment;
import com.example.todolist.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter fragmentPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    AppDataBase appDataBase;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout                = findViewById(R.id.tabs);
        viewPager                = findViewById(R.id.viewpager);
        appDataBase              = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"test1").build();
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            Fragment[] fragments = new Fragment[]{
                    new High_Fragment(),
                    new Medium_Fragment(),
                    new Low_Fragment()
            };
            String[] names = new String[]{
                    "High",
                    "Medium",
                    "Low"
            };
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return names[position];
            }

        };
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void add(View view) {
        Intent intent = new Intent(MainActivity.this,Addnew.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
