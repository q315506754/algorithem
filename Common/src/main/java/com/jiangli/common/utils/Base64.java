//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)

package com.jiangli.common.utils;

public final class Base64 {
    static final int[] base64;
    private static final int BASELENGTH = 255;
    private static final int LOOKUPLENGTH = 63;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final int EIGHTBIT = 8;
    private static final int SIXTEENBIT = 16;
    private static final int SIXBIT = 6;
    private static final int FOURBYTE = 4;
    private static final byte PAD = 61;
    private static byte[] base64Alphabet = new byte[255];
    private static byte[] lookUpBase64Alphabet = new byte[63];

    public Base64() {
    }

    static boolean isBase64(byte octect) {
        return octect == 61 || base64Alphabet[octect] != -1;
    }

    static boolean isArrayByteBase64(byte[] arrayOctect) {
        int length = arrayOctect.length;
        if (length == 0) {
            return false;
        } else {
            for (int i = 0; i < length; ++i) {
                if (!isBase64(arrayOctect[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    public static byte[] encode(byte[] binaryData) {
        int lengthDataBits = binaryData.length * 8;
        int fewerThan24bits = lengthDataBits % 24;
        int numberTriplets = lengthDataBits / 24;
        Object encodedData = null;
        byte[] var13;
        if (fewerThan24bits != 0) {
            var13 = new byte[(numberTriplets + 1) * 4];
        } else {
            var13 = new byte[numberTriplets * 4];
        }

        boolean k = false;
        boolean l = false;
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean encodedIndex = false;
        boolean dataIndex = false;
        boolean i = false;

        byte var14;
        byte var15;
        byte var16;
        byte var17;
        int var19;
        int var20;
        int var21;
        for (var21 = 0; var21 < numberTriplets; ++var21) {
            var20 = var21 * 3;
            var16 = binaryData[var20];
            var17 = binaryData[var20 + 1];
            byte var18 = binaryData[var20 + 2];
            var15 = (byte) (var17 & 15);
            var14 = (byte) (var16 & 3);
            var19 = var21 * 4;
            var13[var19] = lookUpBase64Alphabet[var16 >> 2];
            var13[var19 + 1] = lookUpBase64Alphabet[var17 >> 4 | var14 << 4];
            var13[var19 + 2] = lookUpBase64Alphabet[var15 << 2 | var18 >> 6];
            var13[var19 + 3] = lookUpBase64Alphabet[var18 & 63];
        }

        var20 = var21 * 3;
        var19 = var21 * 4;
        if (fewerThan24bits == 8) {
            var16 = binaryData[var20];
            var14 = (byte) (var16 & 3);
            var13[var19] = lookUpBase64Alphabet[var16 >> 2];
            var13[var19 + 1] = lookUpBase64Alphabet[var14 << 4];
            var13[var19 + 2] = 61;
            var13[var19 + 3] = 61;
        } else if (fewerThan24bits == 16) {
            var16 = binaryData[var20];
            var17 = binaryData[var20 + 1];
            var15 = (byte) (var17 & 15);
            var14 = (byte) (var16 & 3);
            var13[var19] = lookUpBase64Alphabet[var16 >> 2];
            var13[var19 + 1] = lookUpBase64Alphabet[var17 >> 4 | var14 << 4];
            var13[var19 + 2] = lookUpBase64Alphabet[var15 << 2];
            var13[var19 + 3] = 61;
        }

        return var13;
    }

    public static String base64Decode(String orig) {
        char[] chars = orig.toCharArray();
        StringBuffer sb = new StringBuffer();
        boolean i = false;
        int shift = 0;
        int acc = 0;

        for (int var7 = 0; var7 < chars.length; ++var7) {
            int v = base64[chars[var7] & 255];
            if (v >= 64) {
                if (chars[var7] != 61) {
                    System.out.println("Wrong char in base64: " + chars[var7]);
                }
            } else {
                acc = acc << 6 | v;
                shift += 6;
                if (shift >= 8) {
                    shift -= 8;
                    sb.append((char) (acc >> shift & 255));
                }
            }
        }

        return sb.toString();
    }

    static {
        int i;
        for (i = 0; i < 255; ++i) {
            base64Alphabet[i] = -1;
        }

        for (i = 90; i >= 65; --i) {
            base64Alphabet[i] = (byte) (i - 65);
        }

        for (i = 122; i >= 97; --i) {
            base64Alphabet[i] = (byte) (i - 97 + 26);
        }

        for (i = 57; i >= 48; --i) {
            base64Alphabet[i] = (byte) (i - 48 + 52);
        }

        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;

        for (i = 0; i <= 25; ++i) {
            lookUpBase64Alphabet[i] = (byte) (65 + i);
        }

        i = 26;

        int j;
        for (j = 0; i <= 51; ++j) {
            lookUpBase64Alphabet[i] = (byte) (97 + j);
            ++i;
        }

        i = 52;

        for (j = 0; i <= 61; ++j) {
            lookUpBase64Alphabet[i] = (byte) (48 + j);
            ++i;
        }

        base64 = new int[]{64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 62, 64, 64, 64, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 64, 64, 64, 64, 64, 64, 64, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 64, 64, 64, 64, 64, 64, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64};
    }

    public byte[] decode(byte[] base64Data) {
        int numberQuadruple = base64Data.length / 4;
        Object decodedData = null;
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        boolean marker0 = false;
        boolean marker1 = false;
        int encodedIndex = 0;
        boolean dataIndex = false;
        byte[] var13 = new byte[numberQuadruple * 3 + 1];

        for (int i = 0; i < numberQuadruple; ++i) {
            int var20 = i * 4;
            byte var18 = base64Data[var20 + 2];
            byte var19 = base64Data[var20 + 3];
            byte var14 = base64Alphabet[base64Data[var20]];
            byte var15 = base64Alphabet[base64Data[var20 + 1]];
            byte var16;
            if (var18 != 61 && var19 != 61) {
                var16 = base64Alphabet[var18];
                byte var17 = base64Alphabet[var19];
                var13[encodedIndex] = (byte) (var14 << 2 | var15 >> 4);
                var13[encodedIndex + 1] = (byte) ((var15 & 15) << 4 | var16 >> 2 & 15);
                var13[encodedIndex + 2] = (byte) (var16 << 6 | var17);
            } else if (var18 == 61) {
                var13[encodedIndex] = (byte) (var14 << 2 | var15 >> 4);
                var13[encodedIndex + 1] = (byte) ((var15 & 15) << 4);
                var13[encodedIndex + 2] = 0;
            } else if (var19 == 61) {
                var16 = base64Alphabet[var18];
                var13[encodedIndex] = (byte) (var14 << 2 | var15 >> 4);
                var13[encodedIndex + 1] = (byte) ((var15 & 15) << 4 | var16 >> 2 & 15);
                var13[encodedIndex + 2] = (byte) (var16 << 6);
            }

            encodedIndex += 3;
        }

        return var13;
    }
}
