package com.android.woonga.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.android.woonga.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubePlayerActivity extends AppCompatActivity {
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youtube_player_view;

    String youtubeVideoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            youtubeVideoId = bundle.getString("videoId");
        }

        getLifecycle().addObserver(youtube_player_view);

        youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(youtubeVideoId, 0);
                /*youTubePlayer.pause();*/
            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {

            }
        });
    }


}
