package com.cy.ares.common.utils;

import java.util.HashSet;
import java.util.Set;

public class SetUtils {

    public static <T> Set<T> copy(Set<T> set) {

        Set<T> newSet = new HashSet<>();

        set.forEach(ele -> {
            newSet.add(ele);
        });

        return newSet;
    }

    public static <T> Set<T> intersect(Set<T> set1, Set<T> set2) {
        Set<T> jjSet = new HashSet<>();
        set1.forEach(ele -> {
            if (set2.contains(ele)) {
                jjSet.add(ele);
            }
        });
        return jjSet;
    }

    public static <T> Set<T> toSet(T[] t) {
        Set<T> newSet = new HashSet<>();
        if (t == null || t.length == 0) return newSet;
        
        for (T ele : t) {
            newSet.add(ele);
        }
        return newSet;
    }

}
