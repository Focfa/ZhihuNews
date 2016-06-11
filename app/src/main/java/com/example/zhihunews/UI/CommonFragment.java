package com.example.zhihunews.UI;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zhihunews.Adapter.CommomFragmentAdapter;
import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.ThemeJson;
import com.example.zhihunews.Model.ThemeStory;
import com.example.zhihunews.Model.ThemesListJson;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;
import com.example.zhihunews.Utils.ShowTips;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.zhihunews.UI.FragmentNews.ARGUMENT;

/**
 * Created by Lxq on 2016/6/7.
 */

public class CommonFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerViewItemClick {
    private View rootview;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    public static final String ARGUMENT = "argument";
    private LinearLayoutManager layoutManager ;
    private CommomFragmentAdapter adapter;
    public static final String THEME_FIRSTPAGE = "firstPage";
    public static final String THEME_ISAVE = "互联网安全";
    public static final String THEME_PSYCHOLOGY = "日常心理学";
    public static final String THEME_USERLIKE = "用户推荐日报";
    public static final String THEME_NOBORED = "不许无聊";
    public static final String THEME_DESIGN = "设计日报";
    public static final String THEME_COMPANY = "大公司日报";
    public static final String THEME_MOVIE = "电影日报";
    public static final String THEME_GAME = "开始游戏";
    public static final String THEME_MUSIC = "音乐日报";
    public static final String THEME_TYPE = "themeType";
    private String themeType;
    private ThemesListJson themesListJson;
    private long lastGetTime;
    private static final int GET_DURATION = 2000;
    private int firstposition;
    private int lastposition;
    private int themeId;

    public static CommonFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        CommonFragment commonFragment = new CommonFragment();
        commonFragment.setArguments(bundle);
        return commonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootview==null) {
            rootview = inflater.inflate(R.layout.refresh_recyclerview, container, false);
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.refresh_RecyclerView);
        setupRecyclerView();
        return rootview;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(API.TYPE_LATEST);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CommomFragmentAdapter(getActivity(),this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScolledLoad();
                }

            }
        });
    }
    private void onScolledLoad() {
        firstposition = layoutManager.findFirstVisibleItemPosition();
        lastposition = layoutManager.findLastVisibleItemPosition();
        if (lastposition+1 == adapter.getItemCount() && adapter.getItemCount()!=0) {
            getData(API.TYPE_BEFORE);
        }
    }

    @Override
    public void onRefresh() {
         getData(API.TYPE_LATEST);
    }

    @Override
    public void onRecyclerViewItemClick(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof CommomFragmentAdapter.ViewHolder) {
            CommomFragmentAdapter.ViewHolder holder = (CommomFragmentAdapter.ViewHolder) viewHolder;
            Intent intent = new Intent(getActivity(),ThemeContentActivity.class);
            intent.putExtra(Constants.POSITION,holder.getAdapterPosition());
            List<ThemeStory> themeStories = adapter.getThemeStoryList();
            intent.putExtra(Constants.LIST,(Serializable)themeStories);

           // intent.putExtra(Constants.ID,holder.themeStory.getId());
          //  intent.putExtra(Constants.NEWS_TYPE,Constants.COMMON_NEWS);
         //   ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
         //           holder.mImageView,getString(R.string.shared_img));
         //   ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
              startActivity(intent);
        }
    }

    private void getData(int datatype) {
        themeType = getArguments().getString(ARGUMENT);
      //  Log.d("tag","themetype is "+themeType);
        lastGetTime = System.currentTimeMillis();
        if(datatype == API.TYPE_LATEST) {
            getThemesListJson(datatype);
        }else if(datatype == API.TYPE_BEFORE) {
            getThemeJsonBefore(datatype);
        }
    }

    private void getThemesListJson(final int datatype) {

        VolleyManager.getInstance(getActivity()).GetGsonRequest(getActivity(), API.NEWS_THEMES, ThemesListJson.class, new Response.Listener<ThemesListJson>() {
            @Override
            public void onResponse(ThemesListJson response) {
               // Log.d("tag","limit = "+response.getLimit());
              //  Log.d("tag","first name = "+response.getOthers().get(0).getName());
                themesListJson = response;
                if(response != null) {
                    for (int i = 0; i < response.getOthers().size(); i++) {
                       // Log.d("tag","name= "+response.getOthers().get(i).getName());
                        String themeName = response.getOthers().get(i).getName();
                        if (themeName.equals(themeType)) {
                            themeId = response.getOthers().get(i).getId();
                            getThemeJsonById(themeId,datatype);
                            return;
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                    getThemesListJson(datatype);
                    return;
                }
                error.printStackTrace();
                ShowTips.showSnack(((MainActivity) getActivity()).getmDrawLayout(),R.string.no_net);
            }
        });
    }


    private void getThemeJsonById(int id, final int datatype) {
        VolleyManager.getInstance(getActivity()).GetGsonRequest(getActivity(), API.THEME_CONTENT_LISTS + id, ThemeJson.class,
                new Response.Listener<ThemeJson>() {
                    @Override
                    public void onResponse(ThemeJson response) {
                      adapter.notifyDataSetChanged(response,datatype);
                        showProgress(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        showProgress(false);
                    }
                });
    }

    public void showProgress(final boolean refreshState) {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(refreshState);
        }
    }

    private void getThemeJsonBefore(final int datatype) {
        List<ThemeStory> themeStoryList = new ArrayList<>();
        themeStoryList = adapter.getThemeStoryList();
          int miniId = themeStoryList.get(0).getId();
            for (int i = 1; i < themeStoryList.size();i++) {
                if(miniId > themeStoryList.get(i).getId()) {
                    miniId = themeStoryList.get(i).getId();
                }
            }
         Log.d("tag","mini id = "+miniId);
        if(themeId != 0) {
            VolleyManager.getInstance(getActivity()).GetGsonRequest(getActivity(), API.THEME_CONTENT_LISTS + themeId + "/before/" + miniId, ThemeJson.class,
                    new Response.Listener<ThemeJson>() {
                        @Override
                        public void onResponse(ThemeJson response) {
                            adapter.notifyDataSetChanged(response,datatype);
                            Log.d("tag","news title"+response.getStories().get(0).getTitle());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

                        }
                    });
        }
    }
}
