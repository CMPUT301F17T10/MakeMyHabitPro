package com.example.spei.makemyhabitpro;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

/**
 * Created by spei on 11/30/17.
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


    public void addHabit(Habit habit){addH.add(habit);}
    public void deleteHabit(Habit habit){deleteH.add(habit);}
    public void editHabit(Habit habit){editH.add(habit);}
    public void addEvent(Event event){addE.add(event);}
    public void deleteEvent(Event event){deleteE.add(event);}
    public void editEvent(Event event){editE.add(event);}

    public void updateAll(){

        if(addH != null){
            for(Habit i : addH) {
                ElasticsearchHabit.AddHabitTask addHabitTask = new ElasticsearchHabit.AddHabitTask();
                addHabitTask.execute(i);
            }
        }

        if (deleteH != null) {
            for (Habit j : deleteH) {
                ElasticsearchHabit.DeleteHabitTask deleteHabitTask = new ElasticsearchHabit.DeleteHabitTask();
                deleteHabitTask.execute(j);

            }
        }

        if (editH != null) {
            for (Habit k : editH) {
                ElasticsearchHabit.DeleteHabitTask deleteHabitTask = new ElasticsearchHabit.DeleteHabitTask();
                deleteHabitTask.execute(k);
                ElasticsearchHabit.AddHabitTask addHabitTask = new ElasticsearchHabit.AddHabitTask();
                addHabitTask.execute(k);
            }
        }


        if(addE != null){
            for(Event x : addE) {
                ElasticsearchEvent.AddEventTask addEventTask = new ElasticsearchEvent.AddEventTask();
                addEventTask.execute(x);
            }
        }

        if (deleteE != null) {
            for (Event y : deleteE) {
                ElasticsearchEvent.DeleteEventTask deleteEventTask = new ElasticsearchEvent.DeleteEventTask();
                deleteEventTask.execute(y);

            }
        }

        if (editE != null) {
            for (Event z : editE) {
                ElasticsearchEvent.DeleteEventTask deleteEventTask = new ElasticsearchEvent.DeleteEventTask();
                deleteEventTask.execute(z);
                ElasticsearchEvent.AddEventTask addEventTask = new ElasticsearchEvent.AddEventTask();
                addEventTask.execute(z);
            }
        }
    }

}
