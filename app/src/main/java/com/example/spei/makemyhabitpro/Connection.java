/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class check the internet connection
 *
 * @author spei
 *
 * @since 1.0
 * @see java.io.BufferedReader
 * @see MainActivity
 */
import java.util.ArrayList;

/**
 * Created by spei on 11/30/17.
 */

/**
 * Check the internet connection of app
 *
 * @author team 10
 * @version  2.0
 * @since 1.0
 */
public class Connection {
    private Context context;
    private ArrayList<Habit> addH = new ArrayList<Habit>();
    private ArrayList<Habit> deleteH = new ArrayList<Habit>();
    private ArrayList<Habit> editH = new ArrayList<Habit>();
    private ArrayList<Event> addE = new ArrayList<Event>();
    private ArrayList<Event> deleteE = new ArrayList<Event>();
    private ArrayList<Event> editE = new ArrayList<Event>();

    public Connection(Context context) {

        this.context = context;
    }

    public boolean isConnected () {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if (connectivity != null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }


//    public void addHabit(Habit habit){addH.add(habit);}
//    public void deleteHabit(Habit habit){deleteH.add(habit);}
//    public void editHabit(Habit habit){editH.add(habit);}
//    public void addEvent(Event event){addE.add(event);}
//    public void deleteEvent(Event event){deleteE.add(event);}
//    public void editEvent(Event event){editE.add(event);}
//
//    public void updateAll(){
//
//        if(addH != null){
//            for(Habit i : addH) {
//                ElasticsearchHabit.AddHabitTask addHabitTask = new ElasticsearchHabit.AddHabitTask();
//                addHabitTask.execute(i);
//            }
//            addH.clear();
//        }
//
//        if (deleteH != null) {
//            for (Habit j : deleteH) {
//                ElasticsearchHabit.DeleteHabitTask deleteHabitTask = new ElasticsearchHabit.DeleteHabitTask();
//                deleteHabitTask.execute(j);
//
//            }
//            deleteH.clear();
//        }
//
//        if (editH != null) {
//            for (Habit k : editH) {
//                ElasticsearchHabit.DeleteHabitTask deleteHabitTask = new ElasticsearchHabit.DeleteHabitTask();
//                deleteHabitTask.execute(k);
//                ElasticsearchHabit.AddHabitTask addHabitTask = new ElasticsearchHabit.AddHabitTask();
//                addHabitTask.execute(k);
//            }
//            editH.clear();
//        }
//
//
//        if(addE != null){
//            for(Event x : addE) {
//                ElasticsearchEvent.AddEventTask addEventTask = new ElasticsearchEvent.AddEventTask();
//                addEventTask.execute(x);
//            }
//            addE.clear();
//        }
//
//        if (deleteE != null) {
//            for (Event y : deleteE) {
//                ElasticsearchEvent.DeleteEventTask deleteEventTask = new ElasticsearchEvent.DeleteEventTask();
//                deleteEventTask.execute(y);
//
//            }
//            deleteE.clear();
//        }
//
//        if (editE != null) {
//            for (Event z : editE) {
//                ElasticsearchEvent.DeleteEventTask deleteEventTask = new ElasticsearchEvent.DeleteEventTask();
//                deleteEventTask.execute(z);
//                ElasticsearchEvent.AddEventTask addEventTask = new ElasticsearchEvent.AddEventTask();
//                addEventTask.execute(z);
//            }
//            editE.clear();
//        }
//    }
//
  }
