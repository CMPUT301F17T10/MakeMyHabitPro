package com.example.spei.makemyhabitpro;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by spei on 11/17/17.
 */

public class HabitList {

    public ArrayList<Habit> habits = new ArrayList<Habit>();

    public HabitList(Habit h){
        this.habits.add(h);
    }

       public void add(Habit h){
           this.habits.add(h);
    }

    public void sort(){
        Collections.sort(this.habits, Collections.reverseOrder());
    }

    public ArrayList<Habit> getHabits(){
        return this.habits;
    }


    public boolean contains(Habit h){
        return habits.contains(h);
    }


}