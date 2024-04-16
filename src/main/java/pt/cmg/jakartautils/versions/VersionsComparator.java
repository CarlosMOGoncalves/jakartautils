/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.versions;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Carlos Gonçalves
 */
public class VersionsComparator {

    /**
     * Compares two version strings.
     * Copied straight from the net
     * <p>
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     *
     * @param v1 a string of alpha numerals separated by decimal points.
     * @param v2 a string of alpha numerals separated by decimal points.
     * @return The result is 1 if v1 is greater than v2.
     * The result is 2 if v2 is greater than v1.
     * The result is -1 if the version format is unrecognized.
     * The result is zero if the strings are equal.
     */

    public static int compare(String v1, String v2) {
        int v1Len = StringUtils.countMatches(v1, ".");
        int v2Len = StringUtils.countMatches(v2, ".");

        if (v1Len != v2Len) {
            int count = Math.abs(v1Len - v2Len);
            if (v1Len > v2Len) {
                for (int i = 1; i <= count; i++) {
                    v2 += ".0";
                }
            } else {
                for (int i = 1; i <= count; i++) {
                    v1 += ".0";
                }
            }
        }

        if (v1.equals(v2)) {
            return 0;
        }

        String[] v1Str = StringUtils.split(v1, ".");
        String[] v2Str = StringUtils.split(v2, ".");
        for (int i = 0; i < v1Str.length; i++) {
            String str1 = "", str2 = "";
            str1 = getString(v1Str, i, str1);
            str2 = getString(v2Str, i, str2);
            v1Str[i] = "1" + str1;
            v2Str[i] = "1" + str2;

            int num1;
            int num2;
            try {
                num1 = Integer.parseInt(v1Str[i]);
                num2 = Integer.parseInt(v2Str[i]);
            } catch (NumberFormatException e) {
                return -1;
            }
            if (num1 != num2) {
                if (num1 > num2) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }
        return -1;
    }

    private static String getString(String[] stringVersion, int i, String str) {
        for (char c : stringVersion[i].toCharArray()) {
            if (Character.isLetter(c)) {
                int u = c - 'a' + 1;
                if (u < 10) {
                    str += "0" + u;
                } else {
                    str += String.valueOf(u);
                }
            } else {
                str += String.valueOf(c);
            }
        }
        return str;
    }

}
