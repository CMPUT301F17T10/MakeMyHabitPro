/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import org.junit.Test;

import static junit.framework.Assert.assertNull;
/**
 * Created by yidingfan on 2017-11-27.
 */
public class ElasticsearchUserTest {
    @Test
    public void getUserTest(){
        ElasticsearchUser.GetUserTask get= new ElasticsearchUser.GetUserTask();
        User u;
        try {
            u = get.get();
        } catch (Exception e) {
            u=null;
        }
        assertNull(u);
    }

}