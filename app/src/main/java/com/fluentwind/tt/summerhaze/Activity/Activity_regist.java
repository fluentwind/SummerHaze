package com.fluentwind.tt.summerhaze.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.fluentwind.tt.summerhaze.net.Regist;
import com.fluentwind.tt.summerhaze.tools.MD5Tool;

import io.vov.vitamio.utils.Log;

/**
 * Created by Administrator on 2016/6/4.
 */
public class Activity_regist extends AppCompatActivity {

    private TextView textView_alert;
    private EditText editText_username,editText_password,editText_password2;
    private Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("用户登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Activity_regist.this,Activity_login.class);

                setResult(RESULT_CANCELED, i);

                finish();
                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });


        textView_alert=(TextView)findViewById(R.id.textView_alert);
        textView_alert.setText(config.ALERT_NULL);
        editText_password=(EditText)findViewById(R.id.editText_password);
        editText_password2=(EditText)findViewById(R.id.editText_password2);
        editText_username=(EditText)findViewById(R.id.editText_username);
        textchange();

        button_submit=(Button)findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText_username.getText()) && !TextUtils.isEmpty(editText_password.getText())&& !TextUtils.isEmpty(editText_password2.getText())){

                    if(editText_password.getText().toString() .equals(editText_password2.getText().toString()) ){


                        final ProgressDialog pd = ProgressDialog.show(Activity_regist.this, config.STRING_LOGINING,config.STRING_CONNECTINGTOSERVER);
                        new Regist(editText_username.getText().toString(), editText_password.getText().toString(), new Regist.SuccessCallback() {

                            @Override
                            public void onSuccess() {

                                pd.dismiss();


                                Intent i =new Intent(Activity_regist.this,Activity_login.class);
                                i.putExtra(config.KEY_USERNAME,editText_username.getText().toString());

                                setResult(RESULT_OK, i);
                                finish();
                                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                                        R.anim.fragment_slide_out_to_right);

                            }

                            @Override
                            public void onWrong() {
                                pd.dismiss();
                                textView_alert.setText(config.STRING_REGIST_WRONG);


                            }
                            @Override
                            public void onExisted(){
                                pd.dismiss();


                                editText_password.setText(null);
                                editText_password2.setText(null);
                                editText_username.setText(null);
                                textView_alert.setText(config.STRING_REGIST_EXISTED);
                            }
                        }, new Regist.FailCallback() {

                            @Override
                            public void onFail() {
                                pd.dismiss();
                                textView_alert.setText(config.STRING_REGIST_WRONG);
                                Toast.makeText(Activity_regist.this, config.STRING_FAILTOLOGIN, Toast.LENGTH_LONG).show();
                            }
                        });

                    }else{
                        textView_alert.setText(config.ALERT_WRONGPASSWORD2);
                    }

                }else {
                    if (TextUtils.isEmpty(editText_username.getText())){
                        textView_alert.setText(config.ALERT_NULLUSERNAME);
                    }else{
                        if(TextUtils.isEmpty(editText_password2.getText())) {
                            textView_alert.setText(config.ALERT_NULLPASSWORD2);
                        }else if(TextUtils.isEmpty(editText_password.getText())) {
                            textView_alert.setText(config.ALERT_NULLPASSWORD);
                        }
                    }



                }


            }
        });



    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i =new Intent(Activity_regist.this,Activity_login.class);

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
        editText_password2.addTextChangedListener(new TextWatcher() {
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

}
