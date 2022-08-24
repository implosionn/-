package com.example.zlygz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zlygz.adapter.VPAdapter;
import com.example.zlygz.fragment.BlankFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VPFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    //list
    private ListView mListView;
    private List<Map<String, Object>> mList;//数据
    private SimpleAdapter mSimpleAdapter;//适配器
    private int[] imgs = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5
    };
    //end_list

    private ViewPager mViewPager;
    private VPAdapter mVPAdapter;
    private List<Fragment> mFragmentList;


    private ImageView imovie, itv, igame;
    private TextView tmovie, ttv, tgame;
    private LinearLayout llmovie, lltv, llgame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpfragment);

        //START
        mListView = findViewById(R.id.list_item);

        //数据
     /*   mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", imgs[i % imgs.length]);
            map.put("title", "标题" + i);
            map.put("content", "内容" + i);
            mList.add(map);
        }
        //Adapter
        mSimpleAdapter = new SimpleAdapter(this,
                mList,
                R.layout.simple_item_layout,
                new String[]{"img", "title", "content"},
                new int[]{R.id.i_image, R.id.t_title, R.id.t_content});

        mListView.setAdapter(mSimpleAdapter);*/

        //数据填充
        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(VPFragmentActivity.this, "你点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //END
        initView();
        initData();

        mViewPager = findViewById(R.id.vp);
        mVPAdapter = new VPAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mVPAdapter);

        //监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(VPFragmentActivity.this, "当前为第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();//提示
                onSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //默认显示第一页
        onSelected(0);

        llmovie.setOnClickListener(this);
        lltv.setOnClickListener(this);
        llgame.setOnClickListener(this);
    }

    private void onSelected(int position) {
        resetBottom();
        switch (position) {
            case 0:
                imovie.setSelected(true);
                tmovie.setTextColor(getResources().getColor(R.color.selected));
                changeinfo(0);
                break;
            case 1:
                itv.setSelected(true);
                ttv.setTextColor(getResources().getColor(R.color.selected));
                changeinfo(1);
                break;
            case 2:
                igame.setSelected(true);
                tgame.setTextColor(getResources().getColor(R.color.selected));
                changeinfo(2);
            default:
                break;
        }
    }

    private void resetBottom() {
        imovie.setSelected(false);
        tmovie.setTextColor(getResources().getColor(R.color.gray));
        itv.setSelected(false);
        ttv.setTextColor(getResources().getColor(R.color.gray));
        igame.setSelected(false);
        tgame.setTextColor(getResources().getColor(R.color.gray));
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp);
        llmovie = findViewById(R.id.ll_movie);
        lltv = findViewById(R.id.ll_tv);
        llgame = findViewById(R.id.ll_game);
        imovie = findViewById(R.id.i_movie);
        itv = findViewById(R.id.i_tv);
        igame = findViewById(R.id.i_game);
        tmovie = findViewById(R.id.t_movie);
        ttv = findViewById(R.id.t_tv);
        tgame = findViewById(R.id.t_game);
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
        BlankFragment fragment1 = BlankFragment.newInstance("这是1", "");
        BlankFragment fragment2 = BlankFragment.newInstance("这是2", "");
        BlankFragment fragment3 = BlankFragment.newInstance("这是3", "");

        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_movie:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_tv:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_game:
                mViewPager.setCurrentItem(2);
            default:
                break;
        }
    }

    public void changeinfo(int position){
        mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", imgs[(i+position) % imgs.length]);
            map.put("title", "标题" + i);
            map.put("content", "内容" + i);
            mList.add(map);
        }

        //Adapter
        mSimpleAdapter = new SimpleAdapter(this,
                mList,
                R.layout.simple_item_layout,
                new String[]{"img", "title", "content"},
                new int[]{R.id.i_image, R.id.t_title, R.id.t_content});

        mListView.setAdapter(mSimpleAdapter);

    }

   /* //只有点击切换页面
    private ViewPager mViewPager;
    private VPAdapter mVPAdapter;
    private List<Fragment> mFragmentList;

    private ImageView imovie, itv;
    private TextView tmovie, ttv;
    private LinearLayout llmovie, lltv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpfragment);

        initView();
        initData();

        mViewPager = findViewById(R.id.vp);
        mVPAdapter=new VPAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mVPAdapter);

        //监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //    Toast.makeText(VPFragmentActivity.this, "当前为第"+(position+1)+"页", Toast.LENGTH_SHORT).show();//提示
                onSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //默认显示第一页
        onSelected(0);

        llmovie.setOnClickListener(this);
        lltv.setOnClickListener(this);
    }

    private void onSelected(int position) {
        resetBottom();
        switch (position){
            case 0:
                imovie.setSelected(true);
                tmovie.setTextColor(getResources().getColor(R.color.selected));
                break;
            case 1:
                itv.setSelected(true);
                ttv.setTextColor(getResources().getColor(R.color.selected));
                break;
            default:
                break;
        }

    }

    private void resetBottom() {
        imovie.setSelected(false);
        tmovie.setTextColor(getResources().getColor(R.color.gray));
        itv.setSelected(false);
        ttv.setTextColor(getResources().getColor(R.color.gray));
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp);
        llmovie = findViewById(R.id.ll_movie);
        lltv = findViewById(R.id.ll_tv);
        imovie = findViewById(R.id.i_movie);
        itv = findViewById(R.id.i_tv);
        tmovie = findViewById(R.id.t_movie);
        ttv = findViewById(R.id.t_tv);
    }

    private void initData() {
        mFragmentList=new ArrayList<>();
        BlankFragment fragment1 = BlankFragment.newInstance("这是1","");
        BlankFragment fragment2 = BlankFragment.newInstance("这是2","");

        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_movie:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_tv:
                mViewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }*/

}