/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by spei on 11/17/17.
 */

/**
 *Represents a eventlist
 *
 * @author team 10
 * @version  2.0
 * @since 1.0
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

    /**
     * sort the event list by date
     */
    public void sort(){
        Collections.sort(this.list, Collections.reverseOrder());
    }
}