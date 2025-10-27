package com.debuggeando_ideas.best_travel_2025.util;

import java.time.LocalDateTime;
import java.util.Random;

public class BestTravelUtil {

    private static final Random random = new Random();

    public static LocalDateTime getRandomSoon(){
        var randomHours = random.nextInt(5-2) + 2;
        var now  = LocalDateTime.now();
        var result = now.plusHours(randomHours);

        if (result.isBefore(now) || result.equals(now)) {
            result = result.plusDays(1);
        }
        return result;
    }


    public static LocalDateTime getRandomLatter(){
        var randomHours = random.nextInt(12 - 6) + 6;
        var now  = LocalDateTime.now();
        var result = now.plusHours(randomHours);

        if (result.isBefore(now) || result.equals(now)) {
            result = result.plusDays(1);
        }

        return result;
    }
}
