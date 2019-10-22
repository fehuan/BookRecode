package com.android.bookrecode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class New extends AppCompatActivity {
    EditText ed_bookname;
    EditText ed_state;
    EditText ed_thought;
    FloatingActionButton fFinishi;
    FloatingActionButton fImage;
    MyDatabase myDatabase;
    Data data;
    int ids;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ed_bookname = (EditText) findViewById(R.id.Nbookname);
        ed_state = (EditText) findViewById(R.id.Nstate);
        ed_thought = (EditText) findViewById(R.id.Nthought);
        fFinishi = (FloatingActionButton) findViewById(R.id.Nfinish);
        fImage = (FloatingActionButton) findViewById(R.id.Nimage);
        myDatabase = new MyDatabase(this);
        Intent intent = this.getIntent();
        ids = intent.getIntExtra("ids", 0);
        if (ids != 0) {
            data = myDatabase.getSetData(ids);
            ed_bookname.setText(data.getBookname());
            ed_state.setText(data.getState());
            ed_thought.setText(data.getThought());
        }
        fFinishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSave();
            }
        });

        ed_thought.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {  //添加图片按钮在选中ed_thought时才出现
            @Override
            @SuppressLint("RestrictedApi")
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    fImage.setVisibility(fImage.VISIBLE);
                } else {
                    // 此处为失去焦点时的处理内容
                    fImage.setVisibility(fImage.GONE);
                }
            }
        });
    }



    private void isSave(){   //用于处理新建\修改书签
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 E hh:mm");//格式化日期
        Date date = new Date(System.currentTimeMillis());//获取系统当前时间
        String time = simpleDateFormat.format(date);
        String bookename = ed_bookname.getText().toString();
        String state = ed_state.getText().toString();
        String thought = ed_thought.getText().toString();

        if (state.length()>0 && bookename.length()>0){//判断用户输入情况
            if(ids!=0){  //修改表中数据并返回主页面
            data=new Data(ids,bookename,state,thought,time);
            myDatabase.toUpdate(data);
            Intent intent=new Intent(New.this,MainActivity.class);
            startActivity(intent);
            New.this.finish();
            }
            else{  //新建书签，插入数据表并返回主页面
            data=new Data(bookename,state,thought,time);
            myDatabase.toInsert(data);
            Intent intent=new Intent(New.this,MainActivity.class);
            startActivity(intent);
            New.this.finish();
            }
        }
        else{//用户没有输入书名和阅读状态
            Toast.makeText(this, "请至少输入书名和阅读状态", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onBackPressed() {  //重写返回键方法，按下返回键返回main界面，同时结束页面
        Intent intent=new Intent(New.this,MainActivity.class);
        startActivity(intent);
        New.this.finish();//结束当前页面
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.MUshare :
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        "书名：《"+ed_bookname.getText().toString()+"》\n" +
                                "感想："+ed_thought.getText().toString());
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }
}
