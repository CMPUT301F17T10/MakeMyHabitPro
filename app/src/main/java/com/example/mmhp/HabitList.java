package com.example.mmhp;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yidingfan on 2017-10-13.
 */

public class HabitList {
    public ArrayList<Habit> habits;
    public HabitList(ArrayList<Habit> habits){
        this.habits=habits;
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
}
