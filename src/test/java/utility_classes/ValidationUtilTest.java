/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package utility_classes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static utility_classes.ValidationUtil.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Test cases for the ValidationUtil class used throughout the program.
 * (For convenience, we have decided to test on other default
 * users within the program.)
 *
 */
public class ValidationUtilTest {

    public ValidationUtilTest() {}

    @BeforeClass
    public static void setUpClass() {
        //disable pop ups
        PopUpUtil.testMode = true;
    }

    @AfterClass
    public static void tearDownClass() {
        //enable pop ups 
        PopUpUtil.testMode = false;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of checkDateOfBirth method, of class ValidationUtil. 
     * valid date, invalid date, age under 16
     */
    @Test
    public void validDateTest() {
        String date = "2000-01-01";
        assertTrue(checkDateOfBirth(date));
    }

    @Test
    public void incorrectDateTest() {
        String date = "20-01-01";
        assertFalse(checkDateOfBirth(date));
    }

    @Test
    public void incorrectAgeDateTest() {
        String date = "2020-01-01";
        assertFalse(checkDateOfBirth(date));
    }

    /**
     * Test of checkEmail method, of class ValidationUtil. 
     * valid email, invalid email, invalid email length
     */
    @Test
    public void validEmailTest() {
        String email = "user@domain.com";
        assertTrue(checkEmail(email));
    }

    @Test
    public void invalidEmailTest() {
        String email = "userdomaincom";
        assertFalse(checkEmail(email));
    }

    @Test
    public void invalidEmailLengthTest() {
        String email = "useruseruseruseruseruseruseruseruseruser@domain.com";
        assertFalse(checkEmail(email));
    }

    /**
     * Test of checkPhoneNumber method, of class ValidationUtil. 
     * valid number, invalid numbers via length, invalid number via character
     */
    @Test
    public void validPhoneNumberTest() {
        String phoneNumber = "0211234567";
        assertTrue(checkPhoneNumber(phoneNumber));
    }

    @Test
    public void validInternatinalPhoneNumberTest() {
        String phoneNumber = "+64211234567";
        assertTrue(checkPhoneNumber(phoneNumber));
    }

    @Test
    public void invalidPhoneNumberTest() {
        String phoneNumber = "02112345679";
        assertFalse(checkPhoneNumber(phoneNumber));
    }

    @Test
    public void invalidInternatinalPhoneNumberTest() {
        String phoneNumber = "+64211234567999";
        assertFalse(checkPhoneNumber(phoneNumber));
    }

    @Test
    public void smallInvalidPhoneNumberTest() {
        String phoneNumber = "021123456";
        assertFalse(checkPhoneNumber(phoneNumber));
    }

    @Test
    public void smallInvalidInternatinalPhoneNumberTest() {
        String phoneNumber = "+642112345";
        assertFalse(checkPhoneNumber(phoneNumber));
    }

    @Test
    public void invalidPhoneNumberWithLetterTest() {
        String phoneNumber = "02A1234567";
        assertFalse(checkPhoneNumber(phoneNumber));
    }

    @Test
    public void invalidInternatinalPhoneNumberWithLetterTest() {
        String phoneNumber = "+642A1234567";
        assertFalse(checkPhoneNumber(phoneNumber));
    }

    /**
     * Test of formatPhoneNumber method, of class ValidationUtil. 
     * no white spaces in the phoneNumber after calling method
     */
    @Test
    public void formatPhoneNumberTest() {
        String whiteSpaceNumber = "0 1 2 3 4 5";
        String expected = "012345";
        assertEquals(expected, formatPhoneNumber(whiteSpaceNumber));
    }

    /**
     * Test of checkIntegerRange method, of class ValidationUtil. 
     * in bounds, out of bounds upper and lower, character input
     */
    @Test
    public void validcheckIntegerRangeTest() {
        assertTrue(checkIntegerRange("5", 1, 10));
    }

    @Test
    public void outOfUpperBoundscheckIntegerRangeTest() {
        assertFalse(checkIntegerRange("11", 1, 10));
    }

    @Test
    public void outOfLowerBoundscheckIntegerRangeTest() {
        assertFalse(checkIntegerRange("0", 1, 10));
    }

    @Test
    public void integerRangeWithLetterTest() {
        assertFalse(checkIntegerRange("A", 1, 10));
    }

    /**
     * Test of checkIntegerRange int version of the method,of class ValidationUtil. 
     * in bounds, out of bounds upper and lower
     */
    @Test
    public void intValidcheckIntegerRangeTest() {
        assertTrue(checkIntegerRange(5, 1, 10));
    }

    @Test
    public void intOutOfUpperBoundscheckIntegerRangeTest() {
        assertFalse(checkIntegerRange(11, 1, 10));
    }

    @Test
    public void intOutOfLowerBoundscheckIntegerRangeTest() {
        assertFalse(checkIntegerRange(0, 1, 10));
    }

    /**
     * Test of checkFloatRange of the method,of class ValidationUtil.
     * test in bounds, out of bounds upper and lower, and with character
     */
    @Test
    public void validcheckFloatRangeTest() {
        assertTrue(checkFloatRange("5.5", 1.5f, 10.1f));
    }

    @Test
    public void outOfUpperBoundscheckFloatRangeTest() {
        assertFalse(checkFloatRange("10.5", 1.5f, 10.1f));
    }

    @Test
    public void outOfLowerBoundscheckFloatRangeTest() {
        assertFalse(checkFloatRange("0.4", 1.5f, 10.1f));
    }

    @Test
    public void checkFloatRangeWithLetterTest() {
        assertFalse(checkFloatRange("A", 1.5f, 10.1f));
    }
}
