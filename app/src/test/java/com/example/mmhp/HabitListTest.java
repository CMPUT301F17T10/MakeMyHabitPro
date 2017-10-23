package com.example.mmhp;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by YOYO on 10/22/2017.
 */
public class HabitListTest {
    @Test
    public void add() throws Exception {

        int uid1 = 123;
        int uid2 = 1234;
        String name = "Feiyang";
        int testType = 0;
        ArrayList testDate = new ArrayList();
        testDate.add(5);

        Habit habit1 = new Habit(uid1, name, testType, testDate);
        Habit habit2 = new Habit(uid2, name, testType, testDate);

        HabitList Test = new HabitList(habit1);
        Test.add(habit2);

        assertTrue(Test.contains(habit2));

    }

    @Test
    public void sort() throws Exception {

    }

    @Test
    public void getHabits() throws Exception {

        int uid1 = 123;
        int uid2 = 1234;
        String name1 = "Feiyang";
        String name2 = "Yiding";
        int testType = 0;
        ArrayList testDate = new ArrayList();
        testDate.add(5);

        Habit habit1 = new Habit(uid1, name1, testType, testDate);
        Habit habit2 = new Habit(uid2, name2, testType, testDate);

        HabitList Test = new HabitList(habit1);
        Test.add(habit2);

        ArrayList habits = new ArrayList();
        habits.add(habit1);
        habits.add(habit2);

        assertEquals(Test.getHabits(), habits);

    }

}