package com.example.zhihunews.Adapter;

import android.content.ContentQueryMap;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.Model.PhoenixNews.Beauty;
import com.example.zhihunews.Model.PhoenixNews.BeautyImage;
import com.example.zhihunews.Model.PhoenixNews.PhoenixNewsList;
import com.example.zhihunews.Model.PhoenixNews.SingleItem;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.ImagerLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lxq on 2016/6/17.
 */

public class FragmentBeautyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private RecyclerViewItemClick recyclerViewItemClick;
    private Context context;
    private List<Beauty> beautyList;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private List<Integer> heightLists;

    public FragmentBeautyAdapter(RecyclerViewItemClick viewItemClick, Context context) {
        this.recyclerViewItemClick = viewItemClick;
        this.context = context;
        beautyList = new ArrayList<>();
        heightLists = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recyclerview_footer_loading,parent,false);
            return  new FooterViewHolder(view);
        }else  {
            View view = inflater.inflate(R.layout.recyclerview_item_beauty,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if(holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.beauty = beautyList.get(position);
            ImagerLoad.load(context,viewHolder.beauty.getImg().get(0).getUrl(),viewHolder.beautyImg);
        }
    }
    private void getRandomHeightLists(List<Beauty> beautyList) {

        for (Beauty beauty:beautyList) {

        }
    }

    @Override
    public int getItemCount() {

        if(beautyList == null) {
            return  0;
        }else {
            return beautyList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == beautyList.size()+1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView beautyImg;
        private Beauty beauty;
        public ViewHolder (View itemView) {
            super(itemView);
            beautyImg = (ImageView) itemView.findViewById(R.id.image_beauty);
        }

    }

    public void setBeautyList(List<Beauty> beautyList) {
        this.beautyList = beautyList;
        notifyDataSetChanged();
    }
}
