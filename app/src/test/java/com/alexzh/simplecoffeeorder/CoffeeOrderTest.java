package com.alexzh.simplecoffeeorder;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CoffeeOrderTest {
    private final static float PRICE_TEST = 5.0f;

    private CoffeeOrder mOrder;

    @Before
    public void setUp() {
        mOrder = new CoffeeOrder(PRICE_TEST);
    }

    @Test
    public void orderIsNotNull() {
        assertNotNull(mOrder);
    }

    @Test
    public void orderDecrement() {
        mOrder.decrementCoffeeCount();
        assertEquals(0, mOrder.getCoffeeCount());

        mOrder.setCoffeeCount(25);
        mOrder.decrementCoffeeCount();
        assertEquals(24, mOrder.getCoffeeCount());
    }

    @Test
    public void orderIncrement() {
        mOrder.incrementCoffeeCount();
        assertEquals(1, mOrder.getCoffeeCount());

        mOrder.setCoffeeCount(25);
        mOrder.incrementCoffeeCount();
        assertEquals(26, mOrder.getCoffeeCount());
    }

    @Test
    public void orderTotalPrice() {
        assertEquals(0.0f, mOrder.getTotalPrice());

        mOrder.setCoffeeCount(25);
        assertEquals(PRICE_TEST * 25, mOrder.getTotalPrice());
    }

    @Test
    public void orderSetCoffeeCount() {
        mOrder.setCoffeeCount(-1);
        assertEquals(0, mOrder.getCoffeeCount());

        mOrder.setCoffeeCount(25);
        assertEquals(25, mOrder.getCoffeeCount());
    }

}
