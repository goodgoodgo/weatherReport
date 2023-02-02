package com.example.weatherreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;


import com.example.weatherreport.ui.fragment.FirstFragment;
import com.example.weatherreport.ui.fragment.FourthFragment;
import com.example.weatherreport.ui.fragment.SecondFragment;
import com.example.weatherreport.ui.fragment.ThirdFragment;
import com.google.android.material.tabs.TabLayout;



public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    protected static String name;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private String city="";

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intentMsg=this.getIntent();
        String msg=intentMsg.getStringExtra("isLogin");
        if(msg==null){
            msg="";
        }
        if(!msg.equals("true")){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        initViews();
    }




    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int mFlag = intent.getIntExtra("flag", 0);
        city=intent.getStringExtra("city");
        if (mFlag == 3) { //判断获取到的flag值
            mViewPager.setCurrentItem(1);
        }

    }
    private void initViews() {
        mTablayout= findViewById(R.id.tab_layout);
        mViewPager= findViewById(R.id.viewPager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private String[] mTitles = new String[]{"地图", "天气", "历史天气","设置"};

            @Override
            public Fragment getItem(int position) {
                if (position == 1) {
                    return new SecondFragment();
                } else if (position == 2) {
                    return new ThirdFragment();
                }else if (position==3){
                    return new FourthFragment();
                }
                return new FirstFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });

        mTablayout.setupWithViewPager(mViewPager);

        one = mTablayout.getTabAt(0);
        two = mTablayout.getTabAt(1);
        three = mTablayout.getTabAt(2);
        four = mTablayout.getTabAt(3);

        one.setIcon(R.mipmap.map);
        two.setIcon(R.mipmap.weather);
        three.setIcon(R.mipmap.history);
        four.setIcon(R.mipmap.set);
        mViewPager.setCurrentItem(1);
    }

}