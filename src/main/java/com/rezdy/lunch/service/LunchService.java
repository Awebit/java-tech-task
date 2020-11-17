package com.rezdy.lunch.service;

import com.rezdy.lunch.dao.RecipeDao;
import com.rezdy.lunch.service.domain.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LunchService {

    private final RecipeDao recipeDao;

    @Autowired
    public LunchService(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Transactional(readOnly = true)
    public List<Recipe> getNonExpiredRecipesOnDate(LocalDate date) {
        List<Recipe> recipes = recipeDao.findAllRecipesWithNonExpiredIngredients(date);
        sortRecipesWithPastBestBeforeLast(recipes, date);
        return recipes;
    }

    /**
     * Sorts the given list of Recipes in the following order:
     * 1. Ingredients past the Best Before date based on the given date are last.
     * 2. Recipe Title natural ordering.
     *
     * @param recipes List of Recipe's to sort
     * @param date Date used for Best Before date comparison
     */
    protected static void sortRecipesWithPastBestBeforeLast(List<Recipe> recipes, LocalDate date) {
        recipes.sort(new RecipeBestBeforeComparator(date).thenComparing(Recipe::getTitle));
    }
}
