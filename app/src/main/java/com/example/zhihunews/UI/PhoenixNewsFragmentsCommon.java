package com.example.zhihunews.UI;

import android.content.Intent;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.zhihunews.Adapter.PhoenixNewsFragmentAdapter;
import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.PhoenixNews.Items;
import com.example.zhihunews.Model.PhoenixNews.PhoenixNewsList;
import com.example.zhihunews.Model.PhoenixNews.SingleItem;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhihunews.Net.API.TYPE_DS;
import static com.example.zhihunews.Net.API.TYPE_JS;
import static com.example.zhihunews.Net.API.TYPE_KJ;
import static com.example.zhihunews.Net.API.TYPE_LY;
import static com.example.zhihunews.Net.API.TYPE_SYLB;
import static com.example.zhihunews.Net.API.TYPE_TY;
import static com.example.zhihunews.Net.API.TYPE_YL;

/**
 * Created by Lxq on 2016/6/15.
 */

public class PhoenixNewsFragmentsCommon extends Fragment implements RecyclerViewItemClick,SwipeRefreshLayout.OnRefreshListener{
    private View rootView;
    private static final String ARGUMENT = "argument";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PhoenixNewsFragmentAdapter adapter;
    private String url;
    private String urlBefore;
    private static final String page = "1";
    private String currentPage ;
    private static final String action_auto = "auto";
    private static final String action_up = "up";
    private static final String action_DOWN = "down";
    private ConvenientBanner banner;
    private List<Items> itemsList;
    private List<SingleItem> itemList;
    private List<SingleItem> itemFocusList;
    private PhoenixNewsList phoenixNewsList;
    private PhoenixNewsList phoenixNewsHeader;
    private long lastGetTime;
    private static final int GET_DURATION = 2000;
    private MainActivity mainActivity;
    private static final String SLIDE = "slide";

    public static PhoenixNewsFragmentsCommon newsInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        PhoenixNewsFragmentsCommon fragmentsCommon = new PhoenixNewsFragmentsCommon();
        fragmentsCommon.setArguments(bundle);
        return fragmentsCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(rootView == null) {
            rootView = inflater.inflate(R.layout.refresh_recyclerview,container,false);
       }
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.refresh_RecyclerView);
        initUrl();
        setupRecyclerView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(API.TYPE_LATEST);
        intibanner();
    }

    private void setupRecyclerView() {
        mainActivity = (MainActivity) getActivity();
        linearLayoutManager = new LinearLayoutManager(mainActivity);
        adapter = new PhoenixNewsFragmentAdapter(this,mainActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScrolledLoad();
                }
            }

        });
    }

    private void onScrolledLoad() {
            intibanner();
    }

    @Override
    public void onRecyclerViewItemClick(RecyclerView.ViewHolder viewHolder) {

         if(viewHolder instanceof PhoenixNewsFragmentAdapter.ViewHolder) {
             PhoenixNewsFragmentAdapter.ViewHolder holder = (PhoenixNewsFragmentAdapter.ViewHolder) viewHolder;
             Intent intent = new Intent(getActivity(),PhoenixNewsContentActivity.class);
             intent.putExtra(Constants.URL,holder.item.getId());
             getActivity().startActivity(intent);
         }
    }

    @Override
    public void onRefresh() {
        showProgress(true);
        getData(API.TYPE_LATEST);
    }

    private void getData(int dataType) {
        lastGetTime = System.currentTimeMillis();
        getPhoenixNewsCommonJson(dataType);
        Log.d("tag","url ="+url);

    }

    private void getPhoenixNewsCommonJson(final int dataType) {
        VolleyManager.getInstance(mainActivity).GetGsonRequest(mainActivity, url, new TypeToken<ArrayList<PhoenixNewsList>>() {
        }, new Response.Listener<ArrayList<PhoenixNewsList>>() {
            @Override
            public void onResponse(ArrayList<PhoenixNewsList> response) {
                String test = response.get(0).getItem().get(0).getTitle();
              //  Log.d("test","是否Gson解析正确"+test);
                if(response != null) {
                    phoenixNewsList = response.get(0);
                    phoenixNewsHeader = response.get(1);
                    resolveItemList(phoenixNewsList, phoenixNewsHeader);
                }
                showProgress(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                    getPhoenixNewsCommonJson(API.TYPE_LATEST);
                    return;
                }
                error.printStackTrace();
                showProgress(false);
            }
        });
    }


    private void resolveItemList(PhoenixNewsList phoenixNewsList,PhoenixNewsList phoenixNewsHeader) {
                currentPage = phoenixNewsList.getCurrentPage();
                itemList = phoenixNewsList.getItem();
                itemFocusList = phoenixNewsHeader.getItem();
             //   adapter.setItemLists(itemList,itemFocusList);
                int i=0,j = 0;
                itemsList = new ArrayList<>();
                for (SingleItem singleItem :itemList) {
                    if(singleItem.getType().equals(SLIDE) ) {
                        i++;
                        itemsList.add(new Items().setItems(singleItem,1));
                    }else {
                        itemsList.add(new Items().setItems(singleItem,0));
                        j++;
                    }
                }

                Log.d("test","i ="+i +"  j ="+j);
                adapter.setItemsLists(itemsList,itemFocusList);

    }

    private void intibanner() {
        if(banner == null) {
            if(mRecyclerView.getChildCount() !=0 &&
                    linearLayoutManager.findFirstVisibleItemPosition() == 0 ){
                banner = (ConvenientBanner) linearLayoutManager.findViewByPosition(0);
                banner.setScrollDuration(1000);
                banner.startTurning(5000);
                banner.setPointViewVisible(true);
            }
        }
    }

    private void initUrl() {
        currentPage = page;
        String argument = getArguments().getString(ARGUMENT);
        if(argument == TYPE_SYLB) {
            url = API.HEADLINE_PHOENIX+page;
        }else if(argument == TYPE_KJ) {
            url = API.SLIENCE_PHOENIX+page;
        }else if(argument == TYPE_LY) {
            url = API.TRAVEL_PHOENIX+page;
        }else if(argument == TYPE_YL) {
            url = API.SHOWBIZ_PHOENIX+page;
        }else if(argument == TYPE_TY) {
            url = API.SPORTS_PHOENIX+page;
        }else if(argument == TYPE_JS) {
            url = API.MILITARY_PHOENIX+action_auto;
        }else if(argument ==TYPE_DS) {
            url = API.READ_PHOENIX+action_auto;
        }
    }


    private String getUrlBefore() {
        String nextPage = currentPage+1;
        String argument = getArguments().getString(ARGUMENT);
        if(argument == TYPE_SYLB) {
            urlBefore = API.HEADLINE_PHOENIX+nextPage;
        }else if(argument == TYPE_KJ) {
            urlBefore = API.SLIENCE_PHOENIX+nextPage;
        }else if(argument == TYPE_LY) {
            urlBefore = API.TRAVEL_PHOENIX+nextPage;
        }else if(argument == TYPE_YL) {
            urlBefore = API.SHOWBIZ_PHOENIX+nextPage;
        }else if(argument == TYPE_TY) {
            urlBefore = API.SPORTS_PHOENIX+nextPage;
        }else if(argument == TYPE_JS) {
            urlBefore = API.MILITARY_PHOENIX+action_up;
        }else if(argument ==TYPE_DS) {
            urlBefore = API.READ_PHOENIX+action_up;
        }
        return urlBefore;
    }

    public void showProgress(final boolean refreshState) {
        if (null != refreshLayout) {
            refreshLayout.setRefreshing(refreshState);
        }
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }
}
