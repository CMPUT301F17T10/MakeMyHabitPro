package com.example.mmhp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yidingfan on 2017-10-13.
 */

public class event {
    private String id;
    private String habit;
    private Date happend;
    private String url_img;
    private String owner_comment;
    private String location;
    private ArrayList<String> pub_comment;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public event(String location,String habit, String url_img,String comment){
        this.location=location;
        this.habit=habit;
        this.url_img=url_img;
        this.owner_comment=comment;
        this.happend=new Date();
    }
    public void pub_comment(String comment){
        if(this.pub_comment.isEmpty()){
            this.pub_comment=new ArrayList<String>();
        }
        this.pub_comment.add(comment);
    }

    public String getId(){
        return this.id;
    }
    public String getOwner_comment(){
        return this.owner_comment;
    }
    public String getHabit(){
        return this.habit;
    }
    public String getHappend(){
        return dateFormat.format(this.happend);
    }
    public ArrayList<String> getPub_comment(){
        return this.pub_comment;
    }
    public String getUrl_img(){
        return this.url_img;
    }
    public String getLocation(){
        return  this.location;
    }


}
