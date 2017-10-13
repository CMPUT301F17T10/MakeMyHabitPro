package com.example.mmhp;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yidingfan on 2017-10-13.
 */

public class habitList {
    public ArrayList<habit> habits;
    public habitList(ArrayList<habit> habits){
        this.habits=habits;
    }
    public void add(habit h){
        this.habits.add(h);
    }
    public void sort(){
        Collections.sort(this.habits, Collections.reverseOrder());
    }
    public ArrayList<habit> getHabits(){
        return this.habits;
    }
}
