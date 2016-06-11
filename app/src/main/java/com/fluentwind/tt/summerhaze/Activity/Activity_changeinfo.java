package com.fluentwind.tt.summerhaze.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fluentwind.tt.summerhaze.Config.config;
import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.net.Changeinfo;
import com.fluentwind.tt.summerhaze.tools.MD5Tool;
import com.fluentwind.tt.summerhaze.tools.StringtoHex;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class Activity_changeinfo extends AppCompatActivity {

    private String[]text1={"用户名","昵称","性别","生日","城市","签名"};
    private String[]text2={"用户名","昵称","性别","生日","城市","签名"};
    private EditText editText_nickname,editText_date,editText_text;
    private TextView textView_nickname,textView_date,textView_text,textView_city;
    private RadioButton radioButton_male,radioButton_female;
    private RadioGroup radioGroup;
    private DatePicker datePicker;
    private Calendar calendar;
    private Spinner spinner_city;
    private int year,month,day,hour,minute,second;
    private List<String> citylist;
    private ArrayAdapter arrayAdapter;
    private String[]cityname={"安徽","澳门","北京","重庆","福建","甘肃","广东","广西","贵州","海南","河北","河南","黑龙江","湖北","湖南","吉林","江苏","江西","辽宁","内蒙古","宁夏","青海","山东","山西","陕西","上海","四川","台湾","天津","西藏","香港","新疆","云南","浙江"};
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeinfo_text);
        Intent intent=getIntent();


        for (int i=0;i<text1.length;i++)
        {
            text2[i]=intent.getStringExtra(text1[i].toString());
            if (text2[i].equals(config.STRING_NOINFO)){
                text2[i]=config.STRING_NULL;
            }

        }


        //init
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        radioButton_female=(RadioButton)findViewById(R.id.radiobutton_female);
        radioButton_male=(RadioButton)findViewById(R.id.radiobutton_male);
        if (text2[2].equals(config.STRING_SEX_MALE)){
            radioButton_male.setChecked(true);

        }else if(text2[2].equals(config.STRING_SEX_FEMALE)){
            radioButton_female.setChecked(true);
        }

        editText_date=(EditText)findViewById(R.id.editText_date);
        editText_date.setText(text2[3]);
        editText_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datedlg(v);

            }
        });

        editText_nickname=(EditText)findViewById(R.id.editText_nickname);
        editText_nickname.setText(text2[1]);
        editText_text=(EditText)findViewById(R.id.editText_text);
        editText_text.setText(text2[5]);
        textView_city=(TextView)findViewById(R.id.textView_text);
        textView_date=(TextView)findViewById(R.id.textView_date);
        textView_nickname=(TextView)findViewById(R.id.textView_nickname);

        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);



        citylist=new ArrayList<String>();
        for(int i=0;i<cityname.length;i++)
        {
            citylist.add(cityname[i]);
        }
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,citylist);
        spinner_city=(Spinner)findViewById(R.id.spinner_city);
        spinner_city.setAdapter(arrayAdapter);
        int position = arrayAdapter.getPosition(text2[4]);

        spinner_city.setSelection(position , true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("修改信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Activity_changeinfo.this,Activity_userinfo.class);

                setResult(RESULT_CANCELED, i);

                finish();
                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });


    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i =new Intent(Activity_changeinfo.this,Activity_userinfo.class);

        setResult(RESULT_CANCELED, i);

        finish();


        overridePendingTransition(R.anim.fragment_slide_in_from_left,
                R.anim.fragment_slide_out_to_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.tool_submit) {

            changeinfo();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void datedlg(View view){


        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int mon=monthOfYear+1;
                editText_date.setText(year+"-"+mon +"-"+dayOfMonth);
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },year,month,day).show();
    }
    public void changeinfo(){
        String username,nickname,sex,date,city,text;
        username=text2[0].toString();
        nickname=editText_nickname.getText().toString();

        if (radioButton_male.isChecked()){
            sex=config.STRING_SEX_MALE;
        }else if(radioButton_female.isChecked()){
            sex=config.STRING_SEX_FEMALE;
        }else{
            sex=config.STRING_NULL;
        }
        date=editText_date.getText().toString();
        city=spinner_city.getSelectedItem().toString();
        text=editText_text.getText().toString();

        final ProgressDialog pd = ProgressDialog.show(Activity_changeinfo.this, config.STRING_CONNECTING,config.STRING_CONNECTINGTOSERVER);
        new Changeinfo(MD5Tool.md5(username), StringtoHex.str2HexStr(nickname), StringtoHex.str2HexStr(sex)
                , StringtoHex.str2HexStr(date), StringtoHex.str2HexStr(city), StringtoHex.str2HexStr(text), new Changeinfo.SuccessCallback() {
            @Override
            public void onSuccess() {
                pd.dismiss();
                Intent intent =new Intent(Activity_changeinfo.this,Activity_userinfo.class);

                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);

            }

            @Override
            public void onWrong() {
                pd.dismiss();
                Toast.makeText(Activity_changeinfo.this, config.STRING_FAILTOCHANGEINFO, Toast.LENGTH_LONG).show();
            }
        }, new Changeinfo.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(Activity_changeinfo.this, config.STRING_FAILTOCHANGEINFO, Toast.LENGTH_LONG).show();
            }
        });

    }
}
