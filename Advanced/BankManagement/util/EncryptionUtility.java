package Advanced.BankManagement.util;

public class EncryptionUtility {
    public static String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    encrypted.append((char) ('A' + (c - 'A' + 1) % 26));
                } else {
                    encrypted.append((char) ('a' + (c - 'a' + 1) % 26));
                }
            } else if (Character.isDigit(c)) {
                encrypted.append((char) ('0' + (c - '0' + 1) % 10));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }
}
