package com.hotelMangments.hotelMangments.utils;

import java.util.UUID;

public class Utils {

    public static String generateRandomString(int number) {
        String randomString = UUID.randomUUID().toString().replace("-", "");
        randomString = randomString.substring(0, number);
        return randomString.substring(0, number);
    }
}
