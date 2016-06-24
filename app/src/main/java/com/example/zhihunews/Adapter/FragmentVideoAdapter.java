package com.example.zhihunews.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.zhihunews.APP;
import com.example.zhihunews.Interface.NeedLayoutChanged;
import com.example.zhihunews.MainActivity;
import com.example.zhihunews.Model.PhoenixNews.VideoItem;
import com.example.zhihunews.Net.API;
import com.example.zhihunews.R;
import com.example.zhihunews.UI.FragmentVideo;
import com.example.zhihunews.Utils.ImagerLoad;
import com.example.zhihunews.Utils.TimeUtils;
import com.sprylab.android.widget.TextureVideoView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Lxq on 2016/6/18.
 */

public class FragmentVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoItem> videoItemList;
    private FragmentActivity context;
    private ViewHolder currentViewHolder;
    private final Rect mCurrentViewRect = new Rect();
    private NeedLayoutChanged layoutChanged;


    public FragmentVideoAdapter (FragmentActivity context) {
        this.context = context;
    }

    public void onScrolled (RecyclerView recyclerView) {
        if(currentViewHolder != null) {
            currentViewHolder.onScrolled(recyclerView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item_video,parent,false);
        return new ViewHolder(view);
    }

    public void setNeedLayoutChanged(NeedLayoutChanged layoutChanged) {
        this.layoutChanged = layoutChanged;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        if(holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            if (position == 0) {
                viewHolder.videoTime.setText("");
                ImagerLoad.load(context,R.drawable.hend_background,viewHolder.imageView);
                viewHolder.videoTitle.setText(context.getText(R.string.Live_phoenix));
                viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.progressBar.setVisibility(View.VISIBLE);
                        viewHolder.playButton.setVisibility(View.INVISIBLE);
                        viewHolder.videoView.setVideoPath(API.LIVE_PHOENIX);
                        viewHolder.videoView.setVisibility(View.VISIBLE);//设置可见，设置的监听才生效
                        viewHolder.videoView.requestFocus();
                        viewHolder.videoView.setMediaController(new MediaController(context));
                        currentViewHolder = viewHolder;
                    }
                });

                viewHolder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        viewHolder.progressBar.setVisibility(View.INVISIBLE);
                        viewHolder.imageView.setVisibility(View.GONE);
                        viewHolder.playButton.setVisibility(View.INVISIBLE);
                        viewHolder.videoTitle.setVisibility(View.INVISIBLE);
                        viewHolder.videoTime.setVisibility(View.GONE);
                        viewHolder.videoView.seekTo(0);
                        viewHolder.videoView.start();
                    }
                });

            } else {
                viewHolder.videoItem = videoItemList.get(position-1);
                viewHolder.videoTitle.setText(viewHolder.videoItem.getTitle());
                viewHolder.videoTime.setText(TimeUtils.getTime(viewHolder.videoItem.getDuration()));
                Log.d("videoAdapter", "video_url = " + viewHolder.videoItem.getVideo_url());
                ImagerLoad.load(context, viewHolder.videoItem.getImage(), viewHolder.imageView);
                viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.progressBar.setVisibility(View.VISIBLE);
                        viewHolder.playButton.setVisibility(View.INVISIBLE);
                        viewHolder.videoView.setVideoPath(viewHolder.videoItem.getVideo_url());
                        viewHolder.videoView.setVisibility(View.VISIBLE);//设置可见，设置的监听才生效
                        viewHolder.videoView.requestFocus();
                        viewHolder.videoView.setMediaController(new MediaController(context));
                        currentViewHolder = viewHolder;


                    }
                });
                viewHolder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        viewHolder.progressBar.setVisibility(View.INVISIBLE);
                        viewHolder.imageView.setVisibility(View.GONE);
                        viewHolder.playButton.setVisibility(View.INVISIBLE);
                        viewHolder.videoTime.setVisibility(View.INVISIBLE);
                        viewHolder.videoTitle.setVisibility(View.INVISIBLE);
                        //  viewHolder.videoView.setVisibility(View.VISIBLE);
                        viewHolder.videoView.seekTo(0);
                        viewHolder.videoView.start();

                    }
                });

                viewHolder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        if (viewHolder.videoView.getVisibility() == View.VISIBLE) {
                            viewHolder.videoView.setVisibility(View.GONE);
                        }
                        viewHolder.imageView.setVisibility(View.VISIBLE);
                        viewHolder.playButton.setVisibility(View.VISIBLE);
                        viewHolder.videoTime.setVisibility(View.VISIBLE);
                        viewHolder.videoTitle.setVisibility(View.VISIBLE);
                        currentViewHolder = null;
                        notifyDataSetChanged();

                    }
                });

                viewHolder.videoView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        Log.v("Video", "onFocusChange" + hasFocus);
                        if (!hasFocus && currentViewHolder == viewHolder) {
                            viewHolder.stopVideo();
                        }

                    }
                });
            }
        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if(videoItemList == null) {
            return 0;
        }else {
            return videoItemList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextureVideoView videoView;
        private ProgressBar progressBar;
        private ImageView playButton;
        private VideoItem videoItem;
        private TextView videoTitle,videoTime;

        public ViewHolder(View itemView) {
            super(itemView);
             imageView = (ImageView) itemView.findViewById(R.id.video_bottom_image);
             videoView = (TextureVideoView) itemView.findViewById(R.id.videoView);
             progressBar = (ProgressBar) itemView.findViewById(R.id.video_progress_bar);
             playButton = (ImageView) itemView.findViewById(R.id.video_button);
             videoTitle = (TextView) itemView.findViewById(R.id.video_title);
             videoTime = (TextView) itemView.findViewById(R.id.video_time);
        }

        public void onScrolled(RecyclerView recyclerView) {

            if(getVisibilityPercents(currentViewHolder.itemView) <= 10 ||!isViewVisible(currentViewHolder.itemView,recyclerView)) {
                stopVideo();
            }
        }


        public void stopVideo() {
            videoView.pause();
            if (videoView.getVisibility() == View.VISIBLE) {
                videoView.setVisibility(View.GONE);
            }
            imageView.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
            videoTime.setVisibility(View.VISIBLE);
            videoTitle.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            itemView.requestLayout();
            currentViewHolder = null;
            Log.d("videoAdapter","videopause ??");
        }


        public boolean isViewVisible(View view, RecyclerView recyclerView) {
            Rect scrollBounds = new Rect();
            recyclerView.getHitRect(scrollBounds);
            return view.getLocalVisibleRect(scrollBounds);
        }


        public int getVisibilityPercents(View view) {


            int percents = 100;

            view.getLocalVisibleRect(mCurrentViewRect);

            int height = view.getHeight();


            if(viewIsPartiallyHiddenTop()){
                // view is partially hidden behind the top edge
                percents = (height - mCurrentViewRect.top) * 100 / height;
                Log.d("percents","topPercents ="+percents);
            } else if(viewIsPartiallyHiddenBottom(height)){
                percents = mCurrentViewRect.bottom * 100 / height;
                Log.d("percents","bottomPercents ="+percents);
            }

            return percents;
        }

        private boolean viewIsPartiallyHiddenBottom(int height) {
            return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
        }

        private boolean viewIsPartiallyHiddenTop() {
            return mCurrentViewRect.top > 0;
        }

    }

    public void setVideoItemList(List<VideoItem> videoItemList) {
        this.videoItemList = videoItemList;
        notifyDataSetChanged();
    }

    public List<VideoItem> getVideoItemList() {
        return videoItemList;
    }



}
