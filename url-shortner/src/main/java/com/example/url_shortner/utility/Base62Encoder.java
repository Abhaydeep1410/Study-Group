package com.example.url_shortner.utility;

public class Base62Encoder {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(int input) {

        StringBuilder output = new StringBuilder();
        while (input > 0) {
            int remainder = input % 62;
            output.append(BASE62.charAt(remainder));
            input = input / 62;
        }
        return output.reverse().toString();
    }

    public static int decode(String input) {
        int num = 0;
        for (int i = 0; i < input.length(); i++) {
            num = num * 62 + BASE62.indexOf(input.charAt(i));
        }
        return num;
    }
}
