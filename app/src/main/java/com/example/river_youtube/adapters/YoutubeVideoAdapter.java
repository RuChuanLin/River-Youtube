package com.example.river_youtube.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.river_youtube.BackgroundAudioPlayingIntentService;
import com.example.river_youtube.R;
import com.example.river_youtube.adapters.viewholders.BaseViewHolder;
import com.example.river_youtube.model.YoutubeVideo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "YoutubeVideoAdapter";

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<YoutubeVideo> mYoutubeVideoList;

    public YoutubeVideoAdapter(List<YoutubeVideo> youtubeVideoList) {
        mYoutubeVideoList = youtubeVideoList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false));
            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mYoutubeVideoList != null && mYoutubeVideoList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mYoutubeVideoList != null && mYoutubeVideoList.size() > 0) {
            return mYoutubeVideoList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<YoutubeVideo> youtubeVideoList) {
        mYoutubeVideoList.addAll(youtubeVideoList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.videoThumbnail)
        ImageView coverImageView;

        @BindView(R.id.videoTitle)
        TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
            coverImageView.setImageDrawable(null);
            titleTextView.setText("");
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final YoutubeVideo mYoutubeVideo = mYoutubeVideoList.get(position);
            if (mYoutubeVideo.getThumbnail() != null) {
                Glide.with(itemView.getContext())
                        .load(mYoutubeVideo.getThumbnail())
                        .into(coverImageView);
            }
            if (mYoutubeVideo.getTitle() != null) {
                titleTextView.setText(mYoutubeVideo.getTitle());
            }

            itemView.setOnClickListener(v -> {
                if (mYoutubeVideo.getThumbnail() != null) {
                    try {
                        BackgroundAudioPlayingIntentService.startActionPrepareVideo(mYoutubeVideo);
//        BackgroundAudioPlayingIntentService.startActionPrepareVideo(MainActivity.);
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                        intent.setData(Uri.parse(mYoutubeVideo.getThumbnail()));
//                        itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: Image url is not correct");
                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;
        @BindView(R.id.buttonRetry)
        TextView buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buttonRetry.setOnClickListener(v -> mCallback.onEmptyViewRetryClick());
        }

        @Override
        protected void clear() {

        }

    }
}
