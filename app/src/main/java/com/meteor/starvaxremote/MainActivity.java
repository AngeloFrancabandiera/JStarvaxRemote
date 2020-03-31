package com.meteor.starvaxremote;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.meteor.starvaxremote.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private int IconResourceSet[] = {
            R.drawable.page_connection,
            R.drawable.page_media_a,
            R.drawable.page_media_b,
            R.drawable.page_light_preset,
            R.drawable.page_open_web_net
    };

//    private MainActivityViewModel ShowViewModel;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        for (int i = 0; i < tabs.getTabCount(); i++)
        {
            tabs.getTabAt(i).setIcon( IconResourceSet[i]);
        }

    }
}
