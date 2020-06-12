package com.example.dicodingapp.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dicodingapp.R;
import com.example.dicodingapp.fragment.FollowerFragment;
import com.example.dicodingapp.fragment.FollowingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter{
    private final Context context;
    private String username;

    public ViewPagerAdapter(FragmentManager fm, Context context, String username) {
        super(fm);
        this.context = context;
        this.username = username;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tabFollowing,
            R.string.tabFollower
    };

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment fragmentone = new FollowingFragment();
                Bundle pack = new Bundle();
                pack.putString("keyone", username);
                fragmentone.setArguments(pack);
                return fragmentone;
            case 1:
                Fragment fragment = new FollowerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("key", username);
                fragment.setArguments(bundle);
                return fragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
