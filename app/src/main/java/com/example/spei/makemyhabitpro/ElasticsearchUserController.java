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

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/**
 * Created by spei on 11/25/17.
 */

public class ElasticsearchUserController {
    private static JestDroidClient client;
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
    public static class RegUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301f17t10").type("user").id(user.getName()).build();
                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("In AsyncTask ID", result.getId());
                        //user.setAid(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the user.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Error", "The application failed to build and send the user");
                }

            }
                return null;
        }

    }
    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... strings) {
            verifySettings();
            User foundUser;

            Get get = new Get.Builder("cmput301f17t10", strings[0]) //index, id
                    .type("user")
                    .build();

            Log.i("Get", get.toString());
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    foundUser = result.getSourceAsObject(User.class);

                } else {
                   foundUser=null;
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when communicating with the server");
                foundUser = null;
            }
            return foundUser;
        }
    }
    public static class DeleteUser extends  AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            verifySettings();
            Delete delete = new Delete.Builder(strings[0])
                    .index("cmput301f17t10")
                    .type("user")
                    .build();
            try {
                JestResult result = client.execute(delete);
                if (result.isSucceeded()) {
                    Log.i("User", "deleted");
                } else {
                    Log.i("Error", "unSuncceeded");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong");
            }
            return null;
        }
    }

    public static class IsExist extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            verifySettings();

            User user = new User("empty","empty","empty");

            Get get = new Get.Builder("cmput301f17t10", params[0]).type("user").build();
            Log.d("usertest", params[0]);

            try {
                JestResult result = client.execute(get);
                user = result.getSourceAsObject(User.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            if (user == null) {
                return false;
            }

            return true;
        }
    }

    }
