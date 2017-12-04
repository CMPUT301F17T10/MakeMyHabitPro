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

public class ElasticsearchEvent {
    private static JestDroidClient client;

    public static class AddEventTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... events) {
            verifySettings();

            for (Event event : events) {
                Index index = new Index.Builder(event).index("cmput301f17t10").type("event").id(event.getId()).build();

                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("In ID", result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the Event.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Error", "The application failed to build and send the Event");
                }

            }
            return null;
        }
    }




    public static class DeleteEventTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... events) {
            verifySettings();

            for (Event event : events) {
                Delete delete = new Delete.Builder(event.getId())
                        .index("cmput301f17t10").type("event").build();


                try {
                    // where is the client
                    DocumentResult result = client.execute(delete);
                    if (result.isSucceeded()) {
                        Log.d("In ID", result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to delete the event.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Error", "The application failed to build and delete the event");
                }

            }
            return null;
        }
    }





    /**
     * The function to judge if the user is stored in elastic search
     */
    public static class IsExist extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            verifySettings();

            Event event=null;
            Get get = new Get.Builder("cmput301f17t10", params[0]).type("event").build();

            Log.d("event test", params[0]);

            try {
                JestResult result = client.execute(get);
                event = result.getSourceAsObject(Event.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            if (event == null) {
                return false;
            }

            return true;
        }
    }





    public static class GetEvents extends AsyncTask<String, Void,ArrayList<Event>> {
        @Override
        protected ArrayList<Event> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Event> events = new ArrayList<Event>();

            // TODO Build the query
            Search search = new Search.Builder(search_parameters[0])
                    .addIndex("cmput301f17t10")
                    .addType("event")

                    .build();

            try {

                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Event> foundHistories
                            =result.getSourceAsObjectList(Event.class);

                    for (Event e:foundHistories){
                        events.add(e);
                    }

                }
                else{
                    Log.e("Error","Query failed");
                }
            }
            catch (Exception e) {
                Log.e("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return events;
        }
    }







    /**
     * Verify settings.
     */
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
