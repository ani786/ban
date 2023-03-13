package com.me.service;

import java.util.Arrays;

class OctalStringToNumber {

    public static byte[] decodeOctalString(String octalString) {
        // Convert the octal string to a long integer
        long octalValue = Long.parseLong(octalString, 8);

        // Convert the long integer to an array of bytes
        byte[] byteArray = new byte[Long.SIZE / Byte.SIZE];
        for (int i = byteArray.length - 1; i >= 0; i--) {
            byteArray[i] = (byte) (octalValue & 0xFF);
            octalValue >>= Byte.SIZE;
        }

        // Trim any leading zeros from the byte array
        int firstNonZeroIndex = 0;
        while (firstNonZeroIndex < byteArray.length && byteArray[firstNonZeroIndex] == 0) {
            firstNonZeroIndex++;
        }
        byte[] trimmedArray = Arrays.copyOfRange(byteArray, firstNonZeroIndex, byteArray.length);

        return trimmedArray;
    }











}
