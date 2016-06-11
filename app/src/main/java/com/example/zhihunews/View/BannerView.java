package com.example.zhihunews.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.ZhihuTopStories;
import com.example.zhihunews.R;
import com.example.zhihunews.UI.ZhihuContentActivity;
import com.example.zhihunews.UI.ZhihuContentActivity;
import com.example.zhihunews.Utils.Constants;
import com.example.zhihunews.Utils.ImagerLoad;

/**
 * Created by Lxq on 2016/5/25.
 */
public class BannerView implements Holder <ZhihuTopStories> {
    private View view;
    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.bannerview_real,null);
        return view;
    }

    @Override
    public void UpdateUI(final Context context, int position, final ZhihuTopStories data) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.topStories_img);
        TextView title = (TextView) view.findViewById(R.id.topStories_titel);
        if(data.isValid()) {
            ImagerLoad.load(context, data.getImage(), imageView);
            title.setText(data.getTitle());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZhihuContentActivity.class);
                intent.putExtra(Constants.ID, data.getId());
                intent.putExtra(Constants.TITLE,data.getTitle());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (MainActivity)context,imageView,context.getString(R.string.shared_img)
                );
                ActivityCompat.startActivity((MainActivity)context,intent, optionsCompat.toBundle());
            }
        });
    }
}
