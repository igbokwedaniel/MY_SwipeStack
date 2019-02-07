package com.babbangona.bg_swipestack;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class SpotDiffCallback extends DiffUtil.Callback {

    private List<Spot> Old;
    private List<Spot> New;
    public  SpotDiffCallback(List<Spot> old_, List<Spot> new_){
        this.Old = old_;
        this.New = new_;
    }

    @Override
    public int getOldListSize() {
        return Old.size();
    }

    @Override
    public int getNewListSize() {
        return New.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return (Old.get(i).getId() == New.get(i1).getId());
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return (Old.get(i) == New.get(i1));
    }
}
