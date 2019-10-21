package com.android.bookrecode;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MyDatabase {
    Context context;
    MyOpenHelper myOpenHelper;
    SQLiteDatabase mydatabase;
    public MyDatabase(Context context){
        this.context = context;
        myOpenHelper = new MyOpenHelper(context);
    }
    public ArrayList<Data> getArray(){            //获取listview中要显示的数据
        ArrayList<Data> arr = new ArrayList<Data>();
        ArrayList<Data> arr1 = new ArrayList<Data>();
        mydatabase = myOpenHelper.getReadableDatabase();
        Cursor cursor = mydatabase.rawQuery("select ids,bookname,state,thought,times from mybook",null);
        cursor.moveToFirst();//cursor是每行的集合，moveToFirst是定位第一行
        while(!cursor.isAfterLast()){   //判断当前指针是否已经到文件尾,没有到文件尾则一直循环
            int id = cursor.getInt(cursor.getColumnIndex("ids"));
            String bookname = cursor.getString(cursor.getColumnIndex("bookname"));
            String state = cursor.getString(cursor.getColumnIndex("state"));
            String thought = cursor.getString(cursor.getColumnIndex("thought"));
            String times = cursor.getString(cursor.getColumnIndex("times"));
            Data data = new Data(id,bookname,state,thought,times);
            arr.add(data);
            cursor.moveToNext();
        }
        mydatabase.close();
        for (int i = arr.size(); i >0; i--) {
            arr1.add(arr.get(i-1));
        }
        return arr1;
    }

    public void toInsert(Data data){           //在表中插入新建的书籍的数据
        mydatabase = myOpenHelper.getReadableDatabase();
        mydatabase.execSQL("insert into mybook(bookname,state,thought,times)values('"
                + data.getBookname()+"','"
                +data.getState()+"','"
                +data.getThought()+"','"
                +data.getTimes()
                +"')");
        mydatabase.close();
    }

    public Data getSetData(int id){           //获取要修改数据（就是当选择listview子项想要修改数据时，获取数据显示在新建页面）
        mydatabase = myOpenHelper.getReadableDatabase();
        /*1.它会调用并返回一个可以读写数据库的对象
         *2.在第一次调用时会调用onCreate的方法
         *3.当数据库存在时会调用onOpen方法
         *4. 结束时调用onClose方法
         */
        Cursor cursor=mydatabase.rawQuery("select bookname,state,thought from mybook where ids='"+id+"'" , null);
        cursor.moveToFirst();
        String bookname = cursor.getString(cursor.getColumnIndex("bookname"));
        String state = cursor.getString(cursor.getColumnIndex("state"));
        String thought = cursor.getString(cursor.getColumnIndex("thought"));
        Data data=new Data(bookname,state,thought);
        mydatabase.close();
        return data;
    }

    public void toUpdate(Data data){           //修改表中数据
        mydatabase = myOpenHelper.getReadableDatabase();
        mydatabase.execSQL(
                "update mybook set bookname='"+ data.getBookname()+
                        "',state='"+data.getState()+
                        "',thought='"+data.getThought()+
                        "',times='"+data.getTimes()+
                        "' where ids='"+ data.getIds()+"'");
        mydatabase.close();
    }

    public void toDelete(int ids){            //在表中删除数据
        mydatabase  = myOpenHelper.getReadableDatabase();
        mydatabase.execSQL("delete from mybook where ids="+ids+"");
        mydatabase.close();
    }
}
