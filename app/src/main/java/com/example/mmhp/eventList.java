package com.example.mmhp;

import java.util.ArrayList;

/**
 * Created by yidingfan on 2017-10-13.
 */

public class eventList {
    private ArrayList<event> list;

    public eventList(){
        this.list=new ArrayList<event>();
    }

    public void add(event e){
        this.list.add(e);
    }

    public void del(event e){
        this.list.remove(e);
    }

    public boolean contains(event e){
        return this.list.contains(e);
    }

    public boolean empty(){
        return this.list.isEmpty();
    }

    public event getById(String id){
        for (event e :this.list){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }
}
