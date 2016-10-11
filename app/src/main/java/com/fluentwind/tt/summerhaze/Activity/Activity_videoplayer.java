package com.fluentwind.tt.summerhaze.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fluentwind.tt.summerhaze.Config.config;
import com.fluentwind.tt.summerhaze.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2016/5/23.
 */
public class Activity_videoplayer extends AppCompatActivity {


    private VideoView videoView ;
    private io.vov.vitamio.widget.MediaController mediaController;
    private String videopath,cam,cam1,info;
    private float per;
    private FrameLayout frameLayout_topbar ,frameLayout_bufferpercentage,frameLayout_info;
    private TextView textView_video_info,textView_buffering,textView_percentage,textView_info;
    private ImageView imageView_back,imageView_fullscreen,imageView_getcurrentframe;
    private int flag_orientation;
    private Boolean buffer=false;
    private boolean needResume;
    private MediaPlayer mmediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.videoplayer);
        /*initWindow();*/
        flag_orientation=getApplicationContext().getResources().getConfiguration().orientation;
        if(flag_orientation==2){
            flag_orientation=0;
        }

        Intent i=getIntent();
        videopath=i.getStringExtra("path");

        try {
            cam=i.getStringExtra("cam");
            if(cam==null || cam.equals(config.STRING_NULL) || cam.equals("null")){
                cam="未知机位";
            }else{
                cam1=cam;
                cam="机位："+cam;
            }

        } catch (Exception e) {
            e.printStackTrace();
            cam="未知机位";

        }
        try {
            info=i.getStringExtra("info");
            if(info==null || info.equals(config.STRING_NULL) || info.equals("null")){
                info=config.STRING_NODETAILS;
            }else{

            }
        } catch (Exception e) {
            info=config.STRING_NODETAILS;
            e.printStackTrace();

        }


       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        });*/

        Vitamio.isInitialized(this);

        videoView = (VideoView) findViewById(R.id.rtsp_player);
        //mediaController= new MediaController(view.getContext());
        mediaController=new io.vov.vitamio.widget.MediaController(this,true,findViewById(R.id.rtsp1));
        frameLayout_topbar=(FrameLayout)findViewById(R.id.topbar);
        frameLayout_topbar.setAlpha(0.7f);
        textView_video_info=(TextView)findViewById(R.id.textView_video_info);
        textView_video_info.setText(cam+"  "+info);
        imageView_back=(ImageView)findViewById(R.id.imageview_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
                finish();

                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });


        imageView_getcurrentframe=(ImageView)findViewById(R.id.imageView_getcurrentframe);
        imageView_getcurrentframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcurrentframe(mmediaPlayer);
            }
        });

        imageView_fullscreen=(ImageView)findViewById(R.id.imageView_fullscreen);
        switch (flag_orientation){
            case 1:
                //竖屏

                imageView_fullscreen.setImageResource(R.mipmap.full_screen_32_white);

                break;
            case 0:
                //横屏

                imageView_fullscreen.setImageResource(R.mipmap.exit_full_screen_32_white);

                break;
        }
        imageView_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flag_orientation){
                    case 1:
                        //竖屏
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        imageView_fullscreen.setImageResource(R.mipmap.exit_full_screen_32_white);

                        break;
                    case 0:
                        //横屏
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        imageView_fullscreen.setImageResource(R.mipmap.full_screen_32_white);

                        break;
                }


            }
        });


        frameLayout_bufferpercentage=(FrameLayout)findViewById(R.id.bufferpercentage);

        textView_buffering=(TextView)findViewById(R.id.textView_buffering);
        textView_percentage=(TextView)findViewById(R.id.textView_percentage);
        frameLayout_info=(FrameLayout)findViewById(R.id.frame_info);
        textView_info=(TextView)findViewById(R.id.textView_info);
        textView_info.setText("视频信息："+ "\n"+cam+"\n"+info);
        playfunction(videopath);

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
    private  void playfunction(String path){


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

            videoView.setVideoURI(Uri.parse(path));
            //videoView.setBufferSize( 512 * 1024);
            videoView.setMediaController(mediaController);
            mediaController.setVisibility(videoView.GONE);
            mediaController.setFileName(cam);
            mediaController.setOnShownListener(new MediaController.OnShownListener() {
                @Override
                public void onShown() {
                    frameLayout_topbar.setVisibility(View.VISIBLE);
                }
            });
            mediaController.setOnHiddenListener(new MediaController.OnHiddenListener() {
                @Override
                public void onHidden() {
                    frameLayout_topbar.setVisibility(View.INVISIBLE);
                }
            });
            mediaController.setAlpha(0.7f);

            if(path.substring(0,4)=="rtsp"){

            }
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            //开始缓存，暂停播放
                            if (videoView.isPlaying()) {
                                videoView.pause();
                                needResume = true;
                            }
                            frameLayout_bufferpercentage.setVisibility(View.VISIBLE);
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            //缓存完成，继续播放
                            if (needResume)
                                videoView.start();
                            frameLayout_bufferpercentage.setVisibility(View.INVISIBLE);

                            break;
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                            //显示 下载速度
                            //System.out.println("download rate:" + extra);


                            //textView_percentage.setText(extra +"" );
                            break;
                    }
                    return true;
                }
            });
            videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {

                    per=(percent*videoView.getDuration()/100-videoView.getCurrentPosition())/1000 *100/10 ;
                    if (per<0){
                        per=0;
                    }

                    if (videoView.isPlaying()) {
                        frameLayout_bufferpercentage.setVisibility(View.INVISIBLE);
                        buffer=false;
                    } else {

                        buffer=true;
                        if (per < 100.0f) {
                            frameLayout_bufferpercentage.setVisibility(View.VISIBLE);
                            try {

                                textView_percentage.setText(per + "%");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (buffer){
                                videoView.start();
                                buffer=false;
                            }else{

                            }

                            frameLayout_bufferpercentage.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });

            videoView.requestFocus();

            videoView.setOnPreparedListener(new io.vov.vitamio.MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(io.vov.vitamio.MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    videoView.start();
                    mmediaPlayer=mediaPlayer;


                }
            });

        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation)
        {

            case (Configuration.ORIENTATION_LANDSCAPE):
                //如果转换为横向屏时，有要做的事，请写在这里

                //frameLayout_info.setVisibility(View.GONE);

                videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE,0);
                flag_orientation=0;


                imageView_fullscreen.setImageResource(R.mipmap.exit_full_screen_32_white);

                break;

            case (Configuration.ORIENTATION_PORTRAIT):
                //如果转换为竖向屏时，有要做的事，请写在这里

                //frameLayout_info.setVisibility(View.VISIBLE);
                videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE,0);

                flag_orientation=1;
                imageView_fullscreen.setImageResource(R.mipmap.full_screen_32_white);
                break;
        }
    }
    private void getcurrentframe(MediaPlayer mMediaPlayer){
        Bitmap dd = mMediaPlayer.getCurrentFrame();//截图方法
        if (mMediaPlayer.isPlaying()) {
            try {
                    String  name =cam1+"_b";
                File file=new File( config.PATH_CACHE_ROOT);
                if(!file.exists()) {
                    file.mkdir();

                }
                file=new File( config.PATH_CACHE_ROOT_CACHE);
                if(!file.exists()) {
                    file.mkdir();

                }
                String fileName =config.PATH_CACHE_ROOT_CACHE+ "/" + name;
                FileOutputStream b = new FileOutputStream(fileName);
                dd.compress(Bitmap.CompressFormat.JPEG, 100, b);
                b.flush();
                b.close();
                Toast.makeText(Activity_videoplayer.this, "截图成功",
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else
            Toast.makeText(Activity_videoplayer.this, "视频未播放，请稍候截图",
                    Toast.LENGTH_LONG).show();
    }
}
