package com.app.guess.utils;

import java.util.ArrayList;
import java.util.List;

public class RandomGuess {
    private static int winPercent = 5;
    private static List<Integer> listNumber = List.of(1, 2, 3, 4, 5);

    public static int generateRandomNumber(int userNumber) {
        // random number from 0 to 99, 100 numbers -> 100%
        int random = (int) (Math.random() * 100);

        // winPercent = 5 -> 0 to 4 is win(0 to 4 -> 5 numbers -> 5%)
        if (random < winPercent) {
            return userNumber;
        } else {
            List<Integer> numbers = new ArrayList<>(listNumber);
            numbers.remove(Integer.valueOf(userNumber));
            return numbers.get((int) (Math.random() * numbers.size()));
        }
    }
}
