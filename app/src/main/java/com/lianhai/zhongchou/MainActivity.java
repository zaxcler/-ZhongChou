package com.lianhai.zhongchou;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianhai.zhongchou.adapter.FragmentAdapter;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    public ViewPager main_content;
    private LinearLayout index;
    private LinearLayout page2;
    private LinearLayout page3;
    private LinearLayout page4;

    private TextView index_text;
    private TextView page2_text;
    private TextView page3_text;
    private TextView page4_text;

    private ImageView index_icon;
    private ImageView page2_icon;
    private ImageView page3_icon;
    private ImageView page4_icon;

    public HomePageFragment homePageBean;
    public ProjectListFragment1 projectListFragment1;
    public SponsorFragment sponsorFragment;
    public MyPageFragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findView();
        init();

    }

    private void findView() {
        main_content = (ViewPager) findViewById(R.id.main_content);
        index = (LinearLayout) findViewById(R.id.index);
        page2 = (LinearLayout) findViewById(R.id.page2);
        page3 = (LinearLayout) findViewById(R.id.page3);
        page4 = (LinearLayout) findViewById(R.id.page4);

        index_text = (TextView) findViewById(R.id.index_text);
        page2_text = (TextView) findViewById(R.id.page2_text);
        page3_text = (TextView) findViewById(R.id.page3_text);
        page4_text = (TextView) findViewById(R.id.page4_text);

        index_icon = (ImageView) findViewById(R.id.index_icon);
        page2_icon = (ImageView) findViewById(R.id.page2_icon);
        page3_icon = (ImageView) findViewById(R.id.page3_icon);
        page4_icon = (ImageView) findViewById(R.id.page4_icon);
    }

    private void init() {

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        homePageBean=new HomePageFragment();
        projectListFragment1=new ProjectListFragment1();
        sponsorFragment=new SponsorFragment();
        myPageFragment=new MyPageFragment();
        adapter.fragments.add(homePageBean);
        adapter.fragments.add(projectListFragment1);
        adapter.fragments.add(sponsorFragment);
        adapter.fragments.add(myPageFragment);
        main_content.setAdapter(adapter);
        main_content.setOffscreenPageLimit(3);
        main_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                clearState();
                switch (i) {
                    case 0:
                        index_text.setTextColor(getResources().getColor(R.color.liangyellow));
                        index_icon.setImageDrawable(getResources().getDrawable(R.drawable.home_click));
                        break;
                    case 1:
                        page2_text.setTextColor(getResources().getColor(R.color.liangyellow));
                        page2_icon.setImageDrawable(getResources().getDrawable(R.drawable.list_click));
                        break;
                    case 2:
                        page3_text.setTextColor(getResources().getColor(R.color.liangyellow));
                        page3_icon.setImageDrawable(getResources().getDrawable(R.drawable.faqi_click));
                        break;
                    case 3:
                        page4_text.setTextColor(getResources().getColor(R.color.liangyellow));
                        page4_icon.setImageDrawable(getResources().getDrawable(R.drawable.wo_click));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        index.setOnClickListener(this);
        page2.setOnClickListener(this);
        page3.setOnClickListener(this);
        page4.setOnClickListener(this);
        /*
        初始化
         */
        main_content.setCurrentItem(0);
        index_text.setTextColor(getResources().getColor(R.color.midyellow));
        index_icon.setImageDrawable(getResources().getDrawable(R.drawable.home_click));
    }

    @Override
    public void onClick(View v) {
        clearState();
        switch (v.getId()) {
            case R.id.index:
                main_content.setCurrentItem(0);
                index_text.setTextColor(getResources().getColor(R.color.midyellow));
                index_icon.setImageDrawable(getResources().getDrawable(R.drawable.home_click));

                break;

            case R.id.page2:
                main_content.setCurrentItem(1);
                page2_text.setTextColor(getResources().getColor(R.color.midyellow));
                page2_icon.setImageDrawable(getResources().getDrawable(R.drawable.list_click));

                break;
            case R.id.page3:
                main_content.setCurrentItem(2);
                page3_text.setTextColor(getResources().getColor(R.color.midyellow));
                page3_icon.setImageDrawable(getResources().getDrawable(R.drawable.faqi_click));

                break;
            case R.id.page4:
                main_content.setCurrentItem(3);
                page4_text.setTextColor(getResources().getColor(R.color.midyellow));
                page4_icon.setImageDrawable(getResources().getDrawable(R.drawable.wo_click));
                break;
        }
    }

    /*
    清除选中状态
     */
    private void clearState(){
        index_text.setTextColor(getResources().getColor(R.color.liangboro));
        page2_text.setTextColor(getResources().getColor(R.color.liangboro));
        page3_text.setTextColor(getResources().getColor(R.color.liangboro));
        page4_text.setTextColor(getResources().getColor(R.color.liangboro));

        index_icon.setImageDrawable(getResources().getDrawable(R.drawable.home));
        page2_icon.setImageDrawable(getResources().getDrawable(R.drawable.list));
        page3_icon.setImageDrawable(getResources().getDrawable(R.drawable.faqi));
        page4_icon.setImageDrawable(getResources().getDrawable(R.drawable.wo));
    }

}
