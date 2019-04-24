package com.example.bamaappredesign;

import com.example.bamaappredesign.Grades.GradesClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GradesTest {
    @Test
    public void testGrades() {
        GradesClass a = new GradesClass("CS 495", 94.32);
        assertEquals(a.getCls(), "CS 495");

        String grade = (String.format( "%.2f", (a.getGrd())));
        assertEquals(grade, "94.32");
    }
}
