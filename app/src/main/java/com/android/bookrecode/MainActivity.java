package com.android.bookrecode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    long exitTime=0;// 退出时间
    ListView listView;
    FloatingActionButton floatingActionButton;
    LayoutInflater layoutInflater;
    ArrayList<Data> arrayList;
    MyDatabase myDatabase;
    MyAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.MlistView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.Madd);
        layoutInflater = getLayoutInflater();

        myDatabase = new MyDatabase(this);
        arrayList = myDatabase.getArray();
        adapter= new MyAdapter(layoutInflater, arrayList);
        listView.setAdapter(adapter);
        Log.d("main", "当前适配器数据" + adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //点击跳转到编辑页面（编辑页面与新建页面共用一个布局）
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), New.class);
                intent.putExtra("ids", arrayList.get(position).getIds());
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {  //长按删除
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,View view, final int position, final long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("确定要删除此记录？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("main", "点击了“取消”按钮");
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDatabase.toDelete(arrayList.get(position).getIds());
                                arrayList = myDatabase.getArray();
                                adapter.refresh(arrayList);
                            }
                        })
                        .show();
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {   //点击悬浮按钮时，跳转到新建页面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), New.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) { //捕捉按下返回键操作
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                exit();
                return false;
            }
            return super.onKeyDown(keyCode, event);//若按键为其他则继续调用该方法
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) { 	// 判断间隔时间 小于2秒就退出应用
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);//正常退出应用，但要确保任务栈中所有activity已经finish，否则会重启
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.MUnew:
                Intent intent = new Intent(getApplicationContext(),New.class);
                startActivity(intent);
                MainActivity.this.finish();
                break;
            case R.id.MUexit:
                MainActivity.this.finish();
                System.exit(0);
                break;
            default:
                break;
        }
        return  true;
    }
}

