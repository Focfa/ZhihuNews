package com.example.zhihunews.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zhihunews.R;
import com.example.zhihunews.Utils.CommonUtils;
import com.example.zhihunews.Utils.ImagerLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lxq on 2016/6/21.
 */

public class ImageContainerLayout extends LinearLayout {
    private Context context;
    private List<String> imageUrls;
    private ImageView imageView;
    public ImageContainerLayout(Context context) {
        this(context,null);
        inti(context);
    }

    public ImageContainerLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
        inti(context);
    }

    public ImageContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti(context);
    }

    private void inti(Context context ) {
        this.context = context;
        imageUrls = new ArrayList<>();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void setImageUrls(List<String> imageUrls) {
        if(this.imageUrls != null) {
            this.imageUrls.clear();
        }
        this.imageUrls = imageUrls;
        if(this.imageUrls != null && (!this.imageUrls.isEmpty())) {
            int j = this.imageUrls.size();
            Log.d("tag","imageUrl size is" +j);
            int i = 0;
            while (i < j) {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_RADIX_23p0, getResources().getDimensionPixelSize(R.dimen.image_crop_height), getResources().getDisplayMetrics()), 1.0F);
                if(i < j-1) {
                    layoutParams.rightMargin = (int) CommonUtils.convertDpToPixel(context,5.0f);
                }
                addView(imageView,layoutParams);
                ImagerLoad.load(context,this.imageUrls.get(i),imageView);
                i += 1;
            }
            invalidate();
        }


    }
}
