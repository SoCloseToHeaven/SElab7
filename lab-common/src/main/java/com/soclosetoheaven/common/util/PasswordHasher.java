package com.soclosetoheaven.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    private final static char[] PEPPER = "ZdD4p<>Ef/".toCharArray();

    private final static char[] SALT = "FJk$kf43@".toCharArray();

    private final static int TIMES_HASHING = 5;

    private PasswordHasher() {
    }

    public static char[] hashMD2(char[] password) {
        try {
            char[] pepperedAndSalt = ArrayUtils.addAll(ArrayUtils.addAll(PEPPER, password), SALT);
            CharBuffer charBuffer = CharBuffer.wrap(pepperedAndSalt);
            ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] hashBytes = md.digest(byteBuffer.array());
            for (int i = 0; i < TIMES_HASHING; ++i) {
                hashBytes = md.digest(hashBytes);
            }
            return new String(hashBytes).toCharArray();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
