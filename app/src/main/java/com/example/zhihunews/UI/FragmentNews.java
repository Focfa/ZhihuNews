package com.example.zhihunews.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.zhihunews.APP;
import com.example.zhihunews.Adapter.ZHihuNewsAdapter;
import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.ZHihuStory;
import com.example.zhihunews.Model.ZhihuJson;
import com.example.zhihunews.Model.ZhihuTopStories;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.Net.Volley.GsonRequest;
import com.example.zhihunews.Net.Volley.Okhttp3Stack;
import com.example.zhihunews.Net.Volley.VolleyManager;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;
import com.example.zhihunews.Utils.DateUtils;
import com.example.zhihunews.Utils.SPUtils;
import com.example.zhihunews.Utils.ShowTips;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import okhttp3.OkHttpClient;

/**
 * Created by Lxq on 2016/5/22.
 */
public class FragmentNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerViewItemClick{
    private  View rootview;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    public static final String ARGUMENT = "argument";
    private ConvenientBanner banner;
    private LinearLayoutManager linearLayoutManager;
    private ZHihuNewsAdapter adapter;
    private ZhihuJson zhihuJson;
    private int firstposition;
    private int lastposition;
    private String newdate;
    private List<ZHihuStory> storyList;
    private List<ZhihuTopStories> topStoriesList;
    private static final int GET_DURATION = 2000;
    private long lastGetTime;
    private Realm realm;
    private List<String> list = new ArrayList<>();


    public static FragmentNews newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        FragmentNews fragmentNews = new FragmentNews();
        fragmentNews.setArguments(bundle);
        return fragmentNews;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
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
        intibanner();
        getData(API.TYPE_LATEST);
    }

    private void setupRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new ZHihuNewsAdapter(this,getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //没有滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScolledLoad();
                }
            }
        });
    }

    private void onScolledLoad() {
        intibanner();
        firstposition = linearLayoutManager.findFirstVisibleItemPosition();
        lastposition = linearLayoutManager.findLastVisibleItemPosition();
        if (lastposition+1 == adapter.getItemCount() && adapter.getItemCount()!=0) {
           getData(API.TYPE_BEFORE);
           lastGetTime = System.currentTimeMillis();
        }

    }

    private void intibanner() {
        if(banner == null) {
            if(mRecyclerView.getChildCount() !=0 &&
                    linearLayoutManager.findFirstVisibleItemPosition() ==0 ){
                banner = (ConvenientBanner) linearLayoutManager.findViewByPosition(0);
                banner.setScrollDuration(1000);
                banner.startTurning(5000);
            }
        }
    }
    @Override
    public void onRefresh() {
        showProgress(true);
        getData(API.TYPE_LATEST);
        lastGetTime = System.currentTimeMillis();
    }

    private void getData(final int type) {
        if (type == API.TYPE_LATEST) {
            VolleyManager.getInstance(getActivity()).GetGsonRequest(getActivity(), API.NEWS_LATEST,
                    ZhihuJson.class, new Response.Listener<ZhihuJson>() {
                        @Override
                        public void onResponse(ZhihuJson response) {
                            List<ZHihuStory> zHihuStoryList = response.getStories();
                            List<ZhihuTopStories> zhihuTopStoriesList = response.getTop_stories();

                            Log.d("Title", zHihuStoryList.get(0).getTitle() + "");
                            Log.d("image",zHihuStoryList.get(0).getImages().get(0).getVal());
                            saveZhihu(response,type);
                            showProgress(false);
                            noticeDataChange();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                                getData(API.TYPE_LATEST);
                                return;
                            }
                            error.printStackTrace();
                            showProgress(false);
                            ShowTips.showSnack(((MainActivity) getActivity()).getmDrawLayout(),R.string.no_net);
                        }
                    });
        } else if (type == API.TYPE_BEFORE) {
            final String date = SPUtils.get(Constants.DATE, DateUtils.parseStandardDate(new Date()));
            VolleyManager.getInstance(getActivity()).GetGsonRequest(getActivity(), API.NEW_BEFORE+date,
                    ZhihuJson.class, new Response.Listener<ZhihuJson>() {
                        @Override
                        public void onResponse(ZhihuJson response) {

                            newdate = response.getDate();
                            SPUtils.save(Constants.DATE, newdate);
                            saveZhihu(response, type);
                            noticeDataChange();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(System.currentTimeMillis() - lastGetTime < GET_DURATION) {
                                getData(API.TYPE_BEFORE);
                                return;
                            }
                            error.printStackTrace();
                            ShowTips.showSnack(((MainActivity) getActivity()).getmDrawLayout(),R.string.no_net);
                        }
                    });
        }
    }

    @Override
    public void onRecyclerViewItemClick(RecyclerView.ViewHolder viewHolder) {
         if(viewHolder instanceof ZHihuNewsAdapter.ViewHolder) {
             ZHihuNewsAdapter.ViewHolder holder = (ZHihuNewsAdapter.ViewHolder) viewHolder;
             Intent intent = new Intent(getActivity(),ZhihuContentAcitivity.class);
             intent.putExtra(Constants.ID,holder.zHihuStory.getId());
             intent.putExtra(Constants.TITLE,holder.zHihuStory.getTitle());
             intent.putExtra(Constants.NEWS_TYPE,Constants.HOT_NEWS);
             ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                     holder.mImageView,getString(R.string.shared_img));
             ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());

             holder.mTitle.setTextColor(adapter.getTextGrey());
         }
    }

    public void showProgress(final boolean refreshState) {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(refreshState);
        }
    }

    private void saveZhihu(final ZhihuJson zhihuJson,int type) {
        final String date = DateUtils.parseStandardDate(new Date());
        // Log.d("date is",date);
        final String gsonDate = zhihuJson.getDate();
        final RealmList<ZHihuStory> storyList = zhihuJson.getStories();
        final RealmList<ZhihuTopStories> topStoriesList = zhihuJson.getTop_stories();

        if (null != zhihuJson) {

            if (type == API.TYPE_LATEST ) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(ZhihuTopStories.class).findAll().deleteAllFromRealm();
                        realm.where(ZhihuJson.class).equalTo("date", date).findAll().deleteAllFromRealm();
                        realm.copyToRealmOrUpdate(zhihuJson);
                    }
                });

             //   realm.where(ZhihuJson.class).findAllSorted(Constants.DATE, Sort.DESCENDING);

            } else if(type == API.TYPE_BEFORE) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(zhihuJson);
                realm.commitTransaction();
             //   realm.where(ZhihuJson.class).findAllSorted(Constants.DATE, Sort.DESCENDING);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void noticeDataChange() {
        adapter.notifyDataSetChanged();
    }

}
