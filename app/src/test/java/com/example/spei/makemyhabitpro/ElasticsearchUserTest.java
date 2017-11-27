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