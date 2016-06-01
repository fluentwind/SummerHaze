package com.fluentwind.tt.summerhaze;



import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.vov.vitamio.utils.Log;


public class MainActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private Fragment_videolist fragment_videolist;
    private Fragment_chatlist fragment_chatlist;
    private Fragment nowfragment;

    private TextView text_username,text_userinfo;
    private CircleImageView image_user;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*initWindow();*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.setScrimColor(Color.TRANSPARENT);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_camera) {
                    // Handle the camera action
                } else if (id == R.id.nav_gallery) {

                } else if (id == R.id.nav_slideshow) {

                } else if (id == R.id.nav_manage) {

                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_send) {

                }



                drawerLayout.closeDrawer(GravityCompat.START);

                return false;

            }

        });

        navigationView.setItemIconTintList(null);


        //header ONclick
        text_userinfo=(TextView)navigationView.getHeaderView(0).findViewById(R.id.text_userinfo);
        text_username=(TextView)navigationView.getHeaderView(0).findViewById(R.id.text_username);
        image_user=(CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.image_user);

        text_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);

                Intent intent = new Intent(text_username.getContext(),Activity_login.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);

            }
        });
        image_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(image_user.getContext(),Activity_userinfo.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);
            }
        });
        text_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(image_user.getContext(),Activity_login.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);
            }
        });

        initializefragment();

        //radio check
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId){
                    case R.id.radio_button_1:{
                        switchfragment(fragment_chatlist);
                        break;
                    }
                    case R.id.radio_button_2:{

                        break;
                    }
                    case R.id.radio_button_3:{
                        switchfragment(fragment_videolist);
                        break;
                    }
                    case R.id.radio_button_4:{
                        break;
                    }
                }
            }
        });
  }

    /*private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }*/
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void switchfragment(Fragment fragment){
        if(nowfragment!=fragment){

            getFragmentManager().beginTransaction().hide(nowfragment).show(fragment).commit();
            nowfragment=fragment;
        }

    }



    private void initializefragment() {

        fragment_videolist=new Fragment_videolist();
        fragment_chatlist=new Fragment_chatlist();

        getFragmentManager().beginTransaction()
                .add(R.id.frame, fragment_videolist).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.frame, fragment_chatlist).hide(fragment_videolist).commit();
        nowfragment=fragment_chatlist;
    }

    @Override
    protected void onStop() {


        super.onStop();

    }

}
