package com.cy.ares.common;

/**
 * @author derek.wq
 * @date 2018-06-04
 * @since v1.0.0
 */
public class ConfigJudge {

    public static boolean judgeConfigChanged(Integer oldVersion, String oldDigest, Integer curVersion,
        String curDigest) {
        return judgeVersion(oldVersion, curVersion) || judgeDigest(oldDigest, curDigest);
    }

    private static boolean judgeVersion(Integer oldVersion, Integer curVersion) {
        if (curVersion == null) {
            return false;
        }
        if (oldVersion == null) {
            return true;
        }
        return oldVersion < curVersion;
    }

    private static boolean judgeDigest(String oldDigest, String curDigest) {
        if (curDigest == null) {
            return false;
        }
        if (oldDigest == null) {
            return true;
        }
        return !oldDigest.equals(curDigest);
    }

}
