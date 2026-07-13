package com.ticket.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class NoGenerator {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private NoGenerator() {
    }

    public static String generateOrderNo(Long userId) {
        return "T" + FORMATTER.format(LocalDateTime.now()) + userId + randomPart();
    }

    public static String generatePaymentNo() {
        return "P" + FORMATTER.format(LocalDateTime.now()) + randomPart();
    }

    private static String randomPart() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}