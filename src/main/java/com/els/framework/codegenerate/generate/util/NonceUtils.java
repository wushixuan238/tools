package com.els.framework.codegenerate.generate.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public class NonceUtils {
    private static final SimpleDateFormat DATE_FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final String[] ZERO_ARRAY = {"0", "00", "0000", "00000000"};
    private static Date dateFlag;
    private static int NONCE = 0;

    public static String random(int paramInt) {
        return RandomStringUtils.randomAlphanumeric(paramInt);
    }

    public static int random() {
        return new SecureRandom().nextInt();
    }

    public static String hexRandom() {
        return Integer.toHexString(random());
    }

    public static long randomNextLong() {
        return new SecureRandom().nextLong();
    }

    public static String hexRandomLong() {
        return Long.toHexString(randomNextLong());
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String dateFullStr() {
        Date localDate = new Date();
        return DATE_FULL_FORMAT.format(localDate);
    }

    public static long currentTime() {
        return System.currentTimeMillis();
    }

    public static String hexCurrentTime() {
        return Long.toHexString(currentTime());
    }

    public static synchronized String getDateQueue() {
        Date localDate = new Date();

        if (localDate.equals(dateFlag)) {
            NONCE += 1;
        } else {
            dateFlag = localDate;
            NONCE = 0;
        }
        return Integer.toHexString(NONCE);
    }

    public static String fillWith0(String paramString, int paramInt) {
        int i = paramInt - paramString.length();
        StringBuilder localStringBuilder = new StringBuilder();

        while (i >= 8) {
            localStringBuilder.append(ZERO_ARRAY[3]);
            i -= 8;
        }

        for (int j = 2; j >= 0; j--) {
            if ((i & 1 << j) != 0) {
                localStringBuilder.append(ZERO_ARRAY[j]);
            }
        }

        localStringBuilder.append(paramString);
        return localStringBuilder.toString();
    }

}
