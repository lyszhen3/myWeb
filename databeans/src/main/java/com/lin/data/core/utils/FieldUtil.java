package com.lin.data.core.utils;

public class FieldUtil {
    public FieldUtil() {
    }

    public static String codeFields(String smallField) {
        if (smallField == null) {
            throw new RuntimeException("field为null");
        } else {
            StringBuilder sb = new StringBuilder();
            String bigField = smallField.toUpperCase();
            String[] bigs = bigField.split(",");


            for (String b : bigs) {
                if (sb.length() != 0) {
                    sb.append(",");
                }
                if (b.contains("_")) {
                    sb.append(b);
                    sb.append(" ");
                    sb.append(b.replace("_", ""));
                } else {
                    sb.append(b);
                }
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String hash_map = codeFields("hash_map");
        System.out.println(hash_map);
    }
}
