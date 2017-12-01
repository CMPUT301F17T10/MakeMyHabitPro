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

public class HabitList {

    public ArrayList<Habit> habits = new ArrayList<Habit>();

    public HabitList(Habit h){
        this.habits.add(h);
    }

       public void add(Habit h){
           if (!contains(h)){
               this.habits.add(h);
           }
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