package com.me.service;

import org.apache.commons.codec.binary.Base32;

import java.util.List;

class OctalStringToNumber {


    private OctalStringToNumber() {
        throw new IllegalStateException("Utility class");
    }

    private final List<Integer> bases = List.of(8,16,32,64);


    public static byte[] decodeString(String input, int base) {
        if (base == 8) {
            // Remove any non-octal characters from the input string
            input = input.replaceAll("[^0-7]", "");

            // Calculate the length of the byte array
            int len = (input.length() + 2) / 3;

            // Create a byte array to hold the result
            byte[] result = new byte[len];

            // Loop through the input string and convert each group of 3 digits to a byte
            int start = 0;
            for (int i = 0; i < len; i++) {
                int end = Math.min(start + 3, input.length());
                String group = input.substring(start, end);
                int octalValue = Integer.parseInt(group, 8);
                result[i] = (byte) octalValue;
                start = end;
            }

            return result;
        } else if (base == 16) {
            // Remove any non-hexadecimal characters from the input string
            input = input.replaceAll("[^0-9A-Fa-f]", "");

            // Convert the input string to a byte array
            byte[] result = new byte[input.length() / 2];
            for (int i = 0; i < result.length; i++) {
                int index = i * 2;
                int hexValue = Integer.parseInt(input.substring(index, index + 2), 16);
                result[i] = (byte) hexValue;
            }

            return result;
        } else if (base == 32) {
            // Decode the input string using Base32
            return new Base32().decode(input);
        } else {
            throw new IllegalArgumentException("Unsupported base: " + base);
        }
    }



}
