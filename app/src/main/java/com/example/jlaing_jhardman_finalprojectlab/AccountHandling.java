package com.example.jlaing_jhardman_finalprojectlab;

import java.util.Random;

public class AccountHandling {

    public String generateRandomString (int charLength) {
        char[] characterList = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0','!','@','#','$','%','^','&','*','(',')','?'};
        String stringToMod = "";
        for (int i = 0; i < charLength; i++) {
            System.out.println(characterList[generateRandomNumber(0,characterList.length)]);
            stringToMod += characterList[generateRandomNumber(0, characterList.length)];
        }
        return stringToMod;
    }

    private int generateRandomNumber(int minBound, int maxBound) {
        Random randGenerator = new Random();
        int randNum = randGenerator.nextInt(maxBound - minBound + 1) + minBound;
        return randNum;
    }
}
