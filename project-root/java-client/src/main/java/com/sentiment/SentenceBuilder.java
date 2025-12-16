package com.sentiment;

public class SentenceBuilder {
    public static String buildDescription(String input, String label, double score) {
        String description;

        if (label.equalsIgnoreCase("POSITIVE")) {
            description = "The given sentence is a positive statement about \"" + input + 
                          "\", and the user feels good.";
        } else if (label.equalsIgnoreCase("NEGATIVE")) {
            description = "The given sentence is a negative statement about \"" + input + 
                          "\", and the user feels bad.";
        } else {
            description = "The given sentence is neutral: \"" + input + "\".";
        }

        return description + String.format(" (confidence: %.4f)", score);
    }
}