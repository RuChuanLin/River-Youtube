package com.example.river_youtube;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.river_youtube.adapters.YoutubeVideoAdapter;
import com.example.river_youtube.model.YoutubeVideo;
import com.example.river_youtube.utils.CommonUtils;
import com.example.river_youtube.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements YoutubeVideoAdapter.Callback {
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

//    @BindView(R.id.fab)
//    FloatingActionButton playButton;

    YoutubeVideoAdapter mYoutubeVideoAdapter;

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BackgroundAudioPlayingIntentService.init(MainActivity.this);
        setUp();
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        String[] myDataset = {"a", "b", "c"};

//        recyclerView.setAdapter(mAdapter);

//        BackgroundAudioPlayingIntentService.startActionPrepareVideo(this);
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VideoActions.playNPause();
//            }
//        });
//        Button pauseButton = findViewById(R.id.button2);
//        pauseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VideoActions.pause();
//            }
//        });
    }

    private void setUp() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mYoutubeVideoAdapter = new YoutubeVideoAdapter(new ArrayList<>());

        prepareDemoContent();
    }

    private void prepareDemoContent() {
        List<YoutubeVideo> tmpList = new ArrayList<>();
        tmpList.add(new YoutubeVideo("v6vwADf_qzQ","https://img.youtube.com/vi/v6vwADf_qzQ/mqdefault.jpg","Kalafina - Ring Your Bell"));
        tmpList.add(new YoutubeVideo("vqWV6mBwHgU","https://img.youtube.com/vi/vqWV6mBwHgU/mqdefault.jpg","Kalafina - 「One Light」まとめ (One Light compilation)"));
        tmpList.add(new YoutubeVideo("fh6GLNASOAY","https://img.youtube.com/vi/fh6GLNASOAY/mqdefault.jpg","[INA SUB] 9+ONE Kalafina - Oblivious Live 2018"));
        CommonUtils.showLoading(MainActivity.this);
        new Handler().postDelayed(() -> {
            CommonUtils.hideLoading();
//            ArrayList<YoutubeVideo> mYoutubeVideos = new ArrayList<>();
//            String[] sportsList = getResources().getStringArray(R.array.sports_titles);
//            String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
//            String[] sportsImage = getResources().getStringArray(R.array.sports_images);
//
//            for (int i = 0; i < sportsList.length; i++) {
//                mYoutubeVideos.add();
//            }

            mYoutubeVideoAdapter.addItems(tmpList);
            mRecyclerView.setAdapter(mYoutubeVideoAdapter);
        }, 100);
    }

    @Override
    public void onEmptyViewRetryClick() {
        prepareDemoContent();
    }
}
