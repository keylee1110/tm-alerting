package com.bankmonitoring.tm_alerting.util;

public class MaskingUtil {

    // Mask account number / identifier but keep last 4-5 chars
    public static String maskAccount(String raw) {
        if (raw == null) return null;
        if (raw.length() <= 5) return "****";

        String suffix = raw.substring(raw.length() - 5);
        return "****" + suffix;
    }
}