package com.fluentwind.tt.summerhaze.Activity;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fluentwind.tt.summerhaze.Activity.Activity_login;
import com.fluentwind.tt.summerhaze.Activity.Activity_userinfo;
import com.fluentwind.tt.summerhaze.Config.config;
import com.fluentwind.tt.summerhaze.Fragment.Fragment_chatlist;
import com.fluentwind.tt.summerhaze.Fragment.Fragment_videolist;
import com.fluentwind.tt.summerhaze.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private Fragment_videolist fragment_videolist;
    private Fragment_chatlist fragment_chatlist;
    private Fragment nowfragment;
    private String Token,Username,Password;
    private TextView text_username,text_userinfo;
    private CircleImageView image_user;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

                Userinfo();
            }
        });
        image_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Userinfo();
            }
        });
        text_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Userinfo();
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




        checkstatus();

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
    private void Login(){
        Intent intent = new Intent(MainActivity.this,Activity_login.class);
        startActivityForResult(intent, 10);

        overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);

    }
    private void Userinfo() {
        Intent intent = new Intent(MainActivity.this,Activity_userinfo.class);
        intent.putExtra("username",config.getCachedusername(MainActivity.this));
        startActivityForResult(intent, 11);

        overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:{

                if (resultCode == RESULT_OK) {
                    Token = data.getStringExtra(config.KEY_TOKEN);
                    Username = data.getStringExtra(config.KEY_USERNAME);
                    Password = data.getStringExtra(config.KEY_PASSWORD);
                    System.out.println(Token);
                    System.out.println(Username);
                    System.out.println(Password);
                    config.cacheToken(MainActivity.this, Token);
                    config.cacheuserinfo(MainActivity.this, Username, Password);


                } else if (resultCode == RESULT_CANCELED) {
                    finish();

                }
                break;
            }

            case 11:{

                if (resultCode == RESULT_OK) {
                    final ProgressDialog pd = ProgressDialog.show(MainActivity.this, config.STRING_PLEASEWAIT,config.STRING_LOGOUTING);
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {

                            config.cacheToken(MainActivity.this,config.KEY_NULL);
                            config.cacheuserinfo(MainActivity.this,config.KEY_NULL,config.KEY_NULL);


                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            pd.dismiss();

                            reload();
                        }
                    }.execute();



                }
                else if (resultCode == RESULT_CANCELED) {
                    //refresh user info

                }
                break;
            }

        }
    }
    public void reload() {

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    public void checkstatus(){
        Token=config.getCachedToken(MainActivity.this);
        //System.out.println("Token:"+Token);
        //Username=config.getCachedusername(MainActivity.this);
        //Password=config.getCacheduserpassword(MainActivity.this);
        if (Token==null || Token==config.KEY_NULL ){
            Login();
        }
    }
}
