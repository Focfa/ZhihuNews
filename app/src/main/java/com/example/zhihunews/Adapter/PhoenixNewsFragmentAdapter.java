package com.example.zhihunews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.Model.PhoenixNews.Items;
import com.example.zhihunews.Model.PhoenixNews.PhoenixNewsList;
import com.example.zhihunews.Model.PhoenixNews.SingleItem;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.ImagerLoad;
import com.example.zhihunews.View.BannerViewPhoenix;
import com.example.zhihunews.View.ImageContainerLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lxq on 2016/6/15.
 */

public class PhoenixNewsFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mcontext;
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_ITEM_IMAGE = 3;
    private int textGrey;
    private List<SingleItem> itemList;
    private List<Items> itemsList;
    private List<SingleItem> itemFocusList;
    public ConvenientBanner<SingleItem> banner;
    private RecyclerViewItemClick mRecyclerViewItemClick;


    public PhoenixNewsFragmentAdapter(RecyclerViewItemClick itemClick, Context context) {
        this.mRecyclerViewItemClick = itemClick;
        this.mcontext = context;
        itemFocusList = new ArrayList<>();
        itemList = new ArrayList<>();
        itemsList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_BANNER) {
            View view = inflater.inflate(R.layout.fragment_news_banner,parent,false);
            return new BannerViewHolder(view);
        }else if(viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recyclerview_footer_loading,parent,false);
            return new FooterViewHolder(view);
        }else if(viewType == TYPE_ITEM_IMAGE) {
            View view = inflater.inflate(R.layout.recyclerview_item_images_slide,parent,false);
            return new ImageViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.recyclerview_item_phoenix,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if(holder instanceof BannerViewHolder) {
            final BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.banner.setPages(new CBViewHolderCreator<BannerViewPhoenix>() {
                @Override
                public BannerViewPhoenix createHolder() {
                    return new BannerViewPhoenix();
                }
            },itemFocusList);

            banner = bannerViewHolder.banner;
        }else if(holder instanceof ViewHolder) {
            String imageUrl = null;
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.item = itemsList.get(position-1).getItem();
            if(viewHolder.item.getThumbnail() != "") {
                ImagerLoad.load(context, viewHolder.item.getThumbnail(), viewHolder.thumbnail);
            }else {

                if(viewHolder.item.getStyle() != null) {
                    imageUrl = viewHolder.item.getStyle().getImages().get(0);
                    Log.d("tag","imageUrl ="+imageUrl);
                }

                if( imageUrl != null) {
                    ImagerLoad.load(context,imageUrl,viewHolder.thumbnail);
                }else {
                    ImagerLoad.load(context, R.drawable.welcome, viewHolder.thumbnail);
                }
            }
            viewHolder.title.setText(viewHolder.item.getTitle());
            viewHolder.newSource.setText(viewHolder.item.getSource());
            viewHolder.updateTime.setText(viewHolder.item.getUpdateTime());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerViewItemClick.onRecyclerViewItemClick(viewHolder);
                }
            });
        }else if(holder instanceof ImageViewHolder) {
            final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            List<String> imageUrls;
            imageViewHolder.item = itemsList.get(position-1).getItem();
            imageUrls = imageViewHolder.item.getStyle().getImages();
            imageViewHolder.imageTitle.setText(imageViewHolder.item.getTitle());
            ImagerLoad.loadWithHighPriority(imageUrls.get(0),imageViewHolder.firstImage);
            ImagerLoad.loadWithHighPriority(imageUrls.get(1),imageViewHolder.secondImage);
            ImagerLoad.loadWithHighPriority(imageUrls.get(2),imageViewHolder.thirdImage);
        }

    }

    @Override
    public int getItemCount() {

        if(itemsList == null) {
            return 0;
        }
        return itemsList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_BANNER;
        }else if(position == itemsList.size()+1) {
            return TYPE_FOOTER;
        }else {
            if(itemsList.get(position-1).getPos() == 0) {
                return TYPE_ITEM;
            }else {
                return TYPE_ITEM_IMAGE;
            }
        }
    }

    public void setItemsLists(List<Items> itemsList,List<SingleItem> itemFocusList) {
        this.itemsList = itemsList;
        this.itemFocusList = itemFocusList;
        notifyDataSetChanged();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public final ConvenientBanner<SingleItem> banner;

        public BannerViewHolder(View view) {
            super(view);
            banner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView imageTitle;
        private ImageContainerLayout containerLayout;
        private ImageView firstImage,secondImage,thirdImage;
        public SingleItem item;
        public ImageViewHolder (View view) {
            super(view);
            imageTitle = (TextView) view.findViewById(R.id.images_slide_title);
          //  containerLayout = (ImageContainerLayout) view.findViewById(R.id.images_container_layout);
            firstImage = (ImageView) view.findViewById(R.id.slide_image1);
            secondImage = (ImageView) view.findViewById(R.id.slide_image2);
            thirdImage = (ImageView) view.findViewById(R.id.slide_image3);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView title,newSource,updateTime;
        private PhoenixNewsList phoenixNewsList;
        public SingleItem item;
        public ViewHolder (View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.item_image_phoenix);
            title = (TextView) itemView.findViewById(R.id.item_title_phoenix);
            newSource = (TextView) itemView.findViewById(R.id.news_source);
            updateTime = (TextView) itemView.findViewById(R.id.updateTime);
        }

    }

}
