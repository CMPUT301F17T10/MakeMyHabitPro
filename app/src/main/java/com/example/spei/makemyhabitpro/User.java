/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by spei on 11/17/17.
 */

public class User {
    private String uid;
    private String password;
    private String name;
    private ArrayList<String> friends =new ArrayList<String>();
    private Date resisted;
    private Date last_log_in = new Date();
    private int exp;
    private boolean exped=false;
    /**
     * register function
     * @param name the User name User want to call themselves
     * @param id  the unique id number for each User
     * @param pass the password User want to use
     */
    public User(String name, String id, String pass){
        this.uid=id;
        this.password=pass;
        this.resisted=new Date();
        this.name=name;
        this.exp=0;
    }

    /**
     * Try to login with password
     * @param pass
     * @return true for success, false for failure
     */
    public int log_in(String pass){
        if (this.password.equals(pass)){

            Date today=new Date();
            long diff = today.getTime() - this.last_log_in.getTime();
            long diffdate = diff / (60 * 60 * 1000);
            if (diffdate>=24|!exped){
                this.exp=this.exp+5;
                this.last_log_in=today;
                this.exped=true;
                return 1;}
            else{

                return 2;
            }
        }else{
            return 3;
        }
    }

    /**
     * add friend with others
     * @param uid
     */
    public void add_friend(String uid){
        if (this.friends==null){
            this.friends=new ArrayList<String>();
            this.friends.add(uid);
        }
        if (!this.friends.contains(uid)){
            this.friends.add(uid);
        }

    }

    /**
     * check the uid is friend with User
     * @param uid
     * @return
     */

    public boolean isFriend(String uid){
        return  this.friends.contains(uid);
    }

    /**
     * get the uid list for User's friends
     * @return
     */
    public ArrayList getFriends(){
        return this.friends;
    }


    public void setName(String name){
        this.name=name;
    }

    public void setPassword(String pass){
        this.password=pass;
    }

    public String getName(){
        return this.name;
    }

    public String getUid(){
        return this.uid;
    }
    public  String getReDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(this.resisted);
    }
    public String getLast(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return  dateFormat.format(this.last_log_in);
    }
    public String to_string(){
        String S="Name: "+this.name;
        return S;
    }
    public String get_lvl(){
        int lvl=this.exp/100;
        String s="lvl "+Integer.toString(lvl);

        return s;
    }
}