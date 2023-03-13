package com.me.service.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DecodingTypeTest {

    @Test
    void decodeOctal() {
        String encodedString = "31646541";
        byte[] expected = new byte[] {103, 77, 97};
        assertArrayEquals(expected, DecodingType.OCTAL.decode(encodedString));
    }

    @Test
    void decodeHex() {
        String encodedString = "416261626520546869732049732061205465737420537472696e67";
        byte[] expected = "Ababe This Is a Test String".getBytes();
        assertArrayEquals(expected, DecodingType.HEX.decode(encodedString));
    }

    @Test
    void decodeBase32() {
        String base32String = "NBSWY3DPEB3W64TMMQ======"; // decoded value is "hello world"
        byte[] expected = { 0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64 };
        assertArrayEquals(expected, DecodingType.BASE32.decode(base32String));
    }
}