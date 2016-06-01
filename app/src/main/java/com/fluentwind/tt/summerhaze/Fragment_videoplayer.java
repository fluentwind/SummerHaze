package com.fluentwind.tt.summerhaze;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2016/5/23.
 */
public class Fragment_videoplayer extends Fragment {
    private VideoView videoView ;
    private io.vov.vitamio.widget.MediaController mediaController;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videoplayer, container, false);

        Vitamio.isInitialized(view.getContext());

        videoView = (VideoView) view.findViewById(R.id.rtsp_player);
        //mediaController= new MediaController(view.getContext());
        mediaController=new io.vov.vitamio.widget.MediaController(view.getContext(),true,view.findViewById(R.id.rtsp1));

        playfunction();


        return view;


    }
    private  void playfunction(){
        String path = "";

        path="";
        path="http://www.modrails.com/videos/passenger_nginx.mov";
        //http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4
        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(getActivity(), "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
            return;
        } else {
			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
            //videoView.setVideoPath(path);
            //rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov

            videoView.setVideoURI(Uri.parse("rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov"));
            videoView.setMediaController(mediaController);
            mediaController.setVisibility(videoView.GONE);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new io.vov.vitamio.MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(io.vov.vitamio.MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);

                }
            });

        }
    }
}
