package com.fluentwind.tt.summerhaze;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2016/5/23.
 */
public class Activity_videoplayer extends AppCompatActivity {


    private VideoView videoView ;
    private io.vov.vitamio.widget.MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer);
        /*initWindow();*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("视频监控");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
                finish();

                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });

        Vitamio.isInitialized(this);

        videoView = (VideoView) findViewById(R.id.rtsp_player);
        //mediaController= new MediaController(view.getContext());
        mediaController=new io.vov.vitamio.widget.MediaController(this,true,findViewById(R.id.rtsp1));

        playfunction();



    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("sysysysysys","stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("sysysysysys","pause");
        //Process.killProcess(Process.myPid());
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        videoView.stopPlayback();
        finish();

        overridePendingTransition(R.anim.fragment_slide_in_from_left,
                R.anim.fragment_slide_out_to_right);
    }
    /*private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }*/
    private  void playfunction(){
        String path = "";

        path="";
        path="http://www.modrails.com/videos/passenger_nginx.mov";
        //http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4
        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(this, "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
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
