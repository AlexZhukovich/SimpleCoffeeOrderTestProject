package com.alexzh.simplecoffeeorder.utils;

import com.alexzh.simplecoffeeorder.model.Coffee;

import java.util.TreeMap;

public class CoffeeOrderUtils {

    public static Coffee getCoffeeByPosition(TreeMap<Coffee, Integer> orderedMap, int position) {
        int interactionPosition = 0;
        for (Coffee key : orderedMap.keySet()) {
            if (interactionPosition == position) {
                return  key;
            }
            interactionPosition++;
        }
        return null;
    }

    public static int getCountByCoffee(TreeMap<Coffee, Integer> orderedMap, Coffee coffee) {
        for (Coffee key : orderedMap.keySet()) {
            if (key.equals(coffee)) {
                return  orderedMap.get(key);
            }
        }
        return 0;
    }
}
