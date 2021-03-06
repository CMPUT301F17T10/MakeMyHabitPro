/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by yidingfan on 2017-11-27.
 */

public class ElasticsearchHabit {

    private static JestDroidClient client;

    public static class AddHabitTask extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Index index = new Index.Builder(habit).index("cmput301f17t10").type("habit")
                        .id(habit.getUserId() + habit.getTitle().toUpperCase())
                        .build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("In ID", result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the habit.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Error", "The application failed to build and send the habit");
                }

            }
            return null;
        }
    }




    public static class DeleteHabitTask extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Delete delete = new Delete.Builder(habit.getUserId() + habit.getTitle().toUpperCase())
                        .index("cmput301f17t10").type("habit").build();

                try {
                    DocumentResult result = client.execute(delete);
                    if (result.isSucceeded()) {
                        Log.d("In ID", result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to delete the Habit.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Error", "The application failed to build and send the Habit");
                }

            }
            return null;
        }
    }









    public static class GetHabitTask extends AsyncTask<String, Void, Habit> {
        @Override
        protected Habit doInBackground(String... params) {
            verifySettings();

            Habit habit = new Habit();
            Get get = new Get.Builder("cmput301f17t10", params[0]).type("habit").build();

            try{
                JestResult result = client.execute(get);
                habit = result.getSourceAsObject(Habit.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return habit;
        }

    }




    public static class IsExist extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            verifySettings();

            Habit habit = new Habit();
            Get get = new Get.Builder("cmput301f17t10", params[0]).type("habit").build();


            try {
                JestResult result = client.execute(get);
                habit = result.getSourceAsObject(Habit.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            if (habit == null) {
                return false;
            }

            return true;
        }
    }


    // TODO we need a function which gets tweets from elastic search
    public static class GetHabits extends AsyncTask<String, Void, ArrayList<Habit>> {
        @Override
        protected ArrayList<Habit> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Habit> habits = new ArrayList<Habit>();
            Search search = new Search.Builder(search_parameters[0]).addIndex("cmput301f17t10")
                    .addType("habit")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Habit> foundHistories
                            =result.getSourceAsObjectList(Habit.class);
                    habits.addAll(foundHistories);

                }
                else{
                    Log.e("Error","Query failed");
                }
            }
            catch (Exception e) {
                Log.e("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return habits;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }



}
