package dev.penguinencounter.figurav5addon;

import java.util.regex.Pattern;

public class KeyframeHelpers {
    private static final Pattern regex = Pattern.compile("^\\[string \"[^\"]*?\"]:");

    public static String trimErrorMessage(String message) {
        return regex.matcher(message).replaceAll("");
    }
}
