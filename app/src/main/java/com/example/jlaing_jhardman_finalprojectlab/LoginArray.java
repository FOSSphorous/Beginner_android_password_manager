package com.example.jlaing_jhardman_finalprojectlab;

import java.util.Random;

public class LoginArray {

    public int generateRandomNumber(int minBound, int maxBound) {
        Random randGenerator = new Random();
        int randNum = randGenerator.nextInt(maxBound - minBound + 1) + minBound;
        return randNum;
    }
}
