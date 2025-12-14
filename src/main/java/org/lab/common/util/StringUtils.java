package org.lab.common.util;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    @Nullable
    public static String trim(String source, int length) {
        if (source == null) {
            return null;
        }

        if (source.length() < length) {
            return source;
        }

        return source.substring(0, length);
    }

    @Nullable
    public static String extractFileExtension(String string) {
        if (string == null) {
            return null;
        }
        String[] tokens = string.split("\\.");
        return tokens.length == 1 ? null : tokens[tokens.length - 1];
    }
}
