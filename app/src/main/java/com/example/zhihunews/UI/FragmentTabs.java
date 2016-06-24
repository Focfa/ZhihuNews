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
public class FragmentTabs extends Fragment {
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       if(rootView!=null) {
           rootView = inflater.inflate(R.layout.refresh_recyclerview,container,false);
       }
       return rootView;
    }
}
