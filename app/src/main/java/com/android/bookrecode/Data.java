package com.android.bookrecode;

public class Data {
    private String bookname;   //书名
    private String state; //状态
    private String thought; //感想
    private String times;   //时间
    private int ids;        //编号

    public Data(int id, String bookname, String state , String thought, String time){
        this.ids=id;
        this.bookname=bookname;
        this.state=state;
        this.thought=thought;
        this.times=time;
    }
    public Data(String bookname, String state , String thought){

        this.bookname=bookname;
        this.state=state;
        this.thought=thought;
    }
    public Data(String bookname, String state , String thought, String time){
        this.bookname=bookname;
        this.state=state;
        this.thought=thought;
        this.times=time;
    }

    public int getIds() {
        return ids;
    }

    public String getBookname() {
        return bookname;
    }

    public String getState() {
        return state;
    }

    public  String getThought(){ return thought; }

    public String getTimes() {
        return times;
    }
}
