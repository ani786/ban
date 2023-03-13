package com.me.service;

import org.apache.commons.codec.binary.Base32;

import java.util.Arrays;

public class Base32ToNumber {

    public static byte[] decodeBase32String(String base32String) {
        // Convert the base32 string to a byte array
        Base32 base32 = new Base32();
        byte[] byteArray = base32.decode(base32String);

        // Trim any leading zeros from the byte array
        int firstNonZeroIndex = 0;
        while (firstNonZeroIndex < byteArray.length && byteArray[firstNonZeroIndex] == 0) {
            firstNonZeroIndex++;
        }
        byte[] trimmedArray = Arrays.copyOfRange(byteArray, firstNonZeroIndex, byteArray.length);

        return trimmedArray;
    }













}
