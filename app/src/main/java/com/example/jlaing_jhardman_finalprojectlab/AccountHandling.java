package com.example.jlaing_jhardman_finalprojectlab;

import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class AccountHandling {

    // strings are created from the characters in the list
    public String generateRandomString (int charLength) {
        char[] characterList = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0','!','@','#','$','%','^','&','*','(',')','?'};
        String stringToMod = "";
        for (int i = 0; i < charLength; i++) {
            System.out.println(characterList[generateRandomNumber(0,characterList.length - 1)]);
            stringToMod += characterList[generateRandomNumber(0, characterList.length -1)];
        }
        return stringToMod;
    }

    // where randomly generated numbers are created to be used with random string generation
    private int generateRandomNumber(int minBound, int maxBound) {
        Random randGenerator = new Random();
        int randNum = randGenerator.nextInt(maxBound - minBound + 1) + minBound;
        return randNum;
    }

    // checks to see if an entry is within a given array
    public boolean accountCheck(String[] dictionary, String searchItem) {
        List<String> list = Arrays.asList(dictionary);
        final boolean contains = list.contains(searchItem);
        return contains;
    }

    // gets the index of an item within an array
    public int accountIndex(String[] database, String searchItem){
        int itemLocation = Arrays.asList(database).indexOf(searchItem);
        return itemLocation;
    }

    // This needed to be declared outside of a method so that it could be used throughout onSubmitAccountCreation in MainActivity.java
    public String results = "";

}
