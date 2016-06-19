package com.alexzh.simplecoffeeorder.utils;

import com.alexzh.simplecoffeeorder.model.Coffee;

public class CoffeeUtils {

    public static String getIngredientsString(Coffee coffee) {
        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < coffee.getIngredients().length; i++) {
            ingredients.append(coffee.getIngredients()[i]);
            if (i != coffee.getIngredients().length - 1) {
                ingredients.append(",\n");
            }
        }
        return ingredients.toString();
    }

}
