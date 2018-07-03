package com.walhalla.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {


    public static void main(String[] args) {

        final String regex = "[^c]ar";
        final String string = "The car parked in the garage.";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while(matcher.find())

        {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
    }
}
