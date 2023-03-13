package com.me.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class OctalStringToNumberTest {

    @Test
    public void testDecodeOctalString() {
        String octalString = "31646541";
        byte[] expected = new byte[] {103, 77, 97};
        assertArrayEquals(expected, OctalStringToNumber.decodeOctalString(octalString));
    }

    @Test
    public void testDecodeOctalStringGiven() {
        String octalString = "227711474231113516702134342400414143206126403671660545535070012425145143366515462107042711115720106717127670062717046577704333460730170473602176263254671507630065771335415265546676604140271654231111113150576147605265000452421616177052165224543311447543654741617367042213645643631";

        byte[] expected = new byte[] {9, 126, 76, -15, 50, 75, -89, 112, -117, -114, 40, 4, 48, -58, -122, 43, 64, -9, 59, 11, 45, 116, 112, 10, -118, -103, 76, 111, 106, 108, -56, -114, 34, -28, -109, 122, 4, 110, 121, 95, 112, 50, -25, -119, -81, -4, 70, -36, -61, -80, 120, -99, -32, -113, -53, 53, 102, -26, -113, -104, 26, -2, 91, -80, -43, -83, -101, 125, -124, 48, 46, 117, -119, -110, 73, 44, -47, 126, 51, -31, 86, -96, 9, 84, 71, 28, 127, 21, 29, 82, -106, 54, 76, -98, -57, -84, -16, -29, -34, -30, 36, 94, -105, 71, -103};
        assertArrayEquals(expected, OctalStringToNumber.decodeOctalString(octalString));
    }


}