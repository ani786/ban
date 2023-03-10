package com.me.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OctalStringToNumberTest {

    @Test
    void testDecodeGivenBase8() {
        String octalString = "2277114742311135167021343424004141432061264036716605455&35070012425145143366515462107042711115720106717127670062&71704657770433346073017047360217626325467150763006577133&54152655466766041402716542311111131505761476052650004524&21616177052165224543311447543654741617367042213645643631&33346575330621635642541636644326535501666004333326756424&47003252221104064117622317044717471111";

        byte[] expected = {-105, -55, 60, -103, 75, 78, -62, 92, -30, 0, 12, 99, -122, 86, 3, -71, -80, 101, 93, 56, 10, 21, 101, 99, -10, 77, 50, 71, 34, -55, 77, -48, 70, -49, 87, -72, 50, -49, 38, 127, -60, -37, 48, -40, 120, 59, -126, 126, -77, -84, -71, 71, -104, 53, -7, -35, 13, -75, 102, -66, -124, 96, -71, -84, -103, 73, 75, 104, 126, 103, -123, -75, 0, 42, 17, -114, 127, 42, 117, -108, 99, -55, 39, 99, -84, -31, -113, -9, 34, -117, -91, -93, -103, -37, 53, -21, -58, -114, -18, 21, 14, -10, 35, -75, -19, 14, -80, 35, -37, -73, 116, -92, -64, -43, -110, 72, 6, 9, -14, -103, -60, 57, -25, 73, 1};
        byte[] actual = OctalStringToNumber.decodeString(octalString, 8);


        assertArrayEquals(expected, actual);
    }

    @Test
    void testDecodeBase8() {
        String octalString = "31646541";
        byte[] expected = { -50, 53, 33 };
        byte[] actual = OctalStringToNumber.decodeString(octalString, 8);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testDecodeEmptyStringBase8() {
        String octalString = "";
        byte[] expected = {};
        byte[] actual = OctalStringToNumber.decodeString(octalString,8);
        assertArrayEquals(expected, actual);
    }



    @Test
    void testDecodeNullStringBase8() {
        String octalString = null;
        assertThrows(NullPointerException.class, () -> {
            OctalStringToNumber.decodeString(octalString,8);
        });
    }



    @Test
    public void testDecodeHex() {
        String input = "48656C6C6F20576F726C64";
        int base = 16;
        byte[] expected = {72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100};
        assertArrayEquals(expected, OctalStringToNumber.decodeString(input, base));
    }

    @Test
    public void testDecodeBase32() {
        String input = "JBSWY3DPEB3W64TMMQ======";
        int base = 32;
        byte[] expected = {72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100};
        assertArrayEquals(expected, OctalStringToNumber.decodeString(input, base));
    }



}
