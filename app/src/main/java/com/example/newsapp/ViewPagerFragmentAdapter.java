package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private String[] titles = new String[]{"politics", "sports", "technology", "business"};

    public ViewPagerFragmentAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PoliticsFragment();
            case 1:
                return new SportsFragment();
            case 2:
                return new TechFragment();
            case 3:
                return new BusinessFragment();
        }
        return new PoliticsFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
