package com.example.spei.makemyhabitpro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by spei on 11/17/17.
 */

public class Event implements Comparable<Event>{
    private String id;
    private String UID;
    private Habit habit;
    private Date happend;
    private Bitmap img;
    private String owner_comment;
    private String location;
    private ArrayList<String> pub_comment;


    public Event(Habit habit, String comment, String id, String UID) {
        this.habit = habit;
        this.id = id;
        this.UID = UID;
        this.owner_comment = comment;
        happend = new Date();
        location = "None";

        pub_comment = new ArrayList<String>();
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
    public String getUID() {return this.UID;}
    public String getOwner_comment(){
        return this.owner_comment;
    }
    public Habit getHabit(){
        return this.habit;
    }
    public String getHappend(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(this.happend);
    }
    public Date getHabitDate(){
        return this.happend;
    }
    public ArrayList<String> getPub_comment(){
        return this.pub_comment;
    }
    public void setPub_comment(ArrayList<String> pub_comment){this.pub_comment = pub_comment;}
    public Bitmap getImg(){
        return this.img;
    }
    public String getLocation(){
        return  this.location;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setOwner_comment(String owner_comment) {
        this.owner_comment = owner_comment;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return habit.getTitle()+"  "+owner_comment+" \n"+dateFormat.format(happend);
    }

    @Override
    public int compareTo(Event e) {
        Date d1=this.happend;
        Date d2=e.getHabitDate();
        return d1.compareTo(d2);
    }



}