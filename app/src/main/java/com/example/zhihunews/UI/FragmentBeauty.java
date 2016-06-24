package com.example.zhihunews.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.APP;
import com.example.zhihunews.Adapter.FragmentBeautyAdapter;
import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.PhoenixNews.Beauty;
import com.example.zhihunews.Model.PhoenixNews.BeautyJson;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lxq on 2016/6/17.
 */

public class FragmentBeauty extends Fragment implements RecyclerViewItemClick,SwipeRefreshLayout.OnRefreshListener {
    public static final String ARGUMENT = "argument";
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private FragmentBeautyAdapter adapter;
    private long lastGetTime;
    private static final int GET_DURATION = 2000;
    private MainActivity mainActivity;
    private int nextPage = 2;
    private List<Beauty> beautyList ;
    private int[] lastPositions;

    public static FragmentBeauty newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        FragmentBeauty fragmentBeauty = new FragmentBeauty();
        fragmentBeauty.setArguments(bundle);
        return  fragmentBeauty;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.refresh_recyclerview, container, false);
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.refresh_RecyclerView);
        beautyList = new ArrayList<>();
        setupRecyclerView();
        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(API.TYPE_LATEST);
    }


    public void setupRecyclerView() {
        mainActivity = (MainActivity) getActivity();
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        adapter = new FragmentBeautyAdapter(this,mainActivity);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScolledLoad();
                }

            }
        });

    }
    private void onScolledLoad() {

      // int firstposition = layoutManager
         int lastVisibleItemPosition;
         lastPositions = new int[layoutManager.getSpanCount()];
         layoutManager.findLastVisibleItemPositions(lastPositions);
        lastVisibleItemPosition = findMax(lastPositions);
        if (lastVisibleItemPosition+1 == adapter.getItemCount() && adapter.getItemCount()!=0) {
            getData(API.TYPE_BEFORE);
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onRefresh() {
        getData(API.TYPE_LATEST);
    }

    @Override
    public void onRecyclerViewItemClick(RecyclerView.ViewHolder viewHolder) {

    }

    private void getData(int dataType) {
        lastGetTime = System.currentTimeMillis();
        getBeautyJson(dataType);

    }

    private void getBeautyJson(final int dataType) {

        if(dataType == API.TYPE_LATEST) {
            String url = API.BEAUTY_PHOENIX+"1";
            VolleyManager.getInstance(mainActivity).GetGsonRequest(mainActivity, url, BeautyJson.class,
                    new Response.Listener<BeautyJson>() {
                        @Override
                        public void onResponse(BeautyJson response) {
                            if (response != null) {
                                resolveBeautyList(response,dataType);
                            }
                            showProgress(false);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                                getBeautyJson(dataType);
                                return;
                            }
                            error.printStackTrace();
                            showProgress(false);
                        }
                    });
        }else if(dataType == API.TYPE_BEFORE) {
            String nextUrl = API.BEAUTY_PHOENIX+nextPage;
            VolleyManager.getInstance(APP.getContext()).GetGsonRequest(APP.getContext(), nextUrl, BeautyJson.class,
                    new Response.Listener<BeautyJson>() {
                        @Override
                        public void onResponse(BeautyJson response) {
                            resolveBeautyList(response,dataType);
                            showProgress(false);
                            if(beautyList != null &&
                                    nextPage < response.getMeta().getPageSize()) {
                                nextPage++;
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                                getBeautyJson(dataType);
                                return;
                            }
                            error.printStackTrace();
                            showProgress(false);
                        }
                    });
        }
    }



    private void resolveBeautyList (BeautyJson beautyJson,int dataType) {
        if(beautyList.size() == 0) {
            beautyList.addAll(beautyJson.getBody());
        }else {
            if (dataType == API.TYPE_LATEST) {
                String id = beautyJson.getBody().get(0).getId();
                String fistId = beautyList.get(0).getId();
                if(!fistId.equals(id) && !beautyList.containsAll(beautyJson.getBody())) {
                    beautyList.addAll(0,beautyJson.getBody());
                }

            } else if (dataType == API.TYPE_BEFORE) {
                beautyList.addAll(beautyJson.getBody());
            }
        }
      //  beautyList = beautyJson.getBody();
        adapter.setBeautyList(beautyList);
    }

    public void showProgress(final boolean refreshState) {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(refreshState);
        }
    }

}
