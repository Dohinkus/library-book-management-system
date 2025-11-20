package lbms.utils;

public class ValidationUtils {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isPositiveInt(Integer value) {
        return value != null && value > 0;
    }

    public static boolean isValidIsbn(String isbn) {
        if (isNullOrEmpty(isbn)) return false;
        String cleaned = isbn.replaceAll("-", "").trim();
        return cleaned.length() >= 10 && cleaned.length() <= 13;
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        return email.contains("@") && email.contains(".");
    }
}
