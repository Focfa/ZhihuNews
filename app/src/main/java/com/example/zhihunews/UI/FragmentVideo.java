package com.example.zhihunews.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.Adapter.FragmentVideoAdapter;
import com.example.zhihunews.Interface.NeedLayoutChanged;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.PhoenixNews.VideoItem;
import com.example.zhihunews.Model.PhoenixNews.VideoJson;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.google.gson.reflect.TypeToken;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;

import java.util.List;

/**
 * Created by Lxq on 2016/6/18.
 */

public class FragmentVideo extends Fragment implements SwipeRefreshLayout.OnRefreshListener,NeedLayoutChanged {

     private View rootView;
     private SwipeRefreshLayout refreshLayout;
     private RecyclerView recyclerView;
     private LinearLayoutManager layoutManager;
     private FragmentVideoAdapter adapter;
     private MainActivity mainActivity;
     private static final String ARGUMENT = "argument";
     private List<VideoItem> videoItemList;
     private String currentPage;
     private int totalPage;
    private long lastGetTime;
    private static final int GET_DURATION = 2000;
    private VideoView videoView;
    private ListItemsVisibilityCalculator calculator;
    private SingleListViewItemActiveCalculator calculatorsingle;
    public static FragmentVideo newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        FragmentVideo fragmentVideo = new FragmentVideo();
        fragmentVideo.setArguments(bundle);
        return fragmentVideo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.refresh_recyclerview,container,false);
            refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            refreshLayout.setOnRefreshListener(this);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.refresh_RecyclerView);
        }
        setupRecyclerView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(API.TYPE_LATEST);
    }

    private void setupRecyclerView() {
        mainActivity = (MainActivity) getActivity();
        layoutManager = new LinearLayoutManager(mainActivity);
        adapter = new FragmentVideoAdapter(getActivity());
        adapter.setNeedLayoutChanged(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING || newState == RecyclerView.SCROLL_STATE_IDLE) {
                    adapter.onScrolled(recyclerView);
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        getData(API.TYPE_LATEST);
    }

    @Override
    public void onChanged() {
        recyclerView.requestLayout();
        Log.d("test","是否执行到这里");
    }

    private void getData(int dataType) {
        lastGetTime = System.currentTimeMillis();
        getVideoJson(dataType);
    }

    private void getVideoJson(final int dataType) {
        String url = API.VEDIO_PHOENIX+"1";
        VolleyManager.getInstance(mainActivity).GetGsonRequest(mainActivity, url, new TypeToken<List<VideoJson>>() {
                },
                new Response.Listener<List<VideoJson>>() {
                    @Override
                    public void onResponse(List<VideoJson> response) {

                        if(response != null) {
                            currentPage = response.get(0).getCurrentPage();
                            totalPage = response.get(0).getTotalPage();
                            videoItemList = response.get(0).getItem();
                            resolveVideoItemList(videoItemList);
                        }
                        Log.d("test","是否Gson解析正确"+videoItemList.get(0).getVideo_url());
                        showProgress(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(System.currentTimeMillis() - lastGetTime <GET_DURATION) {
                            getVideoJson(dataType);
                            return;
                        }
                        error.printStackTrace();
                        showProgress(false);
                    }
                });
    }

    private void resolveVideoItemList(List<VideoItem> videoItems) {
        adapter.setVideoItemList(videoItems);
    }

    public void showProgress(final boolean refreshState) {
        if (null != refreshLayout) {
            refreshLayout.setRefreshing(refreshState);
        }
    }

}
