package com.example.zhihunews.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.zhihunews.Model.PhoenixNews.SingleItem;
import com.example.zhihunews.R;
import com.example.zhihunews.Utils.ImagerLoad;

/**
 * Created by Lxq on 2016/6/16.
 */

public class BannerViewPhoenix implements Holder<SingleItem> {
    private View view;


    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.bannerview_real,null);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, SingleItem data) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.topStories_img);
        TextView title = (TextView) view.findViewById(R.id.topStories_titel);


        ImagerLoad.load(context, data.getThumbnail(), imageView);
        title.setText(data.getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
