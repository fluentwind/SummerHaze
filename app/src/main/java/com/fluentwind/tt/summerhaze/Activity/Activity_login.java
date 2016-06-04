package com.fluentwind.tt.summerhaze.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fluentwind.tt.summerhaze.Config.config;
import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.net.Login;
import com.fluentwind.tt.summerhaze.tools.MD5Tool;

import io.vov.vitamio.utils.Log;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Activity_login extends AppCompatActivity {

    private TextView textView_alert,textView_help,textView_regist;
    private EditText editText_username,editText_password;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("用户登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Activity_login.this,MainActivity.class);

                setResult(RESULT_CANCELED, i);

                finish();
                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });


        textView_alert=(TextView)findViewById(R.id.textView_alert);
        textView_alert.setText(config.ALERT_NULL);
        editText_password=(EditText)findViewById(R.id.editText_password);
        editText_username=(EditText)findViewById(R.id.editText_username);
        textchange();
        textView_help=(TextView) findViewById(R.id.textView_help);
        button_login=(Button)findViewById(R.id.button_login);
        textView_regist=(TextView)findViewById(R.id.textView_regist);
        textView_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText_username.getText()) && !TextUtils.isEmpty(editText_password.getText())){

                    final ProgressDialog pd = ProgressDialog.show(Activity_login.this, config.STRING_LOGINING,config.STRING_CONNECTINGTOSERVER);
                    new Login(MD5Tool.md5(editText_username.getText().toString()), MD5Tool.md5(editText_password.getText().toString()), new Login.SuccessCallback() {

                        @Override
                        public void onSuccess(String token) {

                            pd.dismiss();


                            Intent i =new Intent(Activity_login.this,MainActivity.class);
                            i.putExtra(config.KEY_TOKEN, token);
                            i.putExtra(config.KEY_USERNAME, editText_username.getText().toString());
                            i.putExtra(config.KEY_PASSWORD, editText_password.getText().toString());

                            setResult(RESULT_OK, i);
                            finish();
                            overridePendingTransition(R.anim.fragment_slide_in_from_left,
                                    R.anim.fragment_slide_out_to_right);

                        }

                        @Override
                        public void onWrong() {
                            pd.dismiss();
                            textView_alert.setText(config.STRING_WRONGINFO);

                        }
                    }, new Login.FailCallback() {

                        @Override
                        public void onFail() {
                            pd.dismiss();

                            Toast.makeText(Activity_login.this, config.STRING_FAILTOLOGIN, Toast.LENGTH_LONG).show();
                        }
                    });



                }else {
                    if (TextUtils.isEmpty(editText_username.getText()) && TextUtils.isEmpty(editText_password.getText())){
                        textView_alert.setText(config.ALERT_NULLBOTH);
                    }else{
                        if(TextUtils.isEmpty(editText_password.getText())) {
                            textView_alert.setText(config.ALERT_NULLPASSWORD);
                        }else if(TextUtils.isEmpty(editText_username.getText())){
                            textView_alert.setText(config.ALERT_NULLUSERNAME);
                        }
                    }
                }


            }
        });



    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i =new Intent(Activity_login.this,MainActivity.class);

        setResult(RESULT_CANCELED, i);

        finish();


        overridePendingTransition(R.anim.fragment_slide_in_from_left,
                R.anim.fragment_slide_out_to_right);
    }
    public void textchange(){
        editText_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textView_alert.getText().toString()!=config.ALERT_NULL){
                    textView_alert.setText(config.ALERT_NULL);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textView_alert.getText().toString()!=config.ALERT_NULL){
                    textView_alert.setText(config.ALERT_NULL);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void regist(){
        Intent intent = new Intent(Activity_login.this,Activity_regist.class);
        startActivityForResult(intent, 5);

        overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 5:{

                if (resultCode == RESULT_OK) {

                    editText_username.setText(data.getStringExtra(config.KEY_USERNAME));

                } else if (resultCode == RESULT_CANCELED) {

                }
                break;
            }



        }
    }
}
