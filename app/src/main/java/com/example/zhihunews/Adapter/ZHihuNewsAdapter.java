package com.example.zhihunews.Adapter;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.Model.ZHihuStory;
import com.example.zhihunews.Model.ZhihuJson;
import com.example.zhihunews.Model.ZhihuTopStories;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.Constants;
import com.example.zhihunews.Utils.DB;
import com.example.zhihunews.Utils.ImagerLoad;
import com.example.zhihunews.View.BannerView;

import org.w3c.dom.ls.LSException;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Lxq on 2016/5/23.
 */
public class ZHihuNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;

    private static final int TYPE_BANNER = 0;
    /**
     * header is a title to display date
     */
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    /**
     * footer is to show load more hint
     */
    private static final int TYPE_FOOTER = 3;
    private int textGrey;
    private int textDark;
    private RecyclerViewItemClick mRecyclerViewItemClick;
    private RealmResults<ZhihuJson> mZhihuJson;
    private RealmResults<ZhihuTopStories> zhihuTopStories;
    private RealmResults<ZHihuStory> zHihuStories;
    public ConvenientBanner<ZhihuTopStories> banner;
    private Realm realm;
    private RealmChangeListener<RealmResults<ZhihuJson>> zhihuJsonChangeListener;
    private RealmChangeListener<RealmResults<ZhihuTopStories>> changeListener;
    private RealmChangeListener<RealmResults<ZHihuStory>> storyChangeListener;

    public ZHihuNewsAdapter(RecyclerViewItemClick listener,Context context) {
        this.mRecyclerViewItemClick = listener;
        this.mcontext = context;
        realm = Realm.getDefaultInstance();
        mZhihuJson = realm.where(ZhihuJson.class).findAllSorted(Constants.DATE, Sort.DESCENDING);

        zHihuStories = realm.where(ZHihuStory.class).findAllSorted(Constants.GA_PREFIX,Sort.DESCENDING);
        zhihuTopStories = DB.findAll(realm,ZhihuTopStories.class);

        //添加监听器，当realm后台数据发生变化会触发
        zhihuJsonChangeListener = new RealmChangeListener<RealmResults<ZhihuJson>>() {
            @Override
            public void onChange(RealmResults<ZhihuJson> element) {
                notifyDataSetChanged();
            }
        };

        changeListener = new RealmChangeListener<RealmResults<ZhihuTopStories>>() {
           @Override
           public void onChange(RealmResults<ZhihuTopStories> element) {
               if(banner != null) {
                   banner.notifyDataSetChanged();
               }
               notifyDataSetChanged();
           }
        };

        storyChangeListener = new RealmChangeListener<RealmResults<ZHihuStory>>() {
            @Override
            public void onChange(RealmResults<ZHihuStory> element) {
                notifyDataSetChanged();
            }
        };
        mZhihuJson.addChangeListener(zhihuJsonChangeListener);
        zhihuTopStories.addChangeListener(changeListener);
        zHihuStories.addChangeListener(storyChangeListener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_BANNER) {
            View view = inflater.inflate(R.layout.fragment_news_banner,parent,false);
            return new BannerViewHolder(view);
        }else if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.recyclerview_header,parent,false);
            return new HeaderViewHolder(view);
        }else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recyclerview_footer_loading,parent,false);
            return new FooterViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.recyclerview_item,parent,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        textGrey = ContextCompat.getColor(context, R.color.darker_gray);
        textDark = ContextCompat.getColor(context, android.support.design.R.color.abc_primary_text_material_light);
       if(holder instanceof HeaderViewHolder) {
           final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
           if(position ==1) {
              headerViewHolder.headerText.setText(R.string.hotnews);
           }

           }else if(holder instanceof ViewHolder) {
           final ViewHolder viewHolder = (ViewHolder) holder;
           viewHolder.zHihuStory = zHihuStories.get(position-2);

           ImagerLoad.load(context,viewHolder.zHihuStory.getImages().get(0).getVal(),viewHolder.mImageView);
           viewHolder.mTitle.setText(viewHolder.zHihuStory.getTitle());
           viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mRecyclerViewItemClick.onRecyclerViewItemClick(viewHolder);
               }
           });

       }else if(holder instanceof BannerViewHolder) {
           final BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
           bannerViewHolder.banner.setPages(new CBViewHolderCreator<BannerView>() {
               @Override
               public BannerView createHolder() {
                   return new BannerView();
               }
           },zhihuTopStories);
           banner = bannerViewHolder.banner;
       }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        if(zHihuStories == null) {
            return 0;
        }
        return zHihuStories.size()+2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_BANNER;
        }else if(position == zHihuStories.size()+1) {
            return TYPE_FOOTER;
        }else if (position == 1) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView headerText;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerText = (TextView) itemView.findViewById(R.id.story_header);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public final ConvenientBanner<ZhihuTopStories> banner;

        public BannerViewHolder(View view) {
            super(view);
            banner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTitle;
        public final ImageView mImageView;
        public ZHihuStory zHihuStory;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.news_title);
            mImageView = (ImageView) itemView.findViewById(R.id.news_img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }


    public List<ZHihuStory> getzHihuStories() {
        return zHihuStories;
    }

    public List<ZhihuTopStories> getZhihuTopStories() {
        return zhihuTopStories;
    }

    public int getTextGrey() {
        return textGrey;
    }
}
