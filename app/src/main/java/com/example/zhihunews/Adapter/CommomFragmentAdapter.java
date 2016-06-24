package com.example.zhihunews.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhihunews.Interface.RecyclerViewItemClick;
import com.example.zhihunews.Model.ThemeJson;
import com.example.zhihunews.Model.ThemeStory;
import com.example.zhihunews.Model.ThemesListJson;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.ImagerLoad;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lxq on 2016/6/7.
 */

public class CommomFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mcontext;
    private List<ThemeJson> themeJsonList;
    private List<ThemeStory> themeStoryList;
    private RecyclerViewItemClick recyclerViewItemClick;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private int textGrey;


    public CommomFragmentAdapter(Context context,RecyclerViewItemClick itemClick) {
         this.mcontext = context;
         this.recyclerViewItemClick = itemClick;
         themeJsonList = new ArrayList<>();
         themeStoryList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_HEADER) {
            View headerView = inflater.inflate(R.layout.themelist_header,parent,false);
            return new ThemeHeaderViewHolder(headerView);
        } else {
            View itemView = inflater.inflate(R.layout.recyclerview_item,parent,false);
            return new ViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        textGrey = ContextCompat.getColor(context, R.color.darker_gray);
        if(holder instanceof ThemeHeaderViewHolder) {
            final ThemeHeaderViewHolder headerViewHolder = (ThemeHeaderViewHolder) holder;
            if(!themeJsonList.isEmpty()) {
                headerViewHolder.headerTitle.setText(themeJsonList.get(0).getDescription());
                ImagerLoad.load(context, themeJsonList.get(0).getBackground(), headerViewHolder.headerImage);
            }
            } else if(holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            if(!themeStoryList.isEmpty()) {
                viewHolder.themeStory = themeStoryList.get(position - 1);
                viewHolder.mTitle.setText(viewHolder.themeStory.getTitle());
                if (viewHolder.themeStory.getImages() != null && viewHolder.themeStory.getImages().size()!=0) {
                    ImagerLoad.load(context, viewHolder.themeStory.getImages().get(0), viewHolder.mImageView);
                }else {
                    viewHolder.mImageView.setVisibility(View.GONE);
                }
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewItemClick.onRecyclerViewItemClick(viewHolder);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(themeStoryList.isEmpty()) {
            return 0;
        }
        return themeStoryList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class ThemeHeaderViewHolder extends RecyclerView.ViewHolder {
        final ImageView headerImage;
        final TextView headerTitle;

        public ThemeHeaderViewHolder(View view) {
            super(view);
            headerImage = (ImageView) view.findViewById(R.id.theme_header_img);
            headerTitle = (TextView) view.findViewById(R.id.theme_header_title);

        }

    }

   public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTitle;
        public final ImageView mImageView;
        public ThemeStory themeStory;
        public ViewHolder (View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.news_title);
            mImageView = (ImageView) itemView.findViewById(R.id.news_img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }

    public void notifyDataSetChanged(ThemeJson themeJson,int type) {
         if(themeJsonList.size() == 0) {
             themeJsonList.add(themeJson);
         }else {
             if (type == API.TYPE_LATEST) {
                 String responseTitle = themeJson.getStories().get(0).getTitle();
                 String title = themeJsonList.get(0).getStories().get(0).getTitle();
               //  Log.d("tag","responseTitle is "+responseTitle);
               //  Log.d("tag","title is "+title);
                 if (!title.equals(responseTitle)) {
                //     Log.d("tag","title is ");
                     themeJsonList.add(0,themeJson);
                 }
             }else if(type == API.TYPE_BEFORE) {
                 String responseTitle = themeJson.getStories().get(0).getTitle();
                 String title = themeJsonList.get(themeJsonList.size()-1).getStories().get(0).getTitle();
                 if(!title.equals(responseTitle)) {
                     themeJsonList.add(themeJson);
                 }
             }
         }

        // Set<ThemeJson> themeset = new LinkedHashSet<>(themeJsonList);
         themeStoryList.clear();
         for (ThemeJson themeJson1 :themeJsonList) {

             themeStoryList.addAll(themeJson1.getStories());
         }

         notifyDataSetChanged();
     }

    public List<ThemeStory> getThemeStoryList() {
        return themeStoryList;
    }
    public int getTextGrey() {
        return textGrey;
    }
}
