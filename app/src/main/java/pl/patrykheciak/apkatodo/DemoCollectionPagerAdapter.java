package pl.patrykheciak.apkatodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.patrykheciak.apkatodo.db.TaskSublist;

public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

    List<TaskSublist> sublists = new ArrayList<>();

    public DemoCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void setSublists(List<TaskSublist> sublists) {
        this.sublists.clear();
        this.sublists.addAll(sublists);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new SublistFragment();
        Bundle args = new Bundle();
        args.putLong(SublistFragment.ARG_ID_SUBLIST, sublists.get(i).getId_tasksublist());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return sublists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Podlista " + (position + 1);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

