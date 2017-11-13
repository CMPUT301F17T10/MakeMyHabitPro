package com.example.mmhp;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yidingfan on 2017-10-13.
 */

public class EventList {
    private ArrayList<Event> list;

    public EventList(){
        this.list=new ArrayList<Event>();
    }

    public void add(Event e){
        this.list.add(e);
    }

    public void del(Event e){
        this.list.remove(e);
    }

    public boolean contains(Event e){
        return this.list.contains(e);
    }

    public boolean empty(){
        return this.list.isEmpty();
    }

    public ArrayList<Event> getEvents (){
        return this.list;
    }

    public Event getById(String id){
        for (Event e :this.list){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }

    public void sort(){
        Collections.sort(this.list, Collections.reverseOrder());
    }
}
