/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication3;

import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arash
 */
public class MyParserTest {
    
    public MyParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of extractPhoneNumbers method, of class MyParser.
     */
    @Test
    public void testExtractPhoneNumbers() throws Exception {
        System.out.println("extractPhoneNumbers");
        MyParser instance = null;
        HashSet<String> expResult = null;
        HashSet<String> result = instance.extractPhoneNumbers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class MyParser.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        MyParser.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
