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

        byte[] expected = new byte[] {103, 77, 97};
        assertArrayEquals(expected, OctalStringToNumber.decodeOctalString(octalString));
    }


}