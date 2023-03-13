package com.me.service;

import java.math.BigInteger;
import java.util.Arrays;

public class HexStringToNumber {

    public static byte[] decodeHexString(String hexString) {
        // Convert the hexadecimal string to a BigInteger
        BigInteger hexValue = new BigInteger(hexString, 16);

        // Convert the BigInteger to an array of bytes
        byte[] byteArray = hexValue.toByteArray();

        // Trim any leading zeros from the byte array
        int firstNonZeroIndex = 0;
        while (firstNonZeroIndex < byteArray.length && byteArray[firstNonZeroIndex] == 0) {
            firstNonZeroIndex++;
        }
        byte[] trimmedArray = Arrays.copyOfRange(byteArray, firstNonZeroIndex, byteArray.length);

        return trimmedArray;
    }












}
