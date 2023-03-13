package com.me.service.mapper;

import com.me.service.Base32ToNumber;
import com.me.service.HexStringToNumber;
import com.me.service.OctalStringToNumber;

public enum DecodingType {
    OCTAL {
        @Override
        public byte[] decode(String input) {
            // implementation of octal decoding
            return OctalStringToNumber.decodeOctalString(input);
        }
    },
    HEX {
        @Override
        public byte[] decode(String input) {
            // implementation of hexadecimal decoding
            return HexStringToNumber.decodeHexString(input);
        }
    },
    BASE32 {
        @Override
        public byte[] decode(String input) {
            // implementation of base32 decoding
            return Base32ToNumber.decodeBase32String(input);
        }
    };

    public abstract byte[] decode(String input);
}
