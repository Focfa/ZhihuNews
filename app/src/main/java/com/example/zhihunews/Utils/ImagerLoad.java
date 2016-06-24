package com.example.zhihunews.Utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.example.zhihunews.APP;

import java.net.URL;

/**
 * Created by Lxq on 2016/5/24.
 */
public class ImagerLoad {

    private static Bitmap bitmap;
    //网络加载
    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView);
    }
    //本地资源id加载
    public static void load(Context context, int resourceId, ImageView view) {
        Glide.with(context)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(view);
    }
    //使用动画加载
    public static void load(String url, int animationId, ImageView view) {
        Glide.with(APP.getContext())
                .load(url)
                .animate(animationId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }
    //属性动画
    public static void load(String url, ViewPropertyAnimation.Animator animator, ImageView view) {
        Glide.with(APP.getContext())
                .load(url)
                .animate(animator)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }


    //优先加载
    public static void loadWithHighPriority(String url, ImageView view) {
        Glide.with(APP.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .crossFade()
                .into(view);
    }

    public static Bitmap getBitmap(Context context,String url,int width ,int height) {

        try {
            bitmap =  Glide.with(context).load(url).asBitmap().into(width,height).get();
        }catch (Exception e){

        }
        return bitmap;
    }
    //缩略图
    public static void getThumbnail(Context context,String url,float size,ImageView imageView) {
        Glide.with(context)
                .load(url)
                .thumbnail(size)
                .into(imageView);
    }

    ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            // if it's a custom view class, cast it here
            // then find subviews and do the animations
            // here, we just use the entire view for the fade animation
            view.setAlpha( 0f );

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
            fadeAnim.setDuration( 300 );
            fadeAnim.start();
        }
    };
}
