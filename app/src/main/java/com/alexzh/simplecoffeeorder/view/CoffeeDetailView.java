package com.alexzh.simplecoffeeorder.view;

import com.alexzh.simplecoffeeorder.model.Coffee;

public interface CoffeeDetailView {

    Coffee getCoffee();

    void displayCoffeeName(String coffeeName);

    void displayCoffeeDetailInformation(Coffee coffee);

}
