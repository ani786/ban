package com.me.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HexStringToNumberTest {

    @Test
    void testDecodeHexString() {
        String hexString = "416261626520546869732049732061205465737420537472696e67";
        byte[] expectedArray = "Ababe This Is a Test String".getBytes();
        assertArrayEquals(expectedArray, HexStringToNumber.decodeHexString(hexString));
    }

    @Test
    void testDecodeHexStringWithOddLength() {
        String hexString = "416261626520546869732049732061205465737420537472696e";
        byte[] expectedArray = "Ababe This Is a Test Strin".getBytes();
        assertArrayEquals(expectedArray, HexStringToNumber.decodeHexString(hexString));
    }

    @Test
    void testDecodeHexStringWithInvalidCharacters() {
        String hexString = "41626162G520546869732049732061205465737420537472696e";
        assertThrows(NumberFormatException.class, () -> HexStringToNumber.decodeHexString(hexString));
    }


}