package com.android.bookrecode;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private final String TAG = "main";
    LayoutInflater inflater;//找res/layout/下的xml布局文件，并且实例化
    ArrayList<Data> array;//ArrayList只能存储对象，不能存储原生数据类型数据

    public MyAdapter(LayoutInflater inf,ArrayList<Data> arry){
        this.inflater=inf;
        this.array=arry;
    }

    @Override
    public int getCount() {
        return array.size();
    } //填充的数据集数
    @Override
    public Object getItem(int position) {
        return array.get(position);
    }//数据集中指定索引对应的数据项

    @Override
    public long getItemId(int position) {
        return position;
    }//指定行所对应的ID



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {  //每个Item所显示的内容,代码块中包含了对listview的效率优化
        ViewHolder vh; //减少 findViewById() 的使用以及避免过多地 inflate view
        if(convertView==null){   //缓存视图View，用以增加ListView的item view加载效率
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.list_item,null);//加载listview子项

            vh.tv1=(TextView) convertView.findViewById(R.id.Lbookname);
            vh.tv2=(TextView) convertView.findViewById(R.id.Lstate);
            vh.tv3=(TextView) convertView.findViewById(R.id.Lthought);
            vh.tv4=(TextView) convertView.findViewById(R.id.Ltime);
            convertView.setTag(vh);
        }
        vh=(ViewHolder) convertView.getTag();

        vh.tv1.setText("《" +array.get(position).getBookname()+"》");//数据显示
        vh.tv2.setText("阅读状态:"+array.get(position).getState());

        final ViewHolder finalVh = vh;
        vh.tv3.setText(array.get(position).getThought());
        vh.tv3.post(new Runnable() {
            @Override
            public void run() {
                int lineCnt = finalVh.tv3.getLineCount();//获取行数
                if(finalVh.tv3.getLineCount() > 3) {//判断行数大于多少时改变
                    Log.i(TAG, "lineCnt=" + lineCnt);
                    int lineEndIndex = finalVh.tv3.getLayout().getLineEnd(2); //设置第3行打省略号
                    Log.i(TAG, "lineEndIndex=" + lineEndIndex);
                    String text = finalVh.tv3.getText().subSequence(0, lineEndIndex - 2) + "...";
                    finalVh.tv3.setText(text);
                }
            }
        });
        vh.tv3.setBackgroundResource(R.drawable.corner_textview);//textview圆角实现
        vh.tv4.setText(array.get(position).getTimes());

        return convertView;
    }
    class ViewHolder{     //内部类，对控件进行缓存
        TextView tv1,tv2,tv3,tv4;
    }
}
