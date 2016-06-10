package com.example.zhihunews.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhihunews.R;

/**
 * Created by Lxq on 2016/5/22.
 */
public class FragmentFresh extends Fragment {
    private View rootview;
    public static final String ARGUMENT = "argument";

    public static FragmentFresh newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        FragmentFresh fragmentFresh = new FragmentFresh();
        fragmentFresh.setArguments(bundle);
        return fragmentFresh;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootview!=null) {
            rootview = inflater.inflate(R.layout.refresh_recyclerview, container, false);
        }
        return rootview;
    }
}
