package com.neu.cloudcomputing.Utility;

public class GeneralUtility {

    public static String getPrefix(String username) {
        int index = username.indexOf("@");
        return username.substring(0, index);
    }
}
