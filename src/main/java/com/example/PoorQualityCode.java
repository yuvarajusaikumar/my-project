package com.example;

public class PoorQualityCode {

    public static void main(String[] args) {
        
        // Hardcoded values instead of using variables or constants
        System.out.println("Welcome to the application!");
        System.out.println("User input data:");

        // Duplicate code logic
        String data = getUserData();
        System.out.println("User Data: " + data);

        // No exception handling for possible errors
        String result = processUserData(data);
        System.out.println("Processed Data: " + result);

        // Hardcoded print statements in the method
        String feedback = "Good job!"; // Hardcoded feedback
        System.out.println(feedback);
    }

    // Method with a poor name, unclear what it does
    public static String getUserData() {
        String userData = "John, 25"; // Hardcoded user data (again!)
        return userData;
    }

    // A large method with multiple responsibilities (violation of Single Responsibility Principle)
    public static String processUserData(String data) {
        // Process data and return the result (could be more complex)
        String processedData = data.toUpperCase(); // Hardcoded logic

        // Duplicate logic (for illustration)
        String additionalInfo = data + ", processed!";
        System.out.println("Duplicate logic: " + additionalInfo); // Duplicate print statement

        return processedData;
    }

    // A method that doesn't handle input properly (missing validation, etc.)
    public static void poorMethodWithoutValidation(String input) {
        // If input is null, there is no exception handling
        System.out.println("Processing input: " + input); // Could cause issues if input is null
    }

    // Large class with too many responsibilities (bad class design)
    public static void sendEmail(String recipient, String subject, String body) {
        // Code that sends an email (a big, unorganized method)
        System.out.println("Sending email to: " + recipient);
        // More code here that is unorganized...
    }
}

