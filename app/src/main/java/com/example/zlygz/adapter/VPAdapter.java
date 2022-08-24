package com.example.zlygz.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class VPAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;//数据源

    public VPAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {//传
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//返回对应的fragment
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {//页数
        return mFragmentList == null ? 0 :mFragmentList.size();
    }

}
