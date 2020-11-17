package com.rezdy.lunch.service;

import com.rezdy.lunch.service.domain.Recipe;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Comparator to sort Recipes based on comparing the Best Before date of Ingredients to the given date such that
 * Recipes with Ingredients past the Best Before date will be last.
 */
public class RecipeBestBeforeComparator implements Comparator<Recipe> {

    private final LocalDate date;

    public RecipeBestBeforeComparator(LocalDate date) {
        Assert.notNull(date, "Date can not be null");
        this.date = date;
    }

    @Override
    public int compare(Recipe o1, Recipe o2) {
        return Boolean.compare(o1.isPastBestBefore(date), o2.isPastBestBefore(date));
    }
}
