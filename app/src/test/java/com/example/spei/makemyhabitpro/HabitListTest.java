/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by spei on 11/18/17.
 */

public class HabitListTest {
    @Test
    public void add() throws Exception {

        Habit habit1 = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Habit habit2 = new Habit("userId2", "title2", "reason2", "detail2", "type2","startDate2");

        HabitList Test = new HabitList(habit1);
        Test.add(habit2);

        assertTrue(Test.contains(habit2));

    }

    @Test
    public void sort() throws Exception {

    }

    @Test
    public void getHabits() throws Exception {



        Habit habit1 = new Habit("userId", "title", "reason", "detail", "type","startDate");
        Habit habit2 = new Habit("userId2", "title2", "reason2", "detail2", "type2","startDate2");


        HabitList Test = new HabitList(habit1);
        Test.add(habit2);

        ArrayList habits = new ArrayList();
        habits.add(habit1);
        habits.add(habit2);

        assertEquals(Test.getHabits(), habits);

    }

}
