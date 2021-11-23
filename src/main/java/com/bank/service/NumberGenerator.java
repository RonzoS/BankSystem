package com.bank.service;

import java.util.Random;

public class NumberGenerator {

    public int GenereteNumber(int min, int max){
        Random random = new Random();
        return random.nextInt(max-min)+min;
    }
}
