# Credit Card Processor

## How to run

### Main app
- Run App.java in demo/src/main
- Ensure your input files are inside the root directory aka where the Readme.MD file is located
- Your output files will be created exactly where the input files are or will be overwritten if they already exist.

### JUnit Test
- Click on run all tests in AppTest.java (demo/src/test/java/com/example/creditcarddesign/AppTest.java) to see the JUnit testcases being passed.

## Problem Statement
### Primary Problem
The primary problem to solve in this individual project is to design a method (and possibly additional classes) that can read a CSV file containing credit card records, validate the credit card number, and create an instance of appropriate credit card class based on the card issuer and number.

### Secondary Problems
- The problem also involves defining the appropriate class structure for credit cards and considering design patterns that can be used to solve the problem. Specifically, the problem requires verifying the credit card number's validity, identifying the credit card issuer based on the number, and instantiating the appropriate subclass of the CreditCard class.
- We would also need to write/output to a file in the same file format as the input ie json/xml/csv

## Design Patterns Used

## Factory Method
The Factory Method is a creational design pattern that defines an interface for creating objects but lets subclasses decide which class to instantiate. In this pattern, an abstract class (or an interface) is defined, and the concrete classes are created by implementing this abstract class/interface.

In the context of building a Credit Card processor, the Factory Method pattern can be used to create an instance of the appropriate Credit Card subclass based on the credit card number. This allows the code to be flexible enough to add new credit card types later without changing the existing code.

For example, you can define an abstract CreditCardFactory class with a factory method createCreditCard(), which returns a CreditCard object. Each subclass of CreditCardFactory would implement the createCreditCard() method to return an instance of the appropriate Credit Card subclass (MasterCard, Discover, Amex) based on the credit card number.

<img src="https://github.com/gopinathsjsu/individual-project-avibharadwaj/blob/main/images/pasted%20image%200.png">

## Template Method
Template Method is a behavioral design pattern that defines the skeleton of an algorithm in a superclass but lets subclasses override specific steps of the algorithm without changing its structure.

When using the Template Method pattern for reading and processing different file formats (CSV, XML, JSON), we can define the high-level algorithm in the superclass, and the subclasses can provide the specific implementation details for the individual steps. The algorithm can be split into several methods or steps, and each of these methods can be overridden in the subclasses to handle the specific format.

For example, the high-level algorithm for reading and processing files could include the following steps:

- Open the input file.
- Read the contents of the input file.
- Parse the contents of the input file.
- Process the parsed data.
- Write the processed data to the output file.
- The implementation of each step can vary depending on the file format, but the overall structure of the algorithm remains the same. In this way, the Template Method pattern helps to avoid code duplication and promotes code reuse.

<img src="https://github.com/gopinathsjsu/individual-project-avibharadwaj/blob/main/images/Screenshot%202023-05-13%20at%2010.43.16%20PM.png">
<img src="https://github.com/gopinathsjsu/individual-project-avibharadwaj/blob/main/images/pasted%20image%200%20(1).png">

## Consequences - Factory Method

- It promotes loose coupling between the client code and the credit card subclasses, allowing you to change the implementation details of the credit card classes without affecting the client code.

- It allows you to encapsulate the object creation logic in one place, making it easier to modify or extend the creation process in the future.

- It promotes the Open/Closed principle, which means that the system is open to extension but closed to modification. New credit card types can be added by creating a new Credit Card subclass and its corresponding factory method, without modifying the existing code.

## Consequences - Template Method

The consequences of using the Template Method pattern for reading and processing file formats include:

- Improved code maintainability and flexibility: The algorithm's structure remains unchanged, and individual steps can be easily modified or extended without affecting the rest of the algorithm.


- Promotes code reuse: The high-level algorithm is defined in the superclass, and the subclasses can reuse the code for the steps that are common across all file formats.


- Separation of concerns: The different steps of the algorithm are split into different methods, making the code more modular and easier to understand.


- Can be more complex: The use of inheritance and polymorphism can make the code more complicated than if we were to use a simple procedural approach.
