package com.example.creditcarddesign;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
class AppTest {
    //SIMPLE  TEST
    @Test
    void testApp() {
        assertEquals(1, 1);
    }
    


    //TEST IF EXTENSION IS SAME
    @Test
    public void testGetExtension() {
        assertEquals("csv", App.getExtension("src/test/resources/test.csv"));
        assertEquals("", App.getExtension("no_extension_file"));
    }

    //TEST TO SEE IF ITS A FILE OR NOT
    @Test
    public void testIsFile() {
        assertTrue(App.isFile("./src/test/java/com/example/creditcarddesign/sampleFiles/input_file.xml"));
        assertFalse(App.isFile("./demo/src/test/java/com/example/creditcarddesign/sampleFiles/not_a_file.txt"));
    }

    @Test
    public void testMastercard() { //can do this for other credit card types. change line number 40
        // Arrange
        CreditCardFactory creditCardFactory = new CreditCardFactory();
        String outputType = "MasterCard";
        String[] tokenArray = { "5567894523129089", "John Doe", "10/2023", "123" };
        CSVFileParser csvFileParser = new CSVFileParser();
        String type=csvFileParser.processData(tokenArray[0]);
        CreditCard creditCard = creditCardFactory.createCreditCard(type);
        
        // Act
        String output = creditCard.getCreditCardType(tokenArray[0], tokenArray[1], type);
        
        // Assert
        assertEquals(tokenArray[0]+","+outputType, output);
    }
    
    @Test
    public void testNotMastercard() {
        // Arrange
        CreditCardFactory creditCardFactory = new CreditCardFactory();
        String outputType = "MasterCard";
        String[] tokenArray = { "4111111111111111", "John Doe", "10/2023", "123" };
        CSVFileParser csvFileParser = new CSVFileParser();
        String type=csvFileParser.processData(tokenArray[0]);
        CreditCard creditCard = creditCardFactory.createCreditCard(type);
        
        // Act
        String output = creditCard.getCreditCardType(tokenArray[0], tokenArray[1], type);

        // Assert
        assertNotEquals(tokenArray[0]+","+outputType, output);
    }

}
