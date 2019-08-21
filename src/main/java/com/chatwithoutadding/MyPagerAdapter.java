package com.chatwithoutadding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new TabNuevo();
            case 1: return new TabGuardado();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public String getActiveFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  name;
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}
