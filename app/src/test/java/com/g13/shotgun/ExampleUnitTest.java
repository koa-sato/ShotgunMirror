package com.g13.shotgun;

import org.junit.Test;
import java.util.Date;
import java.sql.Time;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public Post testPost = new Post("Santa Barbara", new Date (2017, 2, 16),
            new Time(12, 12, 12), "FirstName LastName");

    @Test
    public void get_city_isCorrect() {
        assertEquals("Santa Barbara", testPost.get_city());
    }

    @Test
    public void get_date_isCorrect() {
        assertEquals(new Date (2017, 2, 16), testPost.get_date());
    }

    @Test
    public void get_time_isCorrect() {
        assertEquals(new Time (12, 12, 12), testPost.get_time());
    }

    @Test
    public void get_user_isCorrect() {
        assertEquals("FirstName LastName", testPost.get_user());
    }


    public static void main(String [] args) {
        ExampleUnitTest tester = new ExampleUnitTest();
        tester.get_city_isCorrect();
        tester.get_date_isCorrect();
        tester.get_time_isCorrect();
        tester.get_user_isCorrect();

    }

}
