package org.yu55.yagga.utils;

/**
 * Custom string utility class.
 */
public class StringUtils {

    /**
     * Replaces all blank substrings with a single-space string.
     *
     * @param source the source string to be processed
     * @return the source string having all blank substrings replaced with a single space character.
     */
    public static String wrapBlankSubstrings(String source) {
        return source.replaceAll("\\s+", " ");
    }
}
