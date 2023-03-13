package com.me.service;

import java.math.BigInteger;
import java.util.Arrays;

class OctalStringToNumber {

    public static byte[] decodeOctalString(String octalString) {
        // Convert the octal string to a BigInteger
        BigInteger octalValue = new BigInteger(octalString, 8);

        // Convert the BigInteger to an array of bytes
        byte[] byteArray = octalValue.toByteArray();

        // Trim any leading zeros from the byte array
        int firstNonZeroIndex = 0;
        while (firstNonZeroIndex < byteArray.length && byteArray[firstNonZeroIndex] == 0) {
            firstNonZeroIndex++;
        }
        byte[] trimmedArray = Arrays.copyOfRange(byteArray, firstNonZeroIndex, byteArray.length);

        return trimmedArray;
    }











}
