package com.me.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Base32ToNumberTest {



    @Test
    void testDecodeBase32StringWithTrailingZeros() {
        String base32String = "KRUGS4ZANFZSA5DUJN2G000"; // contains trailing zeros
        byte[] expectedArray = { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64 };
        assertFalse(Arrays.equals(expectedArray, Base32ToNumber.decodeBase32String(base32String)));
    }


    @Test
    void testDecodeBase32String() {
        String base32String = "NBSWY3DPEB3W64TMMQ======"; // decoded value is "hello world"
        byte[] expectedArray = { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64 };
        assertArrayEquals(expectedArray, Base32ToNumber.decodeBase32String(base32String));
    }






}