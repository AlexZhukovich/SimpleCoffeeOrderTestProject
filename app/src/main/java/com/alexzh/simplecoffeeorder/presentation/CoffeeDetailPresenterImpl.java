package com.alexzh.simplecoffeeorder.presentation;

import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeDetailView;

public class CoffeeDetailPresenterImpl implements CoffeeDetailPresenter {

    CoffeeDetailView mView;

    @Override
    public void setView(CoffeeDetailView view) {
        this.mView = view;
        displayCoffeeInfo();
    }

    private void displayCoffeeInfo() {
        Coffee coffee = mView.getCoffee();
        if (coffee != null) {
            mView.displayCoffeeName(coffee.getName());
            mView.displayCoffeeDetailInformation(coffee);
        }
    }
}
